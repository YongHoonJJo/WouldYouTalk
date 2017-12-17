package WouldYouTalk;

import java.awt.Color;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;

public class ListChatting extends JPanel {
	private HashMap<String, Integer> userToChatNum; // 상대방id에 대응하는 채팅방 번호
	private HashMap<Integer, Integer> chatNumToVecIdx; // 채팅방 번화와 ChatDataVec
	private Vector<ChatData> chatDataVec; // 채팅데이터
	private int chatDataCnt;
	
	private Vector<ChatView> chatViewVec;
	
	// 채팅패널
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
	
/*** [IMG] // 1:1대화 사진 전송 ***/
	public void addChatImg(String msg, byte[] bb) {
		String[] data = msg.split("::");
		int chatNum = Integer.parseInt(data[1]);
		String recvID = data[2];
		String sentID = data[3];
		String msgTime = data[4];
		int byteSize = Integer.parseInt(data[5]);
		
		boolean fromMe = false;
		String partnerID = sentID; // 내가 메세지를 받은 경우
		if(sentID.equals(LoginView.getMyID())) { // 내가보낸 메세지
			fromMe = true;
			partnerID = recvID; // 내가 메세지를 보낸 경우
		}
		
		// 대화 저장
		if(userToChatNum.containsKey(partnerID)) { // 기존 채팅방이 있는경우
			int vecIdx = chatNumToVecIdx.get(userToChatNum.get(partnerID));
			ChatData chatDataTmp = chatDataVec.elementAt(vecIdx); 
			chatDataTmp.addMsg(msg+"@@"); // 메세지 추가.
			chatDataTmp.addImg(msgTime+":"+byteSize, bb);// 바이트 저장
			chatDataTmp.setChatDataInfo(chatNum, partnerID, "<Image>", msgTime);
		}
		else { // 첫 대화인 경우
			userToChatNum.put(partnerID, chatNum);
			chatNumToVecIdx.put(chatNum, chatDataCnt++);
			ChatData chatDataTmp = new ChatData(msg+"@@");
			chatDataVec.add(chatDataTmp);
			chatDataTmp.addImg(msgTime+":"+byteSize, bb); // 바이트 저장
			chatDataTmp.setChatDataInfo(chatNum, partnerID, "<Image>", msgTime);

			for(ChatView c : chatViewVec) {
				if(c.equals(partnerID) ) {
					c.setChatNum(chatNum);  // chatNum 설정
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

		// 채팅룸에 띄우기, 켜져있다면		
		if(cv != null && cv.isChatViewOn()) {			
			cv.popImgOnChatRoom(fromMe, msgTime, bb);
			//cv.popMsgOnChatRoom(fromMe, msgTime, sentMsg);
		}

		// 채팅 패널에 띄우기


	}
	
/*** [MSG] // 1:1대화 ***/
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
		String partnerID = sentID; // 내가 메세지를 받은 경우
		if(sentID.equals(LoginView.getMyID())) { // 내가보낸 메세지
			fromMe = true;
			partnerID = recvID; // 내가 메세지를 보낸 경우
		}
		
		// 대화 저장
		if(userToChatNum.containsKey(partnerID)) { // 기존 채팅방이 있는경우
			int vecIdx = chatNumToVecIdx.get(userToChatNum.get(partnerID));
			ChatData chatDataTmp = chatDataVec.elementAt(vecIdx); 
			chatDataTmp.addMsg(msg+"@@"); // 메세지 추가.
			chatDataTmp.setChatDataInfo(chatNum, partnerID, sentMsg, msgTime);
		}
		else { // 첫 대화인 경우
			userToChatNum.put(partnerID, chatNum);
			chatNumToVecIdx.put(chatNum, chatDataCnt++);
			ChatData chatDataTmp = new ChatData(msg+"@@");
			chatDataVec.add(chatDataTmp);
			chatDataTmp.setChatDataInfo(chatNum, partnerID, sentMsg, msgTime);
			
			for(ChatView c : chatViewVec) {
				if(c.equals(partnerID) ) {
					c.setChatNum(chatNum);  // chatNum 설정
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
		
		// 채팅룸에 띄우기, 켜져있다면		
		if(cv != null && cv.isChatViewOn()) {			
			cv.popMsgOnChatRoom(fromMe, msgTime, sentMsg);
		}
		
		// 채팅 패널에 띄우기
		
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
