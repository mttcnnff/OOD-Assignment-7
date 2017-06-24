package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Class used for click listener in a composite view. Resolves mouse actions into actions
 * specified in the controller.
 */
public class ClickListener implements MouseListener {

  private Map<Integer, Consumer<MouseEvent>> mouseConsumerMap;

  /**
   * Package-private constructor which the Controller Factory uses to make a new instance of this.
   */
  ClickListener() {
    this.mouseConsumerMap = new HashMap<>();
  }

  /**
   * Convenience constructor for making a click listener from outside this package. This supplies
   * the ability to provide custom mouseConsumerMaps.
   * @param mouseConsumerMap desired map of mouse actions.
   */
  public ClickListener(Map<Integer, Consumer<MouseEvent>> mouseConsumerMap) {
    this.mouseConsumerMap = mouseConsumerMap;
  }

  /**
   * Method to set mouseConsumerMap. Package-private, intended to only be used by the controller.
   * @param consumerMap desired map of mouse actions.
   */
  void setMouseConsumerMap(Map<Integer, Consumer<MouseEvent>> consumerMap) {
    this.mouseConsumerMap = consumerMap;
  }

  /**
   * Method which is called when a mouseClicked Event is detected. It looks up the clickEvent in
   * the mouseConsumerMap and executes the action in the map.
   * @param e mouseEvent which was detected by the listener.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    if (mouseConsumerMap.containsKey(e.getButton())) {
      mouseConsumerMap.get(e.getButton()).accept(e);
    }
  }

  /**
   * Empty method for detected mousePressed events. Required for MouseListener interface. Not
   * used in this implementation.
   * @param e mouseEvent which was detected by the listener.
   */
  @Override
  public void mousePressed(MouseEvent e) {
  }

  /**
   * Empty method for detected mouseReleased events. Required for MouseListener interface. Not
   * used in this implementation.
   * @param e mouseEvent which was detected by the listener.
   */
  @Override
  public void mouseReleased(MouseEvent e) {

  }

  /**
   * Empty method for detected mouseEntered events. Required for MouseListener interface. Not
   * used in this implementation.
   * @param e mouseEvent which was detected by the listener.
   */
  @Override
  public void mouseEntered(MouseEvent e) {

  }

  /**
   * Empty method for detected mouseExited events. Required for MouseListener interface. Not
   * used in this implementation.
   * @param e mouseEvent which was detected by the listener.
   */
  @Override
  public void mouseExited(MouseEvent e) {

  }
}
