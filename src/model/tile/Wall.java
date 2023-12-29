package model.tile;

/** A class representing untraversable Maze Component */
public class Wall extends MazeComponent {
  /** The Character representation of the Wall */
  public final static char REPRESENTATION = '#';

  /**
   * Construct an Wall at the given position
   *
   * @param x - The Wall X Position
   * @param y - The Wall Y Position
   */
  public Wall(int x, int y) {
    super(x, y);
  }

  public boolean isTraversable() {
    return false;
  }

  public char getRepresentation() {
    return Wall.REPRESENTATION;
  }
}
