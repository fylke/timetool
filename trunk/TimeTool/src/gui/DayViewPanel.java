package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.joda.time.LocalDateTime;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class DayViewPanel extends JPanel implements ActionListener {
  private static final long serialVersionUID = -2161376983888260337L;
  
  private ZoneLayout layout;
  private LocalDateTime date;
  private JLabel today;
  private JTabbedPane activityPanel;
  private Font font;
  
  private JPanel commonSettingsLeftPanel;
  private JPanel commonSettingsRightPanel;
  private ZoneLayout commonSettingsLayoutLP;
  private ZoneLayout commonSettingsLayoutRP;
  
  private JLabel flexBankLabel;
  private JLabel flexBankContent;
  private JLabel hoursWorkedLabel;
  private JLabel hoursWorkedContent;
  
  private JCheckBox isReportedCB;
  private JLabel isReportedLabel;
  
  private JButton writeJournalBT;
  private JButton createNewTabBT;
   
  public DayViewPanel() {
    super();
    initComponents();
  }
  
  public void actionPerformed(final ActionEvent e) {
    if (e.getSource().equals(writeJournalBT)) {
      java.awt.EventQueue.invokeLater(
          new Runnable() {
            public void run() {
              new JournalFrame().setVisible(true);
            }
          }
        );
    } else if (e.getSource().equals(createNewTabBT)) {
      activityPanel.add(new ActivityPanel());
    }
  }

  private void initComponents() {
    setName("Dagsvy");
    font = new Font("Arial", 1, 20);
    
    layout = ZoneLayoutFactory.newZoneLayout();
    layout.addRow("d^-~....d");
    layout.addRow("....6....");
    layout.addRow("a<-~.....");
    layout.addRow("........a");
    layout.addRow("....6....");
    layout.addRow("c<^.~b._>");
    layout.addRow("...c....b");
    
    setLayout(layout);
    
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    date = new LocalDateTime();
    today = new JLabel(date.dayOfWeek().getAsText() + " " + 
                       date.getDayOfMonth() + "/" +
                       date.getMonthOfYear() + " v." +
                       date.getWeekOfWeekyear() + " " +
                       date.getYear());
    today.setFont(font);
    add(today, "d");
    
    activityPanel = new JTabbedPane();
    activityPanel.add(new ActivityPanel());
    add(activityPanel, "a");
    
    commonSettingsLeftPanel = new JPanel();
    commonSettingsLayoutLP = ZoneLayoutFactory.newZoneLayout();
    commonSettingsLayoutLP.addRow("c>c2f<f");
    commonSettingsLayoutLP.addRow("d>d.w<w");
    commonSettingsLayoutLP.addRow("s>s.r....");
    commonSettingsLeftPanel.setLayout(commonSettingsLayoutLP);
    
    commonSettingsRightPanel = new JPanel();
    commonSettingsLayoutRP = ZoneLayoutFactory.newZoneLayout();
    commonSettingsLayoutRP.addRow("j._>j");
    commonSettingsLayoutRP.addRow("..6..");
    commonSettingsLayoutRP.addRow("n._>n");    
    commonSettingsRightPanel.setLayout(commonSettingsLayoutRP);
    
    flexBankLabel = new JLabel("Flexbank:");
    commonSettingsLeftPanel.add(flexBankLabel, "c");
    flexBankContent = new JLabel("4h 40min");
    commonSettingsLeftPanel.add(flexBankContent, "f");
    
    hoursWorkedLabel = new JLabel("Jobbat idag:");
    commonSettingsLeftPanel.add(hoursWorkedLabel, "d");
    hoursWorkedContent = new JLabel("7h 25min");
    commonSettingsLeftPanel.add(hoursWorkedContent, "w");
    
    isReportedCB = new JCheckBox();
    commonSettingsLeftPanel.add(isReportedCB, "s");
    isReportedLabel = new JLabel("Rapporterad");
    commonSettingsLeftPanel.add(isReportedLabel, "r");
    
    add(commonSettingsLeftPanel, "c");
    
    writeJournalBT = new JButton("Dagbok");
    writeJournalBT.setToolTipText("Uppdatera dagboken");
    writeJournalBT.addActionListener(this);
    commonSettingsRightPanel.add(writeJournalBT, "j");
    
    createNewTabBT = new JButton("Ny flik");
    createNewTabBT.setToolTipText("Skapa ny aktivitetsflik");
    createNewTabBT.addActionListener(this);
    commonSettingsRightPanel.add(createNewTabBT, "n");
    
    add(commonSettingsRightPanel, "b");
  }
}