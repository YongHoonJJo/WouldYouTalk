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
		// ���� ����Ʈ�� ������
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
			// send_Msg(chatMsg); �������� ���ؼ� ������
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
		
		try { // �����ؾ��� ����... cuz TCP
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
			//dis.read(b); // �α��� ���� ����
			String b = dis.readUTF();
			
			String loginInfo = new String(b);
			loginInfo = loginInfo.trim();
			sfTextArea.append("loginInfo : " + loginInfo + "\n"); // ������ textArea ���� ���̰�
			sfTextArea.setCaretPosition(sfTextArea.getText().length()); // �� �Ʒ��� ��ũ��
			
/*** ID / PW check ***/
			String[] data = loginInfo.split(":");
			String[] info = data[1].split("/");
			
			if(lists.isIdPasswdCorrect(info[0], info[1])) { 
				send_Msg("[LOGIN]::OK"); // "[LOGIN]:OK::"
				System.out.println("[LOGIN]::OK");
				userNum = lists.getUserNum(info[0]); // ������ ID�� �����ϴ� userNum ���� ���
				
				// user �� userInfo mapping // �������� ����غ���
				lists.getUserVec().elementAt(userNum).setUserInfo(this);  
				
				sfTextArea.append("ID " + info[0] + " ����\n");
				sfTextArea.setCaretPosition(sfTextArea.getText().length()); // �� �Ʒ��� ��ũ��
				sfTextArea.append("[LOGIN]::OK\n");
				isCorrect = true;
				System.out.println("[LOGIN]::OK end");
			}
			else {
				send_Msg("[LOGIN]:NOK::");
				sfTextArea.append("ID or Passwd ����\n");
				sfTextArea.setCaretPosition(sfTextArea.getText().length()); // �� �Ʒ��� ��ũ��
				sfTextArea.append("[LOGIN]:NOK\n");
			}
			
			sfTextArea.setCaretPosition(sfTextArea.getText().length()); // �� �Ʒ��� ��ũ��
			//send_Msg(Nickname + "�� ȯ���մϴ�."); // ����� ����ڿ��� ���������� �˸�
		} catch(Exception e) {
			sfTextArea.append("��Ʈ�� ���� ����\n");
			sfTextArea.setCaretPosition(sfTextArea.getText().length());
		}
		return isCorrect;
	}
	
	public void send_Bytes(byte[] bb) {
		try {
			dos.write(bb);
		}
		catch (IOException e) {
			sfTextArea.append("Bytes �۽� ���� �߻�---\n");
			sfTextArea.setCaretPosition(sfTextArea.getText().length());
		}
	}
	
	public void send_Msg(String str) {
		try {
			dos.writeUTF(str);
		}
		catch (IOException e) {
			sfTextArea.append("�޼��� �۽� ���� �߻�---\n");
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
	
	public void run() { // ������
		while(true) {
			try {
				// ����ڿ��� �޴� �޼���
				//byte[] b = new byte[128];
				//dis.read(b);	// Block Method
				String b = dis.readUTF();
				
				String msg = new String(b);
				msg = msg.trim();
				System.out.println("read()");
				
				InMessage(msg); // debug
				
				String[] data = msg.split("::");
				/*** �������ݿ� ���� ó���ϱ� ***/
				
/*** 1:1 �޼��� ���� ***/
				if(data[0].equals("[MSG]")) { // �޼��� ����
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
					// ���ȣ�� �ش��ϴ� ä�� ���� �����ϱ�
					lists.addChatMsg(chatNum, serverMsg+"@@"); 
				}
/*** 1:1 �̹��� ���� ***/
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
					// ���ȣ�� �ش��ϴ� �̹��� ���� �����ϱ� 
					lists.addChatMsg(chatNum, "[IMG]"+"@@"); // ���峻�� �ٲٱ�
 				}
				else
					;
			}
			catch (IOException e) {
				try {
					dos.close();
					dis.close();
					user_socket.close();
					vcUserInfo.removeElement(this); // ������ �� ���� ��ü�� ���Ϳ��� ����
					lists.getUserVec().elementAt(userNum).removeUserInfoVec(this);
					sfTextArea.append(vcUserInfo.size() + " : ���� ���Ϳ� ����� ����� ��\n" + userNum + " user logout\n");
					sfTextArea.setCaretPosition(sfTextArea.getText().length());
					break;
				} catch (Exception ee) {
					
				}
			}
		}
	} // End of run method
}