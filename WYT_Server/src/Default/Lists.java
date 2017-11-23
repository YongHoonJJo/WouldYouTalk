package Default;

import java.util.HashMap;
import java.util.Vector;

public class Lists {
	private int userNum;
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	private Vector<User> vcUsers = new Vector<User>(); // 모든 사용자 리스트
	private Vector<UserInfo> vcSoc = new Vector<UserInfo>(); // 접속중인 사용자 리스트
	
	private int chatNum;
	private Vector<String> chattingLists = new Vector<String>(); // 모든 사용자들의 채팅 내용 리스트
	// 단체채팅은 추가할지 같이 포함시킬지 고민해보기..
	
	public Lists() {
		userNum = chatNum = 0;
	}
	
	public Vector<UserInfo> getUserInfoVec() { 
		return vcSoc; 
	}
	
	public void addUser(User user) { // 회원가입...
		vcUsers.add(user);
		map.put(user.getEmail(), userNum); // 이메일로 고유식별 번호 부여
		user.setUserNum(userNum);
		user.addFriends(userNum++); // 자기 자신을 친구로 추가
	}
	
	public int getUserNum(String email) { // 사용자 고유 식별번호 리턴
		return map.get(email);
	}
	
}
