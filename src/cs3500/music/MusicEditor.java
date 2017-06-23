package cs3500.music;

import cs3500.music.controller.ControllerFactory;
import cs3500.music.controller.IController;
import cs3500.music.model.IPlayerModel;
import cs3500.music.model.PlayerModel;
import cs3500.music.model.PlayerModelReadOnly;
import cs3500.music.view.IView;
import cs3500.music.view.ViewFactory;

/**
 * Main entry point of program. System arguments used to specify file to read in and view to create.
 */
public class MusicEditor {

  /**
   * System main method. Called when running program.
   *
   * @param args program arguments in an array of Strings. Should contain [filename, view type] in
   *             that order.
   */
  public static void main(String[] args) {
    IPlayerModel model = new PlayerModel(4);
//    if (args.length != 2) {
//      System.out.println("Wrong number of arguments. Proper argument format: [filename.txt] " +
//              "[desired view]");
//      return;
//    }
//    model.readInSong(args[0]);
//    VisualController controller = new VisualController(model);
//    IView consoleView = ViewFactory.makeView(args[1], new PlayerModelReadOnly(model));
//    controller.setView(consoleView);
//    consoleView.makeVisible();

    model.readInSong("mary-little-lamb.txt");
    IController controller = ControllerFactory.makeController("composite", model);
    IView consoleView = ViewFactory.makeView("composite", new PlayerModelReadOnly(model));
    controller.setView(consoleView);
    consoleView.start();

//    SequencerView seqView = new SequencerView(new PlayerModelReadOnly(model));
//    seqView.start();

//    controller.setView(consoleView);
//    controller.start();
  }
}
