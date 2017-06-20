package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public interface IVisualView extends IView {

  void addKeyListener(KeyListener l);

  void addMouseListener(MouseListener l);

}
