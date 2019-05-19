package chatter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import fuction.IO;
import fuction.MyUser;

public class Serverhandler extends Thread{
	private boolean loginflag = false;
	
	public static List<String> onlineUsers = new ArrayList<>();
	public static List<Socket> onlineSockets = new ArrayList<>();
	
	Socket client;
	public Serverhandler(Socket client) {
		this.client = client;
		onlineSockets.add(this.client);
	}

/*	private void send(MyUser user) {
			try {
				ObjectOutputStream out = new ObjectOutputStream(this.client.getOutputStream());
				out.writeObject(user);
				out.flush();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Mychannel user");
			}
	}*/
/*
	登陆账号检查
*/
	public static boolean Logincheck(MyUser user) {
		try {
			String name = user.getName();
			String password = user.getPassword();
			FileInputStream fis = new FileInputStream(new File("src/users"));
			DataInputStream in = new DataInputStream(fis);
			String row = null;
			while((row = in.readLine()) != null) {
				if((name+"|"+password).equals(row))
					return true;
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File:users Not Found!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fileread Failed!");
		}
		return false;
	}
	public static boolean isLoginflag(MyUser user) {
		for(int i = 0;i < onlineUsers.size();i++) {
			if(user.getName().equals(onlineUsers.get(i))) {
				return false;//登录过的账号
			}	
		}
		return true;
	}
	@Override
	public void run() {
		while(true) {
			boolean nameflag = false;
			System.out.println("客户端等待接收消息...");
			Object obj = IO.readMessage(this.client);
			if(obj instanceof MyUser) {
				MyUser rec = (MyUser) obj;
				for(int i = 0;i < onlineUsers.size(); i++) {
					if(onlineUsers.get(i).equals(rec.getName())) {
						nameflag = true;
						break;
					}
				}
				if(!nameflag) {
					onlineUsers.add(rec.getName());
				}
				rec.setOnlineuser(onlineUsers);
				for(int i = 0;i < onlineSockets.size();i++) {
					Socket tempSocket = onlineSockets.get(i);
					IO.writeMessage(tempSocket , rec);
				}
			}
		}
	}
}
