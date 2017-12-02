package Panels;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StateInfoPanel extends JPanel {
	//private String userName;
	
	private JLabel userNameLabel;
	
	private JButton chatBtn;
	private JButton profileConfigBtn;
	
	public StateInfoPanel(String userName) {
		setBackground(Color.WHITE);
		
		userNameLabel = new JLabel(userName);
		userNameLabel.setBounds(135, 30, 50, 50); // 크기 동적으로 계산해보기..
		add(userNameLabel);
		
		chatBtn = new JButton("1:1 채팅");
		chatBtn.setBounds(110, 80, 100, 30);
		add(chatBtn);
		
		setLayout(null);
		
		//setBounds(0, 300, 328, 218);
	}
}
