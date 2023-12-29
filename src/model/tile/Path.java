package model.tile;

/** A class representing a traversable Maze Component */
public class Path extends MazeComponent {
  /** The Character representation of the Path */
  public final static char REPRESENTATION = ' ';
  /** The character representation of a Backtracked Path */
  public final static char BACKTRACKED_REPRESENTATION = '.';
  /** Has this path been backtracked through */
  private boolean backtracked = false;
  /** Boolean indicating if this path has been traversed */
  private boolean traversed = false;

  /**
   * Construct a Path at the given position
   *
   * @param x - The Path X Position
   * @param y - The Path Y Position
   */
  public Path(int x, int y) {
    super(x, y);
  }

  /**
   * Construct a Path at the given position with the specified backtracked value
   *
   * @param x - The Path X Position
   * @param y - The Path Y Position
   * @param backtracked - Whether the back is backtracked on not
   */
  public Path(int x, int y, Boolean backtracked) {
    super(x, y);
    this.backtracked = backtracked;
  }

  /**
   * Check to see if this Path was backtracked
   *
   * @return True if backtracked, False otherwise
   */
  public boolean isBacktracked() {
    return backtracked;
  }

  /**
   * Check to see if this path has been traversed
   *
   * @return True if traversed, False otherwise
   */
  public boolean isTraversed(){
    return traversed;
  }

  /**
   * Set the backtracked value
   *
   * @param backtracked - The value to set to
   */
  public void setBacktracked(Boolean backtracked) {
    this.backtracked = backtracked;
  }

  /**
   * Set the traversed value
   *
   * @param traversed - The value to set to
   */
  public void setTraversed(Boolean traversed){
    this.traversed = traversed;
  }

  /**
   * Toggle the Paths Backtracked variable
   */
  public void toggleIsBacktracked() {
    this.backtracked = !this.backtracked;
  }


  public boolean isTraversable() {
    return true;
  }

  public char getRepresentation() {
    return Path.REPRESENTATION;
  }
}
