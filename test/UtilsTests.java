import org.junit.Test;

import cs3500.music.notes.Note;
import cs3500.music.pitch.Pitch;
import cs3500.music.util.Utils;

import static org.junit.Assert.assertEquals;


/**
 * Class used to test Utils functionality.
 */
public class UtilsTests {

  //Tests integer to octave for several values
  @Test
  public void integerToOctaveTests() {
    //lowest octave
    assertEquals(0, (int) Utils.integerToOctave(21));
    //highest octave
    assertEquals(8, (int) Utils.integerToOctave(108));
  }

  //Tests integer to Pitch
  @Test
  public void integerToPitchTests() {
    //lowest pitch
    assertEquals(Pitch.A, Utils.integerToPitch(21));
    //highest pitch
    assertEquals(Pitch.C, Utils.integerToPitch(108));
  }

  //Tests note to integer
  @Test
  public void noteToIntegerTests() {
    assertEquals(72, (int) Utils.noteToInteger(new Note.Builder().pitch(Pitch.C).octave(5)
            .build()));

    //lowest note
    assertEquals(21, (int) Utils.noteToInteger(new Note.Builder().pitch(Pitch.A).octave(0)
            .build()));

    //highest note
    assertEquals(108, (int) Utils.noteToInteger(new Note.Builder().pitch(Pitch.C).octave(8)
            .build()));
  }

  //Tests tone to string
  @Test
  public void toneToStringTest() {
    assertEquals("A0", Utils.toneToString(21));
    assertEquals("C8", Utils.toneToString(108));
    assertEquals("A4", Utils.toneToString(69));
  }

  //Validate tone test with invalid tone (negative)
  @Test(expected = IllegalArgumentException.class)
  public void validateTone() {
    Utils.validateTone(-1);
  }

  //Validate tone test with invalid tone (too large)
  @Test(expected = IllegalArgumentException.class)
  public void validateTone1() {
    Utils.validateTone(128);
  }

}
