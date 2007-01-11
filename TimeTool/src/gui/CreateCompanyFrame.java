package gui;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class CreateCompanyFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = -5152709772251665214L;

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
  
  private JButton cancelBT;
  private JButton applyBT;
  private JButton okBT;
  
  public CreateCompanyFrame() {
    super();
    initComponents();
    pack();
  }
  
  public void actionPerformed(final ActionEvent e) {
    // TODO Auto-generated method stub

  }

  private void initComponents() {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    setTitle("Företag");
    setLocationRelativeTo(null); // Centers the window on the screen.
    
    basePanelLayout = ZoneLayoutFactory.newZoneLayout();
    basePanelLayout.addRow("a~-a");
    basePanelLayout.addRow("b.>b");
    
    basePanel = new JPanel(basePanelLayout);
      
    upperPanelLayout = ZoneLayoutFactory.newZoneLayout();
    upperPanelLayout.addRow("a>a2b.-~..b", "valueRow");
    upperPanelLayout.addRow(".....6.....", "valueRow");
    
    upperPanel = new JPanel(upperPanelLayout);
    upperPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Skapa nytt företag"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
    lowerPanelLayout = ZoneLayoutFactory.newZoneLayout();
    lowerPanelLayout.addRow("a>a2c>c2o>o");
    
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
