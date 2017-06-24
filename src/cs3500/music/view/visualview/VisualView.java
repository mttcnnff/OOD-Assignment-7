package cs3500.music.view.visualview;


import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;

import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.view.IVisualView;

/**
 * Class representation of visual view of music editor. Top half shows music sheet display,
 * bottom half shows piano panel.
 */
public class VisualView extends JFrame implements IVisualView {

  private IPlayerModelReadOnly model;
  private PianoPanel pianoPanel;
  private NoteMapPanel noteMapPanel;
  private JScrollPane noteMapScrollPanel;
  private Integer currBeat;

  /**
   * Constructor for visual view.
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
  public void start() {
    this.setVisible(true);
    this.refresh(0);
  }

  /**
   * Tells if view is playing.
   * @return false, the visual view is not aware if the song is playing or not. The method is
   * here for convenience sake.
   */
  @Override
  public boolean isPlaying() {
    return false;
  }

  /**
   * Refreshes this visual view to specified beat.
   * @param beat desired beat to view song at.
   */
  @Override
  public void refresh(Integer beat) {
    if (beat < 0 && beat > this.model.getLength()) {
      throw new IllegalArgumentException("Invalid beat.");
    }
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

  @Override
  public void addMouseListener(MouseListener l) {
    this.pianoPanel.addMouseListener(l);
  }

  @Override
  public Integer getKeyAtXY(int x, int y) {
    return this.pianoPanel.getKeyAtXY(x, y);
  }

}


