package exception;

public class ErrorMethod extends RuntimeException {
    String message;

    public ErrorMethod(String message) {
        this.message = message;
    }
}
