

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import controller.Controller;
import controller.DFSController;
import controller.KeyboardController;
import controller.TerminalController;
import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import io.FileLoader;
import model.Maze;
import model.Player;
import view.GUIView;
import view.TextView;
import view.View;

public class Launcher {
  /**
   * Entry Point of the Program
   *
   * @param arguments - Command Line Arguments
   */
  public static void main(String[] arguments) {
    if (arguments.length < 1) {
      System.err.println("Please Provide a filename.");
      return;
    }

    List<String> args = Arrays.stream(arguments).map(x -> x.toLowerCase()).toList();
    if (args.contains("help")) {
      System.out.println(
          "java Launcher [GUI] [dfs] <maze_file>\n\n [value] indicates optional <value> indicates required");
      return;
    }

    if (args.stream().anyMatch(x -> x.endsWith(".txt"))) {
      Launcher.initWMaze(args);
    } else {
      Launcher.initWOMaze(args);
    }
  }

  /**
   * Initialises the Game with a maze
   *
   * @param args - Command Line arguments parsed into a list
   * @pre
   */
  private static void initWMaze(List<String> args) {
    List<String> mazeFiles = args.stream().filter(x -> x.endsWith(".txt")).toList();
    if (mazeFiles.size() > 1) {
      System.err.println("Multiple Maze files specified");
      return;
    }
    Maze maze = Launcher.loadMazeFromFile(mazeFiles.get(0));
    if (maze.getStartPosition() == null) {
      System.err.println("Maze has no Start position");
      return;
    }
    if (maze.getEndPosition() == null) {
      System.err.println("Maze has no End position");
      return;
    }

    View view;
    if (args.contains("gui")) {
      view = new GUIView();
    } else {
      view = new TextView();
    }

    Player player = new Player(maze.getStartPosition());

    Controller controller;
    if (args.contains("dfs")) {
      controller = new DFSController(view);
    } else if (args.contains("gui")) {
      GUIView guiView = (GUIView) view;
      controller = new KeyboardController(guiView);
    } else {
      controller = new TerminalController(view);
    }
    controller.setupModels(maze, player);

    do {
      controller.play();
      // Clear models to prevent user input while waiting for quit or new maze file
      controller.clearModels();
    } while (!controller.shouldQuit() && !view.quitOnWin());
  }

  /**
   * Initialise the Maze without a maze loaded
   *
   * @requires A view that can load files must be specified.
   * @param args
   */
  private static void initWOMaze(List<String> args) {

    if (!args.contains("gui")) {
      Launcher.ExitWithError("GUI option required if no maze file specified");
    }
    GUIView view = new GUIView();


    Controller controller;
    if (args.contains("dfs")) {
      controller = new DFSController(view);
    } else {
      controller = new KeyboardController(view);
    }
    do {
      controller.play();
      // Clear models to prevent user input while waiting for quit or new maze file
      controller.clearModels();
    } while (!controller.shouldQuit() && !view.quitOnWin());
  }

  /**
   * Helper function to load a Maze from a file
   *
   * @param filename - The file to load
   * @return The maze component
   */
  private static Maze loadMazeFromFile(String filename) {
    Maze maze = new Maze();

    try {
      FileLoader mazeLoader = new FileLoader();
      char[][] characterMaze = mazeLoader.load(filename);
      int width = mazeLoader.getWidth();
      int height = mazeLoader.getHeight();

      maze.loadCharacterMaze(characterMaze, width, height);
    } catch (FileNotFoundException e) {
      Launcher
          .ExitWithError(String.format("File %s Not Found. Maze File must come last!", filename));
    } catch (IllegalArgumentException e) {
      Launcher.ExitWithError("An unexpected Error Occured while loading the Maze");
    } catch (MazeMalformedException e) {
      Launcher.ExitWithError("The Provided Maze file was formatted incorrectly");
    } catch (MazeSizeMissmatchException e) {
      Launcher.ExitWithError("The Size components of the Maze were invalid");
    }

    return maze;
  }

  private static void ExitWithError(String message) {
    System.err.println(message);
    System.exit(1);
  }
}
