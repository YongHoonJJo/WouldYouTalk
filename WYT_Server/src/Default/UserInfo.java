package Default;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UserInfo extends Thread {
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
		
	private Socket user_socket;
	
	private Lists lists;
	int userNum;
	private Vector<UserInfo> vcUserInfo;
	private Vector<User> vcUser;
	//private String Nickname = "";
	
	private ServerFrame serverFrame;
	private JButton sfStartBtn;
	private JTextField sfTextField;
	private JTextArea sfTextArea;
	
	public UserInfo(ServerFrame serverFrame, Socket soc, Lists lists) {
		this.serverFrame = serverFrame;
		sfStartBtn = serverFrame.getStartBtn();
		sfTextField = serverFrame.getTextField();
		sfTextArea = serverFrame.getTextArea();
		
		this.user_socket = soc; 
		this.lists = lists;
		this.vcUserInfo = lists.getUserInfoVec();
		this.vcUser = lists.getUserVec();
		//this.user_vc = vc;
		//User_network();
	}
	
	public int getUserNum() {
		return userNum;
	}
	
	public void sendProfileIcon() {
		try {
			byte[] bytes;
			for(int userNo: vcUser.elementAt(userNum).getFriendsNumVec()) {
				bytes = vcUser.elementAt(userNo).getBytes();
				int size = vcUser.elementAt(userNo).getByteSize();
				String userID = vcUser.elementAt(userNo).getID();
				send_Msg("[PI]::"+size+"::"+userID);
				
				sfTextArea.append("send_Msg([PI]::"+size+"::"+userID+")\n");
				sfTextArea.setCaretPosition(sfTextArea.getText().length());
				Thread.sleep(10);
				dos.write(bytes);
				System.out.println("dos.write(bytes) ok");
				Thread.sleep(40);
			}
			
			send_Msg("[PI_END]::");
			System.out.println("[PI_END]");
			sfTextArea.append("[PI_END]::\n");
			sfTextArea.setCaretPosition(sfTextArea.getText().length());
		}
		catch (Exception e) {
			System.out.println("fail to dos.write() ");
		}
		
	}
	
	public void sendFriendInfo(int userNum) { // [FLIST]:ID:name:stateMsg
		String Flist = vcUser.elementAt(userNum).getID()+":"; 
		Flist += vcUser.elementAt(userNum).getName()+":";
		Flist += vcUser.elementAt(userNum).getStateMsg();
		send_Msg("[FLIST]:"+Flist);
		// 프사 바이트로 보내기
		sfTextArea.append("[FLIST]:"+Flist+'\n');
		sfTextArea.setCaretPosition(sfTextArea.getText().length());
	}
	
	public void sendChatData() {
		System.out.println("sendChatData()");
		Vector<Integer> chattingLists = vcUser.elementAt(userNum).getChattingList();
		for(Integer chatNum : chattingLists) {
			ChatData chatData = lists.getChatData(chatNum);
			String chatMsg = chatData.getMsg();
			System.out.println("chatMsg : " + chatMsg);
			// send_Msg(chatMsg); 프로토콜 정해서 보내기
		}
	}
	
	public void sendFriendsList() { // id, name, stateMsg, photo
		Vector<Integer> Friends = vcUser.elementAt(userNum).getFriendsNumVec();	
		StringBuffer Flist = new StringBuffer("[FLIST]::");
		for(Integer i : Friends) { // i == friendNum
			Flist.append(vcUser.elementAt(i).getID()+":"); 
			Flist.append(vcUser.elementAt(i).getName()+":");
			Flist.append(vcUser.elementAt(i).getStateMsg());
			Flist.append("::");
		}
		String msg = new String(Flist);
		send_Msg(msg);
		sfTextArea.append(msg+"\n");
		sfTextArea.setCaretPosition(sfTextArea.getText().length());
		
		try { // 개선해야할 사항... cuz TCP
			Thread.sleep(100); }
		catch (InterruptedException e) {
		}
		send_Msg("[FLIST_END]::");
		sfTextArea.append("[FLIST_END]\n");
		sfTextArea.setCaretPosition(sfTextArea.getText().length());
	}
	
	public boolean User_network() {
		boolean isCorrect = false;
		try {
			is = user_socket.getInputStream();
			dis = new DataInputStream(is);
			os = user_socket.getOutputStream();
			dos = new DataOutputStream(os);
			
			//byte[] b = new byte[128];
			//dis.read(b); // 로그인 정보 전달
			String b = dis.readUTF();
			
			String loginInfo = new String(b);
			loginInfo = loginInfo.trim();
			sfTextArea.append("loginInfo : " + loginInfo + "\n"); // 내용을 textArea 위에 붙이고
			sfTextArea.setCaretPosition(sfTextArea.getText().length()); // 맨 아래로 스크롤
			
/*** ID / PW check ***/
			String[] data = loginInfo.split(":");
			String[] info = data[1].split("/");
			
			if(lists.isIdPasswdCorrect(info[0], info[1])) { 
				send_Msg("[LOGIN]::OK"); // "[LOGIN]:OK::"
				System.out.println("[LOGIN]::OK");
				userNum = lists.getUserNum(info[0]); // 접속자 ID에 대응하는 userNum 정보 등록
				
				// user 와 userInfo mapping // 다중접속 고려해보기
				lists.getUserVec().elementAt(userNum).setUserInfo(this);  
				
				sfTextArea.append("ID " + info[0] + " 접속\n");
				sfTextArea.setCaretPosition(sfTextArea.getText().length()); // 맨 아래로 스크롤
				sfTextArea.append("[LOGIN]::OK\n");
				isCorrect = true;
				System.out.println("[LOGIN]::OK end");
			}
			else {
				send_Msg("[LOGIN]:NOK::");
				sfTextArea.append("ID or Passwd 오류\n");
				sfTextArea.setCaretPosition(sfTextArea.getText().length()); // 맨 아래로 스크롤
				sfTextArea.append("[LOGIN]:NOK\n");
			}
			
			sfTextArea.setCaretPosition(sfTextArea.getText().length()); // 맨 아래로 스크롤
			//send_Msg(Nickname + "님 환영합니다."); // 연결된 사용자에게 정상접속을 알림
		} catch(Exception e) {
			sfTextArea.append("스트림 세팅 에러\n");
			sfTextArea.setCaretPosition(sfTextArea.getText().length());
		}
		return isCorrect;
	}
	
	public void send_Bytes(byte[] bb) {
		try {
			dos.write(bb);
		}
		catch (IOException e) {
			sfTextArea.append("Bytes 송신 에러 발생---\n");
			sfTextArea.setCaretPosition(sfTextArea.getText().length());
		}
	}
	
	public void send_Msg(String str) {
		try {
			dos.writeUTF(str);
		}
		catch (IOException e) {
			sfTextArea.append("메세지 송신 에러 발생---\n");
			sfTextArea.setCaretPosition(sfTextArea.getText().length());
		}
	}
	
	public void InMessage(String str) {
		//String name = vcUser.elementAt(userNum).getName();
		//sfTextArea.append(name + " " + str + "\n");
		sfTextArea.append(str + "\n");
		sfTextArea.setCaretPosition(sfTextArea.getText().length());

		//broad_cast(str);
	}
	
	public void broad_cast(String str) {
		for(int i=0; i<vcUserInfo.size(); i++) {
			UserInfo imsi = (UserInfo) vcUserInfo.elementAt(i);
			imsi.send_Msg(str);
		}
	}
	
	public void run() { // 스레드
		while(true) {
			try {
				// 사용자에게 받는 메세지
				//byte[] b = new byte[128];
				//dis.read(b);	// Block Method
				String b = dis.readUTF();
				
				String msg = new String(b);
				msg = msg.trim();
				System.out.println("read()");
				
				InMessage(msg); // debug
				
				String[] data = msg.split("::");
				/*** 프로토콜에 따라 처리하기 ***/
				
/*** 1:1 메세지 수신 ***/
				if(data[0].equals("[MSG]")) { // 메세지 수신
					int chatNum = Integer.parseInt(data[1]);
					
					String recvID = data[2];
					String sentID = data[3];
					String msgTime = data[4];
					
					int recvUserNum = lists.getUserNum(recvID);
					int sentUserNum = lists.getUserNum(sentID);

					if(chatNum == 0) {
						chatNum = lists.getNewChatNum(recvUserNum, sentUserNum);
						data[1] = ""+chatNum;
						lists.getUserVec().elementAt(sentUserNum).addChattingLists(chatNum);
						lists.getUserVec().elementAt(recvUserNum).addChattingLists(chatNum);
					}
					
					lists.getUserVec().elementAt(recvUserNum).addChatUser(sentID, chatNum);
					lists.getUserVec().elementAt(sentUserNum).addChatUser(recvID, chatNum);
					
					String serverMsg = "";
					for(String s : data) serverMsg += (s+"::");
					
					lists.getUserVec().elementAt(recvUserNum).sendMsg(serverMsg);
					InMessage("recv client " + recvUserNum + " : " + serverMsg);
					//lists.getUserVec().elementAt(recvUserNum).addChattingLists(chatNum);
					
					if(recvID.equals(sentID) == false) {
						lists.getUserVec().elementAt(sentUserNum).sendMsg(serverMsg);
						InMessage("send client " + sentUserNum + " : " + serverMsg);
						//lists.getUserVec().elementAt(sentUserNum).addChattingLists(chatNum);
					}
					// 방번호에 해당하는 채팅 내용 저장하기
					lists.addChatMsg(chatNum, serverMsg+"@@"); 
				}
/*** 1:1 이미지 수신 ***/
				else if(data[0].equals("[IMG]")) {
					int chatNum = Integer.parseInt(data[1]);
					
					String recvID = data[2];
					String sentID = data[3];
					String msgTime = data[4];
					int byteSize = Integer.parseInt(data[5]);
					
					int recvUserNum = lists.getUserNum(recvID);
					int sentUserNum = lists.getUserNum(sentID);
					
					byte[] bytes = new byte[byteSize];
					
					dis.read(bytes);
					System.out.println("dis.read(bytes) ok");
					
					if(chatNum == 0) {
						chatNum = lists.getNewChatNum(recvUserNum, sentUserNum);
						data[1] = ""+chatNum;
						lists.getUserVec().elementAt(sentUserNum).addChattingLists(chatNum);
						lists.getUserVec().elementAt(recvUserNum).addChattingLists(chatNum);
					}
					
					lists.getUserVec().elementAt(recvUserNum).addChatUser(sentID, chatNum);
					lists.getUserVec().elementAt(sentUserNum).addChatUser(recvID, chatNum);
					
					lists.getUserVec().elementAt(recvUserNum).sendMsg(msg);
					lists.getUserVec().elementAt(recvUserNum).sendBytes(bytes);
					InMessage("recv client " + recvUserNum + " : Bytes size" + byteSize + "\n");
					
					if(recvID.equals(sentID) == false) {
						lists.getUserVec().elementAt(sentUserNum).sendMsg(msg);
						lists.getUserVec().elementAt(sentUserNum).sendBytes(bytes);
						InMessage("send client " + sentUserNum + " : Bytes size" + byteSize + "\n");
					}
					// 방번호에 해당하는 이미지 내용 저장하기 
					lists.addChatMsg(chatNum, "[IMG]"+"@@"); // 저장내용 바꾸기
 				}
				else
					;
			}
			catch (IOException e) {
				try {
					dos.close();
					dis.close();
					user_socket.close();
					vcUserInfo.removeElement(this); // 에러가 난 현재 객체를 벡터에서 삭제
					lists.getUserVec().elementAt(userNum).removeUserInfoVec(this);
					sfTextArea.append(vcUserInfo.size() + " : 현재 벡터에 담겨진 사용자 수\n" + userNum + " user logout\n");
					sfTextArea.setCaretPosition(sfTextArea.getText().length());
					break;
				} catch (Exception ee) {
					
				}
			}
		}
	} // End of run method
}