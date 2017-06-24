import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.KeyboardListener;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for testing KeyListener.
 */
public class KeyListenerTests {

  private ArrayList<Integer> keyLog;
  private KeyboardListener kbd;

  //Tests that key listener calls proper methods when commands are sent to it.
  @Test
  public void testKeyListener() {
    this.initKeyListener();

    ArrayList<Integer> directions = new ArrayList<>();
    directions.addAll(Arrays.asList(KeyEvent.VK_RIGHT,
    KeyEvent.VK_LEFT, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_HOME, KeyEvent.VK_END,
            KeyEvent.VK_SPACE));

    for (Integer ke : directions) {
      this.kbd.keyPressed(this.makeBSKeyEvent(ke));
      assertEquals(ke, keyLog.get(keyLog.size()-1));
    }
  }

  //Method to make fake key event.
  private KeyEvent makeBSKeyEvent(Integer key) {
    return new KeyEvent(new Component(){}, KeyEvent.KEY_PRESSED, 0, 0, key, 'a');
  }

  //Initializes key listener for testing.
  private void initKeyListener() {

    this.keyLog = new ArrayList<>();

    Map<Character, Runnable> keyTypes = new HashMap<Character, Runnable>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<Integer, Runnable>();

    keyPresses.put(KeyEvent.VK_RIGHT, () ->
      this.keyLog.add(KeyEvent.VK_RIGHT));
    keyPresses.put(KeyEvent.VK_LEFT, () ->
            this.keyLog.add(KeyEvent.VK_LEFT));
    keyPresses.put(KeyEvent.VK_HOME, () ->
            this.keyLog.add(KeyEvent.VK_HOME));
    keyPresses.put(KeyEvent.VK_END, () ->
            this.keyLog.add(KeyEvent.VK_END));
    keyPresses.put(KeyEvent.VK_SPACE, () ->
            this.keyLog.add(KeyEvent.VK_SPACE));

    this.kbd = new KeyboardListener(keyTypes, keyPresses, keyReleases);
  }
}
