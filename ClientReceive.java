package chatter;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import fuction.IO;
import fuction.MyUser;

public class ClientReceive extends Thread{
	Socket socket;
	
	Myclient frame;
	
	Mychatwindow window;

	MyUser rec;
	
	MyUser user;
	
	ObjectInputStream in;
	
	private boolean flag = false;
	public ClientReceive(Socket socket, Mychatwindow window,MyUser user) {
		rec = new MyUser();
		this.window = window;
		this.user = user;
		this.socket = socket;
	}
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("�ȴ�������Ϣ��");
				Object objrec = IO.readMessage(this.socket);
				System.out.println("��Ϣ��ȡ�ɹ���");
				
				if(objrec instanceof MyUser){
					rec = (MyUser) objrec;
					
					this.window.txtrOnlineusers.setText("OnlineUsers:\n");
					for(int i = 0;i < rec.getOnlineuser().size();i++) {
						this.window.txtrOnlineusers.append(rec.getOnlineuser().get(i)+"\n");
					}
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String str =rec.getName()+"  "+df.format(new Date())+"\n"+rec.getSend();
					this.window.textArea_Receive.append("\n"+str);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
