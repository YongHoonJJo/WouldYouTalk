package Default;

import java.util.Vector;

public class User {
	private int userNum;
	private String name;
	private String email; // For ����� �ĺ�..
	private String passwd;
	private String stateMsg;
	// userPhotoPath
	
	private Vector<Integer> Friends = new Vector<Integer>(); // ���� ����� ģ�� ����Ʈ
	private Vector<Integer> PeopleYouMayKnow = new Vector<Integer>(); // ���� ����� ģ�� ����Ʈ
	
	private Vector<Integer> chattingLists = new Vector<Integer>(); // ���� ä�� ����Ʈ
	// ��ü ä�� ����Ʈ�� ���Ŀ�..
	
	public User(String name, String email, String passwd) {
		this.name = name;
		this.email = email;
		this.passwd = passwd;
		this.stateMsg = "";
	}
	
	public String getEmail() { 
		return email; 
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
