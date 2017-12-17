package Panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import WouldYouTalk.ChatData;
import WouldYouTalk.LoginView;
import WouldYouTalk.MainView;
import WouldYouTalk.UserInfo;

public class ChatRoomPanel extends JPanel {

	private UserInfo user;
	private int chatNum;
	private int cnt=0;
	private int ySize = 0;
	
	private int ypos = 0;
	
	
	private JScrollPane scroll;
	
	public ChatRoomPanel(int chatNum, UserInfo user) {
		this.user = user;
		this.chatNum = chatNum;
		setPreferredSize(new Dimension(400, 390));
		setLayout(null);
		this.setBackground(Color.PINK);		
	}

	public void initMsg() {

		if(chatNum != 0) { // 기존의 채팅방이 있다면
			// 대화 가져오기
			int chatNumIdx = MainView.getListChatting().getChatNumToVecIdx(chatNum);
			ChatData cd = MainView.getListChatting().getChatDataVec().elementAt(chatNumIdx);
			String msg = cd.getMsg();

			String[] stories = msg.split("@@");
			
			for(String story: stories) {
				String[] data = story.split("::");
				int chatNo = Integer.parseInt(data[1]);
				String recvID = data[2];
				String sentID = data[3];
				String msgTime = data[4];
				
				String sentMsg = data[5]; // or byteSize
				for(int i=6; i<data.length; i++) sentMsg += data[i];
				
				boolean fromMe = false;
				String partnerID = sentID; // 내가 메세지를 받은 경우
				if(sentID.equals(LoginView.getMyID())) { // 내가보낸 메세지
					fromMe = true;
					partnerID = recvID; // 내가 메세지를 보낸 경우
				}
				if(data[0].equals("[MSG]")) 
					addMsg(fromMe, msgTime, sentMsg);
				else if(data[0].equals("[IMG]")) {
					byte[] bb = cd.getBytes(msgTime+":"+sentMsg);
					addImg(fromMe, msgTime, bb);
				}
				else
					;
			}
			System.out.println(user.getID()+" hasChatRoom");
		}
		else {
			System.out.println(user.getID()+" NoChatRoom");
		}

	}
	
	public void setChatRoomScrollp(JScrollPane scroll) {
		this.scroll = scroll;
	}
	
	public void addImg(boolean fromMe, String msgTime, byte[] bb) {
		ChatRoomImgPanel crip = new ChatRoomImgPanel(user, fromMe, msgTime, bb);
		int xpos = 0;
		if(fromMe) xpos = 250;
		//crip.setBounds(xpos,  (50*cnt),  150, 150);
		crip.setBounds(xpos,  ypos,  150, 150);
		ypos += 150;
		add(crip);
		cnt++;
		
		ySize += 100;
		if(ySize > 390) {
			setPreferredSize(new Dimension(400, ySize));
			scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
		}
		this.repaint();
	}
	
	public void addMsg(boolean fromMe, String msgTime, String sentMsg) {
		ChatRoomMsgPanel crmp = new ChatRoomMsgPanel(user, fromMe, msgTime, sentMsg);
		int xpos = 0;
		if(fromMe) xpos = 200;
		//crmp.setBounds(xpos,  50*cnt,  200, 50);
		crmp.setBounds(xpos,  ypos,  200, 50);
		ypos += 50;
		add(crmp);
		cnt++;
		
		ySize += 50;
		if(ySize > 390) {
			setPreferredSize(new Dimension(400, ySize));
			scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
		}
		//revalidate();
		this.repaint();
	}
}
