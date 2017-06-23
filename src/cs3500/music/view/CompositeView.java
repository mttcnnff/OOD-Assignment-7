package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.MetaEventListener;

import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.view.visualview.VisualView;

public class CompositeView implements IVisualView, IView {

  private IVisualView gui;
  private SequencerView midi;

  public CompositeView(IPlayerModelReadOnly model) {
    this.gui = new VisualView(model);
    this.midi = new SequencerView(model);
  }

  @Override
  public void refresh(Integer beat) {
    midi.refresh(beat);
    gui.refresh(midi.getBeat());
  }

  public void reloadMidi() {
    Integer beat = midi.getBeat();
    this.midi.load();
    this.midi.refresh(beat);
  }

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

  public void addSequenceListener(MetaEventListener l) {
    midi.setMetaEventListener(l);
  }

  @Override
  public void start() {
    this.gui.start();
    this.midi.load();
  }
}
