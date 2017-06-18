package cs3500.music.pitch;

/**
 * Enum to represent all 12 possible musical pitches.
 */
public enum Pitch implements IPitch {
  C(0),
  CSHARP(1),
  D(2),
  DSHARP(3),
  E(4),
  F(5),
  FSHARP(6),
  G(7),
  GSHARP(8),
  A(9),
  ASHARP(10),
  B(11);

  private Integer rank;

  /**
   * Constructor for pitch.
   *
   * @param rank desired rank (0-11).
   */
  Pitch(Integer rank) {
    this.rank = rank;
  }

  @Override
  public Integer getRank() {
    return this.rank;
  }

  @Override
  public String toString() {
    switch (this) {
      case C:
        return "C";
      case CSHARP:
        return "C#";
      case D:
        return "D";
      case DSHARP:
        return "D#";
      case E:
        return "E";
      case F:
        return "F";
      case FSHARP:
        return "F#";
      case G:
        return "G";
      case GSHARP:
        return "G#";
      case A:
        return "A";
      case ASHARP:
        return "A#";
      case B:
        return "B";
      default:
        throw new IllegalArgumentException("Pitch doesn't exist.");
    }
  }

}
