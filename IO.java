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
			//��ȡ���������
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(os);
			//���������
			out.writeObject(user);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static Object readMessage(Socket socket) {
		Object obj = null;
		try {
			//��ȡ����������
			InputStream is = socket.getInputStream();
			ObjectInputStream in = new ObjectInputStream(is);
			//��ȡ����
			obj = in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
