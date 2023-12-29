package model.tile;

import nav.Position;

/**
 * Represents an interface for Maze tile operations
 */
public abstract class MazeComponent {

  /** A class Representing the current Position of the Maze Component */
  private Position position;

  /**
   * Constructs a MazeComponent thats positioned at the provided (x,y) point
   *
   * @param x - The X postition of the component
   * @param y - The Y position if the Component
   */
  public MazeComponent(int x, int y) {
    this.position = new Position(x, y);
  }

  /**
   * Helper class to retrieve the representation of a MazeComponent
   *
   * @return The Character Representation
   */
  public abstract char getRepresentation();

  /**
   * Check if the active MazeComponent is Traversable
   *
   * @return True if it's Traversable, False
   */
  public abstract boolean isTraversable();

  /**
   * Reterive the Maze Components current Position
   *
   * @return The Components Position
   */
  public Position getPosition() {
    return this.position;
  }
}
