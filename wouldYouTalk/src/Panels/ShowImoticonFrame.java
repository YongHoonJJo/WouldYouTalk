package Panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.JPanel;

import WouldYouTalk.LoginView;
import WouldYouTalk.MainView;
import WouldYouTalk.Network;
import WouldYouTalk.UserInfo;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ShowImoticonFrame extends JFrame {
	private JLabel imageLabel = new JLabel();
	private JButton sendBtn = new JButton("이모티콘 전송");

	private Network network;
	private UserInfo user;
	private int chatNum;
	private String iconName;
	
	private static ShowImoticonFrame sif;
	private boolean SIFon = false;
	private ChatAdditionalPanel cap;
	
	public ShowImoticonFrame(int chatNum, UserInfo user, ChatAdditionalPanel cap) {
		setTitle("Imoticon Peach");
		
		network = MainView.getNetwork();
		this.chatNum = chatNum;
		this.user = user;
 		sif = this;
		this.cap = cap;
		
		iconName = "[PIMO1]";
		setVisible(true);
		try {
			
			setLayout(null);
			setSize(210, 290);
			ImageIcon ii = new ImageIcon("peach.gif");
			imageLabel.setIcon(ii);
			imageLabel.setBounds(0,  0,  200, 200);
			add(imageLabel);

			sendBtn.setBounds(0,  200, 200, 50);
			sendBtn.addActionListener(new IconSendActionListener());
			add(sendBtn);

			// show it
			this.setLocationRelativeTo(null);
		} catch (Exception exception) {
			exception.printStackTrace();
		}	
	}

	
	
	private class IconSendActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("ImoticonSendAction!!");

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

			network.sendMessage("[MSG]::"+chatNum+"::"+frID+"::"+myID+"::"+curTime+"::"+iconName);
			cap.setSIFnull();
			sif.dispose();
		}
	}
}