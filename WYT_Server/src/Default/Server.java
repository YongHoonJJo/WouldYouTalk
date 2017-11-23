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
		lists.addUser(new User("������", "h01", "1234")); // Name, ID, PW
		lists.addUser(new User("������", "h02", "1234"));
		lists.addUser(new User("�����", "h03", "1234"));
		lists.addUser(new User("�����", "h04", "1234"));
		lists.addUser(new User("������", "h05", "1234"));
		lists.addUser(new User("������", "h06", "1234"));
		lists.addUser(new User("ĥ����", "h07", "1234"));
		lists.addUser(new User("�ȵ���", "h08", "1234"));
		lists.addUser(new User("������", "h09", "1234"));
		lists.addUser(new User("�ʵ���", "h10", "1234"));
		
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
						sfTextArea.append("����� ����!!\n");
						// ����� ���� ������ �ݹ� ������Ƿ�, user Ŭ���� ���·� ��ü ����
						
						UserInfo user = new UserInfo(serverFrame, soc, vc);
						
						vc.add(user);
						user.start(); // ���� ��ü�� ������ ����
						
					} catch (IOException e) {
						sfTextArea.append("!!! accept ���� �߻�...!!\n");
					}
				}
			}
		});
		th.start();
	}
}
