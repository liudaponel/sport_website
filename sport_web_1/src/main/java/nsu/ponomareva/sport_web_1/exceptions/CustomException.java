package nsu.ponomareva.sport_web_1.exceptions;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message, null);
    }
}


