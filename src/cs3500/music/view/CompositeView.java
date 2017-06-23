package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.MetaEventListener;

import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.view.visualview.VisualView;

public class CompositeView implements IVisualView, IAudibleView {

  private IPlayerModelReadOnly model;
  private IVisualView gui;
  private SequencerView midi;

  public CompositeView(IPlayerModelReadOnly model) {
    this.model = model;
    this.gui = new VisualView(model);
    this.midi = new SequencerView(model);
  }

  @Override
  public void refresh(Integer beat) {
    if (beat < 0 || beat > this.model.getLength()) {
      throw new IllegalArgumentException("Bad beat.");
    }
    midi.refresh(beat);
    gui.refresh(midi.getBeat());
  }

  @Override
  public void load() {
    if (!this.midi.isPlaying()) {
      Integer beat = midi.getBeat();
      this.midi.load();
      this.midi.refresh(beat);
    } else {
      throw new IllegalStateException("Can't load while playing");
    }
  }

  @Override
  public void togglePlay() {
    midi.togglePlay();
  }

  public int getBeat() {
    return this.midi.getBeat();
  }

  public boolean isPlaying() {
    return this.midi.isPlaying();
  }

  public Integer getKeyAtXY(int x, int y) {
    return this.gui.getKeyAtXY(x, y);
  }

  @Override
  public void addKeyListener(KeyListener l) {
    gui.addKeyListener(l);
  }

  @Override
  public void addMouseListener(MouseListener l) {
    gui.addMouseListener(l);
  }

  @Override
  public void setMetaEventListener(MetaEventListener l) {
    midi.setMetaEventListener(l);
  }

  @Override
  public void start() {
    this.gui.start();
    this.midi.load();
  }
}
