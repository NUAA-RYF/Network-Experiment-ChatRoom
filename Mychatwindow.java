package chatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fuction.IO;
import fuction.MyUser;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JTextArea;

public class Mychatwindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4595156198257102195L;
	Socket socket;
	MyUser user;
	ObjectOutputStream out;
	
	private JPanel contentPane;
	JButton btnSend;
	private JScrollPane scrollPane_Send;
	private JScrollPane scrollPane_Receive;
	private JScrollPane scrollPane_User;
	JTextArea textArea_Receive;
	JTextArea textArea_Send;
	JTextArea txtrOnlineusers;

/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mychat frame = new Mychat();
					frame.setVisible(true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/


	public Mychatwindow(Socket socket,MyUser user) {
		setTitle("ChatRoom");
		this.socket = socket;
		this.user = user;
	
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 697, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		scrollPane_Receive = new JScrollPane();
		
		scrollPane_Send = new JScrollPane();

		btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String send = textArea_Send.getText();
				textArea_Send.setText("");
				user.setSend(send);
				IO.writeMessage(socket, user);
				System.out.println("消息发送成功！");
			}
		});
		scrollPane_User = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_Receive, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_Send, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane_User, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane_Receive, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(scrollPane_Send, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_User, GroupLayout.PREFERRED_SIZE, 456, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnSend)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
//		textArea_user.setEditable(false);
		txtrOnlineusers = new JTextArea();
		txtrOnlineusers.setText("OnlineUsers:");
		txtrOnlineusers.setEditable(false);
//		textArea_user.setText(name+"\n");
		
		scrollPane_User.setViewportView(txtrOnlineusers);
		
		textArea_Send = new JTextArea();
		scrollPane_Send.setViewportView(textArea_Send);
		
		textArea_Receive = new JTextArea();
		textArea_Receive.setEditable(false);
		scrollPane_Receive.setViewportView(textArea_Receive);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);	
	}
	public void appendText(String in) {
		textArea_Receive.append("\n"+in);
	}
}
