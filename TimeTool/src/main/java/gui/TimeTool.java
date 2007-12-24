package gui;

import gui.create.CreateUserFrame;
import gui.day.DayViewPanel;
import gui.week.WeekViewPanel;

import java.awt.ComponentOrientation;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.araneaframework.blocking.Resumable;
import org.araneaframework.blocking.aranea.AraneaBlockingUtil;

import persistency.PersistencyException;
import persistency.settings.Settings;
import persistency.settings.UserSettings;

public class TimeTool extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTabbedPane background;
	private JPanel dayViewPane;
	private JPanel weekViewPane;
	private final Settings user;

	public TimeTool(final Settings userSettings) {
		super();
		this.user = userSettings;
		initComponents();
		pack();
	}

	@Resumable
	public static void main(final String... args) {
		Settings user = createUser();

		new TimeTool(user).setVisible(true);
	}


	static Settings createUser() {
		final Settings userSettings = new UserSettings();
		try {
			userSettings.populate();
		} catch (final FileNotFoundException e) {
			AraneaBlockingUtil.call(getFlowContext(), new CreateUserFrame(userSettings));
		} catch (final PersistencyException e) {
			throw new RuntimeException("Exception in initialization: " +  e.getMessage(), e);
		}
	}

	void initComponents() {
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