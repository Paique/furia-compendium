package gg.furia.challenge.exception;

public class OpenAiException extends Exception {
    public OpenAiException(String messaage, Exception exception) {
        super(messaage, exception);
    }

    public OpenAiException(String messaage) {
        super(messaage);
    }
}
