package WouldYouTalk;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import Panels.ChatAdditionalPanel;
import Panels.ChatRoomPanel;
import Panels.ChatTextAreaPanel;
import Panels.FriendPanel;

public class ChatView extends JFrame {
	private FriendPanel chatUserInfo;
	private ChatRoomPanel chatRoomPanel;
	private ChatTextAreaPanel chatTextAreaPanel;
	private ChatAdditionalPanel chatAdditionalPanel;
	
	private int chatNum;
	private UserInfo user; // 대화상대에 대한 정보
	private boolean chatViewOn = false;
	private ChatView cv;
	
	public ChatView(UserInfo user) {
		cv = this;
		chatNum = 0;
		this.user = user;
		ListChatting lc = MainView.getListChatting();
		chatViewOn = true;
		
		if(lc.hasChatNum(user.getID())) { // 기존 채팅방이 있는경우
			chatNum = lc.getChatNum(user.getID());
		}
		setBounds(10, 10, 415, 620);
		setVisible(true);
		setLayout(null);
		
		chatUserInfo = new FriendPanel(user);
		chatUserInfo.setBounds(0,  0,  400, 50);
		add(chatUserInfo);
		
		chatRoomPanel = new ChatRoomPanel(chatNum, user);
		//chatRoomPanel.setBounds(0, 50, 400, 400);
		//chatRoomPanel.setPreferredSize(new Dimension(400, 390));
		//add(chatRoomPanel);
		
		final JScrollPane scrollp = new JScrollPane(this.chatRoomPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollp.setBounds(0, 50, 400, 400);
		add(scrollp);
		chatRoomPanel.setChatRoomScrollp(scrollp);
		chatRoomPanel.initMsg();
		
		chatTextAreaPanel = new ChatTextAreaPanel(chatNum, user);
		chatTextAreaPanel.setBounds(0, 450, 400, 90);
		add(chatTextAreaPanel);
		
		chatAdditionalPanel = new ChatAdditionalPanel(chatNum, user);
		chatAdditionalPanel.setBounds(0,  540, 400, 40);
		add(chatAdditionalPanel);
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { 
            	user.setChatViewOn(false);
            	chatViewOn = false;
            	MainView.getListChatting().removeChatView(cv);
            }
		});
	}
	
	public void popImgOnChatRoom(boolean fromMe, String msgTime, byte[] bb) {
		chatRoomPanel.addImg(fromMe, msgTime, bb);
	}
	
	public void popMsgOnChatRoom(boolean fromMe, String msgTime, String sentMsg) {
		chatRoomPanel.addMsg(fromMe, msgTime, sentMsg);
	}
	
	public String getUserID() {
		return user.getID();
	}
	
	public boolean isChatViewOn() {
		return chatViewOn;
	}
	
	public void setChatNum(int chatNum) {
		this.chatNum = chatNum;
		chatTextAreaPanel.setChatNum(chatNum);
	}
}
