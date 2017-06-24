package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClickListener implements MouseListener {

  private Map<Integer, Consumer<MouseEvent>> mouseConsumerMap;

  void setMouseConsumerMap(Map<Integer, Consumer<MouseEvent>> consumerMap) {
    this.mouseConsumerMap = consumerMap;
  }

  ClickListener() {
    this.mouseConsumerMap = new HashMap<>();
  }

  public ClickListener(Map<Integer, Consumer<MouseEvent>> mouseConsumerMap) {
    this.mouseConsumerMap = mouseConsumerMap;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (mouseConsumerMap.containsKey(e.getButton())) {
      mouseConsumerMap.get(e.getButton()).accept(e);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
