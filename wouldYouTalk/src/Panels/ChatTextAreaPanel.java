package Panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import WouldYouTalk.ListFriends;
import WouldYouTalk.LoginView;
import WouldYouTalk.MainView;
import WouldYouTalk.Network;
import WouldYouTalk.UserInfo;

public class ChatTextAreaPanel extends JPanel {
	private JTextArea inputArea;
	private JButton sendMsgBtn;
	
	private Network network;
	private UserInfo user;
	private int chatNum;
	
	public ChatTextAreaPanel(int chatNum, UserInfo user) {
		network = MainView.getNetwork();
		this.user = user;
		this.chatNum = chatNum;
		
		this.setBackground(Color.YELLOW);
		this.setLayout(null);;
		
		inputArea = new JTextArea();
		inputArea.setBounds(5, 5, 300, 80);
		add(inputArea);
		
		sendMsgBtn = new JButton("����"); // imageIcon���� �ٲٱ�...
		sendMsgBtn.setBounds(315, 5, 80, 50);
		add(sendMsgBtn);
		
		MsgSendActionListener msgSendActionListener = new MsgSendActionListener();
		sendMsgBtn.addActionListener(msgSendActionListener);
	}
		
	public void setChatNum(int chatNum) {
		this.chatNum = chatNum;
	}
	
	private class MsgSendActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		String msg = inputArea.getText();	
    		
    		if(msg.equals("")) return ;
    		
    		inputArea.setText("");
    		
    		String myID = LoginView.getMyID();
    		String frID = user.getID();
    		
    		TimeZone jst = TimeZone.getTimeZone ("JST");
    		// �־��� �ð��뿡 �°� ���� �ð����� �ʱ�ȭ�� GregorianCalender ��ü�� ��ȯ.
    		Calendar cal = Calendar.getInstance ( jst );  
    		// System.out.println ( cal.get ( Calendar.YEAR ) + "�� " + ( cal.get ( Calendar.MONTH ) + 1 ) + "�� " + cal.get ( Calendar.DATE ) + "�� " + cal.get ( Calendar.HOUR_OF_DAY ) + "�� " + cal.get ( Calendar.MINUTE ) + "�� " + cal.get ( Calendar.SECOND ) + "�� " );
    		String hour = cal.get(Calendar.HOUR_OF_DAY)+"";
    		String min = cal.get ( Calendar.MINUTE )+"";
    		if(min.length() == 1) min = "0"+min;
    		String curTime = hour + ":" + min;
    		// ������ �޼��� ������
    		network.sendMessage("[MSG]::"+chatNum+"::"+frID+"::"+myID+"::"+curTime+"::"+msg);
    	}
	}
	
	
}
