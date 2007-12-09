package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;

import persistency.settings.Settings;
import persistency.year.WorkDay;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class DayViewPanel extends JPanel implements ActionListener {
  private static final long serialVersionUID = 1L;

  private WorkDay currentDay;
  private Settings user;

  private ZoneLayout layout;
  private ReadableDateTime date;
  private JLabel dateLabel;
  private JTabbedPane activityPanel;
  private Font font;

  private JPanel commonSettingsUpperLeftPanel;
  private JPanel commonSettingsLowerLeftPanel;
  private JPanel commonSettingsRightPanel;

  private ZoneLayout commonSettingsLayoutULP;
  private ZoneLayout commonSettingsLayoutLLP;
  private ZoneLayout commonSettingsLayoutRP;

  private JLabel flexBankLabel;
  private JLabel flexBankContent;
  private JLabel hoursWorkedLabel;
  private JLabel hoursWorkedContent;

  private JLabel overtimeLabel;
  private JRadioButton flexRB;
  private JLabel flexLabel;
  private JRadioButton paidRB;
  private JLabel paidLabel;
  private JRadioButton compRB;
  private JLabel compLabel;
  private ButtonGroup overtimeGroup;

  private JCheckBox isReportedCB;
  private JLabel isReportedLabel;

  private JButton writeJournalBT;
  private JButton createNewTabBT;

  public DayViewPanel(final Settings user) {
    super();
    this.user = user;
    date = new DateTime();
    currentDay = new WorkDay(date);
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
      activityPanel.add(new ActivityPanel(currentDay, user));
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
    layout.addRow("o<^o~b._>");
    layout.addRow("c<^c....b");

    setLayout(layout);

    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    dateLabel = new JLabel(date.toDateTime().dayOfWeek().getAsText() + " " +
                           date.getDayOfMonth() + "/" +
                           date.getMonthOfYear() + " v." +
                           date.getWeekOfWeekyear() + " " +
                           date.getYear());
    dateLabel.setFont(font);
    add(dateLabel, "d");

    activityPanel = new JTabbedPane();
    activityPanel.add(new ActivityPanel(currentDay, user));
    add(activityPanel, "a");

    commonSettingsUpperLeftPanel = new JPanel();
    commonSettingsLayoutULP = ZoneLayoutFactory.newZoneLayout();
    commonSettingsLayoutULP.addRow("l<...l");
    commonSettingsLayoutULP.addRow("mnopqr");

    commonSettingsLowerLeftPanel = new JPanel();
    commonSettingsLayoutLLP = ZoneLayoutFactory.newZoneLayout();
    commonSettingsLayoutLLP.addRow("c>c2f<f");
    commonSettingsLayoutLLP.addRow("d>d.w<w");
    commonSettingsLayoutLLP.addRow("s>s.r..");
    commonSettingsLowerLeftPanel.setLayout(commonSettingsLayoutLLP);

    commonSettingsRightPanel = new JPanel();
    commonSettingsLayoutRP = ZoneLayoutFactory.newZoneLayout();
    commonSettingsLayoutRP.addRow("j._>j");
    commonSettingsLayoutRP.addRow("..6..");
    commonSettingsLayoutRP.addRow("n._>n");
    commonSettingsRightPanel.setLayout(commonSettingsLayoutRP);

    overtimeLabel = new JLabel("Hantera övertid som:");
    flexRB = new JRadioButton();
    flexLabel = new JLabel("Flex");
    paidRB = new JRadioButton();
    paidLabel = new JLabel("Betald");
    compRB = new JRadioButton();
    compLabel = new JLabel("Komp");
    overtimeGroup = new ButtonGroup();
    overtimeGroup.add(flexRB);
    overtimeGroup.add(paidRB);
    overtimeGroup.add(compRB);

    switch(currentDay.getTreatOvertimeAs()) {
      case FLEX:
        flexRB.setSelected(true);
        break;
      case PAID:
        paidRB.setSelected(true);
        break;
      case COMP:
        compRB.setSelected(true);
        break;
    }

    commonSettingsUpperLeftPanel.add(overtimeLabel, "l");
    commonSettingsUpperLeftPanel.add(flexRB, "m");
    commonSettingsUpperLeftPanel.add(flexLabel, "n");
    commonSettingsUpperLeftPanel.add(paidRB, "o");
    commonSettingsUpperLeftPanel.add(paidLabel, "p");
    commonSettingsUpperLeftPanel.add(compRB, "q");
    commonSettingsUpperLeftPanel.add(compLabel, "r");

    add(commonSettingsUpperLeftPanel, "o");

    flexBankLabel = new JLabel("Flexbank:");
    commonSettingsLowerLeftPanel.add(flexBankLabel, "c");
    flexBankContent =
      new JLabel(currentDay.getDayBalanceAsLocalTime().toString("H'h' m'min'"));
    commonSettingsLowerLeftPanel.add(flexBankContent, "f");

    hoursWorkedLabel = new JLabel("Jobbat idag:");
    commonSettingsLowerLeftPanel.add(hoursWorkedLabel, "d");
    hoursWorkedContent =
      new JLabel(currentDay.getDayBalanceAsLocalTime().toString("H'h' m'min'"));
    commonSettingsLowerLeftPanel.add(hoursWorkedContent, "w");

    isReportedCB = new JCheckBox();
    commonSettingsLowerLeftPanel.add(isReportedCB, "s");
    isReportedLabel = new JLabel("Rapporterad");
    commonSettingsLowerLeftPanel.add(isReportedLabel, "r");

    add(commonSettingsLowerLeftPanel, "c");

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