package WouldYouTalk;

public class UserInfo {
	private String ID;
	private String name;
	private String email;
	private String stateMsg;
	private String photoPath;
	// background photo;
	
	public UserInfo(String ID, String name, String stateMsg) {
		this.ID = ID;
		this.name = name;
		this.stateMsg = stateMsg;
		//photoPath = "C:\\Users\\YongHoonJJo\\eclipse-workspace\\wouldYouTalk\\src\\WouldYouTalk/default.png";
		photoPath = "src/WouldYouTalk/default.png";
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
