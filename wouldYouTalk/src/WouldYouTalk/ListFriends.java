package WouldYouTalk;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import Panels.FriendPanel;
import Panels.StateMsgPanel;

public class ListFriends extends JPanel{
	private Vector<UserInfo> friendsList; // For sorting Friend lists
	private Vector<FriendPanel> jpanelList;
	private Vector<StateMsgPanel> jpanelStateList;
	private UserInfo userInfo;
	private int friendCnt;
	
	private String tmpId;
	private String tmpName;
	private String tmpStateMsg;
	
	public ListFriends() {
		friendCnt = 0;
		//setBackground(Color.WHITE);
		friendsList = new Vector<UserInfo>();
		jpanelList = new Vector<FriendPanel>();
		jpanelStateList = new Vector<StateMsgPanel>();
		
		//JScrollPane js = new JScrollPane();
		
		setLayout(null);
		
		/*
		userInfo = new UserInfo("YongHoonJJo");
		friendsList.add(userInfo);
		FriendPanel fp1 = new FriendPanel(userInfo);
		
		userInfo = new UserInfo("JungEunSSong");
		friendsList.add(userInfo);
		FriendPanel fp2 = new FriendPanel(userInfo);
		
		userInfo = new UserInfo("HyungWooAAhn");
		friendsList.add(userInfo);
		FriendPanel fp3 = new FriendPanel(userInfo);
		
		fp1.setBounds(0, 0, 150, 50);
		fp2.setBounds(0, 50, 150, 50);
		fp3.setBounds(0, 100, 150, 50);
		add(fp1);
		add(fp2);
		add(fp3);
		
		StateMsgPanel smp1 = new StateMsgPanel("State Msg 1");
		StateMsgPanel smp2 = new StateMsgPanel("State Msg 2");
		StateMsgPanel smp3 = new StateMsgPanel(userInfo.getStateMsg());
		smp1.setBounds(150, 12, 300, 50);
		smp2.setBounds(150, 12+50, 400, 50);
		smp3.setBounds(150, 12+100, 500, 50);
		add(smp1);
		add(smp2);
		add(smp3);
		*/
		//setFriendPanel("h00", "hoon", "Hello");
		//setFriendPanel("h99", "JJo", "Bye");
	}
	
	public synchronized void setFriendPanel(String id, String name, String stateMsg) {
		System.out.println(">> "+id+":"+name+":"+stateMsg+"::");
		
		userInfo = new UserInfo(id, name, stateMsg);
		friendsList.add(userInfo);
		FriendPanel fp = new FriendPanel(userInfo);	
		fp.setBounds(0, (friendCnt*50), 150, 50);
		jpanelList.add(fp);
		add(fp);

		StateMsgPanel smp = new StateMsgPanel(stateMsg);
		smp.setBounds(150, 12+(friendCnt*50), 450, 50);
		jpanelStateList.add(smp);
		add(smp);
		friendCnt++;
		
		revalidate();
	}
}
