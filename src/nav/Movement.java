package nav;

import java.awt.event.KeyEvent;
import exceptions.InvalidDirectionException;

/**
 * A Class representing valid movement directions for the player
 */
public final class Movement {
  /**
   * A collation of valid Movement Directiosn
   */
  public enum Direction {
    /** Represents a Movement in the Negative Y direction */
    UP,
    /** Represents a Movement in the Positive Y direction */
    DOWN,
    /** Represents a Movement in the Negative X direction */
    LEFT,
    /** Represents a Movement in the Positive X direction */
    RIGHT,
  }

  /**
   * This class should not be instantiated and should only be access statically
   */
  private Movement() {
    throw new IllegalStateException("Cannot be instantiated");
  }

  /**
   * Parses a string and returns the associated Direction
   *
   * @param dir - a String representation of Direction
   * @return - The Direction
   * @throws InvalidDirectionException - If direction is an Invalid String
   * @throws IllegalArgumentException - If dir is not a valid Direction
   */
  public static Direction fromString(String dir)
      throws InvalidDirectionException, IllegalArgumentException {
    if (!(dir instanceof String)) {
      throw new IllegalArgumentException("Invalid action passed");
    }
    return switch (dir.toLowerCase()) {
      case "up", "w" -> Direction.UP;
      case "down", "s" -> Direction.DOWN;
      case "left", "a" -> Direction.LEFT;
      case "right", "d" -> Direction.RIGHT;
      default -> throw new InvalidDirectionException(
          String.format("%s Is an Invalid Movement Direction", dir));
    };
  }

  /**
   * Parse an int keycode and return the associated Direction
   *
   * @param keycode - a keycode associated with a Direction
   * @return - The Direction
   * @throws InvalidDirectionException - If keycode is not a valid Direction
   */
  public static Direction fromKeyCode(int keycode) throws InvalidDirectionException {
    return switch (keycode) {
      case KeyEvent.VK_W, KeyEvent.VK_UP -> Direction.UP;
      case KeyEvent.VK_A, KeyEvent.VK_LEFT -> Direction.LEFT;
      case KeyEvent.VK_S, KeyEvent.VK_DOWN -> Direction.DOWN;
      case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> Direction.RIGHT;
      default -> throw new InvalidDirectionException(
          String.format("%s Is an Invalid Movement Direction", keycode));
    };
  }
}
