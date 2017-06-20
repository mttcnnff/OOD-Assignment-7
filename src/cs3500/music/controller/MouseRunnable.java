package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.util.Objects;

public class MouseRunnable implements Runnable {

  MouseEvent event;

  MouseRunnable(MouseEvent e) {
    Objects.requireNonNull(e, "Null mouse event!!");
    this.event = e;
  }

  @Override
  public void run() {

  }
}
