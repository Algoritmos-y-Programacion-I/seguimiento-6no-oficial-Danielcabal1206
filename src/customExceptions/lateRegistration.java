package customExceptions;

public class lateRegistration extends Exception {
    public lateRegistration() {
        super("No students allowed to enroll after second week.");
    }
}