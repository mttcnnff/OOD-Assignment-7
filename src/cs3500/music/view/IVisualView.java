package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Interface for the functionality that an IVisualView must have.
 */
public interface IVisualView extends IView {

  /**
   * Sets this IVisualView key listener to provided key listener.
   * @param l given key listener.
   */
  void addKeyListener(KeyListener l);

  /**
   * Sets this IVisualView mouse listener to provided mouse listener.
   * @param l given mouse listener.
   */
  void addMouseListener(MouseListener l);

  /**
   *
   * @param beat
   */
  void refresh(Integer beat);

  Integer getKeyAtXY(int x, int y);

}
