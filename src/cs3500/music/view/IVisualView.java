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
   * Refresh this IVisualView to given beat.
   * @param beat given beat to refresh view to.
   */
  void refresh(Integer beat);

  /**
   * Get piano key in this view based on given xy.
   * @param x given x coord.
   * @param y given y coord.
   * @return tone number for corresponding key at given xy.
   */
  Integer getKeyAtXY(int x, int y);
}
