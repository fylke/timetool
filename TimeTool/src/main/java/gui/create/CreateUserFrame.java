package gui.create;

import gui.MyComboBox;
import gui.MyComboBoxDisplayable;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.araneaframework.uilib.core.BaseUIWidget;

import persistency.PersistencyException;
import persistency.company.Company;
import persistency.company.ProjectSet;
import persistency.settings.Settings;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class CreateUserFrame extends BaseUIWidget implements ActionListener {
	private static final long serialVersionUID = 1L;

	private ZoneLayout basePanelLayout;
	private JPanel basePanel;
	private ZoneLayout upperPanelLayout;
	private JPanel upperPanel;
	private ZoneLayout lowerPanelLayout;
	private JPanel lowerPanel;

	private JLabel firstNameLabel;
	private JTextField firstNameTF;

	private JLabel lastNameLabel;
	private JTextField lastNameTF;

	private JLabel compLabel;
	private MyComboBox compCoB;
	private final String compEmptyMsg = "Inga företag definierade";
	private JButton newCompBT;

	private JButton cancelBT;
	private JButton applyBT;
	private JButton okBT;

	private final Settings userSettings;

	public CreateUserFrame(final Settings userSettings) {
		super();
		this.userSettings = userSettings;
		initComponents();
		pack();
		setVisible(true);
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource().equals(newCompBT)) {
			java.awt.EventQueue.invokeLater(
					new Runnable() {
						public void run() {
							// TODO Would be nice to return to the same frame when clicking
							//      on newCompBT if a CreateCompFrame is already open.
							final JFrame createComp = new CreateCompanyFrame();
							createComp.setVisible(true);
						}
					}
				);
		} else if (e.getSource().equals(okBT)) {
			if (isValidInput()) {
				storeUser(userSettings);
				setVisible(false);
				dispose();
			} else {

			}
		} else if (e.getSource().equals(cancelBT)) {
			setVisible(false);
			dispose();
		} else if (e.getSource().equals(applyBT)) {
			if (isValidInput()) {
				storeUser(userSettings);
			}
		}
	}

	void storeUser(final Settings user) {
		user.setFirstName(firstNameTF.getText());
		user.setLastName(lastNameTF.getText());
		user.setEmployerId(((Company) compCoB.getSelected()).getId());

		try {
			user.store();
		} catch (final PersistencyException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(),
																		"File error", JOptionPane.ERROR_MESSAGE);
		}
	}

	void initComponents() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		setTitle("Ny användare");
		setLocationRelativeTo(null); // Centers the window on the screen.

		basePanelLayout = ZoneLayoutFactory.newZoneLayout();
		basePanelLayout.addRow("a~-a");
		basePanelLayout.addRow("b.>b");

		basePanel = new JPanel(basePanelLayout);

		upperPanelLayout = ZoneLayoutFactory.newZoneLayout();
		upperPanelLayout.addRow("a>a.b-~b", "valueRow");
		upperPanelLayout.addRow("....6...", "valueRow");
		upperPanelLayout.addRow("c>c2d2e.", "comboRow");
		upperPanelLayout.addRow("....6...", "comboRow");

		upperPanel = new JPanel(upperPanelLayout);
		final Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		final Border titledBorder = BorderFactory.createTitledBorder(etchedBorder, "Skapa ny användare");
		final Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		upperPanel.setBorder(BorderFactory.createCompoundBorder(titledBorder, emptyBorder));

		lowerPanelLayout = ZoneLayoutFactory.newZoneLayout();
		lowerPanelLayout.addRow("a>a2c2o");

		lowerPanel = new JPanel(lowerPanelLayout);

		upperPanelLayout.insertTemplate("valueRow");
		firstNameLabel = new JLabel("Förnamn:");
		upperPanel.add(firstNameLabel, "a");
		firstNameTF = new JTextField();
		upperPanel.add(firstNameTF, "b");

		upperPanelLayout.insertTemplate("valueRow");
		lastNameLabel = new JLabel("Efternamn:");
		upperPanel.add(lastNameLabel, "a");
		lastNameTF = new JTextField();
		upperPanel.add(lastNameTF, "b");

		upperPanelLayout.insertTemplate("comboRow");
		compLabel = new JLabel("Jobbar:");
		upperPanel.add(compLabel, "c");
		compCoB = new MyComboBox(getComboContents(), compEmptyMsg);
		compCoB.setSelectedIndex(0);
		upperPanel.add(compCoB, "d");
		newCompBT = new JButton("Nytt");
		newCompBT.addActionListener(this);
		upperPanel.add(newCompBT, "e");
		basePanel.add(upperPanel, "a");

		lowerPanel = new JPanel();
		applyBT = new JButton("Spara");
		applyBT.addActionListener(this);
		lowerPanel.add(applyBT, "a");
		cancelBT = new JButton("Avbryt");
		cancelBT.addActionListener(this);
		lowerPanel.add(cancelBT, "c");
		okBT = new JButton("OK");
		okBT.addActionListener(this);
		lowerPanel.add(okBT, "o");
		basePanel.add(lowerPanel, "b");

		add(basePanel);
	}

	Vector<MyComboBoxDisplayable> getComboContents() {
		final Vector<MyComboBoxDisplayable> disps = new Vector<MyComboBoxDisplayable>();
		final ProjectSet ps = userSettings.getProjectSet();
		if (ps.getCompanies() != null) {
			disps.addAll(ps.getCompanies());
		}
		return disps;
	}

	boolean isValidInput() {
		final StringBuilder errorMsg = new StringBuilder();
		if (firstNameTF.getText().isEmpty()) {
			 errorMsg.append("Förnamn\n");
		}

		if (lastNameTF.getText().isEmpty()) {
			 errorMsg.append("Efternamn\n");
		}

		if (compCoB.getSelected() == null) {
			errorMsg.append("Arbetsgivare\n");
		}

		errorMsg.trimToSize();

		if (errorMsg.length() > 0) {
			final String title = "Nödvändig information saknas";
			JOptionPane.showMessageDialog(this,
																		"Följande information saknas " +
																		"fortfarande:\n" + errorMsg,
																		title, JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			return true;
		}
	}
}