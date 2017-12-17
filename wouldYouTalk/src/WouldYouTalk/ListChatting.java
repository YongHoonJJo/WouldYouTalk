package WouldYouTalk;

import java.awt.Color;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;

public class ListChatting extends JPanel {
	private HashMap<String, Integer> userToChatNum; // ����id�� �����ϴ� ä�ù� ��ȣ
	private HashMap<Integer, Integer> chatNumToVecIdx; // ä�ù� ��ȭ�� ChatDataVec
	private Vector<ChatData> chatDataVec; // ä�õ�����
	private int chatDataCnt;
	
	private Vector<ChatView> chatViewVec;
	
	// ä���г�
	public ListChatting() {
		userToChatNum = new HashMap<String, Integer>(); 
		chatNumToVecIdx = new HashMap<Integer, Integer>();
		chatDataVec = new Vector<ChatData>();
		chatDataCnt = 0;
		
		chatViewVec = new Vector<ChatView>();
		
		setBackground(Color.WHITE);
	}
	
	public Vector<ChatData> getChatDataVec() {
		return chatDataVec;
	}
	
	public HashMap<String, Integer> getUserToChatNum() {
		return userToChatNum;
	}
	public int getChatNumToVecIdx(int chatNum) {
		return chatNumToVecIdx.get(chatNum);
	}
	
	public boolean hasChatNum(String userID) {
		return userToChatNum.containsKey(userID);
	}
	
/*** [IMG] // 1:1��ȭ ���� ���� ***/
	public void addChatImg(String msg, byte[] bb) {
		String[] data = msg.split("::");
		int chatNum = Integer.parseInt(data[1]);
		String recvID = data[2];
		String sentID = data[3];
		String msgTime = data[4];
		int byteSize = Integer.parseInt(data[5]);
		
		boolean fromMe = false;
		String partnerID = sentID; // ���� �޼����� ���� ���
		if(sentID.equals(LoginView.getMyID())) { // �������� �޼���
			fromMe = true;
			partnerID = recvID; // ���� �޼����� ���� ���
		}
		
		// ��ȭ ����
		if(userToChatNum.containsKey(partnerID)) { // ���� ä�ù��� �ִ°��
			int vecIdx = chatNumToVecIdx.get(userToChatNum.get(partnerID));
			ChatData chatDataTmp = chatDataVec.elementAt(vecIdx); 
			chatDataTmp.addMsg(msg+"@@"); // �޼��� �߰�.
			chatDataTmp.addImg(msgTime+":"+byteSize, bb);// ����Ʈ ����
			chatDataTmp.setChatDataInfo(chatNum, partnerID, "<Image>", msgTime);
		}
		else { // ù ��ȭ�� ���
			userToChatNum.put(partnerID, chatNum);
			chatNumToVecIdx.put(chatNum, chatDataCnt++);
			ChatData chatDataTmp = new ChatData(msg+"@@");
			chatDataVec.add(chatDataTmp);
			chatDataTmp.addImg(msgTime+":"+byteSize, bb); // ����Ʈ ����
			chatDataTmp.setChatDataInfo(chatNum, partnerID, "<Image>", msgTime);

			for(ChatView c : chatViewVec) {
				if(c.equals(partnerID) ) {
					c.setChatNum(chatNum);  // chatNum ����
					break;
				}
			}
		}

		ChatView cv = null;
		for(ChatView c : chatViewVec) {
			if(c.getUserID().equals(partnerID) ) {
				cv = c;
				break;
			}
		}

		// ä�÷뿡 ����, �����ִٸ�		
		if(cv != null && cv.isChatViewOn()) {			
			cv.popImgOnChatRoom(fromMe, msgTime, bb);
			//cv.popMsgOnChatRoom(fromMe, msgTime, sentMsg);
		}

		// ä�� �гο� ����


	}
	
/*** [MSG] // 1:1��ȭ ***/
	public void addChatData(String msg) {
		String[] data = msg.split("::");
		int chatNum = Integer.parseInt(data[1]);
		String recvID = data[2];
		String sentID = data[3];
		String msgTime = data[4];
		
		String sentMsg = "";
		for(int i=5; i<data.length; i++) sentMsg += data[i];
		
		//String MSG = "";
		//for(String s : data) MSG += (s+"::");
		
		boolean fromMe = false;
		String partnerID = sentID; // ���� �޼����� ���� ���
		if(sentID.equals(LoginView.getMyID())) { // �������� �޼���
			fromMe = true;
			partnerID = recvID; // ���� �޼����� ���� ���
		}
		
		// ��ȭ ����
		if(userToChatNum.containsKey(partnerID)) { // ���� ä�ù��� �ִ°��
			int vecIdx = chatNumToVecIdx.get(userToChatNum.get(partnerID));
			ChatData chatDataTmp = chatDataVec.elementAt(vecIdx); 
			chatDataTmp.addMsg(msg+"@@"); // �޼��� �߰�.
			chatDataTmp.setChatDataInfo(chatNum, partnerID, sentMsg, msgTime);
		}
		else { // ù ��ȭ�� ���
			userToChatNum.put(partnerID, chatNum);
			chatNumToVecIdx.put(chatNum, chatDataCnt++);
			ChatData chatDataTmp = new ChatData(msg+"@@");
			chatDataVec.add(chatDataTmp);
			chatDataTmp.setChatDataInfo(chatNum, partnerID, sentMsg, msgTime);
			
			for(ChatView c : chatViewVec) {
				if(c.equals(partnerID) ) {
					c.setChatNum(chatNum);  // chatNum ����
					break;
				}
			}
		}
		
		ChatView cv = null;
		for(ChatView c : chatViewVec) {
			if(c.getUserID().equals(partnerID) ) {
				cv = c;
				break;
			}
		}
		
		// ä�÷뿡 ����, �����ִٸ�		
		if(cv != null && cv.isChatViewOn()) {			
			cv.popMsgOnChatRoom(fromMe, msgTime, sentMsg);
		}
		
		// ä�� �гο� ����
		
	}
	
	public void removeChatView(ChatView cv) {
		chatViewVec.remove(cv);
	}
	
	public void addChatView(String userID, ChatView chatView) {
		chatViewVec.add(chatView);
	}
	
	public Integer getChatNum(String userID) {
		return userToChatNum.get(userID);
	}
	
	public void setChatNum(String userID, int chatNum) {
		userToChatNum.put(userID, chatNum);
	}
}
