package customExceptions;

public class alreadyActive extends Exception {
    public alreadyActive() {
        super("This student has already been enrolled");
    }
}
