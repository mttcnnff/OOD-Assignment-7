package cs3500.music.pitch;

/**
 * Interface for the functionality a musical pitch must offer.
 */
public interface IPitch {

  /**
   * @return String representation of this pitch.
   */
  String toString();

  /**
   * @return Integer representation of this pitch's rank.
   */
  Integer getRank();
}
