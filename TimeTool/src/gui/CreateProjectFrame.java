package gui;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import persistency.projects.Company;
import persistency.projects.ProjectAdder;
import persistency.projects.ProjectSet;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class CreateProjectFrame extends JFrame implements 
    ActionListener, FocusListener {
  private static final long serialVersionUID = 1L;
  
  private final ProjectSet projectSet;
  
  private ZoneLayout basePanelLayout;
  private JPanel basePanel;
  private ZoneLayout upperPanelLayout;
  private JPanel upperPanel;
  private ZoneLayout lowerPanelLayout;
  private JPanel lowerPanel;

  private JLabel compLabel;
  private JComboBox compCoB;
  
  private JLabel nameLabel;
  private JTextField nameTF;
  
  private JLabel shortNameLabel;
  private JTextField shortNameTF;
  
  private JLabel reportCodeLabel;
  private JTextField reportCodeTF;
  
  private JButton cancelBT;
  private JButton applyBT;
  private JButton okBT;
  
  public CreateProjectFrame(final ProjectSet projectSet) {
    super();
    this.projectSet = projectSet;
    initComponents();
    pack();
  }
  
  /**
   * Update the combo box in case companies has been added.
   */
  public void focusGained(final FocusEvent e) {
    compCoB = new JComboBox(getComboContents());
  }
  
  public void focusLost(FocusEvent e) {}

  public void actionPerformed(final ActionEvent e) {
    if (e.getSource().equals(okBT)) {
      if (validInput()) {
        java.awt.EventQueue.invokeLater(new ProjectAdder(this, 
                                                         getSelectedComp().getId(),
                                                         nameTF.getText(), 
                                                         shortNameTF.getText(),
                                                         projectSet));
        setVisible(false);
        dispose();
      }
    } else if (e.getSource().equals(cancelBT)) {
      setVisible(false);
      dispose();
    } else if (e.getSource().equals(applyBT)) {
      if (validInput()) {
        nameTF.setText("");
        shortNameTF.setText("");
        java.awt.EventQueue.invokeLater(new ProjectAdder(this, 
                                                         getSelectedComp().getId(),
                                                         nameTF.getText(), 
                                                         shortNameTF.getText(),
                                                         projectSet));
      }
    } 
  }

  private void initComponents() {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    setTitle("Projekt");
    setLocationRelativeTo(null); // Centers the window on the screen.
    
    basePanelLayout = ZoneLayoutFactory.newZoneLayout();
    basePanelLayout.addRow("a~-a");
    basePanelLayout.addRow("b.>b");
    
    basePanel = new JPanel(basePanelLayout);
      
    upperPanelLayout = ZoneLayoutFactory.newZoneLayout();
    upperPanelLayout.addRow("a>a2b-~b", "valueRow");
    upperPanelLayout.addRow("....6...", "valueRow");
    
    upperPanel = new JPanel(upperPanelLayout);
    upperPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Skapa nytt projekt"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
    lowerPanelLayout = ZoneLayoutFactory.newZoneLayout();
    lowerPanelLayout.addRow("a>a2c2o");
    
    lowerPanel = new JPanel(lowerPanelLayout);

    upperPanelLayout.insertTemplate("valueRow");
    compLabel = new JLabel("Företag:");
    upperPanel.add(compLabel, "a");

    compCoB = new JComboBox(getComboContents());
    compCoB.setSelectedIndex(0);
    
    upperPanel.add(compCoB, "b");
    
    upperPanelLayout.insertTemplate("valueRow");
    nameLabel = new JLabel("Namn:");
    upperPanel.add(nameLabel, "a");
    nameTF = new JTextField();
    upperPanel.add(nameTF, "b");
    
    upperPanelLayout.insertTemplate("valueRow");
    shortNameLabel = new JLabel("Kortnamn:");
    upperPanel.add(shortNameLabel, "a");
    shortNameTF = new JTextField();
    upperPanel.add(shortNameTF, "b");
    
    upperPanelLayout.insertTemplate("valueRow");
    reportCodeLabel = new JLabel("Rapportkod:");
    upperPanel.add(reportCodeLabel, "a");
    reportCodeTF = new JTextField();
    upperPanel.add(reportCodeTF, "b");
    
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
  
  private String[] getComboContents() {
    List<String> names = new ArrayList<String>();
    if (projectSet.getCompanies() != null) {
      for (Company comp : projectSet.getCompanies()) {
        names.add(comp.getName());
      }
    } 
    return names.isEmpty() ? new String[]{"Inga skapade än"} : 
                             names.toArray(new String[0]);
  }
  
  /**
   * Translates the contents in the company combo box to the actual company 
   * object.
   * @return the in the combo box currently selected company, returns null if no 
   * companies
   */
  private Company getSelectedComp() {
    final String compName = (String) compCoB.getSelectedItem();
    return projectSet.getCompanyByName(compName);
  }
  
  private boolean validInput() {
    StringBuilder errorMsg = new StringBuilder();
    if (nameTF.getText().isEmpty()) {
       errorMsg.append("Projektets namn\n");
    }
    
    if (reportCodeTF.getText().isEmpty()) {
       errorMsg.append("Projektets rapportkod\n");
    }
    
    if (getSelectedComp() == null) {
      errorMsg.append("Företag med vilket projektet skall associeras\n");
    }
        
    errorMsg.trimToSize();
    
    if (errorMsg.length() > 0) {
      JOptionPane.showMessageDialog(this,
                                    "Följande information saknas " +
                                    "fortfarande:\n" + errorMsg,
                                    "Nödvändig information saknas",
                                    JOptionPane.ERROR_MESSAGE);
      return false;
    } else {
      return true;
    }
  }
}