package gg.furia.challenge.exception;

public class InvalidResponseException extends OpenAiException {
    public InvalidResponseException(String message) {
        super(message);
    }
}
