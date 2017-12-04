package Panels;

import java.awt.Color;

import javax.swing.JPanel;

import WouldYouTalk.MainView;
import WouldYouTalk.UserInfo;

public class ChatRoomPanel extends JPanel {

	private UserInfo user;
	
	public ChatRoomPanel(UserInfo user) {
		this.user = user;
		
		if(MainView.getListChatting().hasChatRoom(user.getID())) {
			// ��ȭ ��������
			System.out.println(user.getID()+" hasChatRoom");
		}
		else {
			// ����..
			System.out.println(user.getID()+" NoChatRoom");
		}
		this.setBackground(Color.PINK);
	}
	
}
