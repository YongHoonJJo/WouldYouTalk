package WouldYouTalk;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;

import Panels.StateBgPanel;
import Panels.StateInfoPanel;

public class StateView extends JFrame {
	private UserInfo user;
	//private String userName;
	//private String userStateMsg;
	//private UserImg...
	//private UserBg...
	
	private ListFriends listFriends;
	private StateBgPanel stateBgPanel;
	private StateInfoPanel stateInfoPanel;	
	public StateView(UserInfo user, ListFriends listFriends) {
		this.user = user;
		this.listFriends = listFriends;
		stateBgPanel = new StateBgPanel(user.getStateMsg());
		stateInfoPanel = new StateInfoPanel(user.getName());
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Friend State View");
        setBounds(50, 50, 328, 518);
        setVisible(true);
        setLayout(null);
        
        addFocusListener(new stateViewFocusListener());
        
        stateBgPanel.setBounds(0, 0,  328, 300);
        stateInfoPanel.setBounds(0, 300, 328, 218);
        add(stateBgPanel);
        add(stateInfoPanel);
    
	}
	
	class stateViewFocusListener extends FocusAdapter {
		public void focusLost(FocusEvent e) {
			dispose(); // 포커스를 잃었을 때 창 닫기
			listFriends.setStateViewNull();
		}
	}
	
}
