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
		// 프사 바이트로 보내기
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
				send_Msg("[LOGIN]:OK"); // "[LOGIN]:OK::"
				System.out.println("[LOGIN]:OK");
				userNum = lists.getUserNum(info[0]); // 접속자 ID에 대응하는 userNum 정보 등록
				sfTextArea.append("ID " + info[0] + " 접속\n");
				sfTextArea.setCaretPosition(sfTextArea.getText().length()); // 맨 아래로 스크롤
				sfTextArea.append("[LOGIN]:OK\n");
				isCorrect = true;
				System.out.println("[LOGIN]:OK end");
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
	
	public void send_Msg(String str) {
		try {
			//byte[] b = str.getBytes();
			//dos.write(b);
			dos.writeUTF(str);
		}
		catch (IOException e) {
			sfTextArea.append("메세지 송신 에러 발생\n");
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
				/*** 프로토콜에 따라 브로드 캐스트 하기 ***/
				
				InMessage(msg); // broad_Cast
			}
			catch (IOException e) {
				try {
					dos.close();
					dis.close();
					user_socket.close();
					vcUserInfo.removeElement(this); // 에러가 난 현재 객체를 벡터에서 삭제
					sfTextArea.append(vcUserInfo.size() + " : 현재 벡터에 담겨진 사용자 수\n");
					sfTextArea.setCaretPosition(sfTextArea.getText().length());
					break;
				} catch (Exception ee) {
					
				}
			}
		}
	} // End of run method
}