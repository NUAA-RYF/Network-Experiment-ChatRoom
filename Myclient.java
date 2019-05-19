package chatter;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fuction.IO;
import fuction.MyUser;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JLabel;
import java.awt.Font;

public class Myclient extends JFrame {

	private static final long serialVersionUID = -6768797918609659179L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lbllogin;
	private JLabel lblpassword;
	private JButton btnlogin;
	private JButton btnquit;
	private boolean flag = false;
	Mychatwindow window;
	MyUser rec;
	ObjectOutputStream out;
	ClientReceive cr;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Myclient();
	}

	/**
	 * Create the frame.
	 */
	public Myclient() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 388, 297);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		btnlogin = new JButton("\u767B\u5F55");
		btnlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MyUser user = new MyUser();
				String name = textField.getText();
				String password = passwordField.getText();
				user.setName(name);
				user.setPassword(password);
				connect(user);	
			}
		});
		
		btnquit = new JButton("\u9000\u51FA");
		btnquit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		
		textField = new JTextField();
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		
		lbllogin = new JLabel("\u8D26\u53F7");
		lbllogin.setFont(new Font("微软雅黑 Light", Font.BOLD, 22));
		
		lblpassword = new JLabel("\u5BC6\u7801");
		lblpassword.setFont(new Font("微软雅黑 Light", Font.BOLD, 22));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lbllogin)
						.addComponent(lblpassword))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(33)
							.addComponent(btnlogin, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnquit, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(94, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbllogin))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpassword))
					.addGap(54)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnquit)
						.addComponent(btnlogin, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		setVisible(true);

	}
	public void connect(MyUser user) {
		//创建连接
		Socket socket;
		try {
			socket = new Socket("localhost", 8088);
			IO.writeMessage(socket, user);
			System.out.println("传出消息，等待接收.....");
			Object obj = IO.readMessage(socket);
			System.out.println("消息已经接收");
			if(obj instanceof MyUser) {
				MyUser rec = null;
				rec = (MyUser) obj;
				flag = rec.getloginflag();
			}
			
			if(flag){
				user.setLoginflag(rec.getloginflag());
				this.dispose();
				window = new Mychatwindow(socket,user);
			}else {
				try {
					JOptionPane.showMessageDialog(null, "账号密码错误！");
					socket.close();
					System.exit(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if(user.getloginflag()) {
				ClientReceive cr = new ClientReceive(socket, window, user);
				cr.start();
			}

		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
