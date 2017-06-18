import org.junit.Test;

import cs3500.music.pitch.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing Pitch functionality.
 */
public class PitchTests {

  //Tests Pitch's toString method
  @Test
  public void testToString() {
    assertEquals("C", Pitch.C.toString());
    assertEquals("C#", Pitch.CSHARP.toString());
  }

  //Tests Pitch's getRank method
  @Test
  public void testGetRank() {
    assertEquals(0, (int) Pitch.C.getRank());
    assertEquals(11, (int) Pitch.B.getRank());
  }

}
