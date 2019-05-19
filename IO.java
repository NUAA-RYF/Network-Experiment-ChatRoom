package fuction;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IO {
	Socket socket;
	MyUser user;
	public static void writeMessage(Socket socket,MyUser user){
		try {
			//获取输出对象流
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(os);
			//将对象输出
			out.writeObject(user);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static Object readMessage(Socket socket) {
		Object obj = null;
		try {
			//获取对象输入流
			InputStream is = socket.getInputStream();
			ObjectInputStream in = new ObjectInputStream(is);
			//读取对象
			obj = in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
