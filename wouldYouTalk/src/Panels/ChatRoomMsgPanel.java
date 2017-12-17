package Panels;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import WouldYouTalk.UserInfo;

public class ChatRoomMsgPanel extends JPanel {

	private JLabel lb;
	
	public ChatRoomMsgPanel(UserInfo userInfo, boolean fromMe, String msgTime, String msg) {
		setLayout(null);
		lb = new JLabel(msg);
		add(lb);
		lb.setBounds(0, 0, 200, 50);
		setBackground(Color.WHITE);
	}
}
