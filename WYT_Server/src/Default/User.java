package Default;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class User {
	private int userNum;
	private String name;
	private String id; // For 사용자 식별..
	private String passwd;
	private String stateMsg;
	// userPhotoPath
	
	//private Vector<User> vcUsers;
	private Lists lists;
	private Vector<UserInfo> userInfos; // 다중 접속 허용
	
	private Vector<Integer> Friends = new Vector<Integer>(); // 내가 등록한 친구 리스트
	private Vector<Integer> PeopleYouMayKnow = new Vector<Integer>(); // 나를 등록한 친구 리스트
	
	private Vector<Integer> chattingLists = new Vector<Integer>(); // 나의 채팅 리스트
	private HashMap<String, Integer> chatUserMap;
	
	// 단체 채팅 리스트는 추후에..
	
	public User(String name, String id, String passwd, Lists lists) {
		this.name = name;
		this.id = id;
		this.passwd = passwd;
		this.stateMsg = "default";
		this.lists = lists;
		userInfos = new Vector<UserInfo>();
		
		chatUserMap = new HashMap<String, Integer>();	
	}
	
	public boolean hasChatUser(String userID) {
		return chatUserMap.containsKey(userID);
	}
	
	public int getChatUserChatNum(String userID) {
		return chatUserMap.get(userID);
	}
	
	public void addChatUser(String userID, int chatNum) {
		if(hasChatUser(userID) == false)
			chatUserMap.put(userID, chatNum);
	}
	
	public void removeUserInfoVec(UserInfo userInfo) {
		userInfos.remove(userInfo);
	}
	
	public void setUserInfo(UserInfo userInfo) {
		this.userInfos.add(userInfo);
	}
	
	public void setStateMsg(String msg) {
		this.stateMsg = msg;
	}
	
	public void sendMsg(String msg) {
		System.out.println("sent to " + id);
		for(UserInfo userInfo : userInfos) {
			userInfo.send_Msg(msg);
		}
	}
	
	public String getName() { return name; }
	public String getID() { return id; } 
	public String getPasswd() { return passwd; }
	public String getStateMsg() { return stateMsg; }
	public int getUserNum() { return userNum; }
	public void setUserNum(int num) { userNum = num; }
	public void addFriends(int userNum) { 
		Friends.add(userNum); // 내가 등록한 친구 // 나를 등록한 친구
		lists.getUserVec().elementAt(userNum).addPeopleYouMayKnow(lists.getUserNum(id));
	}
	public Vector<Integer> getFriendsNumVec() { return Friends; }
	public void addPeopleYouMayKnow(int userNum) { PeopleYouMayKnow.add(userNum); }
	public void addChattingLists(int chatNum) { chattingLists.add(chatNum); }
	public Vector<Integer> getChattingList() { return chattingLists; }
}
