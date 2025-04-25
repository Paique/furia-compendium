package gg.furia.challenge.exception;

public class OpenAiException extends Exception {
    public OpenAiException(int errorCode) {
        super(switch (errorCode) {
            case 429 -> "Erro 429, estamos experienciando um alto volume de requisições. Tente novamente mais tarde.";
            case 500 -> "Erro 500, erro interno do servidor. Tente novamente mais tarde.";
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

