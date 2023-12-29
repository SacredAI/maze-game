package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import model.Maze;
import model.Player;
import view.guicomponents.MazePanel;

/**
 * A view that utilises swing to display a GUI version of the maze
 */
public class GUIView extends JFrame implements View {

  /** Unsolvable maze alert text */
  private static final String UNSOLVABLE_MAZE = "Unsolvable Maze!";
  /** Game over alert text */
  private static final String GAME_OVER = "Game Over!!!";
  /** Maze Solved alert text */
  private static final String MAZE_SOLVED = "Maze Solved!";
  /** Invalid command alert text */
  private static final String INVALID_COMMAND = "Invalid Command!";
  /** Open maze file button text */
  private static final String OPEN_MENU = "Open...";
  /** File menu text */
  private static final String FILE_MENU = "File";
  /** GUI Title */
  private static final String GUI_TITLE = "Kingdom of Labyrinthus";
  /** The panel where the maze is drawn */
  private MazePanel mazePanel;
  /** The menu bar containing additional buttons */
  private JMenuBar menuBar;
  /** An item within a menu, used for loading a maze file */
  private JMenuItem menuItem;
  /** A menu present on the menubar */
  private JMenu menu;

  /**
   * Initialise the GUI for use
   */
  public GUIView() {
    super(GUI_TITLE);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    this.menuBar = new JMenuBar();
    this.menu = new JMenu(FILE_MENU);
    this.menuItem = new JMenuItem(OPEN_MENU);
    this.menu.add(this.menuItem);

    this.menuBar.add(menu);

    this.setJMenuBar(menuBar);

    this.setSize(400, 400);
    this.setVisible(true);
    this.setLocationRelativeTo(null);

  }

  /**
   * Setup a listener for the file loader
   *
   * @param listener - The action Listener
   */
  public void addMenuItemListener(ActionListener listener) {
    this.menuItem.addActionListener(listener);
  }


  public void draw(Maze maze, Player ply) {
    if (maze != null && this.mazePanel == null) {
      this.setSize(Math.max(maze.getWidth() * 12, 400), Math.max(maze.getHeight() * 12, 400));
      this.setLocationRelativeTo(null);
      this.mazePanel = new MazePanel(maze, ply);
      this.add(mazePanel, BorderLayout.CENTER);
      this.mazePanel.repaint();
    }
    this.revalidate();
    this.repaint();
    if (this.mazePanel != null) {
      this.mazePanel.repaint();
    }
  }

  public void reset() {
    if (this.mazePanel != null) {
      this.remove(this.mazePanel);
      this.mazePanel = null;
    }
  }

  /**
   * Helper function to close the GUI on quit
   */
  public void onQuit() {
    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }

  public void onInvalidCommand() {
    this.showAlert(INVALID_COMMAND);
  }

  public void onSolve() {
    this.showAlert(MAZE_SOLVED, JOptionPane.INFORMATION_MESSAGE);
  }

  public void onGameOver() {
    this.showAlert(GAME_OVER, JOptionPane.INFORMATION_MESSAGE);
  }

  public void onUnsolvable() {
    this.showAlert(UNSOLVABLE_MAZE);
  }

  public void showAlert(String msg) {
    JOptionPane.showMessageDialog(this, msg);
  }

  /**
   * Helper function to show a custom dialog
   *
   * @param msg - The Dialog message
   * @param title - The dialog window title
   */
  public void showAlert(String msg, String title) {
    JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Helper function to show a custom dialog
   *
   * @param msg - The Dialog message
   * @param title - The dialog window title
   * @param messageType - The Message Type
   */
  public void showAlert(String msg, String title, int messageType) {
    JOptionPane.showMessageDialog(this, msg, title, messageType);
  }

  /**
   * Helper function to show a custom alert
   *
   * @param msg - The Alert message
   * @param messageType - The Message Type
   */
  public void showAlert(String msg, int messageType) {
    JOptionPane.showMessageDialog(this, msg, "Alert", messageType);
  }

  /**
   * Attach the keyboard listner to recieve user input
   *
   * @param listener - The keyboard Listener
   */
  public void attachKeyboardListener(KeyListener listener) {
    this.addKeyListener(listener);
  }

  public Boolean quitOnWin() {
    return false;
  }
}
