package io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;

public class FileLoaderTest {

  private final static char[][] ValidSmallMaze = {
    {'#', '#', '#', '#', '#', '#', '#'},
    {'#', 'S', '#', ' ', ' ', ' ', '#'},
    {'#', ' ', '#', '#', '#', ' ', '#'},
    {'#', ' ', '#', ' ', ' ', ' ', '#'},
    {'#', ' ', '#', ' ', '#', ' ', '#'},
    {'#', ' ', ' ', ' ', '#', 'E', '#'},
    {'#', '#', '#', '#', '#', '#', '#'}
  };

  Path temp;

  public void generateTempFile() throws IOException {
    this.temp = Files.createTempFile("temp", ".txt");
  }

  public void writeToTempFile(List<String> lines) throws IOException {
    Files.write(temp, lines, StandardOpenOption.APPEND);
  }

  public char[][] loadTempFile() throws FileNotFoundException, MazeMalformedException,
      MazeSizeMissmatchException, IllegalArgumentException {
    FileLoader mazeLoader = new FileLoader();
    return mazeLoader.load(temp.toString());
  }

  @Test
  public void testLoad() throws Exception {
    this.generateTempFile();

    List<String> lines = new ArrayList<>();
    lines.add(String.valueOf(FileLoaderTest.ValidSmallMaze.length) + " "
        + String.valueOf(FileLoaderTest.ValidSmallMaze[0].length));
    for (char[] line : FileLoaderTest.ValidSmallMaze) {
      lines.add(new String(line));
    }

    this.writeToTempFile(lines);

    char[][] maze = {};
    maze = loadTempFile();
    assertArrayEquals(FileLoaderTest.ValidSmallMaze, maze);
  }

  @Test
  public void testEvenSize() throws Exception {
    this.generateTempFile();
    List<String> lines = new ArrayList<>();
    lines.add("8 8");
    this.writeToTempFile(lines);
    String msg = "";
    try {
      FileLoader mazeLoader = new FileLoader();
      mazeLoader.load(temp.toString());
    } catch (MazeMalformedException e) {
      msg = e.getMessage();
    }
    assertEquals(msg, "Maze size components must be odd integers");
  }

  @Test
  public void testInvalidLine() throws Exception {
    this.generateTempFile();
    List<String> lines = new ArrayList<>();
    lines.add("7 7");
    lines.add("$");
    this.writeToTempFile(lines);

    String msg = "";
    try {
      this.loadTempFile();
    } catch (MazeMalformedException e) {
      msg = e.getMessage();
    }
    assertEquals(msg, "Maze has Invalid Characters present");
  }

  @Test(expected = FileNotFoundException.class)
  public void testFileNotFound() throws Exception {
    FileLoader mazeLoader = new FileLoader();
    mazeLoader.load("test.txt");
  }

  @Test
  public void testInvalidCharactersOutsideMaze() throws Exception {
    this.generateTempFile();

    List<String> lines = new ArrayList<>();
    lines.add(String.valueOf(FileLoaderTest.ValidSmallMaze.length) + " "
        + String.valueOf(FileLoaderTest.ValidSmallMaze[0].length));
    for (char[] line : FileLoaderTest.ValidSmallMaze) {
      // Add invalid character to all maze lines
      lines.add(new String(line) + " i");
    }

    this.writeToTempFile(lines);

    char[][] maze = {};
    maze = loadTempFile();
    assertArrayEquals(FileLoaderTest.ValidSmallMaze, maze);
  }
}
