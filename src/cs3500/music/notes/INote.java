package cs3500.music.notes;

/**
 * Interface for the functionality that a Note must have.
 */
public interface INote {

  /**
   * @return String representation of this note (i.e. A#0 or C4).
   */
  String toString();

  /**
   * @return Integer representation of the duration of this note (in beats).
   */
  Integer getDuration();

  /**
   * Edits durations of this note to the specified one.
   *
   * @param newDur desired new duration.
   */
  void editDuration(Integer newDur);

  /**
   * @return Integer representation of instrument this notes is being played on.
   */
  Integer getInstrument();

  /**
   * @return Integer value of volume for this note.
   */
  Integer getVolume();

  /**
   * @return midi Integer representation of this note.
   */
  Integer toInteger();
}
