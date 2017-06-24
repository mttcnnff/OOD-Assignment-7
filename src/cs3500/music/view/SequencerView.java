package cs3500.music.view;

import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import cs3500.music.mocks.MockMidiDevice;
import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.notes.INote;

/**
 * Class representation of Sequencer View for Audible Playback of MIDI song.
 */
public class SequencerView implements IView, IAudibleView {

  private IPlayerModelReadOnly model;
  private Sequencer sequencer;
  private Sequence sequence;
  private Track track;
  private Map<Integer, List<INote>> song;
  private Integer currBeat;

  /**
   * Package-private constructor to be used by view factory.
   * @param model given read only model this view will represent.
   */
  SequencerView(IPlayerModelReadOnly model) {
    this.model = model;
    this.song = this.model.getSong();
    try {
      sequence = new Sequence(Sequence.PPQ, this.model.getTempo());
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.currBeat = 0;
  }

  /**
   * Public convenience constructor for providing specific mock midi device for this sequence
   * view to log midi messages in case you need them in the future. (Possibly for visualization
   * or testing).
   * @param mock given mock midi device for recording midi messages.
   * @param model given model to represent with this view.
   */
  public SequencerView(MockMidiDevice mock, IPlayerModelReadOnly model) {
    this.model = model;
    this.song = this.model.getSong();
    try {
      sequence = new Sequence(Sequence.PPQ, this.model.getTempo());
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
      Transmitter transmitter = sequencer.getTransmitter();
      transmitter.setReceiver(mock.getReceiver());
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.currBeat = 0;
  }

  @Override
  public void start() {
    this.setMetaEventListener(meta -> {
      if (meta.getType() == 47) {
        System.out.println("Close program now.");
        this.sequencer.stop();
        this.sequencer.close();
      }
    });
    this.refresh(0);
    this.buildTrack();
    this.sequencer.start();

  }

  @Override
  public void refresh(Integer beat) {
    if (beat < 0 && beat > this.model.getLength()) {
      throw new IllegalArgumentException("Invalid beat.");
    }
    if (!this.sequencer.isRunning()) {
      this.currBeat = beat;
      this.sequencer.setTickPosition(this.currBeat * (sequencer.getTickLength()/this.model
              .getLength()));
    }
  }

  @Override
  public void load() {
    if (!this.sequencer.isRunning()) {
      this.refresh(this.currBeat);
      this.buildTrack();
    }
  }

  @Override
  public void togglePlay() {
    if (!this.sequencer.isRunning()) {
      System.out.println("Playing!");
      this.sequencer.setTempoInMPQ(this.model.getTempo());
      this.sequencer.start();
      this.sequencer.setTempoInMPQ(this.model.getTempo());

    } else {
      this.sequencer.stop();
      this.currBeat = this.getBeat();
      System.out.println("Stopped at beat: " + this.currBeat);
    }
  }

  @Override
  public int getBeat() {
    long ticksPerBeat = (sequencer.getTickLength()/this.model.getLength());
    return (int)(this.sequencer.getTickPosition()/ticksPerBeat);
  }

  @Override
  public void setMetaEventListener(MetaEventListener l) {
    this.sequencer.addMetaEventListener(l);
  }

  @Override
  public boolean isPlaying() {
    return this.sequencer.isRunning();
  }

  /**
   * Method to build track from model into sequencer.
   */
  private void buildTrack() {
    this.sequence.deleteTrack(this.track);
    this.track = this.sequence.createTrack();
    try {
      this.sequencer.setSequence(this.sequence);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    try {
      for (Integer beat : this.song.keySet()) {
        for (INote note : this.song.get(beat)) {

          MidiMessage on = new ShortMessage(ShortMessage.NOTE_ON, note.getInstrument(), note
                  .toInteger(), note.getVolume());
          MidiMessage off = new ShortMessage(ShortMessage.NOTE_OFF, note.getInstrument(), note
                  .toInteger(), note.getVolume());

          MidiEvent onEvent = new MidiEvent(on, beat * this.model.getTempo());
          MidiEvent offEvent = new MidiEvent(off, (beat + note.getDuration()) * this.model
                  .getTempo());

          track.add(onEvent);
          track.add(offEvent);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    sequencer.setTempoInMPQ(this.model.getTempo());
  }
}
