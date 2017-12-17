package WouldYouTalk;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Network {
	private String ip;
	private int port;
	
	//private static Socket socket;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	//private bufferedImg
	
	private Thread th;
	private ListFriends listFriends;
	
	public void setMainView(ListFriends listFriends) {
		this.listFriends = listFriends;
	}
	
	public Network() {
		if(socket == null) {
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
		//return socket;
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
						//Thread.sleep(10);
						//byte[] b = new byte[1024];
						//dis.read(b);
						String b = dis.readUTF();
						
						String msg = new String(b);
						msg = msg.trim();
						System.out.println("read : " + msg);
						String[] data = msg.split("::");
						/*** 프로토콜에 따라 처리하기 ***/
/*** [FLIST] // 친구리스트 받기 ***/
						if(data[0].equals("[FLIST]")) { 
							for(int i=1; i<data.length; i++) {
								String[] info = data[i].split(":");
								listFriends.initUserInfo(info[0], info[1], info[2]);
							}
						}
/*** [FLIST_END] // 친구리스트 다 보냄 ***/						
						else if(data[0].equals("[FLIST_END]")) {
							//listFriends.setFriendPanel();
							//dos.writeUTF("[GET_PI]::");
						}
/*** [PI_END] // 프로필 이미지 다 보냄 ***/						
						else if(data[0].equals("[PI_END]")) {
							System.out.println("setFriendPanel()");
							listFriends.setFriendPanel();
						}
/*** [MSG] // 1:1대화 ***/
						else if(data[0].equals("[MSG]")) {
							MainView.getListChatting().addChatData(msg);
						}
/*** [PI] // Get Profile Icon ***/						
						else if(data[0].equals("[PI]")) {
							System.out.println("Profile Icon..!!");
							
							int size = Integer.parseInt(data[1]);
							byte[] bytes = new byte[size];
							
							String userID = data[2];
							
							dis.read(bytes);
							System.out.println("dis.read(bytes) ok");
							
							FileOutputStream out = new FileOutputStream("out.jpg"); 
							out.write(bytes);
							out.close();
							System.out.println("out.write(bytes) ok");
							
							try { Thread.sleep(100); } 
							catch(Exception e) { }
							
							File imgFile = new File("out.jpg");
							BufferedImage bImg = ImageIO.read(imgFile);
							ImageIcon imgIcon = new ImageIcon(bImg);
							
							int userInfoIdx = MainView.getListFriends().getUserInfoVecIdx(userID);
							MainView.getListFriends().getUserInfoVec().elementAt(userInfoIdx).setProfileIcon(imgIcon);
						}
/*** [IMG] // ProfileIcon ***/		
						else if(data[0].equals("[IMG]")) {
							int chatNum = Integer.parseInt(data[1]);
							
							String recvID = data[2];
							String sentID = data[3];
							String msgTime = data[4];
							int byteSize = Integer.parseInt(data[5]);
							
							byte[] bytes = new byte[byteSize];
							System.out.println("before read(bytes)");
							dis.read(bytes);
							System.out.println("[IMG] dis.read(bytes) ok");
							
							MainView.getListChatting().addChatImg(msg, bytes);
							
						}
						else
							;
					
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
	
	// public Thread getThread() { return th; }
	
	public void threadStart() {
		th.start();
	}
	
	public boolean login(String id, String pw) {
		
		sendMessage("[LOGIN]:"+id+"/"+pw); // [LOGIN]:ID/PW
		try {
			//byte[] b = new byte[128];
			//int k = dis.read(b);
			//System.out.println("lonin : " + k);
			String b = dis.readUTF();
			
			String msg = new String(b);
			msg = msg.trim();
			return msg.equals("[LOGIN]::OK"); 	
		} catch (IOException e) {
			//textArea.append("메세지 수신 에러!!\n");
			try { // 서버와 소켓 통신에 문제가 생겼을 경우 소켓을 닫느다.
				os.close(); is.close();
				dos.close(); dis.close();
				socket.close();
			} catch (IOException ee) {
			}
		}
		return false;
	}
	
	public void sendBytes(byte[] bb) {
		try {
			dos.write(bb);
		}
		catch (Exception e3) {
			System.out.println("fail to dos.write() ");
		}
	}
	
	public void sendMessage(String str) {
		try {
			//byte[] bb = str.getBytes();
			//dos.write(bb);
			dos.writeUTF(str);
		} catch (IOException e) {
			//textArea.append("메세지 송신 에러\n");
		}
	}
}
