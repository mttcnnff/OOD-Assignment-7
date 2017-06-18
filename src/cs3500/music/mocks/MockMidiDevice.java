package cs3500.music.mocks;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

/**
 * Class to represent MockMidiDevice. Allows for caching of MIDI messages sent to be used later.
 * (Possibly for tests, or visualization later).
 */
public class MockMidiDevice implements MidiDevice {

  private MockMidiReceiver receiver;

  /**
   * Constructor for MockMidiDevice.
   */
  public MockMidiDevice() {
    this.receiver = new MockMidiReceiver();
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {
    System.out.println("MockMidiDevice opened.");
  }

  @Override
  public void close() {
    System.out.println("MockMidiDevice closed.");
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return this.receiver;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }

  /**
   * @return ArrayList of ShortMessages sent to this device's receiver.
   */
  public ArrayList<ShortMessage> getLog() {
    return this.receiver.getLog();
  }
}
