package Panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import WouldYouTalk.ChatView;
import WouldYouTalk.StateView;
import WouldYouTalk.UserInfo;

public class StateInfoPanel extends JPanel {
	private UserInfo user;
	
	private StateView stateView;
	
	private JLabel userNameLabel;
	private JButton chatBtn;
	//private JButton profileConfigBtn;
	
	
	public StateInfoPanel(UserInfo user, StateView stateView) {
		this.user = user;
		this.stateView = stateView;
		setBackground(Color.WHITE);
		
		userNameLabel = new JLabel(user.getName());
		userNameLabel.setBounds(135, 30, 50, 50); // ũ�� �������� ����غ���..
		add(userNameLabel);
		
		chatBtn = new JButton("1:1 ä��");
		chatBtn.setBounds(105, 80, 100, 30);
		chatBtn.addActionListener(new PopChatRoomListener());
		add(chatBtn);
		
		setLayout(null);
		
		//setBounds(0, 300, 328, 218);
	}
	
	class PopChatRoomListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			stateView.disposePanel();
			// �̹� �˵Ǿ� �ִ����� ���� ����ó�� �ϱ�
			new ChatView(user); 
		}
	}
}
