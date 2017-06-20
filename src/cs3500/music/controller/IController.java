package cs3500.music.controller;

import cs3500.music.view.IView;

public interface IController {

  void setView(IView v);

  void start();

}
