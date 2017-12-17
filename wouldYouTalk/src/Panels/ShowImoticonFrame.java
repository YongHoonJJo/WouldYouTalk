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
	private JButton sendBtn = new JButton("�̸�Ƽ�� ����");

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
			// �־��� �ð��뿡 �°� ���� �ð����� �ʱ�ȭ�� GregorianCalender ��ü�� ��ȯ.
			Calendar cal = Calendar.getInstance ( jst );  
			// System.out.println ( cal.get ( Calendar.YEAR ) + "�� " + ( cal.get ( Calendar.MONTH ) + 1 ) + "�� " + cal.get ( Calendar.DATE ) + "�� " + cal.get ( Calendar.HOUR_OF_DAY ) + "�� " + cal.get ( Calendar.MINUTE ) + "�� " + cal.get ( Calendar.SECOND ) + "�� " );
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