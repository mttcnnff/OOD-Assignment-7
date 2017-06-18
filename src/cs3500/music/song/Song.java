package cs3500.music.song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import cs3500.music.notes.INote;
import cs3500.music.notes.Note;
import cs3500.music.pitch.Pitch;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.Utils;

/**
 * Class representation of a song.
 */
public class Song implements ISong {

  //Map: ordered by beat number
  //List: ordered by duration; shortest -> longest
  private TreeMap<Integer, List<INote>> startNotes;

  //Map: ordered by beat number
  //List: no order (single duration notes only)
  private TreeMap<Integer, List<INote>> allNotes;

  //ordered lowest -> highest
  private TreeMap<Integer, Integer> toneCount;

  //ordered lowest -> highest
  private TreeMap<Integer, Integer> toneRange;

  private Integer tempo;

  /**
   * Constructor for new song.
   *
   * @param tempo given tempo of new song.
   * @throws IllegalArgumentException if tempo is < 0.
   */
  public Song(Integer tempo) throws IllegalArgumentException {
    if (tempo < 0) {
      throw new IllegalArgumentException("Tempo is less than zero!");
    }
    this.startNotes = new TreeMap<>();
    this.allNotes = new TreeMap<>();
    this.toneCount = new TreeMap<>();
    this.toneRange = new TreeMap<>();
    this.tempo = tempo;
  }

  /**
   * Builder constructor for this class.
   *
   * @param b Builder used to construct this song.
   * @throws IllegalArgumentException if builder is null.
   */
  Song(Builder b) throws IllegalArgumentException {
    Objects.requireNonNull(b, "Builder is null!");
    this.startNotes = b.builderStartNotes;
    this.allNotes = b.builderAllNotes;
    this.toneCount = b.builderToneCount;
    this.tempo = b.builderTempo;
    this.toneRange = new TreeMap<>();
    this.updateToneRange();
    this.fillEmptyBeats();
  }

  @Override
  public TreeMap<Integer, Integer> getToneRange() {
    TreeMap<Integer, Integer> copy = new TreeMap<>(this.toneRange);
    return copy;
  }

  /**
   * Updates this song's tone range when called.
   */
  private void updateToneRange() {
    this.toneRange.clear();
    if (!this.toneCount.isEmpty()) {
      Integer lowestTone = this.toneCount.firstKey();
      Integer highestTone = this.toneCount.lastKey();
      Integer currTone = lowestTone;
      int count = 0;
      while (!currTone.equals(highestTone + 1)) {
        this.toneRange.put(currTone, count);
        count++;
        currTone++;
      }
    }
  }

  @Override
  public Integer getTempo() {
    return this.tempo;
  }

  @Override
  public Map<Integer, List<INote>> getSong() {
    Map<Integer, List<INote>> songContents = new TreeMap<>();
    for (Integer beat : this.startNotes.keySet()) {
      songContents.put(beat, Collections.unmodifiableList(this.startNotes.get(beat)));
    }
    return Collections.unmodifiableMap(songContents);
  }

