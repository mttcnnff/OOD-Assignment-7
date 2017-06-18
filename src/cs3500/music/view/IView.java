package cs3500.music.view;

/**
 * Interface for the functionality that a View must have.
 */
public interface IView {
  /**
   * Make the view visible. This is usually called
   * after the view is constructed. See classes that implement this interface for specifics.
   */
  void makeVisible();

  /**
   * Signal the view to refresh itself.
   */
  void refresh(Integer beat);

}
