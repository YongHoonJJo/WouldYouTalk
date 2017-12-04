package WouldYouTalk;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MainView extends JFrame {
	private static ListChatting listChatting; // JPanel
	private ListFriends listFriends;
	private ListMoreInfo listMoreInfo;
	private MenuView menu;
	
	//private JScrollPane js;
	
	private static Network network;
	
	public static ListChatting getListChatting() {
		return listChatting;
	}
	
	public static Network getNetwork() {
		return network != null ? network : null;
	}
	
	public MainView(Network network) {
		this.network = network;
		
		listChatting = new ListChatting();
		listFriends = new ListFriends();
		listMoreInfo = new ListMoreInfo();
		menu = new MenuView(this);	
		
		this.network = network;
		
		setView();
	}
	
	public void setView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Would You Talk MainView");
        setBounds(0, 0, 428, 618);
        setLayout(null);
        
        menu.setBounds(0, 0, 428, 75);
        listFriends.setBounds(0, 75, 428, 718);
        listChatting.setBounds(0, 75, 428, 718);
        listMoreInfo.setBounds(0, 75, 428, 718);
        
        add(menu);
        add(listFriends);
	}
	//public ListChatting getListChatting() { return listChatting; }
	
	public ListFriends getListFriends() {
		return listFriends;
	}
	
	public ListMoreInfo getListMoreInfo() {
		return listMoreInfo;
	}
	
}
