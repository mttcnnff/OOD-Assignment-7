package cs3500.music.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import cs3500.music.notes.INote;
import cs3500.music.song.ISong;
import cs3500.music.song.Song;
import cs3500.music.util.MusicReader;

/**
 * Class to represent PlayerModel.
 * Allows basic functionality for song manipulation such as adding notes, removing notes, editing
 * notes, combining songs, concatenating songs.
 * Allows for functionality to get information about the song (what is playing at a beat, the
 * tempo, the length, etc).
 * Allows for reading of song in from a file.
 */
public class PlayerModel implements IPlayerModel {

  private ISong song;

  /**
   * Constructor for song.
   *
   * @param measure desired measure.
   */
  public PlayerModel(Integer measure) {
    this.song = new Song(measure);
  }

  @Override
  public void addNote(Integer beat, INote note) throws IllegalArgumentException {
    if (!this.song.addNote(beat, note)) {
      throw new IllegalArgumentException("Not already exists at that beat.");
    }
  }

  @Override
  public void removeNote(Integer beat, INote note) {
    if (!this.song.removeNote(beat, note)) {
      throw new IllegalArgumentException("Trying to remove note that doesn't exist.");
    }
  }

  @Override
  public void editNoteDuration(Integer beat, INote note, Integer newDuration) {
    if (!this.song.editNoteDuration(beat, note, newDuration)) {
      throw new IllegalArgumentException("Trying to edit not which doesn't exist.");
    }
  }

  @Override
  public void combine(ISong otherSong) {
    Objects.requireNonNull(otherSong, "Can't combine with null song.");
    this.song.combine(otherSong);
  }

  @Override
  public void concat(ISong otherSong) {
    Objects.requireNonNull(otherSong, "Can't concat with null song.");
    this.song.concat(otherSong);

  }

  @Override
  public Map<Integer, List<INote>> getSong() {
    return this.song.getSong();
  }

  @Override
  public TreeMap<Integer, Integer> getToneRange() {
    return this.song.getToneRange();
  }

  @Override
  public List<INote> getStartNotes(Integer beat) {
    return this.song.getStartNotes(beat);
  }

  @Override
  public List<INote> getPlayingNotes(Integer beat) {
    return this.song.getPlayingNotes(beat);
  }


  @Override
  public Integer getLength() {
    return this.song.getLength();
  }

  @Override
  public Integer getTempo() {
    return this.song.getTempo();
  }

  @Override
  public void readInSong(String filename) {
    try (FileReader fileReader = new FileReader(filename)) {
      this.song = MusicReader.parseFile(fileReader, new Song.Builder());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
