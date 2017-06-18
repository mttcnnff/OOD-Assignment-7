package cs3500.music.view.visualview;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.BorderLayout;

import cs3500.music.model.IPlayerModelReadOnly;
import cs3500.music.view.IView;

public class VisualView extends JFrame implements IView {

  private PianoPanel pianoPanel;
  private NoteMapPanel noteMapPanel;

  /**
   * Constructor for visual view.
   *
   * @param model given model this view is going to represent.
   */
  public VisualView(IPlayerModelReadOnly model) {

    //main frame
    this.setTitle("Music Player");
    this.setSize(2000, 700);
    this.setResizable(false);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    //music panel
    this.noteMapPanel = new NoteMapPanel(model);
    JScrollPane scrollPane = new JScrollPane(this.noteMapPanel);
    scrollPane.setPreferredSize(new Dimension(500, 600));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setAutoscrolls(true);

    this.add(scrollPane, BorderLayout.NORTH);

    //piano panel
    this.pianoPanel = new PianoPanel(model);
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
    this.noteMapPanel.refresh(beat);
    this.pianoPanel.refresh(beat);
    this.repaint();
  }


}
