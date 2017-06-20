package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.IPlayerModel;
import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.view.visualview.VisualView;

public class CompositeView implements IVisualView, IView {

  private IVisualView gui;
  private AudibleView midi;

  public CompositeView(IPlayerModelReadOnly model) {
    this.gui = new VisualView(model);
    this.midi = new AudibleView(model);
  }

  @Override
  public void makeVisible() {
    gui.makeVisible();
  }

  @Override
  public void refresh(Integer beat) {
    midi.refresh(beat);
    gui.refresh(beat);

  }

  @Override
  public void addKeyListener(KeyListener l) {
    gui.addKeyListener(l);
  }

  @Override
  public void addMouseListener(MouseListener l) {
    gui.addMouseListener(l);
  }
}
