package cs3500.music.view.visualview;


import javax.swing.*;

import java.awt.*;

import cs3500.music.controller.ClickListener;
import cs3500.music.model.IPlayerModel;
import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.view.IView;
import cs3500.music.view.IVisualView;

public class VisualView extends JFrame implements IVisualView {

  private IPlayerModelReadOnly model;
  private PianoPanel pianoPanel;
  private NoteMapPanel noteMapPanel;
  private JScrollPane noteMapScrollPanel;
  private Integer currBeat;



  /**
   * Constructor for visual view.
   *
   * @param model given model this view is going to represent.
   */
  public VisualView(IPlayerModelReadOnly model) {
    this.model = model;

    //main frame
    this.setTitle("Music Player");
    this.setSize(2000, 700);
    this.setResizable(false);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.currBeat = 0;

    //music panel
    this.noteMapPanel = new NoteMapPanel(this.model);
    this.noteMapScrollPanel = new JScrollPane(this.noteMapPanel);
    this.noteMapScrollPanel.setPreferredSize(new Dimension(500, 600));
    this.noteMapScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.noteMapScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.noteMapScrollPanel.setAutoscrolls(true);



    this.add(this.noteMapScrollPanel, BorderLayout.NORTH);

    //piano panel
    this.pianoPanel = new PianoPanel(this.model);
    this.pianoPanel.setPreferredSize(new Dimension(1040, 200));
    this.add(pianoPanel, BorderLayout.SOUTH);

    this.pack();
  }

  /**
   * Displays the visual view.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
    this.refresh(0);
  }

  /**
   * Refreshes this visual view to specified beat.
   *
   * @param beat desired beat to view song at.
   */
  @Override
  public void refresh(Integer beat) {
    this.currBeat = beat;

    Integer currentRedlinePos = (40 + (this.currBeat * 25));
    Integer locationTest = currentRedlinePos/this.noteMapScrollPanel
            .getHorizontalScrollBar().getVisibleAmount();
    Integer beginWindow = -1 * this.noteMapPanel.getX();
    Integer endWindow = -1 * this.noteMapPanel.getX() + (int)this.noteMapScrollPanel.getSize().getWidth();

    if (currentRedlinePos >= endWindow || currentRedlinePos <= beginWindow) {
      this.noteMapScrollPanel.getHorizontalScrollBar().setValue(locationTest * this.noteMapScrollPanel
              .getHorizontalScrollBar().getVisibleAmount());
    }

    this.noteMapPanel.refresh(this.currBeat);
    this.pianoPanel.refresh(this.currBeat);
    this.repaint();
  }

}
