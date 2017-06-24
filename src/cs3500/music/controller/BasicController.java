package cs3500.music.controller;

import cs3500.music.model.IPlayerModel;
import cs3500.music.view.IView;

/**
 * This class represents a basic controller which is only able to start a view. It has no user
 * input controls available.
 */
public class BasicController implements IController {

  private IPlayerModel model;
  private IView view;

  /**
   * Constructor for controller.
   * @param m model this controller is for.
   */
  BasicController(IPlayerModel m) {
    this.model = m;
  }

  /**
   * Sets the current view of this controller.
   * @param v view for this controller.
   */
  @Override
  public void setView(IView v) {
    this.view = v;
  }

  /**
   * Starts the current view of the controller if it is not null.
   */
  @Override
  public void start() {
    if (this.view != null) {
      this.view.start();
    }
  }


}
