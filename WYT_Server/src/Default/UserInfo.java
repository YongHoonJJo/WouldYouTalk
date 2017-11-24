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
	private Vector<UserInfo> user_vc;
	private String Nickname = "";
	
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
		this.user_vc = lists.getUserInfoVec();
		//this.user_vc = vc;
		//User_network();
	}
	
	public boolean User_network() {
		boolean isCorrect = false;
		try {
			is = user_socket.getInputStream();
			dis = new DataInputStream(is);
			os = user_socket.getOutputStream();
			dos = new DataOutputStream(os);
			
			byte[] b = new byte[128];
			dis.read(b); // 로그인 정보 전달
			String loginInfo = new String(b);
			loginInfo = loginInfo.trim();
			sfTextArea.append("loginInfo : " + loginInfo + "\n"); // 내용을 textArea 위에 붙이고
			sfTextArea.setCaretPosition(sfTextArea.getText().length()); // 맨 아래로 스크롤
			
			/*** ID / PW check ***/
			String[] data = loginInfo.split(":");
			String[] info = data[1].split("/");
			
			if(lists.isIdPasswdCorrect(info[0], info[1])) { 
				send_Msg("[LOGIN]:OK");
				sfTextArea.append("ID " + info[0] + " 접속\n");
				isCorrect = true;
			}
			else {
				send_Msg("[LOGIN]:NOK");
				sfTextArea.append("ID or Passwd 오류\n");
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
			byte[] b = str.getBytes();
			dos.write(b);
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
		for(int i=0; i<user_vc.size(); i++) {
			UserInfo imsi = (UserInfo) user_vc.elementAt(i);
			imsi.send_Msg(str);
		}
	}
	
	public void run() { // 스레드
		while(true) {
			try {
				// 사용자에게 받는 메세지
				byte[] b = new byte[128];
				dis.read(b);	// Block Method
				String msg = new String(b);
				msg = msg.trim();

				/*** 프로토콜에 따라 브로드 캐스트 하기 ***/
				
				InMessage(msg); // broad_Cast
			}
			catch (IOException e) {
				try {
					dos.close();
					dis.close();
					user_socket.close();
					user_vc.removeElement(this); // 에러가 난 현재 객체를 벡터에서 삭제
					sfTextArea.append(user_vc.size() + " : 현재 벡터에 담겨진 사용자 수\n");
					sfTextArea.setCaretPosition(sfTextArea.getText().length());
					break;
				} catch (Exception ee) {
					
				}
			}
		}
	} // End of run method
}