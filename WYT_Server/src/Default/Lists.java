package Default;

import java.util.HashMap;
import java.util.Vector;

public class Lists {
	private int userNum;
	private HashMap<String, Integer> map = new HashMap<String, Integer>(); // id,userNum
	private HashMap<String, String> idpw = new HashMap<String, String>();
	
	private Vector<User> vcUsers = new Vector<User>(); // 모든 사용자 리스트
	private Vector<UserInfo> vcSoc = new Vector<UserInfo>(); // 접속중인 사용자 리스트
	
	private int chatNum;
	private Vector<ChatData> chatDataVec = new Vector<ChatData>(); // 모든 사용자들의 채팅 내용 리스트
	// 단체채팅은 추가할지 같이 포함시킬지 고민해보기..
	
	public Lists() {
		userNum = chatNum = 0;
		chatDataVec.add(new ChatData("empty")); // 0번 인덱스는 사용하지 않음.
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
		// 대화중인 방번호가 있는지 체크하기
		String sentID = vcUsers.elementAt(sentUserNum).getID();
		if(vcUsers.elementAt(recvUserNum).hasChatUser(sentID)) 
			return vcUsers.elementAt(recvUserNum).getChatUserChatNum(sentID);
		else {
			return ++chatNum; // 1번방부터 사용
		}
	}
	
	public Vector<User> getUserVec() {
		return vcUsers;
	}
	
	public Vector<UserInfo> getUserInfoVec() { 
		return vcSoc; 
	}
	
	public void addUser(User user) { // 회원가입...
		vcUsers.add(user);
		map.put(user.getID(), userNum); // 이메일로 고유식별 번호 부여
		user.setUserNum(userNum);
		user.addFriends(userNum++); // 자기 자신을 친구로 추가
		
		idpw.put(user.getID(), user.getPasswd()); // login 검사용
	}
	
	public int getUserNum(String ID) { // 사용자 고유 식별번호 리턴
		return map.get(ID);
	}
	
	public boolean isIdPasswdCorrect(String id, String pw) {
		return idpw.get(id).equals(pw);
	}
}
