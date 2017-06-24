import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import cs3500.music.controller.ClickListener;

import static org.junit.Assert.assertEquals;

public class MouseListenerTests {

  private ArrayList<MouseEvent> clickLog;
  private ClickListener clk;

  @Test
  public void testClickListener() {
    this.initClickListener();

    ArrayList<MouseEvent> directions = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      directions.add(this.makeBSMouseEvent((int)(Math.random()*100), (int)(Math.random()*100)));
    }

    for (MouseEvent me : directions) {
      this.clk.mouseClicked(me);
      assertEquals(me, this.clickLog.get(clickLog.size()-1));
    }
  }

  private MouseEvent makeBSMouseEvent(int x, int y) {
    return new MouseEvent(new Component(){}, MouseEvent.BUTTON1, 0, 0, x, y, 1, false,
            MouseEvent.BUTTON1);
  }

  private void initClickListener() {
    this.clickLog = new ArrayList<>();

    Map<Integer, Consumer<MouseEvent>> mouseConsumerMap = new HashMap<>();
    mouseConsumerMap.put(MouseEvent.BUTTON1, mouseEvent ->
            this.clickLog.add(mouseEvent));

    this.clk = new ClickListener(mouseConsumerMap);
  }


}
