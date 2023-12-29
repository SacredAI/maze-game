package view;

import model.Maze;
import model.Player;
import model.tile.EndPoint;
import model.tile.MazeComponent;
import model.tile.Path;
import model.tile.StartPoint;
import model.tile.Wall;
import nav.Position;

/**
 * A View that utilises the Terminal to display the maze
 */
public class TextView implements View {

  /** Unsolvable maze alert text */
  private static final String UNSOLVABLE_MAZE = "Unsolvable Maze!";
  /** Game over alert text */
  private static final String GAME_OVER = "Game Over!!!";
  /** Maze Solved alert text */
  private static final String MAZE_SOLVED = "Maze Solved!";
  /** Invalid command alert text */
  private static final String INVALID_COMMAND = "Invalid Command!";
  /** ANSI character to reset styling */
  public static final String ANSI_RESET = "\u001B[0m";
  /** ANSI character to colour text Black */
  public static final String ANSI_BLACK = "\u001B[30m";
  /** ANSI character to colour text Red */
  public static final String ANSI_RED = "\u001B[31m";
  /** ANSI character to colour text Green */
  public static final String ANSI_GREEN = "\u001B[32m";
  /** ANSI character to colour text Yellow */
  public static final String ANSI_YELLOW = "\u001B[33m";
  /** ANSI character to colour text Blue */
  public static final String ANSI_BLUE = "\u001B[34m";
  /** ANSI character to colour text purple */
  public static final String ANSI_PURPLE = "\u001B[35m";
  /** ANSI character to hide text */
  public static final String ANSI_HIDDEN = "\u001B[8m";
  /** UTF character that represents a square */
  public static final String UTF_SQUARE = "\u25A0";
  /** Character to clear the console output */
  public static final String CLEAR_CONSOLE = "\033[H\033[2J";

  /** Instantiate a new Text view */
  public TextView(){
    //Do Nothing
  }

  /**
   * helper method to create a coloured square
   * @param colour - The ANSI colour to use
   * @return The coloured string
   */
  public static final String ANSI_SQUARE(String colour) {
    return colour + UTF_SQUARE + " " + ANSI_RESET;
  }

  public void draw(Maze maze, Player ply){
    Position playerPos = ply.getPosition();
    StringBuilder drawnMaze = new StringBuilder();
    for(int y = 0; y < maze.getHeight(); y++){
      for(int x = 0; x < maze.getWidth(); x++){
        if(playerPos.getY() == y && playerPos.getX() == x){
          drawnMaze.append(ANSI_SQUARE(ANSI_PURPLE));
        }else{
          MazeComponent mazeComponent = maze.getLocation(x, y);
          switch (mazeComponent.getRepresentation()) {
            case StartPoint.REPRESENTATION -> drawnMaze.append(ANSI_SQUARE(ANSI_GREEN));
            case EndPoint.REPRESENTATION -> drawnMaze.append(ANSI_SQUARE(ANSI_RED));
            case Wall.REPRESENTATION -> drawnMaze.append(ANSI_SQUARE(ANSI_BLACK));
            case Path.REPRESENTATION, Path.BACKTRACKED_REPRESENTATION -> {
              Path path = (Path) mazeComponent;
              if(!path.isTraversed()){
                drawnMaze.append(ANSI_SQUARE(ANSI_HIDDEN));
                break;
              }
              if(path.isBacktracked()){
                drawnMaze.append(ANSI_SQUARE(ANSI_YELLOW));
              }else{
                drawnMaze.append(ANSI_SQUARE(ANSI_BLUE));
              }
            }
          }
        }
      }
      drawnMaze.append("\n\r");
    }
    System.out.print(drawnMaze.toString());
  }

  public void reset(){
    System.out.println(CLEAR_CONSOLE);
  }

  public void onInvalidCommand(){
    System.err.println(INVALID_COMMAND);
  }

  public void onSolve(){
    System.out.println(MAZE_SOLVED);
  }

  public void onGameOver(){
    System.out.println(GAME_OVER);
  }

  public void onUnsolvable(){
    System.out.println(UNSOLVABLE_MAZE);
  }

  public void showAlert(String msg){
    System.err.println(msg);
  }

  public Boolean quitOnWin(){
    return true;
  }
}
