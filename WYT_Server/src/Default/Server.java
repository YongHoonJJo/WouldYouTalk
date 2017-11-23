package Default;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;

public class Server {
	private ServerSocket socket; // server socket
	private Socket soc; // 연결소켓
	
	private Lists lists;
	private Vector<UserInfo> vc; // 접속중인 사용자 리스트
	
	private ServerFrame serverFrame;
	private JButton sfStartBtn;
	private JTextField sfTextField;
	private JTextArea sfTextArea;
	
	public Server(ServerFrame serverFrame, int Port) {
		
		/*** 최초 실행이 아닐겨우 파일 입출력 활용해보기 ***/
		// lists = 파일에서 데이터 가져오기...
		
		
		/*** 서버 최초 실행시 ***/
		lists = new Lists();
		vc = lists.getUserInfoVec();
		
		/*** 약식 회원가입 ***/
		lists.addUser(new User("송정은", "h01", "1234")); // Name, ID, PW
		lists.addUser(new User("조용훈", "h02", "1234"));
		lists.addUser(new User("삼순이", "h03", "1234"));
		lists.addUser(new User("사순이", "h04", "1234"));
		lists.addUser(new User("오순이", "h05", "1234"));
		lists.addUser(new User("육순이", "h06", "1234"));
		lists.addUser(new User("칠돌이", "h07", "1234"));
		lists.addUser(new User("팔돌이", "h08", "1234"));
		lists.addUser(new User("구돌이", "h09", "1234"));
		lists.addUser(new User("십돌이", "h10", "1234"));
		
		this.serverFrame = serverFrame;
		sfStartBtn = serverFrame.getStartBtn();
		sfTextField = serverFrame.getTextField();
		sfTextArea = serverFrame.getTextArea();
		
		try {
			socket = new ServerSocket(Port);
			sfStartBtn.setText("서버 실행중");
			sfStartBtn.setEnabled(false);
			sfTextField.setEnabled(false);
			
			if(socket != null) { // 소켓이 정상적으로 열렸을 때
				Connection();
			}
		} catch (IOException e) {
			sfTextArea.append("소켓이 이미 사용중입니다.");
		}
	}
	
	private void Connection() {
		Thread th = new Thread(new Runnable() { // 사용자 접속을 받을 스레드
			@Override
			public void run() {
				while(true) { // 사용자 접속을 계속해서 받기 위한 무한루프
					try {
						sfTextArea.append("사용자 접속 대기중...\n");
						/*** Block Method ***/
						soc = socket.accept(); 
						sfTextArea.append("사용자 접속!!\n");
						// 연결된 소켓 정보는 금방 사라지므로, user 클래스 형태로 객체 생성
						
						UserInfo user = new UserInfo(serverFrame, soc, vc);
						
						vc.add(user);
						user.start(); // 유저 객체의 스레드 실행
						
					} catch (IOException e) {
						sfTextArea.append("!!! accept 에러 발생...!!\n");
					}
				}
			}
		});
		th.start();
	}
}
