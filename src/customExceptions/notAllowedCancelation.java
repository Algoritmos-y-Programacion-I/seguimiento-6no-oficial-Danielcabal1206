package customExceptions;

public class notAllowedCancelation extends Exception {
    public notAllowedCancelation() {
        super("Cancellation not allowed. Student has more than 50% grades assigned.");
    }
}
