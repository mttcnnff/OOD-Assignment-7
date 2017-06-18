import org.junit.Test;

import cs3500.music.notes.INote;
import cs3500.music.notes.Note;
import cs3500.music.pitch.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the Note class' functionality.
 */
public class NoteTests {

  //Tests toString method with normal note
  @Test
  public void testToString() {
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals("a4", a4.toString());
  }

  //Tests toString method with sharp note
  @Test
  public void testToString1() {
    INote aSharp4 = new Note.Builder().pitch(Pitch.ASHARP).octave(4).build();
    assertEquals("A#4", aSharp4.toString());
  }

  //Tests note builder with invalid duration (negative)
  @Test(expected = IllegalArgumentException.class)
  public void testBuilder() {
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(4).duration(-1).build();
  }

  //Tests note builder with invalid octave (negative)
  @Test(expected = IllegalArgumentException.class)
  public void testBuilder1() {
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(-1).build();
  }

  //Tests note getDuration
  @Test
  public void testGetDuration() {
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals(0, (int) a4.getDuration());
    INote a5 = new Note.Builder().pitch(Pitch.A).octave(5).duration(5).build();
    assertEquals(5, (int) a5.getDuration());
  }

  //Tests editDuration with valid newDuration
  @Test
  public void testEditDuration() {
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals(0, (int) a4.getDuration());
    a4.editDuration(3);
    assertEquals(3, (int) a4.getDuration());
  }

  //Tests editDuration with invalid newDuration (negative)
  @Test(expected = IllegalArgumentException.class)
  public void testEditDuration1() {
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals(0, (int) a4.getDuration());
    a4.editDuration(-1);
    assertEquals(3, (int) a4.getDuration());
  }

  //Tests toInteger method
  @Test
  public void testToInteger() {
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(4).build();
    assertEquals(69, (int) a4.toInteger());

    INote a0 = new Note.Builder().pitch(Pitch.A).octave(0).build();
    assertEquals(21, (int) a0.toInteger());

    INote c8 = new Note.Builder().pitch(Pitch.C).octave(8).build();
    assertEquals(108, (int) c8.toInteger());
  }

  //Tests getInstrument method
  @Test
  public void testGetInstrument() {
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(4).instrument(5).build();
    assertEquals(5, (int) a4.getInstrument());
  }

  //Tests getVolume method
  @Test
  public void testVolume() {
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(4).instrument(5).volume(10).build();
    assertEquals(10, (int) a4.getVolume());
  }

}
