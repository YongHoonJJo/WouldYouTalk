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
	
	private Thread th;
	private ListFriends listFriends;
	
	public void setMainView(ListFriends listFriends) {
		this.listFriends = listFriends;
	}
	
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
			//textArea.append("���� ���� ����!!\n");
		}
	}
	
	public void Connection() {
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			//textArea.append("��Ʈ�� ���� ����!!\n");
		}
		
		//sendMessage(id);
		th = new Thread(new Runnable() { // �����带 ���� �����κ��� �޼��� ����
			@Override
			public void run() {
				while(true) {
					try {
						//Thread.sleep(10);
						byte[] b = new byte[1024];
						dis.read(b);
						String msg = new String(b);
						msg = msg.trim();
						System.out.println(msg);
						String[] data = msg.split("::");
						/*** �������ݿ� ���� ó���ϱ� ***/
						if(data[0].equals("[FLIST]")) { 
							for(int i=1; i<data.length; i++) {
								String[] info = data[i].split(":");
								listFriends.initUserInfo(info[0], info[1], info[2]);
							}
						} 
						else if(data[0].equals("[FLIST_END]")) {
							listFriends.setFriendPanel();
						}
					//} catch (InterruptedException ee) {
					} catch (IOException e) {
						//textArea.append("�޼��� ���� ����!!\n");
						try { // ������ ���� ��ſ� ������ ������ ��� ������ �ݴ���.
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
			byte[] b = new byte[128];
			dis.read(b);
			String msg = new String(b);
			msg = msg.trim();
			return msg.equals("[LOGIN]:OK"); 	
		} catch (IOException e) {
			//textArea.append("�޼��� ���� ����!!\n");
			try { // ������ ���� ��ſ� ������ ������ ��� ������ �ݴ���.
				os.close(); is.close();
				dos.close(); dis.close();
				socket.close();
			} catch (IOException ee) {
			}
		}
		return false;
	}
	
	public void sendMessage(String str) {
		try {
			byte[] bb = str.getBytes();
			dos.write(bb);
		} catch (IOException e) {
			//textArea.append("�޼��� �۽� ����\n");
		}
	}
}
