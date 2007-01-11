package gui;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class CreateActivityFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = -8898541990178357181L;
  
  private ZoneLayout basePanelLayout;
  private JPanel basePanel;
  private ZoneLayout upperPanelLayout;
  private JPanel upperPanel;
  private ZoneLayout lowerPanelLayout;
  private JPanel lowerPanel;
  
  private JLabel nameLabel;
  private JTextField nameTF;
  
  private JLabel reportCodeLabel;
  private JTextField reportCodeTF;
  
  private JLabel companyLabel;
  private JComboBox companyCoB;
  private JButton newCompanyBT;
  
  private JLabel projectLabel;
  private JComboBox projectCoB;
  private JButton newProjectBT;
  
  private JButton cancelBT;
  private JButton applyBT;
  private JButton okBT;
  
  private final String[] companies = { "Ericsson", "Combitech" };
  private final String[] projects  = { "R4", "R5" };
  
  public CreateActivityFrame() {
    super();
    initComponents();
    pack();
  }
  
  public void actionPerformed(final ActionEvent e) {
    if (e.getSource().equals(newCompanyBT)) {
      java.awt.EventQueue.invokeLater(
          new Runnable() {
            public void run() {
              new CreateCompanyFrame().setVisible(true);
            }
          }
        );
    } else if (e.getSource().equals(newProjectBT)) {
      java.awt.EventQueue.invokeLater(
          new Runnable() {
            public void run() {
              new CreateProjectFrame().setVisible(true);
            }
          }
        );
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
    upperPanelLayout.addRow("a>a.b.-~..b", "valueRow");
    upperPanelLayout.addRow(".....6.....", "valueRow");
    upperPanelLayout.addRow("c>c2d<d2e<e", "comboRow");
    upperPanelLayout.addRow(".....6.....", "comboRow");
    
    upperPanel = new JPanel(upperPanelLayout);
    upperPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Skapa ny aktivitet"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
    lowerPanelLayout = ZoneLayoutFactory.newZoneLayout();
    lowerPanelLayout.addRow("a>a2c>c2o>o");
    
    lowerPanel = new JPanel(lowerPanelLayout);
       
    upperPanelLayout.insertTemplate("valueRow");
    nameLabel = new JLabel("Namn:");
    upperPanel.add(nameLabel, "a");
    nameTF = new JTextField();
    upperPanel.add(nameTF, "b");
    
    upperPanelLayout.insertTemplate("valueRow");
    reportCodeLabel = new JLabel("Rapportkod:");
    upperPanel.add(reportCodeLabel, "a");
    reportCodeTF = new JTextField();
    upperPanel.add(reportCodeTF, "b");
    
    upperPanelLayout.insertTemplate("comboRow");
    companyLabel = new JLabel("Företag:");
    upperPanel.add(companyLabel, "c");
    companyCoB = new JComboBox(companies);
    upperPanel.add(companyCoB, "d");
    newCompanyBT = new JButton("Nytt");
    newCompanyBT.addActionListener(this);
    upperPanel.add(newCompanyBT, "e");
    
    upperPanelLayout.insertTemplate("comboRow");
    projectLabel = new JLabel("Projekt:");
    upperPanel.add(projectLabel, "c");
    projectCoB = new JComboBox(projects);
    upperPanel.add(projectCoB, "d");
    newProjectBT = new JButton("Nytt");
    newProjectBT.addActionListener(this);
    upperPanel.add(newProjectBT, "e");
    basePanel.add(upperPanel, "a");
    
    lowerPanel = new JPanel();
    applyBT = new JButton("Spara");
    lowerPanel.add(applyBT, "a");
    cancelBT = new JButton("Avbryt");
    lowerPanel.add(cancelBT, "c");
    okBT = new JButton("OK");
    lowerPanel.add(okBT, "o");
    basePanel.add(lowerPanel, "b");
    
    add(basePanel);
  }
}
