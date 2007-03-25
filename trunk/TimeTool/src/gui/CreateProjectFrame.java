package gui;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import persistency.projects.Company;
import persistency.projects.ProjectAdder;
import persistency.projects.ProjectSet;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class CreateProjectFrame extends JFrame implements ActionListener {
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
  
  public void actionPerformed(final ActionEvent e) {
    if (e.getSource().equals(okBT)) {
      Company comp = new Company();
      compCoB.getSelectedItem();
      java.awt.EventQueue.invokeLater(new ProjectAdder(this, comp.getId(),
                                                       nameTF.getText(), 
                                                       shortNameTF.getText(),
                                                       projectSet));
      setVisible(false);
      dispose();
    } else if (e.getSource().equals(cancelBT)) {
      setVisible(false);
      dispose();
    } else if (e.getSource().equals(applyBT)) {
      nameTF.setText("");
      shortNameTF.setText("");
      Company comp = new Company();
      compCoB.getSelectedItem();
      java.awt.EventQueue.invokeLater(new ProjectAdder(this, comp.getId(),
                                                       nameTF.getText(), 
                                                       shortNameTF.getText(),
                                                       projectSet));
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
    compLabel = new JLabel("F�retag:");
    upperPanel.add(compLabel, "a");
   
    if (projectSet.getCompanies().isEmpty()) {
      //TODO N�r detta f�nster f�r fokus, uppdatera ComboBoxen
      compCoB = new JComboBox(new String[]{"Inga f�retag definerade"});
    } else {
      compCoB = new JComboBox(new Vector<Company>(projectSet.getCompanies()));
    }
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
}