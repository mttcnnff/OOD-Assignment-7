package cs3500.music.mocks;

import java.util.ArrayList;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Mock Midi Receiver used to record messages sent to it for use later.
 */
public class MockMidiReceiver implements Receiver {

  ArrayList<ShortMessage> log;

  /**
   * Constructor for MockMidiReceiver.
   */
  MockMidiReceiver() {
    this.log = new ArrayList<>();
  }

  /**
   * Adds sent message to receiver's log.
   *
   * @param message   message sent.
   * @param timeStamp timeStamp of message.
   */
  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage msg = (ShortMessage) message;
    this.log.add(msg);
  }

  @Override
  public void close() {
    System.out.println("MockMidiReceiver closed.");
  }

  /**
   * @return ArrayList of messages sent to this receiver.
   */
  ArrayList<ShortMessage> getLog() {
    return this.log;
  }
}
