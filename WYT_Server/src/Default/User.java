package Default;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class User {
	private int userNum;
	private String name;
	private String id; // For 사용자 식별..
	private String passwd;
	private String stateMsg;
	
	private File imgFile;
	private byte[] bytes;
	private int byteSize;
	
	private ImageIcon profileIcon;
	private BufferedImage bImg = null;
	
	
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
		this.stateMsg = "DoraEmong";
		
		//setDefaultProfileIcon();
		
		this.lists = lists;
		userInfos = new Vector<UserInfo>();
		chatUserMap = new HashMap<String, Integer>();	
		
		imgFile = new File("emong.jpg");
		byteSize = (int)imgFile.length();
		bytes = new byte[byteSize];
		try {
			DataInputStream inByte = new DataInputStream(new FileInputStream(imgFile));
			inByte.readFully(bytes);
			inByte.close();
		} catch(Exception e) {}
	}
	
	public int getByteSize() {
		return byteSize;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public Vector<UserInfo> getUserInfoVec() {
		return userInfos;
	}
	
	public void setProfileIcon(ImageIcon icon) {
		this.profileIcon = icon;
	}
	
	public ImageIcon getProfileIcon() {
		return profileIcon;
	}
	
	/*
	private void setDefaultProfileIcon() {

		try {
			imgFile = new File("today.jpg");
			bImg = ImageIO.read(imgFile);
			profileIcon = new ImageIcon(bImg);
		} catch (Exception e) {
			System.out.println("ImageIO.read() error");
		}
	}
	*/
	
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
		System.out.println("sent msg to " + id);
		for(UserInfo userInfo : userInfos) {
			userInfo.send_Msg(msg);
		}
	}
	
	public void sendBytes(byte[] bb) {
		System.out.println("sent bytes to " + id);
		for(UserInfo userInfo : userInfos) {
			userInfo.send_Bytes(bb);
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
