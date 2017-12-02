package WouldYouTalk;

import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Panels.StateBgPanel;
import Panels.StateInfoPanel;
import Panels.StateProfileImgPanel;

public class StateView extends JFrame {
	private UserInfo user;
	//private String userName;
	//private String userStateMsg;
	//private UserImg...
	//private UserBg...
	
	private ListFriends listFriends;
	private StateBgPanel stateBgPanel;
	private StateInfoPanel stateInfoPanel;	
	private StateProfileImgPanel stateProfileImgPanel;
	
	public StateView(UserInfo user, ListFriends listFriends) {
		this.user = user;
		this.listFriends = listFriends;

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Friend State View");
        setBounds(50, 50, 328, 518);
        setVisible(true);
        setLayout(null);
        
        addFocusListener(new stateViewFocusListener());
        
        stateProfileImgPanel = new StateProfileImgPanel(user); // 동그랗게 만들고 싶다....
        stateProfileImgPanel.setBounds(130, 275, 50, 50);
        add(stateProfileImgPanel);

		stateBgPanel = new StateBgPanel(user.getStateMsg());
        stateBgPanel.setBounds(0, 0,  328, 300);
        add(stateBgPanel);

		stateInfoPanel = new StateInfoPanel(user, this);
        stateInfoPanel.setBounds(0, 300, 328, 218);
        add(stateInfoPanel);
        
	}
	
	public void disposePanel() {
		dispose(); 
		listFriends.setStateViewNull();
	}
	
	class stateViewFocusListener extends FocusAdapter {
		public void focusLost(FocusEvent e) {
			disposePanel(); // 포커스를 잃었을 때 창 닫기
		}
	}
	
}
