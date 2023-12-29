package exceptions;

/**
 * An exception that represents a Malformed maze file
 */
public class MazeMalformedException extends Exception {

    /**
     * Construct a MazeMalformed Exception with no Arguments
     */
    public MazeMalformedException() {
        super();
    }

    /**
     * Construct a MazeMalformed Exception with a message
     *
     * @param message A detailed error Message
     */
    public MazeMalformedException(String message) {
        super(message);
    }
}
