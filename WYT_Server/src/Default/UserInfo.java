package Default;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

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
	
	public void sendFriendInfo(int userNum) { // [FLIST]:ID:name:stateMsg
		String Flist = vcUser.elementAt(userNum).getID()+":"; 
		Flist += vcUser.elementAt(userNum).getName()+":";
		Flist += vcUser.elementAt(userNum).getStateMsg();
		send_Msg("[FLIST]:"+Flist);
		// ���� ����Ʈ�� ������
		sfTextArea.append("[FLIST]:"+Flist+'\n');
		sfTextArea.setCaretPosition(sfTextArea.getText().length());
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
				send_Msg("[LOGIN]:OK"); // "[LOGIN]:OK::"
				System.out.println("[LOGIN]:OK");
				userNum = lists.getUserNum(info[0]); // ������ ID�� �����ϴ� userNum ���� ���
				sfTextArea.append("ID " + info[0] + " ����\n");
				sfTextArea.setCaretPosition(sfTextArea.getText().length()); // �� �Ʒ��� ��ũ��
				sfTextArea.append("[LOGIN]:OK\n");
				isCorrect = true;
				System.out.println("[LOGIN]:OK end");
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
	
	public void send_Msg(String str) {
		try {
			//byte[] b = str.getBytes();
			//dos.write(b);
			dos.writeUTF(str);
		}
		catch (IOException e) {
			sfTextArea.append("�޼��� �۽� ���� �߻�\n");
			sfTextArea.setCaretPosition(sfTextArea.getText().length());
		}
	}
	
	public void InMessage(String str) {
		sfTextArea.append(str + "\n");
		sfTextArea.setCaretPosition(sfTextArea.getText().length());

		broad_cast(str);
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
				/*** �������ݿ� ���� ��ε� ĳ��Ʈ �ϱ� ***/
				
				InMessage(msg); // broad_Cast
			}
			catch (IOException e) {
				try {
					dos.close();
					dis.close();
					user_socket.close();
					vcUserInfo.removeElement(this); // ������ �� ���� ��ü�� ���Ϳ��� ����
					sfTextArea.append(vcUserInfo.size() + " : ���� ���Ϳ� ����� ����� ��\n");
					sfTextArea.setCaretPosition(sfTextArea.getText().length());
					break;
				} catch (Exception ee) {
					
				}
			}
		}
	} // End of run method
}