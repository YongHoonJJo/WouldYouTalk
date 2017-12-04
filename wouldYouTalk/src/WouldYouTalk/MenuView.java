package WouldYouTalk;

import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuView extends JPanel {
	
	private MainView mainView;
	//private ImageIcon bgImg;
	
	public MenuView(MainView mainView) {
		this.mainView = mainView;
		
		setBackground(Color.LIGHT_GRAY);
		setLayout(new FlowLayout());
		
		JButton FriendsBtn = new JButton("Friends");
		FriendsBtn.addActionListener(new FriendsActionListener());
		add(FriendsBtn);
		
		JButton ChattingBtn = new JButton("Chattings");
		ChattingBtn.addActionListener(new ChattingsActionListener());
		add(ChattingBtn);
	
		JButton MoreInfoBtn = new JButton("MoreInfo");
		MoreInfoBtn.addActionListener(new MoreInfoActionListener());
		add(MoreInfoBtn);	
	}
	
	private class FriendsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mainView.remove(mainView.getListChatting());
			mainView.remove(mainView.getListMoreInfo());
			mainView.add(mainView.getListFriends());
			mainView.revalidate();
			mainView.repaint();
		}
	}
	
	private class ChattingsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mainView.remove(mainView.getListFriends());
			mainView.remove(mainView.getListMoreInfo());
			mainView.add(mainView.getListChatting());
			mainView.revalidate();
			mainView.repaint();
		}
	}
	
	private class MoreInfoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mainView.remove(mainView.getListFriends());
			mainView.remove(mainView.getListChatting());
			mainView.add(mainView.getListMoreInfo());
			mainView.revalidate();
			mainView.repaint();
		}
	}
}
