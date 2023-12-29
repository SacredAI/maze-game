package controller;

import java.util.Scanner;
import java.util.regex.Pattern;

import exceptions.InvalidDirectionException;
import model.tile.MazeComponent;
import nav.Movement;
import nav.Position;
import view.View;

/**
 * A Class that implements Navigation via the terminal to controls the Player
 */
public class TerminalController extends Controller {

    /** A Scanner for Manual mode */
    private Scanner scanner;
    /** Quit Command */
    private final Pattern quit_command = Pattern.compile("quit|q", Pattern.CASE_INSENSITIVE);

    /**
     * Construct a Controller with the given maze in Manual mode
     *
     * @param view - The Active View
     */
    public TerminalController(View view) {
        super(view);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initalise a new Terminal Controller for testing
     */
    public TerminalController() {
        super();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Peeks at the next value of the scanner to see if it matches the provided
     * pattern
     *
     * @param pattern - The pattern to check for
     * @return True if the next value matches the pattern, False otherwise
     */
    private boolean peekNext(Pattern pattern) {
        if (this.scanner instanceof Scanner) {
            if (this.scanner.hasNext()) {
                return this.scanner.hasNext(pattern);
            }
        }
        return false;
    }

    public boolean shouldQuit() {
        return this.peekNext(quit_command);
    }

    public boolean shouldDraw() {
        return !testing;
    }

    /**
     * Think function that handles user input and manages player state
     */
    public void think() {
        if (this.hasWon()) {
            return;
        }
        if (scanner.hasNext()) {
            String next = scanner.next();

            Movement.Direction direction;
            try {
                direction = Movement.fromString(next);
            } catch (InvalidDirectionException e) {
                this.view.onInvalidCommand();
                return;
            }
            Position playerPosition = this.getPlayerPosition();

            Position nextPosition = playerPosition.peekPosition(direction);
            MazeComponent nextComponent = this.maze.getLocation(nextPosition);

            if (nextComponent.isTraversable()) {
                this.managePathTraversal(playerPosition, nextComponent);
                this.player.setPosition(nextPosition);
            }
        }
    }

    /**
     * Get the current scanner
     *
     * <p>
     * This method is only accessable in a testing environment to allow for methods
     * that do not consume the 'nextline'
     * of the scanner to be tested.
     * </p>
     *
     * @return The current Scanner
     * @throws RuntimeException - Thrown is the scanner is accessed outside of a
     *                          testing environment
     */
    public Scanner getScanner() {
        if (!testing) {
            throw new RuntimeException("Scanner is only retrievable in a testing environment");
        }
        return this.scanner;
    }
}
