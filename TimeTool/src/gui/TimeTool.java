package gui;

import java.awt.ComponentOrientation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import persistency.settings.Settings;

public class TimeTool extends JFrame {
  private static final long serialVersionUID = 1L;
  
  private JTabbedPane background;
  private JPanel dayViewPane;
  private JPanel weekViewPane;

  public TimeTool() {
    super();
    initComponents();
    pack();
  }

  public static void main(final String[] args) {
    java.awt.EventQueue.invokeLater(
      new Runnable() {
        public void run() {
          new TimeTool().setVisible(true);
        }
      }
    );
  }
  
  private void initComponents() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    setTitle("TimeTool");
    setLocationRelativeTo(null); // Centers the window on the screen.

    background = new JTabbedPane();
    dayViewPane = new DayViewPanel(new Settings());
    weekViewPane = new WeekViewPanel();
    
    add(background);
    background.add(dayViewPane);
    background.add(weekViewPane);
  }
}