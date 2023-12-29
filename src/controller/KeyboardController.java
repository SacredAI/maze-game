package controller;

import exceptions.InvalidDirectionException;
import model.tile.MazeComponent;
import nav.Movement;
import nav.Position;
import view.GUIView;
import java.awt.event.*;

/**
 * A controller that utilises keyPresses to control the player
 */
public class KeyboardController extends Controller implements KeyListener {

    private Boolean hasQuit = false;

    /**
     * Construct a Controller that listens for Keyboard input to control the player
     *
     * @param view - The Active View
     */
    public KeyboardController(GUIView view) {
        super(view);

        view.attachKeyboardListener(this);
    }

    /**
     * Function to indicate that the user has request to quit the game
     *
     * @return - True if the program should exit, False otherwise
     */
    public boolean shouldQuit() {
        if (this.hasQuit) {
            if (view instanceof GUIView) {
                GUIView guiView = (GUIView) view;
                guiView.onQuit();
            }
        }
        return this.hasQuit;
    }

    public boolean shouldDraw() {
        return true;
    }

    public void think() {
        // Do Nothing
    }

    protected void handleMove(Movement.Direction direction) {
        Position playerPosition = this.getPlayerPosition();

        Position nextPosition = playerPosition.peekPosition(direction);
        MazeComponent nextComponent = this.maze.getLocation(nextPosition);

        if (nextComponent.isTraversable()) {
            this.managePathTraversal(playerPosition, nextComponent);
            this.player.setPosition(nextPosition);
        }
    }

    public void keyTyped(KeyEvent e) {
        // Do Nothing
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q, KeyEvent.VK_ESCAPE:
                this.hasQuit = true;
                break;
            default:
                Movement.Direction direction;
                try {
                    direction = Movement.fromKeyCode(e.getKeyCode());
                } catch (InvalidDirectionException ex) {
                    this.view.onInvalidCommand();
                    return;
                }
                this.handleMove(direction);
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        // Do Nothing
    }
}
