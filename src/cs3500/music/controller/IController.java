package cs3500.music.controller;

import cs3500.music.view.IView;

/**
 * Interface for all Controllers.
 */
public interface IController {

  /**
   * Sets current view of this controller.
   * @param v view to be set for this controller.
   */
  void setView(IView v);

  /**
   * Initiates the controller to being operating.
   */
  void start();
}
