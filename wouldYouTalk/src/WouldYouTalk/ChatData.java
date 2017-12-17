package WouldYouTalk;

import java.util.HashMap;
import java.util.Vector;

public class ChatData {
	private int chatNum;
	private String partnerID;
	private String lastMsg;
	private String lastMsgTime;
	
	private String msg;
	
	private Vector<byte[]> byteVec;
	private HashMap<String, Integer> strToByteVecIdx;
	
	public ChatData(String msg) {
		chatNum = 0;
		this.msg = msg;
		byteVec = new Vector<byte[]>();
		strToByteVecIdx = new HashMap<String, Integer>();
	}
	
	public void setChatNum(int chatNum) {
		this.chatNum = chatNum;
	}
	
	public byte[] getBytes(String s) {
		return byteVec.elementAt(strToByteVecIdx.get(s));
	}
	
	public void addImg(String s, byte[] bb) {
		strToByteVecIdx.put(s,  byteVec.size());
		byteVec.add(bb);
	}
	
	public void addMsg(String msg) {
		this.msg += msg;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setChatDataInfo(int chatNum, String partnerID, String lastMsg, String lastMsgTime) {
		this.chatNum = chatNum;
		this.partnerID = partnerID;
		this.lastMsg = lastMsg;
		this.lastMsgTime = lastMsgTime;
	}
}
