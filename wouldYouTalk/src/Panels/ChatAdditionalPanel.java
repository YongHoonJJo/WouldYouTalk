package Panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import WouldYouTalk.LoginView;
import WouldYouTalk.MainView;
import WouldYouTalk.Network;
import WouldYouTalk.UserInfo;

public class ChatAdditionalPanel extends JPanel {
	private UserInfo user;
	private int chatNum;
	private Network network;
	
	private JButton photoBtn;
	private JButton imoticonBtn;
	
	private boolean SIFon = false;
	private ChatAdditionalPanel cap;
	private ShowImoticonFrame sif = null;
	
	public ChatAdditionalPanel(int chatNum, UserInfo user) {
		this.setBackground(Color.WHITE);
		setLayout(null);
		
		network = MainView.getNetwork();
		this.user = user;
		this.chatNum = chatNum;
		cap = this;
		
		photoBtn = new JButton("그림");
		photoBtn.setBounds(5, 6, 60, 30);
		ImgSendActionListener imgSendActionListener = new ImgSendActionListener();
		photoBtn.addActionListener(imgSendActionListener);
		add(photoBtn);
		
		imoticonBtn = new JButton("이모티콘");
		imoticonBtn.setBounds(70, 6, 90, 30);
		ImoticonSendActionListener imoticonSendActionListener = new ImoticonSendActionListener();
		imoticonBtn.addActionListener(imoticonSendActionListener);
		add(imoticonBtn);
	}
	
	public void setSIFnull() {
		sif = null;
	}
	
	private class ImoticonSendActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("ImoticonSendAction");
			if(sif == null)
				new ShowImoticonFrame(chatNum, user, cap);
			
		}
	}
	
	private class ImgSendActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("ImgSendAction");
			
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif"); 
			fileChooser.setFileFilter(filter);
			
			int ret = fileChooser.showOpenDialog(null);
			
			if(ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.", "Warnning", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			String filePath = fileChooser.getSelectedFile().getPath();
			System.out.println(filePath);
			
			File imgFile = new File(filePath);
			int byteSize = (int)imgFile.length();
			
			// 용량 제한 걸기..!!
			if(byteSize > 1024*40) {
				JOptionPane.showMessageDialog(null, "이미지의 용량이 너무 큽니다.", "Warnning", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			byte[] bytes = new byte[byteSize];
			try {
				DataInputStream inByte = new DataInputStream(new FileInputStream(imgFile));
				inByte.readFully(bytes);
				inByte.close();
			} catch(Exception ee) {}
			
			String myID = LoginView.getMyID();
    		String frID = user.getID();
			
			TimeZone jst = TimeZone.getTimeZone ("JST");
    		Calendar cal = Calendar.getInstance ( jst );  
    		String hour = cal.get(Calendar.HOUR_OF_DAY)+"";
    		String min = cal.get ( Calendar.MINUTE )+"";
    		if(min.length() == 1) min = "0"+min;
    		String curTime = hour + ":" + min;
			
    		network.sendMessage("[IMG]::"+chatNum+"::"+frID+"::"+myID+"::"+curTime+"::"+byteSize);
			//Thread.sleep(10);
			
			network.sendBytes(bytes);
			System.out.println("[IMG] sendBytes(bytes) ok");
		}
	}
}
