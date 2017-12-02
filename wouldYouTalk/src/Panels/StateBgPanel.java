package Panels;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StateBgPanel extends JPanel {
	//private String userStateMsg;
	private JLabel stateMsgLabel;
	// backGround Img
	
	public StateBgPanel(String stateMsg) {
		setBackground(Color.YELLOW);
		
		stateMsgLabel = new JLabel(stateMsg);
		stateMsgLabel.setBounds(135, 100, 50, 50); // 크기 동적으로 계산해보기..
		add(stateMsgLabel);
		
		setLayout(null);
		//setBounds(0, 0,  328, 300);
	}
}
