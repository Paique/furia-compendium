package gg.furia.challenge.chatbot.telegram;

import gg.furia.challenge.config.YamlUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Slf4j
public class TelegramBot {
    private final TelegramBotsLongPollingApplication application;
    private final String botToken;
    private final MessageListener messageListener;

    /**
     * Creates an instance for the Telegram bot
     */
    public static TelegramBot init() {
        return new TelegramBot(new TelegramBotsLongPollingApplication(), YamlUtil.getConfig().getTelegram().getToken(), new MessageListener());
    }

    @SneakyThrows
    public void start() {
        application.registerBot(botToken, messageListener);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                application.close();
            } catch (Exception e) {
                log.error("Error while shutting down Telegram bot: {}", e.getMessage());
            }
        }));

        Thread.currentThread().join();
    }
}
