package exceptions;

/**
 * An Exception that occurs when an invalid Direction identifier is used
 */
public class InvalidDirectionException extends Exception {

    /**
     * Construct a InvalidDirection Exception with no Arguments
     */
    public InvalidDirectionException() {
        super();
    }

    /**
     * Construct a InvalidDirection Exception with a message
     *
     * @param message A detailed error Message
     */
    public InvalidDirectionException(String message) {
        super(message);
    }

}
