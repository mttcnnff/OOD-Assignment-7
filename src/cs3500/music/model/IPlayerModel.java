package cs3500.music.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cs3500.music.notes.INote;
import cs3500.music.song.ISong;

/**
 * Interface for the functionality that a PlayerModel must have.
 */
public interface IPlayerModel {

  /**
   * Adds specified note at specified beat.
   * You cannot add duplicate notes!
   * A duplicate note is defined as a note with the same tone, octave, and instrument.
   * If you try to add an already existing note, an exception with be thrown.
   *
   * @param beat beat at which you want to add this note.
   * @param note note that you would like to add.
   */
  void addNote(Integer beat, INote note);

  /**
   * Removes specified note at the specified beat.
   * You cannot remove notes that do not exist at this beat!
   * You must address the note you want to remove by the correct tone, octave, and instrument.
   * If you try and remove a note which does not exist at this beat OR remove anything from a
   * beat which contains no notes an exception will be thrown.
   *
   * @param beat beat at which you want to add this note.
   * @param note note that you would like to remove.
   */
  void removeNote(Integer beat, INote note);

  /**
   * Edits specified notes duration with given duration.
   * You must address the note you want to edit by the correct tone, octave, and instrument.
   * Duration must be > 0.
   *
   * @param beat        beat at which you are editing.
   * @param note        note in this beat that you are editing.
   * @param newDuration new duration you wish to set.
   */
  void editNoteDuration(Integer beat, INote note, Integer newDuration);

  /**
   * Combines this model's song with given song by "overlaying" it from the start.
   * Notes of the same beat, tone, octave, and instrument will be combined according to which one
   * is longer.
   *
   * @param song song you wish to combine with current one in your model.
   */
  void combine(ISong song);

  /**
   * Concatenates this model's song with the given song by appending it to the end.
   *
   * @param song song you wish to concatenate with current one in your model.
   */
  void concat(ISong song);

  /**
   * Returns a copy of the entire song in the form of a map.
   * This only returns the "start notes" of a beat.
   *
   * @return map of beats -> corresponding notes played at that beat.
   */
  Map<Integer, List<INote>> getSong();

  /**
   * Gets the current full range of tones in this piece of music.
   * This goes from the lowest notes to the highest with every note on the scale in between.
   *
   * @return map of midi tone code -> index it is in the tone range.
   */
  TreeMap<Integer, Integer> getToneRange();

  /**
   * Gets a list of notes that are played (i.e. only starting notes) at the given beat.
   *
   * @param beat desired beat to get start notes from.
   * @return list of notes that are played (i.e. only starting notes) at the given beat.
   */
  List<INote> getStartNotes(Integer beat);

  /**
   * Gets a list of notes currently playing at a particular beat (i.e. not only start notes).
   *
   * @param beat desired beat to get the playing notes from.
   * @return list of notes currently playing at a particular beat (i.e. not only start notes).
   */
  List<INote> getPlayingNotes(Integer beat);

  /**
   * @return Integer value of the current length of the song (in beats).
   */
  Integer getLength();

  /**
   * @return Integer value of the current tempo of the song (in microseconds per beat).
   */
  Integer getTempo();

  /**
   * Reads in song from specified filename and builds it in this model.
   *
   * @param filename name of file to read in from root directory.
   */
  void readInSong(String filename);
}
