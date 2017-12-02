package WouldYouTalk;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MainView extends JFrame {
	private ListChatting listChatting; // JPanel
	private ListFriends listFriends;
	private ListMoreInfo listMoreInfo;
	private MenuView menu;
	
	//private JScrollPane js;
	
	public MainView() {
		listChatting = new ListChatting();
		listFriends = new ListFriends();
		listMoreInfo = new ListMoreInfo();
		menu = new MenuView(this);
		setView();
	}

	public void setView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Would You Talk MainView");
        setBounds(0, 0, 428, 618);
        setLayout(null);
        
        menu.setBounds(0, 0, 428, 75);
        listFriends.setBounds(0, 75, 428, 518);
        listChatting.setBounds(0, 75, 428, 518);
        listMoreInfo.setBounds(0, 75, 428, 518);
        
        add(menu);
        add(listFriends);
	}
	
	public ListChatting getListChatting() {
		return listChatting;
	}
	
	public ListFriends getListFriends() {
		return listFriends;
	}
	
	public ListMoreInfo getListMoreInfo() {
		return listMoreInfo;
	}
	
}
