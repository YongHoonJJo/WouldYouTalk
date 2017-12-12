package Default;

import java.util.HashMap;
import java.util.Vector;

public class Lists {
	private int userNum;
	private HashMap<String, Integer> map = new HashMap<String, Integer>(); // id,userNum
	private HashMap<String, String> idpw = new HashMap<String, String>();
	
	private Vector<User> vcUsers = new Vector<User>(); // ��� ����� ����Ʈ
	private Vector<UserInfo> vcSoc = new Vector<UserInfo>(); // �������� ����� ����Ʈ
	
	private int chatNum;
	private Vector<ChatData> chatDataVec = new Vector<ChatData>(); // ��� ����ڵ��� ä�� ���� ����Ʈ
	// ��üä���� �߰����� ���� ���Խ�ų�� ����غ���..
	
	public Lists() {
		userNum = chatNum = 0;
		chatDataVec.add(new ChatData("empty")); // 0�� �ε����� ������� ����.
	}
	
	public ChatData getChatData(int chatNum) {
		return chatDataVec.elementAt(chatNum);
	}
	
	public void addChatMsg(int chatNum, String msg) {
		if(chatDataVec.size() == chatNum)
			chatDataVec.add(new ChatData(msg));
		else
			chatDataVec.elementAt(chatNum).addMsg(msg);
	}

	
	public int getNewChatNum(int recvUserNum, int sentUserNum) {
		// ��ȭ���� ���ȣ�� �ִ��� üũ�ϱ�
		String sentID = vcUsers.elementAt(sentUserNum).getID();
		if(vcUsers.elementAt(recvUserNum).hasChatUser(sentID)) 
			return vcUsers.elementAt(recvUserNum).getChatUserChatNum(sentID);
		else {
			return ++chatNum; // 1������� ���
		}
	}
	
	public Vector<User> getUserVec() {
		return vcUsers;
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
	
	public int getUserNum(String ID) { // ����� ���� �ĺ���ȣ ����
		return map.get(ID);
	}
	
	public boolean isIdPasswdCorrect(String id, String pw) {
		return idpw.get(id).equals(pw);
	}
}
