package controller;

import model.Maze;
import model.Player;
import model.tile.MazeComponent;
import model.tile.Path;
import nav.Position;
import view.GUIView;
import view.View;
import listeners.FileChooser;

/**
 * Represents an interface for GUI operations This interface provides a contract
 * for the
 * game to allow user interaction
 */
public abstract class Controller {

    /** The Player */
    protected Player player;
    /** The Current Maze being traversed */
    protected Maze maze;
    /** The Graphics Controller */
    protected View view;
    /**
     * Flag to indicate if this class is running inside a test
     * Inside a test no view will be present
     */
    protected Boolean testing = false;

    /**
     * Initialises a new Controller
     *
     * @param view - The active View
     */
    public Controller(View view) {
        this.view = view;

        if (view instanceof GUIView) {
            GUIView guiView = (GUIView) view;
            Controller controller = this;
            guiView.addMenuItemListener(new FileChooser(controller, guiView));
        }
    }

    /**
     * Initialises a new Controller for testing
     */
    public Controller() {
        testing = true;
    }

    /**
     * Clears the active models
     */
    public void clearModels() {
        this.maze = null;
        this.player = null;
    }

    /**
     * Loads in the maze and Player for a game
     *
     * @param maze   - The Maze
     * @param player - The Player
     */
    public void setupModels(Maze maze, Player player) {
        this.maze = maze;
        this.player = player;
    }

    /**
     * Main game logic loop
     *
     * @return True if the game has ended, false Otherwise
     */
    protected boolean playLoop() {
        if (this.shouldDraw()) {
            this.view.draw(this.maze, this.player);
        }

        if (this.shouldQuit()) {
            return true;
        }

        if (this.maze != null) {
            this.think();

            if (this.hasWon()) {
                if (!testing) {
                    this.view.onSolve();
                }
                return true;
            }

        }

        return false;
    }

    /**
     * Main game loop
     */
    public void play() {
        while (true) {
            if (this.playLoop()) {
                break;
            }
        }
        if (!testing) {
            this.view.onGameOver();
        }
    }

    /**
     * Function to indicate that the user has request to quit the game
     *
     * @return - True if the program should exit, False otherwise
     */
    public abstract boolean shouldQuit();

    /**
     * Think Function call every loop. All controller side-effects (player movement,
     * etc) occur here
     */
    public abstract void think();

    /**
     * Helper function to indicate whether the view should draw
     *
     * @return True if the view should draw, False otherwise
     */
    public abstract boolean shouldDraw();

    /**
     * Manages the state transition for Path components.
     *
     * @param lastPlayerPosition - The Players last Position
     * @param nextComponent      - The MazeComponent at the players next (new
     *                           current) Position
     */
    protected void managePathTraversal(Position lastPlayerPosition, MazeComponent nextComponent) {
        MazeComponent currentComponent = this.maze.getLocation(lastPlayerPosition);

        if (!(currentComponent instanceof Path)) {
            // Player is somehow on a Non path component fail
            return;
        }

        Path curPath = (Path) currentComponent;
        curPath.setTraversed(true);
        if (nextComponent instanceof Path) {
            Path nextPath = (Path) nextComponent;
            if (nextPath.isTraversed()) {
                if (!(nextPath.isBacktracked())) {
                    // We're moving onto a path we've traversed that is not backtracked
                    // i.e. We are now backtracking
                    curPath.setBacktracked(true);
                } else {
                    curPath.setBacktracked(false);
                }
            } else {
                curPath.setBacktracked(false);
            }
        }
    }

    /**
     * Retrieve the Players Position
     *
     * @return The Players Position
     */
    public Position getPlayerPosition() {
        return this.player.getPosition();
    }

    /**
     * Checks to see if the maze has been solved
     *
     * @return - True if solved, false Otherwise
     */
    public boolean hasWon() {
        return this.maze.getEndPosition().equals(this.getPlayerPosition());
    }
}
