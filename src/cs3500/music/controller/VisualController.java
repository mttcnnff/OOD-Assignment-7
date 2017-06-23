package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IPlayerModel;
import cs3500.music.view.IView;
import cs3500.music.view.IVisualView;

/**
 * Basic controller class used for moving current beat up and down only.
 */
public class VisualController implements IController {
  private IPlayerModel model;
  private IVisualView view;
  private Integer currentBeat;

  private Runnable moveRight = () -> {
    if (currentBeat < this.model.getLength()) {
      currentBeat++;
    }
    view.refresh(currentBeat);
  };
  private Runnable moveLeft = () -> {
    if (currentBeat > 0) {
      currentBeat--;
    }
    view.refresh(currentBeat);
  };
  private Runnable jumpToEnd = () -> {
    currentBeat = this.model.getLength();
    view.refresh(currentBeat);
  };
  private Runnable jumpToStart = () -> {
    currentBeat = 0;
    view.refresh(currentBeat);
  };


  /**
   * Constructor for controller.
   *
   * @param m model this controller is for.
   */
  VisualController(IPlayerModel m) {
    this.model = m;
  }

  /**
   * Sets the current view of this controller.
   *
   * @param v view for this controller.
   */
  @Override
  public void setView(IView v) {
    view = (IVisualView)v;
    currentBeat = 0;
    configureKeyBoardListener();
  }

  @Override
  public void start() {
    view.start();
  }

  /**
   * Private method to increment current beat up by one and refresh the view.
   */
  private void moveRight() {
      new Thread(this.moveRight).start();
  }

  /**
   * Private method to increment current beat down by one and refresh the view.
   */
  private void moveLeft() {
      new Thread(this.moveLeft).start();
  }

  private void leftMouseClick() {
    System.out.println("Left Mouse Clicked.");
  }

  private void jumpToStart() {
      new Thread(this.jumpToStart).start();
  }

  private void jumpToEnd() {
      new Thread(this.jumpToEnd).start();
  }

  /**
   * Creates and sets a keyboard listener for the view
   * In effect it creates snippets of code as Runnable object, one for each time a key
   * is typed, pressed and released, only for those that the program needs.
   * Last we create our KeyboardListener object, set all its maps and then give it to
   * the view.
   */
  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<Character, Runnable>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<Integer, Runnable>();

    keyPresses.put(KeyEvent.VK_RIGHT, this::moveRight);
    keyPresses.put(KeyEvent.VK_LEFT, this::moveLeft);
    keyPresses.put(KeyEvent.VK_HOME, this::jumpToStart);
    keyPresses.put(KeyEvent.VK_END, this::jumpToEnd);

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
  }

}