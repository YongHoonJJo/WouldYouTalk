package WouldYouTalk;

import javax.swing.ImageIcon;

public class UserInfo {
	private String ID;
	private String name;
	private String stateMsg;
	private String photoPath;
	// background photo;
	
	private ImageIcon profileIcon = null;
	
	private boolean chatViewOn;
	
	public UserInfo(String ID, String name, String stateMsg) {
		this.ID = ID;
		this.name = name;
		this.stateMsg = stateMsg;
		//photoPath = "C:\\Users\\YongHoonJJo\\eclipse-workspace\\wouldYouTalk\\src\\WouldYouTalk/default.png";
		photoPath = "src/WouldYouTalk/default.png";
	
		profileIcon = new ImageIcon(photoPath);
		
		chatViewOn = false;
	}
	
	public ImageIcon getProfileIcon() {
		return profileIcon;
	}
	
	public void setProfileIcon(ImageIcon icon) {
		this.profileIcon = icon;
	}
	
	public boolean getChatViewOn() {
		return chatViewOn;
	}
	
	public void setChatViewOn(boolean chatViewOn) {
		this.chatViewOn = chatViewOn;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhotoPath() {
		return photoPath;
	}
	
	public String getStateMsg() {
		return stateMsg;
	}
}
