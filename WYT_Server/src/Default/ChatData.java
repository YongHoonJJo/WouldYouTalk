package Default;

public class ChatData {
	private String msg;
	
	public ChatData(String msg) {
		this.msg = msg;
	}
	
	public void addMsg(String msg) {
		this.msg += msg;
	}
	
	public String getMsg() {
		return msg;
	}
}
