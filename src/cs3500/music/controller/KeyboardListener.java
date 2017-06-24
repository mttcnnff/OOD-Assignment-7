package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a keyboard listener. It is configurable by the controller that
 * instantiates it.
 * This listener keeps three maps, one each for key typed, key pressed and key released
 * Each map stores a key mapping. A key mapping is a pair
 * (keystroke,code to be executed with that keystroke)
 * The latter part of that pair is actually a function object, i.e. an object of a class
 * that implements the Runnable interface
 * This class implements the KeyListener interface, so that its object can be used as a
 * valid keylistener for Java Swing.
 */
public class KeyboardListener implements KeyListener {
  private Map<Character, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap;
  private Map<Integer, Runnable> keyReleasedMap;

  /**
   * Package-private constructor intended only for use by Controllers in this package to make a
   * Keyboard Listener.
   */
  KeyboardListener() {
    this.keyTypedMap = new HashMap<>();
    this.keyPressedMap = new HashMap<>();
    this.keyReleasedMap = new HashMap<>();
  }

  /**
   * Public convenience constructor used for testing. Allows for setting custom action maps for
   * keyListener.
   * @param keyTypedMap map which provides functionality if a key is typed.
   * @param keyPressedMap map which provides functionality if a key is pressed.
   * @param keyReleasedMap map which provides functionality if a key is released.
   */
  public KeyboardListener(Map<Character, Runnable> keyTypedMap, Map<Integer, Runnable>
          keyPressedMap,
                   Map<Integer, Runnable> keyReleasedMap) {
    this.keyTypedMap = keyTypedMap;
    this.keyPressedMap = keyPressedMap;
    this.keyReleasedMap = keyReleasedMap;
  }

  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters
   */
  void setKeyTypedMap(Map<Character, Runnable> map) {
    keyTypedMap = map;
  }

  /**
   * Set the map for key pressed events. Key pressed events in Java Swing are integer codes
   */
  void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  /**
   * Set the map for key released events. Key released events in Java Swing are integer codes
   */
  void setKeyReleasedMap(Map<Integer, Runnable> map) {
    keyReleasedMap = map;
  }

  /**
   * This is called when the view detects that a key has been typed.
   * Find if anything has been mapped to this key character and if so, execute it
   */
  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyChar())) {
      keyTypedMap.get(e.getKeyCode()).run();
    }
  }

  /**
   * This is called when the view detects that a key has been pressed.
   * Find if anything has been mapped to this key code and if so, execute it
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  /**
   * This is called when the view detects that a key has been released.
   * Find if anything has been mapped to this key code and if so, execute it
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleasedMap.containsKey(e.getKeyCode())) {
      keyReleasedMap.get(e.getKeyCode()).run();
    }
  }
}
