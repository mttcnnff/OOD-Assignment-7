package cs3500.music.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.music.notes.INote;
import cs3500.music.pitch.Pitch;

/**
 * Utility class for common functionality across many classes.
 */
public class Utils {

  /**
   * @param value given integer value of note.
   * @return octave given note is in.
   */
  public static Integer integerToOctave(Integer value) throws IllegalArgumentException {
    Utils.validateTone(value);
    return (value - 12) / 12;
  }

  /**
   * @param value given integer value of note.
   * @return pitch given note is.
   */
  public static Pitch integerToPitch(Integer value) {
    Utils.validateTone(value);
    return Pitch.values()[value % 12];
  }

  /**
   * @param note given note.
   * @return integer representation of note's tone.
   */
  public static Integer noteToInteger(INote note) {
    Objects.requireNonNull(note, "Null note.");
    return note.toInteger();
  }

  /**
   * @param tone given tone (from midi tone scale).
   * @return String representation of tone (i.e. A#0).
   */
  public static String toneToString(Integer tone) {
    Utils.validateTone(tone);
    return Pitch.values()[tone % 12].toString() + String.valueOf((tone - 12) / 12);
  }

  /**
   * @param notes given list of notes.
   * @return list of notes in integer form.
   */
  public static List<Integer> notesToIntegers(List<INote> notes) {
    ArrayList<Integer> tones = new ArrayList<>();
    for (INote note : notes) {
      tones.add(Utils.noteToInteger(note));
    }
    return tones;
  }

  /**
   * Validates tone given.
   *
   * @param tone given tone.
   * @throws IllegalArgumentException If invalid midi tone is passed throws an exception.
   */
  public static void validateTone(Integer tone) throws IllegalArgumentException {
    if (tone < 0 || tone > 127) {
      throw new IllegalArgumentException("Invalid value passed.");
    }
  }

}
