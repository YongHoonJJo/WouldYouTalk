package Panels;

import java.awt.*;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;

import WouldYouTalk.UserInfo;

public class FriendPanel extends JPanel {
	//private UserInfo user;
	private String userName;
	private String stateMsg;
	private ImageIcon userPhoto;
	private JLabel nameLabel;
	
	public FriendPanel(UserInfo user) {
		//this.user = user;
		userName = user.getName();
		stateMsg = user.getStateMsg();
		setOpaque(false);
		
		/*** changes ImageIcon size (w, h) ***/
		//userPhoto = new ImageIcon(user.getPhotoPath());
		userPhoto = user.getProfileIcon();
		Image img = userPhoto.getImage(); 
		
		Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		userPhoto = new ImageIcon(newImg);
		nameLabel = new JLabel(" "+userName, userPhoto, JLabel.LEFT);
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1)); // hgap, vgap
		add(nameLabel);
	}
}
