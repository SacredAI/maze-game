package controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.Test;
import model.Maze;
import model.Player;
import model.tile.Path;
import model.tile.StartPoint;

public class TerminalControllerTest {

  private final static char[][] ValidSmallMaze = {
    {'#', '#', '#', '#', '#', '#', '#'},
    {'#', 'S', '#', ' ', ' ', ' ', '#'},
    {'#', ' ', '#', '#', '#', ' ', '#'},
    {'#', ' ', '#', ' ', ' ', ' ', '#'},
    {'#', ' ', '#', ' ', '#', ' ', '#'},
    {'#', ' ', ' ', ' ', '#', 'E', '#'},
    {'#', '#', '#', '#', '#', '#', '#'}};

  public TerminalController setupController() throws Exception {
    Maze maze = new Maze(ValidSmallMaze, 7, 7);
    Player player = new Player(maze.getStartPosition());
    TerminalController controller = new TerminalController();
    controller.setupModels(maze, player);
    return controller;
  }

  @Test
  public void testQuit() throws Exception {
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream("q\nquit\n".getBytes()));

    TerminalController controller = this.setupController();

    assertTrue(controller.shouldQuit());
    controller.getScanner().nextLine();

    assertTrue(controller.shouldQuit());
    controller.getScanner().nextLine();

    System.setIn(stdin);
  }

  @Test
  public void testWin() throws Exception {
    TerminalController controller = this.setupController();
    controller.player.setPosition(controller.maze.getEndPosition());
    assertTrue(controller.hasWon());

    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream("s\ns\ns\ns\nd\nd\nw\nw\nd\nd\ns\ns\n".getBytes()));
    controller = this.setupController();

    while (true) {
      if (controller.playLoop()) {
        assertTrue(controller.hasWon());
        break;
      }
    }
    System.setIn(stdin);
  }

  @Test
  public void testTraversed() throws Exception {
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream("s\ns\n".getBytes()));

    TerminalController controller = this.setupController();
    controller.think();
    controller.think();

    StartPoint sPoint = (StartPoint) controller.maze.getLocation(1,1);
    assertTrue(sPoint.isTraversed());

    Path firstPoint = (Path) controller.maze.getLocation(1,2);
    assertTrue(firstPoint.isTraversed());

    Path playerPoint = (Path) controller.maze.getLocation(controller.getPlayerPosition());
    // Current position hasn't been move from yet thus should be false
    assertFalse(playerPoint.isTraversed());

    System.setIn(stdin);
  }

  @Test
  public void testBacktrack() throws Exception {
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream("s\ns\nw\nw\ns\ns\ns\n".getBytes()));

    TerminalController controller = this.setupController();

    for(int i = 0; i< 4; i++){
      controller.think();
    }

    StartPoint sPoint = (StartPoint) controller.maze.getLocation(1,1);
    assertTrue(sPoint.isTraversed() && !sPoint.isBacktracked());

    // Test for correctly marked backtracked paths
    Path firstPoint = (Path) controller.maze.getLocation(1,2);
    assertTrue(firstPoint.isTraversed() && firstPoint.isBacktracked());

    Path secondPoint = (Path) controller.maze.getLocation(1,3);
    assertTrue(secondPoint.isTraversed() && secondPoint.isBacktracked());

    for(int i = 0; i< 3; i++){
      controller.think();
    }

    // Test to ensure the paths have been correctly marked as no longer backtracked
    assertFalse(firstPoint.isBacktracked());
    assertFalse(secondPoint.isBacktracked());

    System.setIn(stdin);
  }
}
