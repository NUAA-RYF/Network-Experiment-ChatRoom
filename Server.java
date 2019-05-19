package chatter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import fuction.IO;
import fuction.MyUser;

public class Server extends Thread{
	private static boolean loginflag = false;
	private static boolean islogin = false;
	//创建集合对象存储客户端  
//	public static List<Serverhandler> list = new ArrayList<Serverhandler>();
	public static void main(String[] args) throws IOException{
			//创建服务器对象
			ServerSocket server = new ServerSocket(8088);
			//监听客户端连接
			while(true) {
				
			System.out.println("等待连接...");
			Socket socket = server.accept();
			
			System.out.println("服务器连接...");
			Object obj =IO.readMessage(socket);
			System.out.println("服务器接收到对象...");
			
	//		if(obj instanceof MyUser) {
			
			MyUser rec = (MyUser) obj;
			loginflag = Serverhandler.Logincheck(rec);
			rec.setLoginflag(loginflag);
			
			islogin = Serverhandler.isLoginflag(rec);
			
			System.out.println(loginflag);
			
			if(loginflag && islogin) {
					IO.writeMessage(socket,rec);
					System.out.println("写消息成功！");
					Serverhandler channel = new Serverhandler(socket);
					channel.start();
			}else{
				System.out.println("进入啦！！！！！！");
				IO.writeMessage(socket , rec);
				socket.close();
			}	
		}
	}
}
