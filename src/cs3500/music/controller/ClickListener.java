package cs3500.music.controller;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class ClickListener implements MouseListener {

  private MouseEvent mouseEvent;
  private Map<Integer, Runnable> mouseClickedMap;

  void setMouseClickedMap(Map<Integer, Runnable> map) {
    this.mouseClickedMap = map;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.mouseEvent = e;
    if (mouseClickedMap.containsKey(e.getButton())) {
      mouseClickedMap.get(e.getButton()).run();
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