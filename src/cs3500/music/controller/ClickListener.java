package cs3500.music.controller;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class ClickListener implements MouseListener {

  private Map<Integer, Runnable> mouseClickedMap;
  private SyncedController controller;

  ClickListener(SyncedController controller) {
    this.controller = controller;
  }

  void setMouseClickedMap(Map<Integer, Runnable> map) {
    this.mouseClickedMap = map;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.controller.setClickXY(e.getX(), e.getY());
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
