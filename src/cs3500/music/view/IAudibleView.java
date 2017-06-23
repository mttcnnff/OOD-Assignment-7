package cs3500.music.view;

import javax.sound.midi.MetaEventListener;

public interface IAudibleView extends IView {

  void refresh(Integer beat);

  void load();

  void togglePlay();

  boolean isPlaying();

  int getBeat();

  void setMetaEventListener(MetaEventListener l);

}
