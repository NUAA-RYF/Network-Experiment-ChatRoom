package chatter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import fuction.IO;
import fuction.MyUser;

public class Server extends Thread{
	private static boolean loginflag = false;
	private static boolean islogin = false;
	//�������϶���洢�ͻ���  
//	public static List<Serverhandler> list = new ArrayList<Serverhandler>();
	public static void main(String[] args) throws IOException{
			//��������������
			ServerSocket server = new ServerSocket(8088);
			//�����ͻ�������
			while(true) {
				
			System.out.println("�ȴ�����...");
			Socket socket = server.accept();
			
			System.out.println("����������...");
			Object obj =IO.readMessage(socket);
			System.out.println("���������յ�����...");
			
	//		if(obj instanceof MyUser) {
			
			MyUser rec = (MyUser) obj;
			loginflag = Serverhandler.Logincheck(rec);
			rec.setLoginflag(loginflag);
			
			islogin = Serverhandler.isLoginflag(rec);
			
			System.out.println(loginflag);
			
			if(loginflag && islogin) {
					IO.writeMessage(socket,rec);
					System.out.println("д��Ϣ�ɹ���");
					Serverhandler channel = new Serverhandler(socket);
					channel.start();
			}else{
				System.out.println("������������������");
				IO.writeMessage(socket , rec);
				socket.close();
			}	
		}
	}
}
