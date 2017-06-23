package cs3500.music.notes;

import java.util.Objects;

import cs3500.music.pitch.IPitch;
import cs3500.music.util.Utils;

/**
 * Note class used to represent a musical note being played in piece of music.
 */
public class Note implements Comparable<INote>, INote {
  private Integer instrument;
  private Integer duration;
  private Integer volume;
  private Integer tone;

  /**
   * Constructor used only by this class's builder
   *
   * @param b builder used to create this instance.
   */
  private Note(Builder b) {
    Objects.requireNonNull(b.pitch, "Pitch cannot be null.");
    if (b.octave < 0) {
      throw new IllegalArgumentException("Negative octave.");
    }
    if (b.duration < 0) {
      throw new IllegalArgumentException("Negative duration.");
    }
    if (b.volume < 0) {
      throw new IllegalArgumentException("Negative volume.");
    }
    if (b.instrument < 0) {
      throw new IllegalArgumentException("Negative instrument code.");
    }
    this.volume = b.volume;
    this.duration = b.duration;
    this.instrument = b.instrument;
    this.tone = ((b.octave + 1) * 12) + b.pitch.getRank();
  }

  @Override
  public String toString() {
    return Utils.toneToString(this.tone);
  }

  @Override
  public Integer getDuration() {
    return this.duration;
  }

  @Override
  public void editDuration(Integer newDur) {
    if (newDur < 0) {
      throw new IllegalArgumentException("Bad Duration.");
    }
    this.duration = newDur;
  }

  @Override
  public Integer toInteger() {
    return this.tone;
  }

  @Override
  public Integer getInstrument() {
    return this.instrument;
  }

  @Override
  public Integer getVolume() {
    return this.volume;
  }

  @Override
  public int compareTo(INote o) {
    return this.toInteger() - o.toInteger();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Note note = (Note) o;

    if (instrument != null ? !instrument.equals(note.instrument) : note.instrument != null) {
      return false;
    }
    return tone != null ? tone.equals(note.tone) : note.tone == null;
  }

  @Override
  public int hashCode() {
    int result = instrument != null ? instrument.hashCode() : 0;
    result = 31 * result + (tone != null ? tone.hashCode() : 0);
    return result;
  }


  /**
   * Builder class for Note.
   */
  public static class Builder {
    private IPitch pitch;
    private Integer octave = 0;
    private Integer instrument = 0;
    private Integer duration = 1;
    private Integer volume = 70;

    /**
     * Sets builders pitch.
     *
     * @param pitch new pitch.
     * @return this builder instance.
     */
    public Builder pitch(IPitch pitch) {
      this.pitch = pitch;
      return this;
    }

    /**
     * Sets builder's octave.
     *
     * @param octave new octave.
     * @return this builder instance.
     */
    public Builder octave(Integer octave) {
      this.octave = octave;
      return this;
    }

    /**
     * Sets builder's instrument.
     *
     * @param instrument new instrument.
     * @return this builder instance.
     */
    public Builder instrument(Integer instrument) {
      this.instrument = instrument;
      return this;
    }

    /**
     * Sets builder's duration.
     *
     * @param duration new duration.
     * @return this builder instance.
     */
    public Builder duration(Integer duration) {
      this.duration = duration;
      return this;
    }

    /**
     * Sets builder's volume.
     *
     * @param volume new volume.
     * @return this builder instance.
     */
    public Builder volume(Integer volume) {
      this.volume = volume;
      return this;
    }

    /**
     * Build this note specified by this builder.
     *
     * @return new Note.
     */
    public INote build() {
      return new Note(this);
    }

  }

}
