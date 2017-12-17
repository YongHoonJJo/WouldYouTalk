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
		
		sendMsgBtn = new JButton("전송"); // imageIcon으로 바꾸기...
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
    		// 주어진 시간대에 맞게 현재 시각으로 초기화된 GregorianCalender 객체를 반환.
    		Calendar cal = Calendar.getInstance ( jst );  
    		// System.out.println ( cal.get ( Calendar.YEAR ) + "년 " + ( cal.get ( Calendar.MONTH ) + 1 ) + "월 " + cal.get ( Calendar.DATE ) + "일 " + cal.get ( Calendar.HOUR_OF_DAY ) + "시 " + cal.get ( Calendar.MINUTE ) + "분 " + cal.get ( Calendar.SECOND ) + "초 " );
    		String hour = cal.get(Calendar.HOUR_OF_DAY)+"";
    		String min = cal.get ( Calendar.MINUTE )+"";
    		if(min.length() == 1) min = "0"+min;
    		String curTime = hour + ":" + min;
    		// 서버로 메세지 보내기
    		network.sendMessage("[MSG]::"+chatNum+"::"+frID+"::"+myID+"::"+curTime+"::"+msg);
    	}
	}
	
	
}
