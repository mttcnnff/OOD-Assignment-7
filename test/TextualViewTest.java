import org.junit.Test;

import java.io.IOException;

import cs3500.music.model.IPlayerModel;
import cs3500.music.model.PlayerModel;
import cs3500.music.model.PlayerModelReadOnly;
import cs3500.music.notes.INote;
import cs3500.music.notes.Note;
import cs3500.music.pitch.Pitch;
import cs3500.music.view.TextualView;

import static org.junit.Assert.assertEquals;

public class TextualViewTest {

  //Tests print adds padding to number column
  @Test
  public void TestTextualView() {
    IPlayerModel model = new PlayerModel(4);
    TextualView consoleView = new TextualView(new PlayerModelReadOnly(model));
    INote c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(16).build();
    model.addNote(0, c4);
    String expected = "    C0 \n" +
            " 0  X  \n" +
            " 1  |  \n" +
            " 2  |  \n" +
            " 3  |  \n" +
            " 4  |  \n" +
            " 5  |  \n" +
            " 6  |  \n" +
            " 7  |  \n" +
            " 8  |  \n" +
            " 9  |  \n" +
            "10  |  \n" +
            "11  |  \n" +
            "12  |  \n" +
            "13  |  \n" +
            "14  |  \n" +
            "15  |  ";
    assertEquals(expected, consoleView.getText());
  }

  //Tests adding several notes
  @Test
  public void TestAddTextualView() {
    IPlayerModel model = new PlayerModel(4);
    TextualView consoleView = new TextualView(new PlayerModelReadOnly(model));
    INote c4 = new Note.Builder().pitch(Pitch.C).octave(0).duration(3).build();
    INote a4 = new Note.Builder().pitch(Pitch.A).octave(0).duration(2).build();
    INote b4 = new Note.Builder().pitch(Pitch.B).octave(0).duration(3).build();
    INote d4 = new Note.Builder().pitch(Pitch.D).octave(0).duration(6).build();
    model.addNote(0, a4);
    model.addNote(0, b4);
    model.addNote(0, c4);
    model.addNote(0, d4);
    model.addNote(1, c4);
    String expected = "   C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0 \n" +
            "0  X         X                                  X         X  \n" +
            "1  X         |                                  |         |  \n" +
            "2  |         |                                            |  \n" +
            "3  |         |                                               \n" +
            "4            |                                               \n" +
            "5            |                                               ";
    assertEquals(expected, consoleView.getText());
  }

  //Tests textual view empty song
  @Test
  public void TestPrintEmptySong() {
    IPlayerModel model = new PlayerModel(4);
    TextualView consoleView = new TextualView(new PlayerModelReadOnly(model));
    assertEquals("", consoleView.getText());
  }

  //Tests mary had a little lamb is properly printed
  @Test
  public void testMary() {
    IPlayerModel model = new PlayerModel(4);
    model.readInSong("mary-little-lamb.txt");
    TextualView consoleView = new TextualView(new PlayerModelReadOnly(model));
    String expected = "    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  " +
            "D#4   E4   F4  F#4   G4 \n" +
            " 0                 X                                            X                 \n" +
            " 1                 |                                            |                 \n" +
            " 2                 |                                  X                           \n" +
            " 3                 |                                  |                           \n" +
            " 4                 |                        X                                     \n" +
            " 5                 |                        |                                     \n" +
            " 6                 |                                  X                           \n" +
            " 7                                                    |                           \n" +
            " 8                 X                                            X                 \n" +
            " 9                 |                                            |                 \n" +
            "10                 |                                            X                 \n" +
            "11                 |                                            |                 \n" +
            "12                 |                                            X                 \n" +
            "13                 |                                            |                 \n" +
            "14                 |                                            |                 \n" +
            "15                                                                                \n" +
            "16                 X                                  X                           \n" +
            "17                 |                                  |                           \n" +
            "18                 |                                  X                           \n" +
            "19                 |                                  |                           \n" +
            "20                 |                                  X                           \n" +
            "21                 |                                  |                           \n" +
            "22                 |                                  |                           \n" +
            "23                 |                                  |                           \n" +
            "24                 X                                            X                 \n" +
            "25                 |                                            |                 \n" +
            "26                                                                             X  \n" +
            "27                                                                             |  \n" +
            "28                                                                             X  \n" +
            "29                                                                             |  \n" +
            "30                                                                             |  \n" +
            "31                                                                             |  \n" +
            "32                 X                                            X                 \n" +
            "33                 |                                            |                 \n" +
            "34                 |                                  X                           \n" +
            "35                 |                                  |                           \n" +
            "36                 |                        X                                     \n" +
            "37                 |                        |                                     \n" +
            "38                 |                                  X                           \n" +
            "39                 |                                  |                           \n" +
            "40                 X                                            X                 \n" +
            "41                 |                                            |                 \n" +
            "42                 |                                            X                 \n" +
            "43                 |                                            |                 \n" +
            "44                 |                                            X                 \n" +
            "45                 |                                            |                 \n" +
            "46                 |                                            X                 \n" +
            "47                 |                                            |                 \n" +
            "48                 X                                  X                           \n" +
            "49                 |                                  |                           \n" +
            "50                 |                                  X                           \n" +
            "51                 |                                  |                           \n" +
            "52                 |                                            X                 \n" +
            "53                 |                                            |                 \n" +
            "54                 |                                  X                           \n" +
            "55                 |                                  |                           \n" +
            "56  X                                       X                                     \n" +
            "57  |                                       |                                     \n" +
            "58  |                                       |                                     \n" +
            "59  |                                       |                                     \n" +
            "60  |                                       |                                     \n" +
            "61  |                                       |                                     \n" +
            "62  |                                       |                                     \n" +
            "63  |                                       |                                     ";
    assertEquals(expected, consoleView.getText());
  }

  //Produces console-transcript.txt file for submission
  @Test
  public void testMystery1Console() {
    IPlayerModel model = new PlayerModel(4);
    model.readInSong("mystery-1.txt");
    TextualView consoleView = new TextualView(new PlayerModelReadOnly(model));
    try {
      MIDITests.writeToFile("console-transcript.txt", consoleView.getText());
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(1347, (int) model.getLength());
  }

}
