package view.guicomponents;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.Maze;
import model.Player;
import model.tile.*;
import nav.Position;

public class MazePanel extends JPanel {

  /** Width of a drawn tile */
  private static final int TILE_WIDTH = 10;
  /** Height of a drawn tile */
  private static final int TILE_HEIGHT = 10;
  private Maze maze;
  private Player ply;

  public MazePanel(Maze maze, Player ply) {
    super();
    this.maze = maze;
    this.ply = ply;
    // this.setBounds(0, 0, this.maze.getWidth()*10, this.maze.getHeight()*10);
  }

  /**
   * Function called when the component should be drawn
   */
  public void paintComponent(Graphics g) {
    super.paintComponents(g);
    Position playerPos = ply.getPosition();
    for (int y = 0; y < maze.getHeight(); y++) {
      for (int x = 0; x < maze.getWidth(); x++) {
        if (playerPos.getY() == y && playerPos.getX() == x) {
          g.setColor(Color.ORANGE);
          g.fillRect(x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        } else {
          MazeComponent mazeComponent = maze.getLocation(x, y);
          switch (mazeComponent.getRepresentation()) {
            case StartPoint.REPRESENTATION -> g.setColor(Color.GREEN);
            case EndPoint.REPRESENTATION -> g.setColor(Color.RED);
            case Wall.REPRESENTATION -> g.setColor(Color.BLACK);
            case Path.REPRESENTATION, Path.BACKTRACKED_REPRESENTATION -> {
              Path path = (Path) mazeComponent;
              if(!path.isTraversed()){
                g.setColor(Color.WHITE);
                break;
              }
              if (path.isBacktracked()) {
                g.setColor(Color.YELLOW);
              } else {
                g.setColor(Color.BLUE);
              }
            }
          }
          g.fillRect(x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        }
      }
    }
  }
}
