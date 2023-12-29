package nav;

/** A class representing a Position within the maze */
public class Position {

  /** The Current X position */
  private int x;
  /** The Current y position */
  private int y;
  /** Prime value used for hashing */
  private final int prime = 31;

  /**
   * Construct a Position with given X, y coordinates
   *
   * @param x - The X position
   * @param y - The Y position
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Get the Positions X position
   *
   * @return Int representing the X position
   */
  public int getX() {
    return this.x;
  }

  /**
   * Get the Positions Y position
   *
   * @return Int representing the Y position
   */
  public int getY() {
    return this.y;
  }

  /**
   * Set the X Position
   *
   * @param x - The X position
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Set the Y Position
   *
   * @param y - The Y Position
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Helper function to move this position in a specific Direction
   *
   * @param dir - The direction to move
   */
  public void movePosition(Movement.Direction dir) {
    switch (dir) {
      case DOWN -> this.moveDown();
      case UP -> this.moveUp();
      case LEFT -> this.moveLeft();
      case RIGHT -> this.moveRight();
    }
  }

  /**
   * Acquire the next position in the specified direction without modifying the current position.
   *
   * @param action - The direction to check move
   * @return The Next Position
   */
  public Position peekPosition(Movement.Direction action) {
    Position pos = new Position(this.getX(), this.getY());
    pos.movePosition(action);
    return pos;
  }

  /**
   * Move The Position 1 point in the positive Y direction
   */
  public void moveDown() {
    this.y += 1;
  }

  /**
   * Move the Position 1 point in the negative Y direction
   */
  public void moveUp() {
    this.y -= 1;
  }

  /**
   * Move the Position 1 point in the positive X direction
   */
  public void moveRight() {
    this.x += 1;
  }

  /**
   * Move the Position 1 point in the negative X direction
   */
  public void moveLeft() {
    this.x -= 1;
  }

  /**
   * A function to check if another position is a neighbour (Directly next to) The current Position
   *
   * @param other - The other Position
   * @return - True if neighbouring, false Otherwise
   */
  public boolean isNeighbough(Position other) {
    int selfX = this.getX();
    int otherX = other.getX();
    int selfY = this.getY();
    int otherY = other.getY();
    if (selfX + 1 == otherX && selfY == otherY) {
      return true;
    }
    if (selfX - 1 == otherX && selfY == otherY) {
      return true;
    }
    if (selfY + 1 == otherY && selfX == otherX) {
      return true;
    }
    if (selfY - 1 == otherY && selfX == otherX) {
      return true;
    }
    return false;
  }

  /**
   * Hash the current position based on its X and Y coordinate
   */
  @Override
  public int hashCode() {
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  /**
   * Compare another object to see if its a Position at the same X and Y coordinates
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Position other = (Position) obj;
    return (this.getX() == other.getX() && this.getY() == other.getY());
  }

}
