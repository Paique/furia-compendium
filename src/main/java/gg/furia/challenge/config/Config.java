package gg.furia.challenge.config;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Activity;

/**
 * Config is a class that represents the configuration for the application.
 * It contains all the necessary configurations.
 * Instanciated by the YamlConfig class.
 * @see YamlUtil
 */
@Getter
@Setter
public class Config {
    private DiscordConfig discord;
    private TelegramConfig telegram;
    private OpenAIConfig openai;
    private ChatbotTextConfig chatbotText;

    @Getter
    @Setter
    public static class DiscordConfig {
        private String token;
        private StatusConfig status;

        @Getter
        @Setter
        public static class StatusConfig {
            private String text;
            private Activity.ActivityType activity;
        }
    }

    @Getter
    @Setter
    public static class TelegramConfig {
        private String token;
        private String hash;
    }

    @Getter
    @Setter
    public static class OpenAIConfig {
        private String token;
        private String model;
        private String systemMessage;
        private int maxCharacters;
    }

    @Getter
    @Setter
    public static class ChatbotTextConfig {
        private String tooManyCharacters;
        private String tooManyRequests;
        private String genericError;
    }
}

