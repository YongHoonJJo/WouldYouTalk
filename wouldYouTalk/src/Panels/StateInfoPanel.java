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
		userNameLabel.setBounds(135, 30, 50, 50); // 크기 동적으로 계산해보기..
		add(userNameLabel);
		
		chatBtn = new JButton("1:1 채팅");
		chatBtn.setBounds(105, 80, 100, 30);
		chatBtn.addActionListener(new PopChatRoomListener());
		add(chatBtn);
		
		setLayout(null);
		
		//setBounds(0, 300, 328, 218);
	}
	
	class PopChatRoomListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			stateView.disposePanel();
			// 이미 팝되어 있는지에 대한 예외처리 하기
			new ChatView(user); 
		}
	}
}
