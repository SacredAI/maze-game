package exceptions;

/**
 * An exception that represents mismatched maze size
 */
public class MazeSizeMissmatchException extends Exception {

    /**
     * Construct a MazeSizeMismatch Exception with no Arguments
     */
    public MazeSizeMissmatchException() {
        super();
    }

    /**
     * Construct a MazeSizeMismatch Exception with a message
     *
     * @param message A detailed error Message
     */
    public MazeSizeMissmatchException(String message) {
        super(message);
    }

}
