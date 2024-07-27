package exception;

public class ProcessingExcetpion extends Exception {
    String message;

    public ProcessingExcetpion(String message) {
        this.message = message;
    }
}
