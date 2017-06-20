package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IPlayerModel;
import cs3500.music.view.IView;

public class BasicController implements IController {
  private IPlayerModel model;
  private IView view;

  /**
   * Constructor for controller.
   *
   * @param m model this controller is for.
   */
  public BasicController(IPlayerModel m) {
    this.model = m;
  }

  /**
   * Sets the current view of this controller.
   *
   * @param v view for this controller.
   */
  public void setView(IView v) {
    this.view = v;
  }

  @Override
  public void start() {
    this.view.makeVisible();
  }


}
