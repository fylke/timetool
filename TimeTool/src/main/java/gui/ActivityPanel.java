package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import persistency.projects.Company;
import persistency.projects.Project;
import persistency.settings.Settings;
import persistency.year.WorkDay;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class ActivityPanel extends JPanel implements ActionListener,
    FocusListener {
  private static final long serialVersionUID = 1L;

  private final Settings user;
  private final WorkDay currentDay;
  private int actId;

  private Font font;
  private ZoneLayout layout;
  private ZoneLayout layoutUpper;
  private ZoneLayout layoutLowerLeft;
  private ZoneLayout layoutLowerRight;

  private JPanel upperPanel;
  private JLabel activityLabel;
  private MyComboBox actCoB;
  private String actCoBEmptyMsg = "Inga aktiviteter definerade";
  private JButton createNewActBT;

  private TimePanel timePanel;

  private JPanel lowerLeftPanel;
  private JCheckBox includeLunchCB;
  private JLabel includeLunchLabel;

  private JPanel lowerRightPanel;
  private JButton deleteTabBT;
  private JButton saveTabBT;

  public ActivityPanel(final WorkDay currentDay, Settings user) {
    super();
    this.currentDay = currentDay;
    this.user = user;
    initComponents();
  }

  public ActivityPanel(final WorkDay currentDay, Settings user, final int actId) {
    this(currentDay, user);
    this.actId = actId;
  }

  /* (non-Javadoc)
   * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
   */
  public void focusGained(final FocusEvent e) {
    actCoB.setContents(getComboContents());
  }

  /* (non-Javadoc)
   * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
   */
  public void focusLost(final FocusEvent e) {}

  public void actionPerformed(final ActionEvent e) {
    if (e.getSource().equals(createNewActBT)) {
      java.awt.EventQueue.invokeLater(
          new Runnable() {
            public void run() {
              final JFrame createActFrame =
                new CreateActFrame(user);
              createActFrame.setVisible(true);
            }
          }
        );
    }
  }

  private void initComponents() {
    font = new Font("Arial", 1, 14);

    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    layout = ZoneLayoutFactory.newZoneLayout();
    layout.addRow("a<..~.a");
    layout.addRow("...6...");
    layout.addRow("t<-.~.t");
    layout.addRow("...6...");
    layout.addRow("l<l3b>b");
    setLayout(layout);

    layoutUpper = ZoneLayoutFactory.newZoneLayout();
    layoutUpper.addRow("a2c2n");

    layoutLowerLeft = ZoneLayoutFactory.newZoneLayout();
    layoutLowerLeft.addRow("c2l");

    layoutLowerRight = ZoneLayoutFactory.newZoneLayout();
    layoutLowerRight.addRow("d2s");

    upperPanel = new JPanel(layoutUpper);
    activityLabel = new JLabel("Aktivitet:");
    activityLabel.setFont(font);
    upperPanel.add(activityLabel, "a");
    actCoB = new MyComboBox(getComboContents(), actCoBEmptyMsg);
    actCoB.setSelectedIndex(0);
    upperPanel.add(actCoB, "c");

    createNewActBT = new JButton("Ny");
    createNewActBT.setToolTipText("Skapa ny aktivitetstyp");
    createNewActBT.addActionListener(this);
    upperPanel.add(createNewActBT, "n");

    add(upperPanel, "a");

    timePanel = new TimePanel();

    add(timePanel, "t");

    if (currentDay.getActivity(actId) != null) {
      lowerLeftPanel = new JPanel(layoutLowerLeft);
      includeLunchCB = new JCheckBox();
      lowerLeftPanel.add(includeLunchCB, "c");
      includeLunchLabel =
        new JLabel("Inkludera lunch (" +
                   currentDay.getActivity(actId).getLunchLenght() + "min)");
      lowerLeftPanel.add(includeLunchLabel, "l");

      add(lowerLeftPanel, "l");
    }

    lowerRightPanel = new JPanel(layoutLowerRight);
    deleteTabBT = new JButton("Ta bort");
    deleteTabBT.setToolTipText("Ta bort aktivitet");
    lowerRightPanel.add(deleteTabBT, "d");
    saveTabBT = new JButton("Spara");
    saveTabBT.setToolTipText("Spara aktivitet");
    lowerRightPanel.add(saveTabBT, "s");

    add(lowerRightPanel, "b");

    if (actCoB.getSelected() != null) {
      setName(actCoB.getSelected().getShortDispString());
    } else {
      setName(actCoBEmptyMsg);
    }
  }

  private Vector<MyComboBoxDisplayable> getComboContents() {
    final Vector<MyComboBoxDisplayable> acts =
      new Vector<MyComboBoxDisplayable>();
    for (Company comp : user.getProjectSet().getCompanies()) {
      for(Project proj : comp.getProjects()) {
        acts.addAll(proj.getActivities());
      }
    }
    return acts;
  }
}