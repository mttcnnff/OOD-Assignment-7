package cs3500.music.controller;

import java.util.Objects;

import cs3500.music.model.IPlayerModel;

/**
 * Factory class used for constructing controllers based on the type of view requested.
 */
public class ControllerFactory {

  /**
   * Method which makes new requested controller for the provided model.
   * @param viewType type of view to make controller for.
   * @param model model to pass to controller.
   * @return new IController to use to control view.
   */
  public static IController makeController(String viewType, IPlayerModel model) {
    Objects.requireNonNull(model, "Null model passed!!");
    switch (viewType) {
      case "console":
        return new BasicController(model);
      case "visual":
        return new VisualController(model);
      case "midi":
        return new BasicController(model);
      case "composite":
        return new SyncedController(model);
      default:
        throw new IllegalArgumentException("Invalid View: " + viewType + " doesn't exist.");
    }
  }
}
