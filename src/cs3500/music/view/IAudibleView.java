package cs3500.music.view;

import javax.sound.midi.MetaEventListener;

/**
 * Interface for the functionality that an IAudibleView must have.
 */
public interface IAudibleView extends IView {

  /**
   * Refresh the IAudibleView to the provided beat.
   * @param beat given beat to refresh IAudibleView to.
   */
  void refresh(Integer beat);

  /**
   * Load song from model into midi sequencer.
   */
  void load();

  /**
   * Play/pause for IAudibleView.
   */
  void togglePlay();

  /**
   * Gets the current beat of this IAudibleView.
   * @return integer current beat of this IAudibleView.
   */
  int getBeat();

  /**
   * Sets metaEventListener for this IAudibleView to provided listener.
   * @param l provided listener to process meta events.
   */
  void setMetaEventListener(MetaEventListener l);

}
