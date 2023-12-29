package model.tile;

/** A Class Representing the Ending Point of the Maze */
public class EndPoint extends Path {

  /** The Character representation of the EndPoint */
  public final static char REPRESENTATION = 'E';

  /**
   * Construct an Endpoint at the given position
   *
   * @param x - The Endpoint X Position
   * @param y - The Endpoint Y Position
   */
  public EndPoint(int x, int y) {
    super(x, y);
  }

  public char getRepresentation() {
    return EndPoint.REPRESENTATION;
  }
}
