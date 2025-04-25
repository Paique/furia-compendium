package gg.furia.challenge.chatbot.telegram;

import gg.furia.challenge.config.YamlUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@RequiredArgsConstructor
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
                System.out.println("Error shutting down Telegram bot: " + e.getMessage());
            }
        }));

        Thread.currentThread().join();
    }
}
