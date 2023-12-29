package model;

import nav.Movement;
import nav.Position;

/**
 * A class Representing the Player
 */
public class Player {

  /** A class Representing the current Position of the Maze Component */
  private Position position;

  /**
   * Construct a Player at the given starting position
   *
   * @param x - The Starting x Position
   * @param y - The Starting y Position
   */
  public Player(int x, int y) {
    this.position = new Position(x, y);
  }

  /**
   * Construct a Player at the given starting position
   *
   * @param position - The position
   */
  public Player(Position position) {
    this.position = position;
  }

  /**
   * Retrieve the Players Current Position
   *
   * @return The Position
   */
  public Position getPosition() {
    return position;
  }

  /**
   * Set the Players Current Position
   *
   * @param position - The Position to set
   */
  public void setPosition(Position position) {
    this.position = position;
  }

  /**
   * Set the Players Current Position
   *
   * @param x - The X position to set
   * @param y - The Y position to set
   */
  public void setPosition(int x, int y) {
    this.position.setX(x);
    this.position.setY(y);
  }

  /**
   * Moves the Player in the specified direction
   *
   * @param dir - The direction to move the player in
   */
  public void movePlayer(Movement.Direction dir) {
    this.position.movePosition(dir);
  }
}
