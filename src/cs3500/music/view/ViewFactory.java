package cs3500.music.view;

import java.util.Objects;

import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.view.visualview.VisualView;

/**
 * Factory class for creating views at runtime.
 */
public class ViewFactory {
  /**
   * Static method to return specified type of view for given model.
   *
   * @param type  type of view to be made from three viable options: 1. "console" - makes a console
   *              view of song. 2. "visual" - makes a GUI view of the song. 3. "midi" - plays song
   *              through through midi.
   * @param model given model to be represented by this view.
   * @return view of specified type for specified model.
   * @throws IllegalArgumentException if model is null or requested type is not one of the 3
   *                                  options.
   */
  public static IView makeView(String type, IPlayerModelReadOnly model)
          throws IllegalArgumentException {
    Objects.requireNonNull(model, "Model is null!");
    switch (type) {
      case "console":
        return new TextualView(model);
      case "visual":
        return new VisualView(model);
      case "midi":
        return new SequencerView(model);
      case "composite":
        return new CompositeView(model);
      default:
        throw new IllegalArgumentException("Invalid View requested: " + type + " doesn't exist.");
    }
  }

}
