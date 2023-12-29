package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import model.tile.EndPoint;
import model.tile.Path;
import model.tile.StartPoint;
import model.tile.Wall;

/**
 * A Class Responsible for Parsing and Validating a Maze file
 */
public class FileLoader implements io.FileInterface {

  /** A Scanner class pointing to the Maze File being processed */
  private Scanner scanner;
  /** The Width of the maze */
  private int width = -1;
  /** The height of the maze */
  private int height = -1;

  /** Instatiate a new MazeFileLoader */
  public FileLoader() {
    // Do Nothing
  }

  @Override
  public char[][] load(String filename) throws MazeMalformedException, MazeSizeMissmatchException,
      IllegalArgumentException, FileNotFoundException {
    if (!(filename instanceof String)) {
      throw new IllegalArgumentException("Invalid Filename passed");
    }
    char[][] maze;
    // TODO: Clean this up
    try {
      this.scanner = new Scanner(new File(filename));
      this.parseMazeSize();
      maze = this.parseMazeFile();
      this.scanner.close();
    } catch (MazeMalformedException e) {
      this.scanner.close();
      throw new MazeMalformedException(e.getMessage());
    } catch (MazeSizeMissmatchException e) {
      this.scanner.close();
      throw new MazeSizeMissmatchException(e.getMessage());
    }

    return maze;
  }

  /**
   * Parse the First line of a Maze file to determine the width and height of the Maze
   *
   * @throws MazeMalformedException - If the maze dimensions are invalid
   */
  private void parseMazeSize() throws MazeMalformedException {
    if (!this.scanner.hasNextLine()) {
      throw new MazeMalformedException("Maze has no width and height components");
    }

    String line = scanner.nextLine();
    String MazeSize[] = line.split(" ");
    if (MazeSize.length < 2) {
      throw new MazeMalformedException("Maze is missing a one or more size component");
    }
    try {
      this.height = Integer.parseInt(MazeSize[0]);
      this.width = Integer.parseInt(MazeSize[1]);
    } catch (NumberFormatException e) {
      throw new MazeMalformedException("Maze Dimenions are not Numbers");
    }
    if (this.width % 2 != 1 || this.height % 2 != 1) {
      throw new MazeMalformedException("Maze size components must be odd integers");
    }
  }

  /**
   * Parses a maze file based on the height and converts it to a 2D character array
   *
   * @return 2D character array that represents the maze
   * @throws MazeSizeMissmatchException - If the maze dimensions are mismatched
   * @throws MazeMalformedException - If Invalid Attributes are present
   */
  private char[][] parseMazeFile() throws MazeSizeMissmatchException, MazeMalformedException {
    char[][] maze = new char[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      if (!this.scanner.hasNextLine()) {
        throw new MazeSizeMissmatchException(
            "Failed to Parse Maze! Maze Ended before specified Height");
      }
      maze[i] = this.parseLine(scanner.nextLine());
    }
    return maze;
  }

  /**
   * Parses the provided line into a 1D Character Array
   *
   * @param line - The line to parse
   * @return A 1D Character array Representing the Maze Row
   * @throws MazeSizeMissmatchException - If the maze dimensions are mismatched
   * @throws MazeMalformedException - If Invalid Attributes are Present
   */
  private char[] parseLine(String line) throws MazeSizeMissmatchException, MazeMalformedException {
    if (line.length() < this.width) {
      throw new MazeSizeMissmatchException(
          "Failed to Parse Maze! Maze Ends before specified Width");
    }
    char[] mazeAttributes = new char[this.width];
    for (int j = 0; j < this.width; j++) {
      char attribute = line.charAt(j);
      if (!this.CheckForValidAttribute(attribute)) {
        throw new MazeMalformedException("Maze has Invalid Characters present");
      }
      mazeAttributes[j] = attribute;
    }
    return mazeAttributes;
  }

  /**
   * Helper function to check if a character is a valid maze attribute
   *
   * @param attribute - The Maze Attribute to check
   * @return True if the attribute is valid, False otherwise
   */
  private boolean CheckForValidAttribute(char attribute) {
    //
    return switch (attribute) {
      case Wall.REPRESENTATION, StartPoint.REPRESENTATION, EndPoint.REPRESENTATION, Path.REPRESENTATION, Path.BACKTRACKED_REPRESENTATION -> true;
      default -> false;
    };
  }

  /**
   * Retrieve the Maze Width
   *
   * @return The Mazes Width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Retrieve the Maze Height
   *
   * @return The Maze Height
   */
  public int getHeight() {
    return height;
  }
}
