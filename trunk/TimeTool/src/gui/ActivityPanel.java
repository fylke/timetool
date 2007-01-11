package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class ActivityPanel extends JPanel implements ActionListener {
  private static final long serialVersionUID = -8200082420428928371L;
  
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
  
  private final String[] activities = { "R4 Maint.", "R5 Feas.", "R5 Exec." }; 
   
  public ActivityPanel() {
    super();
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
    
    activityCombo = new JComboBox(activities);
    activityCombo.setSelectedIndex(0);
    upperPanel.add(activityCombo, "c");
    
    createNewActBT = new JButton("Ny");
    createNewActBT.setToolTipText("Skapa ny aktivitetstyp");
    createNewActBT.addActionListener(this);
    upperPanel.add(createNewActBT, "n");
    
    add(upperPanel, "a");
    
    timePanel = new TimePanel();
    
    add(timePanel, "t");
    
    lowerLeftPanel = new JPanel(layoutLowerLeft);
    includeLunchCB = new JCheckBox();
    lowerLeftPanel.add(includeLunchCB, "c");
    includeLunchLabel = new JLabel("Inkludera lunch (40min)");
    lowerLeftPanel.add(includeLunchLabel, "l");
    
    add(lowerLeftPanel, "l");
    
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
}