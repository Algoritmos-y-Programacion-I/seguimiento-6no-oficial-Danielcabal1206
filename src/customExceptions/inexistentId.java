package customExceptions;

public class inexistentId extends Exception {
    public inexistentId() {
        super("This student does not exist");
    }
}
