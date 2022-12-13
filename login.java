import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.*;
import java.io.*;

public class login extends JFrame {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	login() {

		JFrame frm = new JFrame("login");
		frm.setSize(700, 1000);
		frm.setLocationRelativeTo(null);
		frm.setResizable(false);
		frm.getContentPane().setLayout(null);
		frm.getContentPane().setBackground(Color.WHITE);

		JLabel title = new JLabel("login", JLabel.CENTER);
		title.setBounds(0, 70, 700, 50);
		JLabel idL = new JLabel("ID");
		idL.setBounds(200, 300, 80, 30);

		JTextField idF = new JTextField();
		idF.setBounds(300, 300, 200, 30);

		JLabel pwL = new JLabel("Password");
		pwL.setBounds(200, 350, 80, 30);

		JPasswordField pwF = new JPasswordField();
		pwF.setBounds(300, 350, 200, 30);

		JButton bt1 = new JButton("Login");
		bt1.setBounds(270, 450, 150, 30);
		bt1.setBackground(Color.WHITE);

		JButton bt2 = new JButton("Forgot Password?");
		bt2.setBounds(200, 700, 140, 30);
		bt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new pwd();
				frm.setVisible(false);
			}
		});
		bt2.setBackground(Color.WHITE);

		JButton bt3 = new JButton("Create Account");
		bt3.setBounds(350, 700, 140, 30);
		bt3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUp();
				frm.setVisible(false);
			}
		});
		bt3.setBackground(Color.WHITE);

		frm.add(title);
		frm.add(idL);
		frm.add(idF);
		frm.add(pwL);
		frm.add(pwF);
		frm.add(bt1);
		frm.add(bt2);
		frm.add(bt3);

		setTitle("Login");
		setLayout(null);
		frm.setVisible(true);



		bt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userid = new String(idF.getText());
				String pw = new String(pwF.getPassword());
				System.out.println("입력된 id: " + userid);
				System.out.println("입력된 pw: " + pw);
				try {
					DatagramSocket ds = new DatagramSocket();//데이터 그램 소켓 생성
					InetAddress ia = InetAddress.getByName("localhost");
					byte[] bf = userid.getBytes();
					DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9990);
					ds.send(dp);
					bf=pw.getBytes();
					dp=new DatagramPacket(bf,bf.length,ia,9990);
					ds.send(dp);
					byte[] bf_2=new byte[1500];
					DatagramPacket dp_2=new DatagramPacket(bf_2,bf_2.length);
					ds.receive(dp_2);
					String a=new String(bf_2).trim();
					if (userid.equals("") || pw.equals("")) {
						JOptionPane.showMessageDialog(null, "Enter ID and Password!");
					} else {
						int result = Integer.parseInt(a);
						if (result == 1) { // 성공
							System.out.println("로그인 성공: " + userid);
							JOptionPane.showMessageDialog(null, userid + ",\nLogin Success!");
							Messenger messenger = new Messenger(userid);
							messenger.jf.setVisible(true);
							frm.setVisible(false);
						} else if (result == 0) {
							JOptionPane.showMessageDialog(null, "Wrong Password!");
							pwF.requestFocus();
							System.out.println("비번 틀림");
						} else if (result == -1) {
							JOptionPane.showMessageDialog(null, "ID doesn't exist!");
							idF.requestFocus();
							System.out.println("아이디 존재하지 않음");
							// 아이디 틀림
						}
					}

				}catch(IOException es) {}


				}

		});
	}


}
