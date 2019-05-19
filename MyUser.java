package fuction;

import java.io.Serializable;
import java.util.List;

public class MyUser implements Serializable{
	
	private static final long serialVersionUID = 6543722756249559791L;
	
	private String name;
	private String password;
	private String send;
	private List<String> onlineuser;
	private boolean loginflag;
	
	public void setLoginflag(boolean loginflag) {
		this.loginflag = loginflag;
	}
	
	public boolean getloginflag() {
		return loginflag;
	}
	
	public void setOnlineuser(List<String> onlineuser) {
		this.onlineuser = onlineuser;
	}
	
	public List<String> getOnlineuser() {
		return onlineuser;
	}
	
	public void setSend(String send) {
		this.send = send;
	}
	
	public String getSend() {
		return send;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
}
