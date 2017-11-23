package WouldYouTalk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network {
	private String ip;
	private int port;
	
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	Thread th;
	
	public Network() {
		ip = "127.0.0.1";
		port = 30015;
		
		try {
			socket = new Socket(ip, port);
			if(socket != null) {
				Connection();
			}
		} catch (UnknownHostException e) {
		} catch (IOException e) {
			//textArea.append("소켓 접속 에러!!\n");
		}
	}
	
	public void Connection() {
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			//textArea.append("스트림 설정 에러!!\n");
		}
		
		//sendMessage(id);
		th = new Thread(new Runnable() { // 스레드를 통한 서버로부터 메세지 수신
			@Override
			public void run() {
				while(true) {
					try {
						byte[] b = new byte[128];
						dis.read(b);
						String msg = new String(b);
						msg = msg.trim();
						
						/*** 프로토콜에 따라 처리하기 ***/
						
						
					} catch (IOException e) {
						//textArea.append("메세지 수신 에러!!\n");
						try { // 서버와 소켓 통신에 문제가 생겼을 경우 소켓을 닫느다.
							os.close(); is.close();
							dos.close(); dis.close();
							socket.close();
							break;
						} catch (IOException ee) {
						}
					}
				}
			}
		});
		//th.start();
	}
	
	public Thread getThread() {
		return th;
	}
	
	public boolean login(String id, String pw) {
		boolean isOk = false;
		sendMessage("[LOGIN]"+id+"/"+pw); // [LOGIN]ID/PW
		try {
			byte[] b = new byte[128];
			dis.read(b);
			String msg = new String(b);
			msg = msg.trim();
			isOk = msg.equals("OK");
			
		} catch (IOException e) {
			//textArea.append("메세지 수신 에러!!\n");
			try { // 서버와 소켓 통신에 문제가 생겼을 경우 소켓을 닫느다.
				os.close(); is.close();
				dos.close(); dis.close();
				socket.close();
			} catch (IOException ee) {
			}
		}
		return isOk;
	}
	
	public void sendMessage(String str) {
		try {
			byte[] bb = str.getBytes();
			dos.write(bb);
		} catch (IOException e) {
			//textArea.append("메세지 송신 에러\n");
		}
	}
}
