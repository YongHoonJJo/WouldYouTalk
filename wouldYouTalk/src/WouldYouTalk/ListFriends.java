package WouldYouTalk;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;

import Panels.FriendPanel;
import Panels.StateMsgPanel;

public class ListFriends extends JPanel{
	private Vector<FriendPanel> jpanelList;
	private Vector<StateMsgPanel> jpanelStateList;
	
	private HashMap<String, Integer> IDtoUserInfoVecIdx;
	
	private Vector<UserInfo> userInfoVec; // My Friends list
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
		IDtoUserInfoVecIdx = new HashMap<String, Integer>();
		setLayout(null);
		
		stateView = null;
	}
	
	public int getUserInfoVecIdx(String ID) {
		return IDtoUserInfoVecIdx.get(ID);
	}
	
	public Vector<UserInfo> getUserInfoVec() {
		return userInfoVec;
	}
	
	public UserInfo getMyUserInfo() {
		return Me;
	}
	
	public void initUserInfo(String id, String name, String stateMsg) {
		System.out.println(">> "+id+":"+name+":"+stateMsg);
		UserInfo userInfo = new UserInfo(id, name, stateMsg);
		if(Me == null) Me = userInfo;
		userInfoVec.add(userInfo);
		IDtoUserInfoVecIdx.put(id, userInfoVec.size()-1);
	}
	
	public void doRevalidate() {
		revalidate();
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
