package cs3500.music.view;

/**
 * Interface for the functionality that a View must have.
 */
public interface IView {

  /**
   * Initialize operations of the IView.
   */
  void start();

  /**
   * @return if IView is playing or not. True if playing, False if not playing.
   */
  boolean isPlaying();

}
