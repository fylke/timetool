package gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.LocalDateTime;

import persistency.settings.Settings;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class WeekViewPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  private ZoneLayout layout;
  private LocalDateTime date;
  private JLabel thisWeek;
  private TriangleButton previous;
  private TriangleButton next;

  private Settings user;

  public WeekViewPanel(final Settings user) {
    super();
    this.user = user;
    initComponents();
  }

  private void initComponents() {
    setName("Veckovy");

    layout = ZoneLayoutFactory.newZoneLayout();
    layout.addRow("p>pt^-...tn<n");
    layout.addRow("......6......");
    layout.addRow("w<-~........w");

    setLayout(layout);
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    date = new LocalDateTime();
    thisWeek = new JLabel("Vecka " + date.getWeekOfWeekyear());
    thisWeek.setFont(new Font("Arial", 1, 20));
    add(thisWeek, "t");

    previous = new TriangleButton(TriangleButton.LEFT);
    add(previous, "p");
    next = new TriangleButton(TriangleButton.RIGHT);
    add(next, "n");
  }
}