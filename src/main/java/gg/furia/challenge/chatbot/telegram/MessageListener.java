package gg.furia.challenge.chatbot.telegram;

import gg.furia.challenge.Main;
import gg.furia.challenge.chatbot.discord.util.MessageUtil;
import gg.furia.challenge.config.YamlUtil;
import gg.furia.challenge.exception.OpenAiException;
import gg.furia.challenge.openai.OpenAi;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Slf4j
public class MessageListener implements LongPollingSingleThreadUpdateConsumer {

    private static final long MAX_AGE_SECONDS = 10;
    private static final String DEFAULT_USERNAME = "Furioso";
    private final TelegramClient telegramClient;
    private final OpenAi openAiService;

    public MessageListener() {
        this(new OkHttpTelegramClient(YamlUtil.getConfig().getTelegram().getToken()), Main.getOpenAi());
    }

    @Override
    public void consume(Update update) {
        if (!isValidMessage(update)) return;

        String chatId = update.getMessage().getChatId().toString();
        String userText = update.getMessage().getText();
        String username = extractUsername(update);

        sendTypingAction(chatId);


        if (Objects.equals(userText, "/start")) {
            sendMessage(chatId, YamlUtil.getConfig().getChatbotText().getStartMessage());
            return;
        }

        try {
            String messageToSend = openAiService.generateText(List.of(new MessageUtil.RoleMessage(userText, false)), username);
            sendMessage(chatId, messageToSend);
        } catch (OpenAiException e) {
            sendMessage(chatId, YamlUtil.getConfig().getChatbotText().getGenericError());
            log.error(e.getMessage());
        }
    }

    private boolean isValidMessage(Update update) {
        Message message = update.getMessage();
        return message != null
                && message.hasText()
                && !isMessageTooOld(message);
    }

    private boolean isMessageTooOld(Message message) {
        long currentEpochSeconds = System.currentTimeMillis() / 1000;
        return message.getDate() < (currentEpochSeconds - MAX_AGE_SECONDS);
    }

    private String extractUsername(Update update) {
        String firstName = update.getMessage().getChat().getFirstName();
        return firstName != null ? firstName : DEFAULT_USERNAME;
    }

    @SneakyThrows(TelegramApiException.class)
    private void sendTypingAction(String chatId) {
        SendChatAction action = SendChatAction.builder()
                .chatId(chatId)
                .action("typing")
                .build();
        telegramClient.execute(action);
    }

    @SneakyThrows(TelegramApiException.class)
    private void sendMessage(String chatId, String text) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        telegramClient.execute(msg);
    }
}
