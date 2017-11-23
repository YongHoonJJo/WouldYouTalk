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
	
	private Vector<UserInfo> user_vc;
	private String Nickname = "";
	
	private ServerFrame serverFrame;
	private JButton sfStartBtn;
	private JTextField sfTextField;
	private JTextArea sfTextArea;
	
	public UserInfo(ServerFrame serverFrame, Socket soc, Vector<UserInfo> vc) {
		this.serverFrame = serverFrame;
		sfStartBtn = serverFrame.getStartBtn();
		sfTextField = serverFrame.getTextField();
		sfTextArea = serverFrame.getTextArea();
		
		this.user_socket = soc;
		this.user_vc = vc;
		User_network();
	}
	
	public void User_network() {
		try {
			is = user_socket.getInputStream();
			dis = new DataInputStream(is);
			os = user_socket.getOutputStream();
			dos = new DataOutputStream(os);
			
			byte[] b = new byte[128];
			dis.read(b);
			
			String Nickname = new String(b);
			Nickname = Nickname.trim();
			sfTextArea.append("ID " + Nickname + " ����\n"); // ������ textArea ���� ���̰�
			sfTextArea.setCaretPosition(sfTextArea.getText().length()); // �� �Ʒ��� ��ũ��
			send_Msg(Nickname + "�� ȯ���մϴ�."); // ����� ����ڿ��� ���������� �˸�
		} catch(Exception e) {
			sfTextArea.append("��Ʈ�� ���� ����\n");
			sfTextArea.setCaretPosition(sfTextArea.getText().length());
		}
	}
	
	public void send_Msg(String str) {
		try {
			byte[] b = str.getBytes();
			dos.write(b);
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
		for(int i=0; i<user_vc.size(); i++) {
			UserInfo imsi = (UserInfo) user_vc.elementAt(i);
			imsi.send_Msg(str);
		}
	}
	
	public void run() { // ������
		while(true) {
			try {
				// ����ڿ��� �޴� �޼���
				byte[] b = new byte[128];
				dis.read(b);	// Block Method
				String msg = new String(b);
				msg = msg.trim();

				/*** �������ݿ� ���� ��ε� ĳ��Ʈ �ϱ� ***/
				
				InMessage(msg); // broad_Cast
			}
			catch (IOException e) {
				try {
					dos.close();
					dis.close();
					user_socket.close();
					user_vc.removeElement(this); // ������ �� ���� ��ü�� ���Ϳ��� ����
					sfTextArea.append(user_vc.size() + " : ���� ���Ϳ� ����� ����� ��\n");
					sfTextArea.setCaretPosition(sfTextArea.getText().length());
					break;
				} catch (Exception ee) {
					
				}
			}
		}
	} // End of run method
}