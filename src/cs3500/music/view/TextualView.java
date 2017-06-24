package cs3500.music.view;

import java.util.List;
import java.util.TreeMap;

import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.util.Utils;

/**
 * Class to represent textual view.
 */
public class TextualView implements IView {

  private IPlayerModelReadOnly model;

  /**
   * Constructor for textual view.
   *
   * @param model given model this view will represent.
   */
  public TextualView(IPlayerModelReadOnly model) {
    this.model = model;
  }

  @Override
  public void start() {
    System.out.println(this.getText());
  }

  @Override
  public boolean isPlaying() {
    return false;
  }

  /**
   * @return song contained in this model to console according to the specs found in Assignment 5.
   */
  public String getText() {
    StringBuilder result = new StringBuilder();
    Integer songLength = this.model.getLength();
    TreeMap<Integer, Integer> toneRange = this.model.getToneRange();

    int firstColPad = String.valueOf(songLength).length();

    if (songLength <= 1 && this.model.getStartNotes(0).isEmpty()) {
      result.append("");
      return result.toString();
    }

    //populate first row
    result.append(padNumber(" ", firstColPad));
    for (Integer tone : toneRange.keySet()) {
      result.append(padNoteName(Utils.toneToString(tone)));
    }
    result.append("\n");

    for (Integer i = 0; i < songLength; i++) {
      result.append(padNumber(i.toString(), firstColPad));
      List<Integer> beatStartTones = Utils.notesToIntegers(this.model.getStartNotes(i));
      List<Integer> beatPlayingNotes = Utils.notesToIntegers(this.model.getPlayingNotes(i));
      for (Integer tone : toneRange.keySet()) {
        if (beatStartTones.contains(tone)) {
          result.append(padNote("X"));
        } else if (beatPlayingNotes.contains(tone)) {
          result.append(padNote("|"));
        } else {
          result.append(padNote(" "));
        }
      }
      result.append("\n");
    }

    result.deleteCharAt(result.length() - 1);
    return result.toString();
  }

  /**
   * Pads given number for printing.
   *
   * @param num     given number to pad.
   * @param padding padding to apply.
   * @return number with specified padding.
   */
  private String padNumber(String num, Integer padding) {
    return String.format("%1$" + padding + "s", num);
  }

  /**
   * Pads given note name with 4 spaces for heading row in textual view.
   *
   * @param note given note name in String form to pad.
   * @return note name with proper padding.
   */
  private String padNoteName(String note) {
    return String.format("%1$" + 4 + "s ", note);
  }

  /**
   * Pads given note representation for textual view ("|", "X", or " ") so it has 5 spaces for
   * printing.
   *
   * @param tone character to pad.
   * @return tone padded with 5 spaces.
   */
  private String padNote(String tone) {
    return String.format("  %s  ", tone);
  }

}
