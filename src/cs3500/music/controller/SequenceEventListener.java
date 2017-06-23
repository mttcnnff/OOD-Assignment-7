package cs3500.music.controller;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

/**
 * Created by Matt on 6/23/17.
 */
public class SequenceEventListener implements MetaEventListener {
  @Override
  public void meta(MetaMessage meta) {
    int i = meta.getType();
    System.out.print(String.valueOf(i));
  }
}
