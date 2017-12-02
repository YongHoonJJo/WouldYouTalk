package WouldYouTalk;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import Panels.FriendPanel;
import Panels.StateMsgPanel;

public class ListFriends extends JPanel{
	private Vector<FriendPanel> jpanelList;
	private Vector<StateMsgPanel> jpanelStateList;
	
	private Vector<UserInfo> userInfoVec;
	private UserInfo Me = null;
	private int friendCnt;
	
	private String tmpId;
	private String tmpName;
	private String tmpStateMsg;
	
	private StateView stateView;
	private String stateViewUserID;
	
	//private JScrollPane js;
	
	public ListFriends() {
		friendCnt = 0;
		setBackground(Color.WHITE);
		jpanelList = new Vector<FriendPanel>();
		jpanelStateList = new Vector<StateMsgPanel>();
		userInfoVec = new Vector<UserInfo>();
		setLayout(null);
		
		stateView = null;
		
		/*
		fp1.setBounds(0, 0, 150, 50);
		fp2.setBounds(0, 50, 150, 50);
		fp3.setBounds(0, 100, 150, 50);
		
		smp1.setBounds(150, 12, 300, 50);
		smp2.setBounds(150, 12+50, 400, 50);
		smp3.setBounds(150, 12+100, 500, 50);
		*/
	}
	
	public void initUserInfo(String id, String name, String stateMsg) {
		System.out.println(">> "+id+":"+name+":"+stateMsg);
		UserInfo userInfo = new UserInfo(id, name, stateMsg);
		if(Me == null) Me = userInfo;
		userInfoVec.add(userInfo);
	}
	
	public void setFriendPanel() {
		int i=0;
		
		for(UserInfo user : userInfoVec) {	
			FriendPanel fp = new FriendPanel(user);	
			fp.setBounds(0, (i*50), 150, 50);
			jpanelList.add(fp);
			add(fp);
			
			fp.addMouseListener(new popStateViewListener(user, this));
			
			StateMsgPanel smp = new StateMsgPanel(user.getStateMsg());
			smp.setBounds(150, 12+(i*50), 450, 50);
			jpanelStateList.add(smp);
			add(smp);
			i++;
		}
		revalidate();
	}
	
	public void setStateViewNull() {
		stateView = null;
	}
	class popStateViewListener extends MouseAdapter {
		
		UserInfo user;
		ListFriends listFriends;
		public popStateViewListener(UserInfo user, ListFriends listFriends) {
			this.user = user;
			this.listFriends = listFriends;
		}
		public void mouseClicked(MouseEvent e) {
			if(stateView == null) {
				stateViewUserID = user.getID();
				stateView = new StateView(user, listFriends);
			}
			else if(!stateViewUserID.equals(user.getID())) {
				stateView.dispose(); // 기존 프레임 닫기
				stateViewUserID = user.getID();
				stateView = new StateView(user, listFriends);
			}
		}
	}
}
