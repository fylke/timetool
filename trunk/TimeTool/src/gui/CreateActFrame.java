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

import persistency.projects.ActAdder;
import persistency.projects.Company;
import persistency.projects.Project;
import persistency.projects.ProjectSet;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class CreateActFrame extends JFrame implements 
    ActionListener, FocusListener {
  private static final long serialVersionUID = 1L;
    
  ProjectSet projectSet;
  
  private ZoneLayout basePanelLayout;
  private JPanel basePanel;
  private ZoneLayout upperPanelLayout;
  private JPanel upperPanel;
  private ZoneLayout lowerPanelLayout;
  private JPanel lowerPanel;
  
  private JLabel nameLabel;
  private JTextField nameTF;
  
  private JLabel shortNameLabel;
  private JTextField shortNameTF;
  
  private JLabel reportCodeLabel;
  private JTextField reportCodeTF;
  
  private JLabel compLabel;
  private JComboBox compCoB;
  private JButton newCompBT;
  
  private JLabel projLabel;
  private JComboBox projCoB;
  private JButton newProjBT;
  
  private JButton cancelBT;
  private JButton applyBT;
  private JButton okBT;
  
  public CreateActFrame(final ProjectSet projectSet) {
    super();
    this.projectSet = projectSet;
    initComponents();
    pack();
  }
  
  /**
   * Update the combo boxes in case companies or projects have been added.
   */
  public void focusGained(final FocusEvent e) {
    compCoB = new JComboBox(getComboContents("company"));
    projCoB = new JComboBox(getComboContents("project"));
  }
  
  public void focusLost(FocusEvent e) {}
  
  public void actionPerformed(final ActionEvent e) {
    if (e.getSource().equals(newCompBT)) {
      java.awt.EventQueue.invokeLater(
          new Runnable() {
            public void run() {
              // TODO Link to parent?
              final JFrame createComp = new CreateCompanyFrame(projectSet);
              createComp.setVisible(true);
            }
          }
        );
    } else if (e.getSource().equals(newProjBT)) {
      java.awt.EventQueue.invokeLater(
          new Runnable() {
            public void run() {
              final JFrame createProj = new CreateProjectFrame(projectSet);
              createProj.setVisible(true);
            }
          }
        );
    } else if (e.getSource().equals(okBT)) {
      if (validInput()) {
        java.awt.EventQueue.invokeLater(new ActAdder(this, 
                                                     getSelectedComp().getId(), 
                                                     getSelectedProj().getId(),
                                                     nameTF.getText(), 
                                                     shortNameTF.getText(),
                                                     reportCodeTF.getText(),
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
        reportCodeTF.setText("");
        java.awt.EventQueue.invokeLater(new ActAdder(this, 
                                                     getSelectedComp().getId(), 
                                                     getSelectedProj().getId(),
                                                     nameTF.getText(), 
                                                     shortNameTF.getText(),
                                                     reportCodeTF.getText(),
                                                     projectSet));
      }
    }
  }
  
  private void initComponents() {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    setTitle("Aktiviteter");
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
    upperPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Skapa ny aktivitet"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
    lowerPanelLayout = ZoneLayoutFactory.newZoneLayout();
    lowerPanelLayout.addRow("a>a2c2o");
    
    lowerPanel = new JPanel(lowerPanelLayout);
       
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
    
    upperPanelLayout.insertTemplate("comboRow");
    compLabel = new JLabel("Företag:");
    upperPanel.add(compLabel, "c");
    compCoB = new JComboBox(getComboContents("company"));
    compCoB.setSelectedIndex(0);
    upperPanel.add(compCoB, "d");
    newCompBT = new JButton("Nytt");
    newCompBT.addActionListener(this);
    upperPanel.add(newCompBT, "e");
    
    upperPanelLayout.insertTemplate("comboRow");
    projLabel = new JLabel("Projekt:");
    upperPanel.add(projLabel, "c");
    projCoB = new JComboBox(getComboContents("project"));
    projCoB.setSelectedIndex(0);
    upperPanel.add(projCoB, "d");
    newProjBT = new JButton("Nytt");
    newProjBT.addActionListener(this);
    upperPanel.add(newProjBT, "e");
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
  
  private String[] getComboContents(final String kind) {
    List<String> names = new ArrayList<String>();
    if ("company".equalsIgnoreCase(kind) && projectSet.getCompanies() != null) {
      for (Company comp : projectSet.getCompanies()) {
        names.add(comp.getName());
      }
    } else if ("project".equalsIgnoreCase(kind)) {
      final Company currComp = getSelectedComp();
      
      if (currComp != null && currComp.getProjects() != null) {
        for (Project proj : currComp.getProjects()) {
          names.add(proj.getName());
        }
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
  
  private Project getSelectedProj() {
    final Company comp = getSelectedComp();
    if (comp != null) {
      final String projName = (String) projCoB.getSelectedItem();
      return comp.getProjectByName(projName); 
    }
    return null;
  }
  
  private boolean validInput() {
    StringBuilder errorMsg = new StringBuilder();
    if (nameTF.getText().isEmpty()) {
       errorMsg.append("Aktivitetens namn\n");
    }
    
    if (reportCodeTF.getText().isEmpty()) {
       errorMsg.append("Aktivitetens rapportkod\n");
    }
    
    if (getSelectedComp() == null) {
      errorMsg.append("Företag med vilket aktiviteten skall associeras\n");
    }
    
    if (getSelectedProj() == null) {
      errorMsg.append("Projekt med vilket aktiviteten skall associeras\n");
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