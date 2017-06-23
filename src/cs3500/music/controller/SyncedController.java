package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IPlayerModel;
import cs3500.music.notes.Note;
import cs3500.music.util.Utils;
import cs3500.music.view.CompositeView;
import cs3500.music.view.IView;

public class SyncedController implements IController {
  private IPlayerModel model;
  private CompositeView view;
  private Integer currentBeat;

  private Integer mostRecentXClick;
  private Integer mostRecentYClick;

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
  private Runnable togglePlay = () -> {
    int songLength = this.model.getLength();
    int lastBeat = currentBeat;
    view.togglePlay();
    while (this.view.isPlaying() && currentBeat < songLength) {
      if (view.getBeat() != lastBeat) {
        this.currentBeat = view.getBeat();
        lastBeat = this.currentBeat;
        view.refresh(this.currentBeat);
        System.out.println("Current beat: " + this.currentBeat);
      }
    }
    if (currentBeat >= songLength) {
      view.togglePlay();
      this.currentBeat = view.getBeat();
    }
  };



  /**
   * Constructor for controller.
   *
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
    configureSequenceListener();
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

  private void moveRight() {
    if (!this.view.isPlaying()) {
      new Thread(this.moveRight).start();
    }
  }

  private void moveLeft() {
    if (!this.view.isPlaying()) {
      new Thread(this.moveLeft).start();
    }
  }

  private void jumpToStart() {
    if (!this.view.isPlaying()) {
      new Thread(this.jumpToStart).start();
    }
  }

  private void jumpToEnd() {
    if (!this.view.isPlaying()) {
      new Thread(this.jumpToEnd).start();
    }
  }

  private void leftMouseClick() {
    if (!this.view.isPlaying()) {
      Integer key = this.view.getKeyAtXY(this.mostRecentXClick, this.mostRecentYClick);
      if (key != null) {
        System.out.println(Utils.toneToString(key));
        this.model.addNote(this.currentBeat, new Note.Builder().pitch(Utils.integerToPitch(key))
                .octave(Utils.integerToOctave(key)).build());
        this.moveRight.run();
        this.view.load();
      }
    }
  }

  void setClickXY(int x, int y) {
    this.mostRecentXClick = x;
    this.mostRecentYClick = y;
  }


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

  private void configureMouseListener() {
    Map<Integer, Runnable> mouseClicks = new HashMap<>();

    mouseClicks.put(MouseEvent.BUTTON1, this::leftMouseClick);

    ClickListener clk = new ClickListener(this);
    clk.setMouseClickedMap(mouseClicks);

    view.addMouseListener(clk);

  }

  private void configureSequenceListener() {
    view.setMetaEventListener(meta -> {
      if (meta.getType() == 47) {
        System.out.println("End of sing reach but not ending.");
      }
    });
  }
}
