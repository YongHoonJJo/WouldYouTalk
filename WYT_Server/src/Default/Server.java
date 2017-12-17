package Default;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;

public class Server {
	private ServerSocket socket; // server socket
	private Socket soc; // �������
	
	private Lists lists;
	private Vector<UserInfo> vc; // �������� ����� ����Ʈ
	
	private ServerFrame serverFrame;
	private JButton sfStartBtn;
	private JTextField sfTextField;
	private JTextArea sfTextArea;
	
	public Server(ServerFrame serverFrame, int Port) {
		
		/*** ���� ������ �ƴҰܿ� ���� ����� Ȱ���غ��� ***/
		// lists = ���Ͽ��� ������ ��������...
		
		
		/*** ���� ���� ����� ***/
		lists = new Lists();
		vc = lists.getUserInfoVec();
		
		/*** ��� ȸ������ ***/
		lists.addUser(new User("������", "h01", "1234", lists)); // Name, ID, PW
		lists.addUser(new User("������", "h02", "1234", lists));
		lists.addUser(new User("�����", "h03", "1234", lists));
		lists.addUser(new User("�����", "h04", "1234", lists));
		lists.addUser(new User("������", "h05", "1234", lists));
		lists.addUser(new User("������", "h06", "1234", lists));
		lists.addUser(new User("ĥ����", "h07", "1234", lists));
		lists.addUser(new User("�ȵ���", "h08", "1234", lists));
		lists.addUser(new User("������", "h09", "1234", lists));
		lists.addUser(new User("�ʵ���", "h10", "1234", lists));
		lists.addUser(new User("������", "h11", "1234", lists));
		
		lists.getUserVec().elementAt(1).setStateMsg("Hoon");
		lists.getUserVec().elementAt(0).setStateMsg("prettyje");
		
		// ģ���߰�
		for(int i=1; i<8; i++)
			lists.getUserVec().elementAt(0).addFriends(i);
		lists.getUserVec().elementAt(1).addFriends(0);
		lists.getUserVec().elementAt(1).addFriends(2);
		lists.getUserVec().elementAt(2).addFriends(0);
		lists.getUserVec().elementAt(2).addFriends(1);
		
		this.serverFrame = serverFrame;
		sfStartBtn = serverFrame.getStartBtn();
		sfTextField = serverFrame.getTextField();
		sfTextArea = serverFrame.getTextArea();
		
		try {
			socket = new ServerSocket(Port);
			sfStartBtn.setText("���� ������");
			sfStartBtn.setEnabled(false);
			sfTextField.setEnabled(false);
			
			if(socket != null) { // ������ ���������� ������ ��
				Connection();
			}
		} catch (IOException e) {
			sfTextArea.append("������ �̹� ������Դϴ�.");
		}
	}
	
	private void Connection() {
		Thread th = new Thread(new Runnable() { // ����� ������ ���� ������
			@Override
			public void run() {
				while(true) { // ����� ������ ����ؼ� �ޱ� ���� ���ѷ���
					try {
						sfTextArea.append("����� ���� �����...\n");
						/*** Block Method ***/
						soc = socket.accept(); 
						sfTextArea.append("����� soc ����!!\n");
						// ����� ���� ������ �ݹ� ������Ƿ�, user Ŭ���� ���·� ��ü ����
						
						UserInfo userInfo = new UserInfo(serverFrame, soc, lists);
						if(userInfo.User_network()) { // ID, PW�� ��ȿ�ϴٸ�,
							vc.add(userInfo);
							// userNum�� ���� soc ã��, ���Ͼ��̵� �������� ����غ���..ok
							
							userInfo.sendFriendsList(); // ģ������Ʈ ������
						
							userInfo.sendProfileIcon(); // ���� ������ ������
							
							//userInfo.sendChatData(); // ä�ø���Ʈ ������
							// ���� ����� ģ�� ����Ʈ ������..
							
							userInfo.start(); // ���� ��ü�� ������ ����
							System.out.println("after thread");
						}
						else {
							// ��Ʈ�� ����
						}
						
					} catch (IOException e) {
						sfTextArea.append("!!! accept ���� �߻�...!!\n");
					}
				}
			}
		});
		th.start();
	}
}