  @Override
  public List<INote> getStartNotes(Integer beat) {
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number");
    }
    List<INote> result = this.startNotes.get(beat);
    if (result == null) {
      return new ArrayList<>();
    } else {
      return Collections.unmodifiableList(result);
    }
  }

  @Override
  public List<INote> getPlayingNotes(Integer beat) {
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number");
    }
    List<INote> result = this.allNotes.get(beat);
    if (result == null) {
      return new ArrayList<>();
    } else {
      return Collections.unmodifiableList(result);
    }
  }

  @Override
  public Integer getLength() {
    return this.allNotes.size();
  }

  @Override
  public Boolean addNote(Integer beat, INote note) {
    Objects.requireNonNull(note, "Note is null");
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number : " + beat.toString());
    }
    if (addNoteHelper(beat, note, this.startNotes, this.allNotes, this.toneCount)) {
      this.updateToneRange();
      this.fillEmptyBeats();
      return true;
    } else {
      return false;
    }
  }

  /**
   * Helper for adding notes to allow builder to use the same logic to addNotes.
   *
   * @param beat       desired beat to add note at.
   * @param note       note to add.
   * @param startNotes map of start notes to edit.
   * @param allNotes   map of all notes to edit.
   * @param toneCount  tone count map to edit.
   * @return true if add was successfully executed, false if not.
   */
  private static Boolean addNoteHelper(Integer beat, INote note, TreeMap<Integer, List<INote>>
          startNotes, TreeMap<Integer, List<INote>> allNotes, Map<Integer, Integer> toneCount) {
    Objects.requireNonNull(note, "Note is null");
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number : " + beat.toString());
    }
    startNotes.putIfAbsent(beat, new ArrayList<>());
    allNotes.putIfAbsent(beat, new ArrayList<>());
    List<INote> beatStartNotes = startNotes.get(beat);

    //if note with from same instrument at same tone doesn't exist at this beat
    if (!beatStartNotes.contains(note)) {
      //add to startNotes at this beat
      beatStartNotes.add(note);

      //add to allNotes at this beat through the duration
      for (int i = beat; i < beat + note.getDuration(); i++) {
        allNotes.putIfAbsent(i, new ArrayList<>());
        allNotes.get(i).add(note);
      }

      //increment tone count
      toneCount.putIfAbsent(note.toInteger(), 0);
      toneCount.put(note.toInteger(), toneCount.get(note.toInteger()) + 1);
    } else {
      return false;
    }
    return true;
  }

  /**
   * Ensures any totally empty beats are accoutned for in allNotes.
   */
  private void fillEmptyBeats() {
    for (int i = 0; i < this.startNotes.size(); i++) {
      this.allNotes.putIfAbsent(i, new ArrayList<>());
    }
  }

  @Override
  public Boolean removeNote(Integer beat, INote note) {
    Objects.requireNonNull(note, "Notes are null");
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number.");
    }
    List<INote> beatStartNotes = this.startNotes.get(beat);

    //if note is contained at specified beat
    if (beatStartNotes != null && beatStartNotes.contains(note)) {
      //remove note from start notes
      beatStartNotes.remove(note);

      //remove note from allNotes from this beat through the duration
      for (int i = beat; i < beat + note.getDuration(); i++) {
        if (this.allNotes.get(i) != null) {
          this.allNotes.get(i).remove(note);
          if (this.allNotes.get(i).isEmpty()) {
            this.allNotes.remove(i);
          }
        }
      }

      //update tone count
      this.toneCount.put(note.toInteger(), this.toneCount.get(note.toInteger()) - 1);
      if (this.toneCount.get(note.toInteger()) == 0) {
        this.toneCount.remove(note.toInteger());
      }
    } else {
      return false;
    }
    if (beatStartNotes.isEmpty()) {
      this.startNotes.remove(beat);
    }
    this.updateToneRange();
    this.fillEmptyBeats();
    return true;
  }

  @Override
  public Boolean editNoteDuration(Integer beat, INote note, Integer newDuration) {
    List<INote> beatNotes = this.startNotes.get(beat);

    if (beatNotes == null || newDuration < 1) {
      return false;
    }

    Integer indexOfOldNote = beatNotes.indexOf(note);
    if (indexOfOldNote > -1) {
      INote oldNote = beatNotes.get(indexOfOldNote);
      this.removeNote(beat, note);
      oldNote.editDuration(newDuration);
      this.addNote(beat, oldNote);
    } else {
      return false;
    }
    return true;
  }

  @Override
  public void concat(ISong newSong) {
    Integer offset = this.getLength();
    Map<Integer, List<INote>> newSongContents = newSong.getSong();
    for (Integer beat : newSongContents.keySet()) {
      List<INote> beatNotes = newSongContents.get(beat);
      for (INote note : beatNotes) {
        this.addNote(beat + offset, note);
      }
    }
  }

  @Override
  public void combine(ISong newSong) {
    Map<Integer, List<INote>> newSongContents = newSong.getSong();
    for (Integer beat : newSongContents.keySet()) {

      List<INote> oldNotes = this.startNotes.get(beat);
      List<INote> beatNotes = newSongContents.get(beat);

      for (INote note : beatNotes) {
        Integer index = oldNotes.indexOf(note);
        if (index == -1) {
          this.addNote(beat, note);
        } else {
          int oldDur = oldNotes.get(index).getDuration();
          int newDur = note.getDuration() > oldDur ? note.getDuration() : oldDur;
          this.editNoteDuration(beat, note, newDur);
        }

      }
    }

  }

  /**
   * Builder for Song.
   */
  public static class Builder implements CompositionBuilder<ISong> {

    private TreeMap<Integer, List<INote>> builderStartNotes = new TreeMap<>();
    private TreeMap<Integer, List<INote>> builderAllNotes = new TreeMap<>();
    private TreeMap<Integer, Integer> builderToneCount = new TreeMap<>();
    private Integer builderTempo;

    @Override
    public ISong build() {
      return new Song(this);
    }

    @Override
    public CompositionBuilder<ISong> setTempo(int tempo) {
      this.builderTempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<ISong> addNote(int start, int end, int instrument, int pitch, int
            volume) {
      Integer octave = Utils.integerToOctave(pitch);
      Pitch tone = Utils.integerToPitch(pitch);
      Integer duration = end - start;
      addNoteHelper(start, new Note.Builder().pitch(tone).octave(octave).instrument(instrument - 1)
                      .duration(duration).volume(volume).build(), this.builderStartNotes,
              this.builderAllNotes,
              this.builderToneCount);
      return this;
    }
  }
}
