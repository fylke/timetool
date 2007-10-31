package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.joda.time.LocalDateTime;

import com.atticlabs.zonelayout.swing.ZoneLayout;
import com.atticlabs.zonelayout.swing.ZoneLayoutFactory;

public class TimePanel extends JPanel implements ActionListener {
  private static final long serialVersionUID = 1L;

  private ZoneLayout layout;
  private ZoneLayout layoutUpper;
  private ZoneLayout layoutLower;
  
  private Font font;
  private Font timeFont;
  private LocalDateTime date;
  
  private JPanel upperPanel;
//  private JPanel lowerPanel;
  
//  private JRadioButton startFinishRB;
//  private JRadioButton percentEstimateRB;
//  private ButtonGroup radioGroup;
  
  private JLabel startLabel;
  private JTextField startTF;
  private JButton setStartToNowBT;
  private JLabel timeLabel;
  private JLabel finishLabel;
  private JTextField finishTF;
  private JButton setFinishToNowBT;
  
//  private JLabel percentEstLabel;
//  private JTextField percentEstTF;
//  private JLabel bigSigmaLabel;

  public TimePanel() {
    super();
    initComponents();
  }
  
  public void actionPerformed(final ActionEvent ae) {
    if (ae.getSource().equals(setStartToNowBT)) {
      date = new LocalDateTime();
      startTF.setText(date.toString("HH:mm"));
    } else if (ae.getSource().equals(setFinishToNowBT)) {
      date = new LocalDateTime();
      finishTF.setText(date.toString("HH:mm"));
    } 
  }
  
  private void initComponents() {
    font = new Font("Arial", 1, 12);
    timeFont = new Font("Arial", 1, 36);
    
    layout = ZoneLayoutFactory.newZoneLayout();
    layout.addRow("a<a~t~");
//    layout.addRow("b<b...");
    
    setLayout(layout);
    
    setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), 
                                                 "Tidsåtgång"));
    
    layoutUpper = ZoneLayoutFactory.newZoneLayout();
    layoutUpper.addRow("r2s2v2n");
    layoutUpper.addRow("...6...");
    layoutUpper.addRow("..f.x.m");
    layoutUpper.addRow("...6...");
    
    layoutLower = ZoneLayoutFactory.newZoneLayout();
    layoutLower.addRow("t2p2y2z");
    
    upperPanel = new JPanel(layoutUpper);
    
//    startFinishRB = new JRadioButton();
//    percentEstimateRB = new JRadioButton();    
//    radioGroup = new ButtonGroup();
//    radioGroup.add(startFinishRB);
//    radioGroup.add(percentEstimateRB);
//    startFinishRB.setSelected(true);
//    upperPanel.add(startFinishRB, "r");
        
    startLabel = new JLabel("Började:");
    startLabel.setFont(font);
    upperPanel.add(startLabel, "s");
    startTF = new JTextField(3);
    startTF.setHorizontalAlignment(SwingConstants.RIGHT);
    upperPanel.add(startTF, "v");
    setStartToNowBT = new JButton("Nu");
    setStartToNowBT.setToolTipText("Sätt aktivitetens starttid till aktuellt klockslag");
    setStartToNowBT.addActionListener(this);
    upperPanel.add(setStartToNowBT, "n");
        
    finishLabel = new JLabel("Slutade:");
    finishLabel.setFont(font);
    upperPanel.add(finishLabel, "f");
    finishTF = new JTextField(3);
    finishTF.setHorizontalAlignment(SwingConstants.RIGHT);
    upperPanel.add(finishTF, "x");
    setFinishToNowBT = new JButton("Nu");
    setFinishToNowBT.setToolTipText("Sätt aktivitetens sluttid till aktuellt klockslag");
    setFinishToNowBT.addActionListener(this);
    upperPanel.add(setFinishToNowBT, "m");
    
    add(upperPanel, "a");
    
    date = new LocalDateTime();
    timeLabel = new JLabel(date.toString("HH:mm"));
    timeLabel.setFont(timeFont);
    add(timeLabel, "t");
    
//    lowerPanel = new JPanel(layoutLower);
//    
//    lowerPanel.add(percentEstimateRB, "t");
//    
//    percentEstLabel = new JLabel("Procentskattning:");
//    percentEstLabel.setFont(font);
//    lowerPanel.add(percentEstLabel, "p");
//    percentEstTF = new JTextField(2);
//    percentEstTF.setHorizontalAlignment(SwingConstants.RIGHT);
//    lowerPanel.add(percentEstTF, "y");
//    bigSigmaLabel = new JLabel("\u03A3" + "40%");
//    bigSigmaLabel.setFont(font);
//    lowerPanel.add(bigSigmaLabel, "z");
//    
//    add(lowerPanel, "b");
  }
}