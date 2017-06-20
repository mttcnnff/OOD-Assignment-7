package cs3500.music.view;

import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.notes.INote;

/**
 * Created by Matt on 6/20/17.
 */
public class SequencerView implements IView {

  private IPlayerModelReadOnly model;
  private Sequencer sequencer;
  private Sequence sequence;
  private Map<Integer, List<INote>> song;
  private Integer currBeat;

  public SequencerView(IPlayerModelReadOnly model) {
    this.model = model;
    this.song = this.model.getSong();
    this.buildSequencer();
    this.currBeat = 0;
  }



  @Override
  public void makeVisible() {

  }

  @Override
  public void refresh(Integer beat) {
    this.song = this.model.getSong();
    this.buildSequencer();
    this.buildTrack();
  }

  private void buildSequencer() {
    try {
      sequence = new Sequence(Sequence.PPQ, this.model.getTempo());
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void buildTrack() {
    Track track = this.sequence.createTrack();

    try {
      this.sequencer.setSequence(this.sequence);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }

    for (Integer beat : this.song.keySet()) {
      for (INote note : this.song.get(beat)) {
        try {
          MidiMessage on = new ShortMessage(ShortMessage.NOTE_ON, note.getInstrument(), note
                  .toInteger(), note.getVolume());
          MidiMessage off = new ShortMessage(ShortMessage.NOTE_OFF, note.getInstrument(), note
                  .toInteger(), note.getVolume());

          MidiEvent onEvent = new MidiEvent(on, beat * this.model.getTempo());
          MidiEvent offEvent = new MidiEvent(off, (beat + note.getDuration() * this.model
                  .getTempo()));

          track.add(onEvent);
          track.add(offEvent);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    sequencer.setTempoInMPQ(this.model.getTempo());
  }
}
