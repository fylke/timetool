package gui;

import java.awt.ComponentOrientation;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import persistency.PersistencyException;
import persistency.settings.Settings;
import persistency.settings.UserSettings;

public class TimeTool extends JFrame {
  private static final long serialVersionUID = 1L;

  private JTabbedPane background;
  private JPanel dayViewPane;
  private JPanel weekViewPane;
  private Settings user;

  public TimeTool(final Settings userSettings) {
    super();
    this.user = userSettings;
    initComponents();
    pack();
  }

  public static void main(final String... args) {
  	Settings userSettings = new UserSettings();
    try {
			userSettings.populate();
		} catch (FileNotFoundException e) {
			new CreateUserFrame(userSettings).setVisible(true);
		} catch (PersistencyException e) {
			throw new RuntimeException("Exception in initialization: " +  e.getMessage(), e);
		}

    new TimeTool(userSettings).setVisible(true);
  }

  private void initComponents() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    setTitle("TimeTool");
    setLocationRelativeTo(null); // Centers the window on the screen.

    background = new JTabbedPane();
    dayViewPane = new DayViewPanel(user);
    weekViewPane = new WeekViewPanel(user);

    add(background);
    background.add(dayViewPane);
    background.add(weekViewPane);
  }
}