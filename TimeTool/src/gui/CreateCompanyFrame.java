package gui;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import persistency.projects.CompanyAdder;
import persistency.projects.ProjectSet;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class CreateCompanyFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;
  
  private final ProjectSet projectSet;

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
  
  private JLabel empIdLabel;
  private JTextField empIdTF;
  
  private JButton cancelBT;
  private JButton applyBT;
  private JButton okBT;
  
  public CreateCompanyFrame(final ProjectSet projectSet) {
    super();
    this.projectSet = projectSet;
    initComponents();
    pack();
  }
  
  public void actionPerformed(final ActionEvent e) {
    if (e.getSource().equals(okBT)) {
      if (validInput()) {
        java.awt.EventQueue.invokeLater(new CompanyAdder(this, nameTF.getText(), 
                                                         shortNameTF.getText(),
                                                         empIdTF.getText(),
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
        empIdTF.setText("");
        java.awt.EventQueue.invokeLater(new CompanyAdder(this, nameTF.getText(), 
                                                         shortNameTF.getText(),
                                                         empIdTF.getText(),
                                                         projectSet));
      }
    } 
  }

  private void initComponents() {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    setTitle("F�retag");
    setLocationRelativeTo(null); // Centers the window on the screen.
    
    basePanelLayout = ZoneLayoutFactory.newZoneLayout();
    basePanelLayout.addRow("a~-a");
    basePanelLayout.addRow("b.>b");
    
    basePanel = new JPanel(basePanelLayout);
      
    upperPanelLayout = ZoneLayoutFactory.newZoneLayout();
    upperPanelLayout.addRow("a>a2b.-~..b", "valueRow");
    upperPanelLayout.addRow(".....6.....", "valueRow");
    
    upperPanel = new JPanel(upperPanelLayout);
    upperPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Skapa nytt f�retag"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
    lowerPanelLayout = ZoneLayoutFactory.newZoneLayout();
    lowerPanelLayout.addRow("a2c2o");
    
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
    empIdLabel = new JLabel("Anst�llningsnr:");
    upperPanel.add(empIdLabel, "a");
    empIdTF = new JTextField();
    upperPanel.add(empIdTF, "b");
    
    basePanel.add(upperPanel, "a");
    
    lowerPanel = new JPanel();
    applyBT = new JButton("Spara");
    lowerPanel.add(applyBT, "a");
    cancelBT = new JButton("Avbryt");
    lowerPanel.add(cancelBT, "c");
    okBT = new JButton("OK");
    okBT.addActionListener(this);
    lowerPanel.add(okBT, "o");
    
    basePanel.add(lowerPanel, "b");
    
    add(basePanel);
  }
  
  private boolean validInput() {
    StringBuilder errorMsg = new StringBuilder();
    if (nameTF.getText().isEmpty()) {
       errorMsg.append("F�retagets namn\n");
    }
    
    if (empIdTF.getText().isEmpty()) {
       errorMsg.append("Anst�llningsnummer hos f�retaget\n");
    }
        
    errorMsg.trimToSize();
    
    if (errorMsg.length() > 0) {
      JOptionPane.showMessageDialog(this,
                                    "F�ljande information saknas " +
                                    "fortfarande:\n" + errorMsg,
                                    "N�dv�ndig information saknas",
                                    JOptionPane.ERROR_MESSAGE);
      return false;
    } else {
      return true;
    }
  }
}