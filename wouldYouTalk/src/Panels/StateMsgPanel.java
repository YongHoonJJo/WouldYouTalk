package Panels;

import java.awt.Color;

import javax.swing.*;

public class StateMsgPanel extends JPanel {
	private JLabel msgLabel;
	
	public StateMsgPanel(String msg) { 
		msgLabel = new JLabel(msg);	
		add(msgLabel);
		//setBackground(Color.WHITE);
		setOpaque(false);
	}
	
	public void setMsgLabel(String newMsg) {
		msgLabel.setText(newMsg);
	}
}
