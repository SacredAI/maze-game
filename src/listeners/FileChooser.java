package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import controller.Controller;
import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import io.FileLoader;
import model.Maze;
import model.Player;
import view.GUIView;

/**
 * An Action listener that manages loading a maze file
 */
public class FileChooser implements ActionListener {

  private GUIView view;
  private Controller controller;

  /**
   * Initialise a new Action listener
   *
   * @param controller - The active Controller
   * @param view - The active GUI View
   */
  public FileChooser(Controller controller, GUIView view) {
    this.controller = controller;
    this.view = view;
  }

  /**
   * Function that recieves an event after subscribing as a listener to a button
   *
   * @param e The associated Event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    this.showFileChooser();
  }

  /**
   * Initialise a File chooser Dialog
   */
  public void showFileChooser() {
    JFileChooser fileChooser = new JFileChooser();
    switch (fileChooser.showOpenDialog(view)) {
      case JFileChooser.APPROVE_OPTION:
        this.loadMazeFromFile(fileChooser.getSelectedFile().getAbsolutePath());
        break;
    }
  }

  /**
   * Parses and Loads a maze from the given filename
   *
   * @param filename - The file to load
   */
  public void loadMazeFromFile(String filename) {
    Maze maze = new Maze();
    try {
      FileLoader mazeLoader = new FileLoader();
      char[][] characterMaze = mazeLoader.load(filename);
      int width = mazeLoader.getWidth();
      int height = mazeLoader.getHeight();

      maze.loadCharacterMaze(characterMaze, width, height);
      Player player = new Player(maze.getStartPosition());
      view.reset();
      controller.setupModels(maze, player);
    } catch (FileNotFoundException ex) {
      view.showAlert("File Not Found!");
    } catch (IllegalArgumentException ex) {
      view.showAlert(ex.getMessage());
    } catch (MazeMalformedException ex) {
      view.showAlert(ex.getMessage());
    } catch (MazeSizeMissmatchException ex) {
      view.showAlert(ex.getMessage());
    }
  }
}
