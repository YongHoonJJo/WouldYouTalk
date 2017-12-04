package WouldYouTalk;

import javax.swing.JFrame;

import Panels.ChatAdditionalPanel;
import Panels.ChatRoomPanel;
import Panels.ChatTextAreaPanel;
import Panels.FriendPanel;

public class ChatView extends JFrame {
	private FriendPanel chatUserInfo;
	private ChatRoomPanel chatRoomPanel;
	private ChatTextAreaPanel chatTextAreaPanel;
	private ChatAdditionalPanel chatAdditionalPanel;
	
	public ChatView(UserInfo user) {
		setBounds(10, 10, 415, 620);
		setVisible(true);
		setLayout(null);
		
		chatUserInfo = new FriendPanel(user);
		chatUserInfo.setBounds(0,  0,  400, 50);
		add(chatUserInfo);
		
		chatRoomPanel = new ChatRoomPanel(user);
		chatRoomPanel.setBounds(0, 50, 400, 400);
		add(chatRoomPanel);
		
		chatTextAreaPanel = new ChatTextAreaPanel(user);
		chatTextAreaPanel.setBounds(0, 450, 400, 90);
		add(chatTextAreaPanel);
		
		chatAdditionalPanel = new ChatAdditionalPanel();
		chatAdditionalPanel.setBounds(0,  540, 400, 40);
		add(chatAdditionalPanel);
	}
}
