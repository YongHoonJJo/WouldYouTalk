package Default;

import java.util.Vector;

public class User {
	private int userNum;
	private String name;
	private String id; // For 사용자 식별..
	private String passwd;
	private String stateMsg;
	// userPhotoPath
	
	private Vector<Integer> Friends = new Vector<Integer>(); // 내가 등록한 친구 리스트
	private Vector<Integer> PeopleYouMayKnow = new Vector<Integer>(); // 나를 등록한 친구 리스트
	
	private Vector<Integer> chattingLists = new Vector<Integer>(); // 나의 채팅 리스트
	// 단체 채팅 리스트는 추후에..
	
	public User(String name, String id, String passwd) {
		this.name = name;
		this.id = id;
		this.passwd = passwd;
		this.stateMsg = "";
	}
	
	public String getID() { 
		return id; 
	} 
	public String getPasswd() {
		return passwd;
	}
	public void setUserNum(int num) { 
		userNum = num; 
	}
	public void addFriends(int userNum) {
		Friends.add(userNum);
	}
	public void addPeopleYouMayKnow(int userNum) {
		PeopleYouMayKnow.add(userNum);
	}
	public void addChattingLists(int chatNum) {
		chattingLists.add(chatNum);
	}
}
