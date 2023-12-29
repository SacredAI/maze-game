package view;

import model.Maze;
import model.Player;

/** Represents an interface for Displaying a Maze  */
public interface View {

  /**
   * Should the game exit on win
   * @return - True if the game should exit, False otherwise
   */
  public Boolean quitOnWin();

  /**
   * Draws the provided maze.
   * @param maze - The maze to draw
   * @param ply - The player to draw
   */
  public void draw(Maze maze, Player ply);

  /**
   * Called when the controller recieves an Invalid command
   */
  public void onInvalidCommand();

  /**
   * Called when the maze has been solved
   */
  public void onSolve();

  /**
   * Called right before the controller exits the program.
   * <p><b>Note:</b> This is called no matter the exit condition, win, quit, etc</p>
   */
  public void onGameOver();

  /**
   * Called when the maze is determined to be unsolvable
   */
  public void onUnsolvable();

  /**
   * Reset the View panel so a New game can be started
   */
  public void reset();

  /**
   * Helper function to show a custom alert
   * @param msg - The Alert message
   */
  public void showAlert(String msg);
}
