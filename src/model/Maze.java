package model;

import exceptions.MazeMalformedException;
import model.tile.EndPoint;
import model.tile.MazeComponent;
import model.tile.Path;
import model.tile.StartPoint;
import model.tile.Wall;
import nav.Position;

/** A class representing the Maze */
public class Maze {

  /** A 2D Array of maze components representing the Maze */
  private MazeComponent[][] maze;
  /** The Maze Width */
  private int width;
  /** The Maze Height */
  private int height;
  /** The Maze Start Position */
  private Position StartPosition;
  /** The Maze End Position */
  private Position EndPosition;

  /**
   * Instantiate a new Maze
   */
  public Maze() {
    // Do Nothing
  }

  /**
   * Initialise a new Maze with the provided character maze
   *
   * @requires Width and Height must match the width and height of the 2D array
   * @param maze - The 2D character representation
   * @param width - The width of the maze
   * @param height - The height of the maze
   * @throws MazeMalformedException - Thrown if an invalid Maze character is present
   */
  public Maze(char[][] maze, int width, int height) throws MazeMalformedException {
    this.loadCharacterMaze(maze, width, height);
  }

  /**
   * Generate a Maze from its 2D character array representation
   *
   * @requires Width and Height must match the width and height of the 2D array
   *
   * @param maze - The 2D Character representation
   * @param width - The width of the maze
   * @param height - The height of the maze
   * @throws MazeMalformedException - Thrown if an invalid Maze character is present
   */
  public void loadCharacterMaze(char[][] maze, int width, int height)
      throws MazeMalformedException {
    this.height = height;
    this.width = width;
    this.maze = new MazeComponent[this.height][this.width];

    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        this.maze[y][x] = this.charToMazeComponent(maze[y][x], x, y);
      }
    }
  }

  /**
   * Convert a Character to its MazeComponent Representation
   *
   * @param attribute - The character to Convert
   * @param x - The X position of the character
   * @param y - The Y Position of the Character
   * @return A MazeComponent
   * @throws MazeMalformedException - If an Invalid character is passed
   */
  private MazeComponent charToMazeComponent(char attribute, int x, int y)
      throws MazeMalformedException {
    if (attribute == EndPoint.REPRESENTATION) {
      if (this.EndPosition != null) {
        throw new MazeMalformedException("Maze has Multiple End Positions");
      }
      this.EndPosition = new Position(x, y);
    } else if (attribute == StartPoint.REPRESENTATION) {
      if (this.EndPosition != null) {
        throw new MazeMalformedException("Maze has Multiple Start Positions");
      }
      this.StartPosition = new Position(x, y);
    }

    return switch (attribute) {
      case EndPoint.REPRESENTATION -> new EndPoint(x, y);
      case Path.REPRESENTATION -> new Path(x, y);
      case Path.BACKTRACKED_REPRESENTATION -> new Path(x, y, true);
      case StartPoint.REPRESENTATION -> new StartPoint(x, y);
      case Wall.REPRESENTATION -> new Wall(x, y);
      default -> throw new MazeMalformedException("Invalid Maze Character");
    };
  }

  /**
   * Retrieve the Maze Component at a given (x,y) position
   *
   * @param x - The X position of the component
   * @param y - The Y position of the component
   * @return The MazeComponent at the given position
   *
   * @throws IndexOutOfBoundsException - If the (x,y) position is outside the bounds of the Maze
   */
  public MazeComponent getLocation(int x, int y) throws IndexOutOfBoundsException {
    if (x < 0 || y < 0 || x > this.width || y > this.height) {
      throw new IndexOutOfBoundsException(
          String.format("Position (%d,%d) Is outside the maze", x, y));
    }
    return this.maze[y][x];
  }

  /**
   * Retrieve the Maze Component at a given (x,y) position
   *
   * @param position - The Position to retieve
   * @return The MazeComponent at the given position
   *
   * @throws IndexOutOfBoundsException - If the (x,y) position is outside the bounds of the Maze
   */
  public MazeComponent getLocation(Position position) throws IndexOutOfBoundsException {
    int x = position.getX();
    int y = position.getY();
    if (x < 0 || y < 0 || x > this.width || y > this.height) {
      throw new IndexOutOfBoundsException(
          String.format("Position (%d,%d) Is outside the maze", x, y));
    }
    return this.maze[y][x];
  }

  /**
   * Retrieve the Starting Position
   *
   * @return Position
   */
  public Position getStartPosition() {
    return StartPosition;
  }

  /**
   * Retrieve the Ending Position
   *
   * @return Position
   */
  public Position getEndPosition() {
    return EndPosition;
  }

  /**
   * Retrieve the Maze Width
   *
   * @return The Width as an Integer
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Retrieve the Maze Height
   *
   * @return The Height as an Integer
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Set a maze position to a new Maze Component
   *
   * @param position - The Position to update
   * @param component - The Component to set
   */
  public void SetMazeComponent(Position position, MazeComponent component) {
    int y = position.getY();
    int x = position.getX();
    this.maze[y][x] = component;
  }
}
