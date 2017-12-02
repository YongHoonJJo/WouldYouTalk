package Panels;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import WouldYouTalk.UserInfo;

public class StateProfileImgPanel extends JPanel {
	private ImageIcon userPhoto;
	private JLabel photoLabel;
	
	public StateProfileImgPanel(UserInfo user) {
		setLayout(null);
		
        userPhoto = new ImageIcon(user.getPhotoPath()); // 변경해야할 부분...
		Image img = userPhoto.getImage(); 
		Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		userPhoto = new ImageIcon(newImg);
		photoLabel = new JLabel(userPhoto);
		photoLabel.setBounds(0, 0, 50, 50);
		add(photoLabel);
	}
}
