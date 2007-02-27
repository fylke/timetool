package gui;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

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
import persistency.projects.Project;
import persistency.settings.Settings;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class CreateActivityFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;
  
  Settings settings;
  Collection<Company> companies;
  Collection<Project> projects;
  
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
  
  public CreateActivityFrame(Settings settings) {
    super();
    this.settings = settings;
    this.companies = settings.getProjectSet().getCompanies();
    this.projects = getAllProjects();
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
    if (companies.isEmpty()) {
      companyCoB = new JComboBox(new String[]{"Inget företag skapat än"});
    } else {
      companyCoB = new JComboBox(companies.toArray(new String[0]));
    }
    companyCoB.setSelectedIndex(0);
    upperPanel.add(companyCoB, "d");
    newCompanyBT = new JButton("Nytt");
    newCompanyBT.addActionListener(this);
    upperPanel.add(newCompanyBT, "e");
    
    upperPanelLayout.insertTemplate("comboRow");
    projectLabel = new JLabel("Projekt:");
    upperPanel.add(projectLabel, "c");
    if (companies.isEmpty()) {
      projectCoB = new JComboBox(new String[]{"Inget företag skapat än"});
    } else if (projects.isEmpty()) {
      projectCoB = new JComboBox(new String[]{"Inga projekt skapade än"});
    } else {
      projectCoB = new JComboBox(projects.toArray(new String[0]));
    }
    projectCoB.setSelectedIndex(0);
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
  
  private Collection<Project> getAllProjects() {
    Collection<Project> allProjects = new ArrayList<Project>();
    for(Company comp : companies) {
      allProjects.addAll(comp.getProjects());
    }
    return allProjects;
  }
}