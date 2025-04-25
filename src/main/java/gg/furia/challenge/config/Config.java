package gg.furia.challenge.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Config is a class that represents the configuration for the application.
 * It contains all the necessary configurations.
 * Instanciated by the YamlConfig class.
 * @see YamlUtil
 */
@Getter
@Setter
public class Config {
    private ChatBotConfig discord;
    private ChatBotConfig telegram;
    private ChatBotConfig whatsapp;
    private OpenAIConfig openai;
    private ChatbotTextConfig chatbotText;

    @Getter
    @Setter
    public static class ChatBotConfig {
        private boolean enabled;
        private String token;
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

