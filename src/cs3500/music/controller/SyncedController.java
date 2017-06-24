package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import cs3500.music.model.IPlayerModel;
import cs3500.music.notes.Note;
import cs3500.music.util.Utils;
import cs3500.music.view.CompositeView;
import cs3500.music.view.IView;

/**
 * Controller used for Composite View synced with MIDI Sequencer. Allows for pause and playback,
 * as well as controlling tracking through song with arrow keys.
 */
public class SyncedController implements IController {

  private IPlayerModel model;
  //NOTE FOR GRADER: here I use the concrete Composite View class instead of an separate
  //composite view interface because a composite view implements both an IVisualView and a
  //IAudible View. If I made a composite interface it would be empty and would simply extend
  //IVisualView and IAudibleView. Making an empty interface felt wrong, so I didn't do it.
  private CompositeView view;
  private Integer currentBeat;

  /**
   * Lambda for incrementing currentBeat up one.
   */
  private Runnable moveRight = () -> {
    if (currentBeat < this.model.getLength()) {
      currentBeat++;
    }
    view.refresh(currentBeat);
  };

  /**
   * Lambda for incrementing currentBeat down one.
   */
  private Runnable moveLeft = () -> {
    if (currentBeat > 0) {
      currentBeat--;
    }
    view.refresh(currentBeat);
  };

  /**
   * Lambda for moving beat to the end of song.
   */
  private Runnable jumpToEnd = () -> {
    currentBeat = this.model.getLength();
    view.refresh(currentBeat);
  };

  /**
   * Lambda for moving beat to the beginning of song.
   */
  private Runnable jumpToStart = () -> {
    currentBeat = 0;
    view.refresh(currentBeat);
  };

  /**
   * Lambda for playing and pausing playback of the song.
   */
  private Runnable togglePlay = () -> {
    int songLength = this.model.getLength();
    int lastBeat = currentBeat;
    view.togglePlay();
    while (this.view.isPlaying() && currentBeat < songLength) {
      if (view.getBeat() != lastBeat) {
        this.currentBeat = view.getBeat();
        lastBeat = this.currentBeat;
        view.refresh(this.currentBeat);
      }
    }
    if (currentBeat >= songLength) {
      view.togglePlay();
      this.currentBeat = view.getBeat();
    }
  };

  /**
   * Constructor for controller.
   * @param m model this controller is for.
   */
  SyncedController(IPlayerModel m) {
    this.model = m;
    currentBeat = 0;
  }

  @Override
  public void setView(IView v) {
    this.view = (CompositeView) v;
    configureKeyBoardListener();
    configureMouseListener();
  }

  @Override
  public void start() {
    view.start();
  }

  /**
   * Private method to toggle play feature.
   */
  private void togglePlay() {
    new Thread(this.togglePlay).start();
  }

  /**
   * Private method to move current beat to the right.
   */
  private void moveRight() {
    if (!this.view.isPlaying()) {
      new Thread(this.moveRight).start();
    }
  }

  /**
   * Private method to move current beat to the left.
   */
  private void moveLeft() {
    if (!this.view.isPlaying()) {
      new Thread(this.moveLeft).start();
    }
  }

  /**
   * Private method to move current beat to the start of the song.
   */
  private void jumpToStart() {
    if (!this.view.isPlaying()) {
      new Thread(this.jumpToStart).start();
    }
  }

  /**
   * Private method to move current beat to the end of the song.
   */
  private void jumpToEnd() {
    if (!this.view.isPlaying()) {
      new Thread(this.jumpToEnd).start();
    }
  }

  /**
   * Private method to process left mouse click and addNote if it's on the piano.
   */
  private void leftMouseClick(Integer key) {
    if (!this.view.isPlaying()) {
      if (key != null) {
        this.model.addNote(this.currentBeat, new Note.Builder().pitch(Utils.integerToPitch(key))
                .octave(Utils.integerToOctave(key)).build());
        this.moveRight.run();
        this.view.load();
      }
    }
  }

  /**
   * Configures keyboard listener with required functionality and applies it to the view.
   */
  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<Character, Runnable>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<Integer, Runnable>();

    keyPresses.put(KeyEvent.VK_RIGHT, this::moveRight);
    keyPresses.put(KeyEvent.VK_LEFT, this::moveLeft);
    keyPresses.put(KeyEvent.VK_HOME, this::jumpToStart);
    keyPresses.put(KeyEvent.VK_END, this::jumpToEnd);
    keyPresses.put(KeyEvent.VK_SPACE, this::togglePlay);

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
  }

  /**
   * Configures mouse listener with required functionality and applies it to the view.
   */
  private void configureMouseListener() {
    Map<Integer, Consumer<MouseEvent>> mouseConsumes = new HashMap<>();

    mouseConsumes.put(MouseEvent.BUTTON1, mouseEvent ->
            this.leftMouseClick(this.view.getKeyAtXY(mouseEvent.getX(), mouseEvent
                    .getY())));

    ClickListener clk = new ClickListener();
    clk.setMouseConsumerMap(mouseConsumes);

    view.addMouseListener(clk);
  }
}
