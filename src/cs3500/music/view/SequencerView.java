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

public class SequencerView implements IView {

  private IPlayerModelReadOnly model;
  private Sequencer sequencer;
  private Sequence sequence;
  private Track track;
  private Map<Integer, List<INote>> song;
  private Integer currBeat;

  public SequencerView(IPlayerModelReadOnly model) {
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

  public void refresh(Integer beat) {
    if (!this.sequencer.isRunning()) {
      this.currBeat = beat;
      this.sequencer.setTickPosition(this.currBeat * (sequencer.getTickLength()/this.model
              .getLength()));
//      System.out.println("-----\n Current beat: " + this.currBeat);
//      System.out.println("Current Tick: " + this.sequencer.getTickPosition());
    }
  }

  public void load() {
    this.refresh(this.currBeat);
    this.buildTrack();
  }

  void togglePlay() {
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

  public int getBeat() {
    long ticksPerBeat = (sequencer.getTickLength()/this.model.getLength());
    //System.out.println("Current tick pos: " + this.sequencer.getTickPosition());

    //System.out.println("Ticks per beat: " + String.valueOf(ticksPerBeat));
    int beat = (int)(this.sequencer.getTickPosition()/ticksPerBeat);
    //System.out.println("Beat got: " + beat);
    return beat;

  }

  public void setMetaEventListener(MetaEventListener l) {
    this.sequencer.addMetaEventListener(l);
  }

  public boolean isPlaying() {
    return this.sequencer.isRunning();
  }


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
    System.out.println("Ticks: " + sequencer.getTickLength());
    System.out.println("Beats: " + this.model.getLength());
    System.out.println((sequencer.getTickLength()/this.model.getLength()) + " Ticks per Beat");
    sequencer.setTempoInMPQ(this.model.getTempo());
  }
}
