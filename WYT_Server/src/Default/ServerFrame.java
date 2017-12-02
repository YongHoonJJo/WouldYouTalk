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
	private JTextField textField; // 사용할 Port번호 입력
	private JButton Start; // Server를 실행시킬 버튼
	private JTextArea textArea; // 클라이언트 및 서버 메세지 출력
	
	private int Port; // 포트번호
	
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
		
		textArea = new JTextArea(); // 클라이언트 및 서버 메세지 출력
		textArea.setColumns(20);
		textArea.setRows(5);;
		js.setBounds(0, 0, 264, 254);
		contentPane.add(js);
		js.setViewportView(textArea); // ???
		
		textField = new JTextField("30015"); // 사용할 포트번호 입력
		textField.setBounds(98, 264, 154, 37);
		textField.setColumns(10);
		contentPane.add(textField);
		
		JLabel portLabel = new JLabel("Port Number");
		portLabel.setBounds(12, 264, 98, 37);
		contentPane.add(portLabel);
		
		Start = new JButton("서버 실행"); // 서버 실행 버튼
		Start.setBounds(0, 325, 264, 37);
		contentPane.add(Start);
		textArea.setEditable(false); // 사용자가 수정하지 못하도록 설정 
		
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
			// action Event가 sendBtn 또는 textField에서 Enter key를 쳤을때
			if(e.getSource() == Start || e.getSource() == textField) {
				// textField에 값이 들어있지 않을때
				if(textField.getText().equals("") || textField.getText().length() == 0) {
					textField.setText("포트번호를 입력해주세요");
					textField.requestFocus(); // 포커스를 다시 textField에 넣어준다.
				}
				else {
					try {
						Port = Integer.parseInt(textField.getText());
						//server_start();
						new Server(sf, Port);
					}
					catch(Exception er) {
						textField.setText("숫자로 입력해주세요");
						textField.requestFocus();
					}
				}
			}
		}
	}
}
