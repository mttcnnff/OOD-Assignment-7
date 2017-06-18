package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IPlayerModel;
import cs3500.music.view.IView;
import cs3500.music.view.visualview.VisualView;

/**
 * Basic controller class used for moving current beat up and down only.
 */
public class Controller {
  private IPlayerModel model;
  private IView view;
  private Integer currentBeat;

  /**
   * Constructor for controller.
   *
   * @param m model this controller is for.
   */
  public Controller(IPlayerModel m) {
    model = m;
  }

  /**
   * Sets the current view of this controller.
   *
   * @param v view for this controller.
   */
  public void setView(IView v) {
    view = v;
    currentBeat = 0;
    configureKeyBoardListener();
  }

  /**
   * Private method to increment current beat up by one and refresh the view.
   */
  private void moveRight() {
    if (currentBeat < this.model.getLength() - 1) {
      currentBeat++;
    }
    view.refresh(currentBeat);
  }

  /**
   * Private method to increment current beat down by one and refresh the view.
   */
  private void moveLeft() {
    if (currentBeat > 0) {
      currentBeat--;
    }
    view.refresh(currentBeat);
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
    Map<Integer, Runnable> keyPresses = new HashMap<Integer, Runnable>();
    Map<Integer, Runnable> keyReleases = new HashMap<Integer, Runnable>();

    keyPresses.put(KeyEvent.VK_RIGHT, this::moveRight);
    keyPresses.put(KeyEvent.VK_LEFT, this::moveLeft);

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    if (view instanceof VisualView) {
      ((VisualView) view).addKeyListener(kbd);
    }
  }

}