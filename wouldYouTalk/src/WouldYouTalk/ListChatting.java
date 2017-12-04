package WouldYouTalk;

import java.awt.Color;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;

public class ListChatting extends JPanel {
	
	private Vector<Integer> chatNumVec;
	private HashMap<String, Integer> userToChatNum;
	
	public ListChatting() {
		chatNumVec = new Vector<Integer>();
		userToChatNum = new HashMap<String, Integer>(); 
		setBackground(Color.WHITE);
	}
	
	public boolean hasChatRoom(String userID) {
		return userToChatNum.containsKey(userID);
	}
	
	public Integer getChatNum(String userID) {
		return userToChatNum.get(userID);
	}
}
