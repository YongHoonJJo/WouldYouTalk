package Default;

import java.util.HashMap;
import java.util.Vector;

public class Lists {
	private int userNum;
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	private HashMap<String, String> idpw = new HashMap<String, String>();
	
	private Vector<User> vcUsers = new Vector<User>(); // ��� ����� ����Ʈ
	private Vector<UserInfo> vcSoc = new Vector<UserInfo>(); // �������� ����� ����Ʈ
	
	private int chatNum;
	private Vector<String> chattingLists = new Vector<String>(); // ��� ����ڵ��� ä�� ���� ����Ʈ
	// ��üä���� �߰����� ���� ���Խ�ų�� ����غ���..
	
	public Lists() {
		userNum = chatNum = 0;
	}
	
	public Vector<UserInfo> getUserInfoVec() { 
		return vcSoc; 
	}
	
	public void addUser(User user) { // ȸ������...
		vcUsers.add(user);
		map.put(user.getID(), userNum); // �̸��Ϸ� �����ĺ� ��ȣ �ο�
		user.setUserNum(userNum);
		user.addFriends(userNum++); // �ڱ� �ڽ��� ģ���� �߰�
		
		idpw.put(user.getID(), user.getPasswd()); // login �˻��
	}
	
	public int getUserNum(String email) { // ����� ���� �ĺ���ȣ ����
		return map.get(email);
	}
	
	public boolean isIdPasswdCorrect(String id, String pw) {
		return idpw.get(id).equals(pw);
	}
}
