package model.tile;

/** A Class Representing the Starting Point of the Maze */
public class StartPoint extends Path {
  /** The Character representation of the StartPoint */
  public final static char REPRESENTATION = 'S';

  /**
   * Construct an StartPoint at the given position
   *
   * @param x - The StartPoint X Position
   * @param y - The StartPoint Y Position
   */
  public StartPoint(int x, int y) {
    super(x, y);
  }

  public char getRepresentation() {
    return StartPoint.REPRESENTATION;
  }
}
