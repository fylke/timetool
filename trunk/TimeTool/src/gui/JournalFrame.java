package gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.joda.time.LocalDateTime;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class JournalFrame extends JFrame {
  private static final long serialVersionUID = 1L;
  
  private JPanel basePanel;
  private ZoneLayout layout;
  
  private JLabel heading;
  private Font font;
  private LocalDateTime date;
  
  private JScrollPane textAreaPanel;
  private JTextArea journalTA;
  
  private JButton cancelBT;
  private JButton applyBT;
  private JButton okBT;
  
  public JournalFrame() {
    super();
    initComponents();
    pack();
  }
  
  private void initComponents() {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    setTitle("Dagbok");
    setLocationRelativeTo(null); // Centers the window on the screen.

    font = new Font("Arial", 1, 20);
    
    basePanel = new JPanel();
    basePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      
    layout = ZoneLayoutFactory.newZoneLayout();
    layout.addRow("h<h...........");
    layout.addRow("......6.......");
    layout.addRow("t.............");
    layout.addRow("......+*......");
    layout.addRow(".............t");
    layout.addRow("......6.......");
    layout.addRow("b...........>b");
    basePanel.setLayout(layout);
    
    date = new LocalDateTime();
    heading = new JLabel(date.dayOfWeek().getAsText() + " " + 
                         date.getDayOfMonth() + "/" +
                         date.getMonthOfYear() + " v." +
                         date.getWeekOfWeekyear() + " " +
                         date.getYear());
    heading.setFont(font);
    basePanel.add(heading, "h");
    
    journalTA = new JTextArea();
    textAreaPanel = new JScrollPane(journalTA);
    textAreaPanel.setPreferredSize(new Dimension(400, 200));
    basePanel.add(textAreaPanel, "t");
    
    applyBT = new JButton("Spara");
    basePanel.add(applyBT, "b");
    cancelBT = new JButton("Avbryt");
    basePanel.add(cancelBT, "b");
    okBT = new JButton("OK");
    basePanel.add(okBT, "b");
    
    add(basePanel);
  }
}