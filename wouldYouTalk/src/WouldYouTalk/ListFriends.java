package WouldYouTalk;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import Panels.FriendPanel;
import Panels.StateMsgPanel;

public class ListFriends extends JPanel{
	private ArrayList<UserInfo> friendsList;
	private ArrayList<JPanel> jpanelList;
	
	public ListFriends() {
		//setBackground(Color.WHITE);
		friendsList = new ArrayList<UserInfo>();
		jpanelList = new ArrayList<JPanel>();
		setLayout(null);
		
		FriendPanel fp1 = new FriendPanel(new UserInfo("YongHoonJJo"));
		FriendPanel fp2 = new FriendPanel(new UserInfo("JungEunSSong"));
		FriendPanel fp3 = new FriendPanel(new UserInfo("HyungWooAAhn"));
		fp1.setBounds(0, 0, 150, 50);
		fp2.setBounds(0, 50, 150, 50);
		fp3.setBounds(0, 100, 150, 50);
		add(fp1);
		add(fp2);
		add(fp3);
		
		StateMsgPanel smp1 = new StateMsgPanel("State Msg 1");
		StateMsgPanel smp2 = new StateMsgPanel("State Msg 2");
		StateMsgPanel smp3 = new StateMsgPanel("State Msg 3");
		smp1.setBounds(150, 12, 300, 50);
		smp2.setBounds(150, 12+50, 400, 50);
		smp3.setBounds(150, 12+100, 500, 50);
		add(smp1);
		add(smp2);
		add(smp3);
	}
}
