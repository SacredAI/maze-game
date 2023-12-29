package controller;

import java.util.Stack;
import model.Maze;
import model.Player;
import model.tile.MazeComponent;
import nav.Movement;
import nav.Position;
import view.View;
import java.util.HashMap;

/**
 * A controller that utilises a Depth First Search algorithm to solve the maze
 */
public class DFSController extends Controller {

    /** Delay between steps */
    private static final int STEP_DELAY = 10;
    /** A stack to track which cells need visiting */
    private Stack<Position> stack;
    /** Hashmap containing all Visited positions for quick lookup */
    private HashMap<Position, Boolean> visited;
    /** True when back tracking the stack to get to a visited position */
    private Boolean isBacktracking = false;
    /** A stack containing all positions to trace back if we hit a dead end */
    private Stack<Position> backTrackStack;

    /**
     * Construct a Controller that utilises a Deapth First search algorithim
     *
     * @param view - The active View
     */
    public DFSController(View view) {
        super(view);
        this.stack = new Stack<>();
        this.backTrackStack = new Stack<>();
    }

    @Override
    public void setupModels(Maze maze, Player player) {
        this.maze = maze;
        this.player = player;

        if (maze instanceof Maze) {
            this.stack.push(maze.getStartPosition());

            this.visited = new HashMap<>();
            this.visited.put(maze.getStartPosition(), true);
        }
    }

    @Override
    public boolean playLoop() {
        Boolean res = super.playLoop();
        if (res) {
            return res;
        }
        try {
            // TODO: Make this time changeable?
            Thread.sleep(STEP_DELAY);
        } catch (Exception e) {
            // pass
        }
        return false;
    }

    /**
     * Function to indicate that the user has request to quit the game
     *
     * @return - True if the program should exit, False otherwise
     */
    public boolean shouldQuit() {
        if (this.stack.isEmpty()) {
            this.view.onUnsolvable();
            return true;
        }
        return false;
    }

    public boolean shouldDraw() {
        return true;
    }

    public void think() {
        if (this.isBacktracking) {
            this.handleBacktrack();
            return;
        }
        if (!stack.isEmpty()) {
            Position currentPosition = stack.pop();

            this.visited.put(currentPosition, true);
            this.backTrackStack.push(currentPosition);

            Boolean foundUnvisited = false;
            // Check all Neighbouring Cells to see if they are visitable
            for (Movement.Direction dir : Movement.Direction.values()) {
                Position neighbourPosition = currentPosition.peekPosition(dir);
                MazeComponent neighbourComponent = this.maze.getLocation(neighbourPosition);
                if (neighbourComponent.isTraversable()) {
                    if (!this.visited.getOrDefault(neighbourPosition, false)) {
                        foundUnvisited = true;
                        this.stack.push(neighbourPosition);
                    }
                }
            }
            if (!foundUnvisited) {
                this.isBacktracking = true;
                return;
            }

            Position nextPosition = this.stack.peek();
            MazeComponent nextComponent = this.maze.getLocation(nextPosition);
            if (nextComponent.isTraversable()) {
                this.managePathTraversal(currentPosition, nextComponent);
                this.player.setPosition(nextPosition);
            }
        }
    }

    /**
     * Handles the Backtracking until a neighbouring cell is unvisited
     */
    private void handleBacktrack() {
        if (!this.backTrackStack.isEmpty()) {
            Position currentPosition = this.backTrackStack.pop();
            Position nextTraversablePosition = this.stack.peek();

            if (currentPosition.isNeighbough(nextTraversablePosition)) {
                this.backTrackStack.push(currentPosition);
                this.isBacktracking = false;
                return;
            }

            Position nextPosition = this.backTrackStack.peek();
            MazeComponent nextComponent = this.maze.getLocation(nextPosition);
            this.managePathTraversal(currentPosition, nextComponent);

            this.player.setPosition(nextPosition);
        }
    }
}
