package WouldYouTalk;

public class UserInfo {
	private int userID;
	private String name;
	private String email;
	private String stateMsg;
	private String photoPath;
	// background photo;
	
	public UserInfo(String name) {
		this.name = name;
		stateMsg = "Hello Wolrd!";
		//photoPath = "C:\\Users\\YongHoonJJo\\eclipse-workspace\\wouldYouTalk\\src\\WouldYouTalk/default.png";
		photoPath = "src/WouldYouTalk/default.png";
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
