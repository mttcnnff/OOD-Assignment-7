package cs3500.music.view;

import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.mocks.MockMidiDevice;
import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.notes.INote;
import cs3500.music.util.Utils;

/**
 * Class representation of an audible view. Plays song from midi.
 */
public class AudibleView implements IView {

  private IPlayerModelReadOnly model;
  private Receiver receiver;
  private Map<Integer, List<INote>> song;


  /**
   * Constructor for audible view.
   *
   * @param model model this view will use.
   */
  AudibleView(IPlayerModelReadOnly model) {
    this.model = model;
    this.song = this.model.getSong();
    try {
      Synthesizer synthesizer = MidiSystem.getSynthesizer();
      this.receiver = synthesizer.getReceiver();
      synthesizer.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

  }

  /**
   * Constructor for using MockMidiDevice to record Midi messages.
   *
   * @param model       given model to view.
   * @param synthesizer given MockMidiDevice to record with.
   */
  public AudibleView(IPlayerModelReadOnly model, MockMidiDevice synthesizer) {
    this.model = model;
    this.song = this.model.getSong();
    try {
      this.receiver = synthesizer.getReceiver();
      synthesizer.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

  }

  /**
   * Initially called when view is created.
   */
  @Override
  public void makeVisible() {
    this.refresh(0);
  }

  /**
   * For the Audible View:
   * This method reads the whole song and sends the midi reciever the appropriate messages to
   * play the song. After the messages are sent it waits for a period of time equal to the length
   * of the song for the program to finish playing the song until the program ends.
   */
  @Override
  public void refresh(Integer beat) {
    this.song = this.model.getSong();
    Integer tempo = this.model.getTempo();
    Integer length = this.model.getLength();
    for (Integer songBeat : this.song.keySet()) {
      for (INote note : this.song.get(songBeat)) {
        try {
          ShortMessage msgOn = new ShortMessage(ShortMessage.NOTE_ON, note.getInstrument(), Utils
                  .noteToInteger(note), note.getVolume());
          ShortMessage msgOff = new ShortMessage(ShortMessage.NOTE_OFF, note.getInstrument(), Utils
                  .noteToInteger(note), note.getVolume());

          this.receiver.send(msgOn, songBeat * tempo);
          this.receiver.send(msgOff, tempo * (songBeat + note.getDuration()));
        } catch (InvalidMidiDataException e) {
          e.printStackTrace();
        }

      }
    }
    try {
      long tempoMillis = tempo / 1000;
      long tempoNanos = (tempo % 1000) * 1000;
      Thread.sleep(tempoMillis * length);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.receiver.close();
  }

}
