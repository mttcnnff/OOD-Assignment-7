import org.junit.Test;

import cs3500.music.model.IPlayerModel;
import cs3500.music.model.PlayerModel;
import cs3500.music.model.PlayerModelReadOnly;
import cs3500.music.view.CompositeView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testing class for Composite View.
 */
public class CompositeViewTests {

  private CompositeView testView;

  //Tests that view is not playing when it opens and starts at beat 0
  @Test
  public void testStart() {
    this.initTest();
    testView.start();
    assertFalse(testView.isPlaying());
    assertTrue(testView.getBeat() == 0);
  }

  //ALL REFRESH TESTS TEST getBeat AS WELL

  //Tests refreshing to beat changes songs beat
  @Test
  public void testRefresh() {
    this.initTest();
    testView.start();
    testView.refresh(15);
    assertTrue(testView.getBeat() == 15);
    testView.refresh(0);
  }

  //Tests negative refresh value
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRefresh() {
    this.initTest();
    testView.start();
    testView.refresh(-10);
  }

  //Tests giant refresh value bigger than song length
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRefresh1() {
    this.initTest();
    testView.start();
    testView.refresh(100000);
  }

  //Tests trying to refresh while playing
  @Test
  public void testInvalidRefresh3() {
    this.initTest();
    testView.start();
    testView.togglePlay();
    testView.refresh(15);
    assertFalse(testView.getBeat() == 15);
    testView.togglePlay();
  }


  //Tests load doesn't change current beat
  @Test
  public void testLoad() {
    this.initTest();
    testView.start();
    testView.refresh(15);
    testView.load();
    assertTrue(testView.getBeat() == 15);
    testView.refresh(4);
    testView.load();
    assertTrue(testView.getBeat() == 4);
  }

  //Tests loading while playing isn't allowed
  @Test(expected = IllegalStateException.class)
  public void testLoad1() {
    this.initTest();
    testView.start();
    testView.togglePlay();
    testView.load();
  }

  //Tests togglePlay and isPlaying
  @Test
  public void testTogglePlay() {
    this.initTest();
    testView.start();
    testView.togglePlay();
    assertTrue(testView.isPlaying());
    testView.togglePlay();
    assertFalse(testView.isPlaying());
  }


  //Tests get key for various points on key board and off key board
  @Test
  public void testGetKeyFromXY() {
    this.initTest();
    testView.start();
    assertEquals(48, (int)testView.getKeyAtXY(329, 94));
    assertEquals(59, (int)testView.getKeyAtXY(449, 129));
    assertEquals(21, (int)testView.getKeyAtXY(0, 0));
    assertEquals(null, testView.getKeyAtXY(-10, 0));
    assertEquals(null, testView.getKeyAtXY(-10, -10));
    assertEquals(null, testView.getKeyAtXY(3000, 150));
    assertEquals(null, testView.getKeyAtXY(3000, 3000));
  }

  //Method to initialize test
  private void initTest() {
    IPlayerModel model = new PlayerModel(4);
    model.readInSong("mary-little-lamb.txt");
    testView = new CompositeView(new PlayerModelReadOnly(model));
  }
}
