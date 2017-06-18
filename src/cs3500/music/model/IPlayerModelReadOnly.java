package cs3500.music.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cs3500.music.notes.INote;

/**
 * Interface for functionality that a PlayerModelReadOnly has. Acts as an adapter so class
 * containing an instance of IPlayerModelReadOnly cannot edit the model.
 */
public interface IPlayerModelReadOnly {

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

}
