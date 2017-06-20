package cs3500.music.controller;

import java.util.Objects;

import cs3500.music.model.IPlayerModel;

public class ControllerFactory {

  public static IController makeController(String viewType, IPlayerModel model) {
    Objects.requireNonNull(model, "Null model passed!!");
    switch (viewType) {
      case "console":
        return new BasicController(model);
      case "visual":
        return new Controller(model);
      case "midi":
        return new BasicController(model);
      case "composite":
        return new Controller(model);
      default:
        throw new IllegalArgumentException("Invalid View: " + viewType + " doesn't exist.");
    }
  }
}
