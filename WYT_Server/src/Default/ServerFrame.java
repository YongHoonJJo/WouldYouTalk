package Default;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ServerFrame extends JFrame {
	private JPanel contentPane;
	private JTextField textField; // ����� Port��ȣ �Է�
	private JButton Start; // Server�� �����ų ��ư
	private JTextArea textArea; // Ŭ���̾�Ʈ �� ���� �޼��� ���
	
	private int Port; // ��Ʈ��ȣ
	
	public ServerFrame() { initGUI(); }
	public JButton getStartBtn() { return Start; }
	public JTextField getTextField() { return textField; }
	public JTextArea getTextArea() { return textArea; }
	
	public void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane js = new JScrollPane();
		
		textArea = new JTextArea(); // Ŭ���̾�Ʈ �� ���� �޼��� ���
		textArea.setColumns(20);
		textArea.setRows(5);;
		js.setBounds(0, 0, 264, 254);
		contentPane.add(js);
		js.setViewportView(textArea); // ???
		
		textField = new JTextField("30015"); // ����� ��Ʈ��ȣ �Է�
		textField.setBounds(98, 264, 154, 37);
		textField.setColumns(10);
		contentPane.add(textField);
		
		JLabel portLabel = new JLabel("Port Number");
		portLabel.setBounds(12, 264, 98, 37);
		contentPane.add(portLabel);
		
		Start = new JButton("���� ����"); // ���� ���� ��ư
		Start.setBounds(0, 325, 264, 37);
		contentPane.add(Start);
		textArea.setEditable(false); // ����ڰ� �������� ���ϵ��� ���� 
		
		serverAction sAction = new serverAction(this);
		Start.addActionListener(sAction);
		textField.addActionListener(sAction);
		
	}
	
	class serverAction implements ActionListener {
		private ServerFrame sf;
		public serverAction(ServerFrame sf) {
			this.sf = sf;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// action Event�� sendBtn �Ǵ� textField���� Enter key�� ������
			if(e.getSource() == Start || e.getSource() == textField) {
				// textField�� ���� ������� ������
				if(textField.getText().equals("") || textField.getText().length() == 0) {
					textField.setText("��Ʈ��ȣ�� �Է����ּ���");
					textField.requestFocus(); // ��Ŀ���� �ٽ� textField�� �־��ش�.
				}
				else {
					try {
						Port = Integer.parseInt(textField.getText());
						//server_start();
						new Server(sf, Port);
					}
					catch(Exception er) {
						textField.setText("���ڷ� �Է����ּ���");
						textField.requestFocus();
					}
				}
			}
		}
	}
}
