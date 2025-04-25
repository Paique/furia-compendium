package gg.furia.challenge.exception;

import gg.furia.challenge.config.Config;
import gg.furia.challenge.config.YamlUtil;

// Exception only for reference
public class OpenAiException extends Exception {
    private static final Config.ChatbotTextConfig config = YamlUtil.getConfig().getChatbotText();

    public OpenAiException(int errorCode) {
        super(switch (errorCode) {
            case 429 -> config.getTooManyRequests();
            default -> "Erro desconhecido, tente novamente mais tarde.";
        });
    }

    public OpenAiException(String string, Exception e) {
        super(string, e);
    }

    public OpenAiException(String string) {
        super(string);
    }

    public OpenAiException(Exception e) {
        super("Erro desconhecido, tente novamente mais tarde.", e);
    }
}

