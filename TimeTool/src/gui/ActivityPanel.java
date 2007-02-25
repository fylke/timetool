package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import persistency.projects.Activity;
import persistency.projects.Company;
import persistency.projects.Project;
import persistency.year.WorkDay;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class ActivityPanel extends JPanel implements ActionListener {
  private static final long serialVersionUID = 1L;
  
  private final WorkDay currentDay;
  private int actId;
  private String[] activities = {};
  
  private Font font;
  private ZoneLayout layout;
  private ZoneLayout layoutUpper;
  private ZoneLayout layoutLowerLeft;
  private ZoneLayout layoutLowerRight;
  
  private JPanel upperPanel;
  private JLabel activityLabel;
  private JComboBox activityCombo;
  private JButton createNewActBT;
    
  private TimePanel timePanel;
  
  private JPanel lowerLeftPanel;
  private JCheckBox includeLunchCB;
  private JLabel includeLunchLabel;
  
  private JPanel lowerRightPanel;
  private JButton deleteTabBT;
  private JButton saveTabBT; 

  public ActivityPanel(final WorkDay currentDay) {
    super();
    this.currentDay = currentDay;
    initComponents();
  }
  
  public ActivityPanel(final WorkDay currentDay, final int actId) {
    super();
    this.currentDay = currentDay;
    this.actId = actId;
    initComponents();
  }

  public void actionPerformed(final ActionEvent e) {
    if (e.getSource().equals(createNewActBT)) {
      java.awt.EventQueue.invokeLater(
          new Runnable() {
            public void run() {
              new CreateActivityFrame().setVisible(true);
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
    
    activities = getActivityList().toArray(activities);
    if (activities.length > 0) {
      activityCombo = new JComboBox(activities);
    } else {
      activityCombo = new JComboBox(new String[]{"Inga aktiviteter definerade"});
    }
    activityCombo.setSelectedIndex(0);
    upperPanel.add(activityCombo, "c");
    
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
    
    setName((String) activityCombo.getSelectedItem());
  }
  
  private List<String> getActivityList() {
    List<String> actShortNames = new ArrayList<String>();
    for (Company comp : currentDay.getSettings().getProjectSet().getCompanies()) {
      for(Project proj : comp.getProjects()) {
        for (Activity act : proj.getActivities()) {
          actShortNames.add(act.getShortName());
        }
      }
    }
    return actShortNames;
  }
}