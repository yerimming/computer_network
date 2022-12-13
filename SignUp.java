import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Arrays;


import javax.swing.*;

public class SignUp extends JFrame{


	SignUp(){

		setTitle("Sign Up");
		setSize(500,700);

		JLabel idL = new JLabel("ID");
		idL.setBounds(100, 120, 80, 30);
		JTextField idF = new JTextField();
		idF.setBounds(200, 120, 200, 30);

		JLabel emailL = new JLabel("e-mail");
		emailL.setBounds(100, 170, 80, 30);
		JTextField emailF = new JTextField();
		emailF.setBounds(200, 170, 200, 30);

		JLabel pwL = new JLabel("Password");
		pwL.setBounds(100, 220, 80, 30);
		JTextField pwF = new JTextField();
		pwF.setBounds(200, 220, 200, 30);

		JLabel pnL = new JLabel("Phone Number");
		pnL.setBounds(100, 270, 80, 30);
		JTextField pnF = new JTextField();
		pnF.setBounds(200, 270, 200, 30);

		JLabel gL = new JLabel("Gender");
		gL.setBounds(100, 320, 80, 30);
		JTextField gF = new JTextField();
		gF.setBounds(200, 320, 200, 30);

		JLabel nL = new JLabel("Name");
		nL.setBounds(100, 370, 80, 30);
		JTextField nF = new JTextField();
		nF.setBounds(200, 370, 200, 30);

		JLabel birthL = new JLabel("Birth");
		birthL.setBounds(100, 420, 80, 30);
		JTextField birthF = new JTextField();
		birthF.setBounds(200, 420, 200, 30);

		JButton button = new JButton("Sign Up ");
		button.setBounds(180, 520, 150, 30);


		add(idL);
		add(idF);
		add(emailL);
		add(emailF);
		add(pwL);
		add(pwF);
		add(pnL);
		add(pnF);
		add(nL);
		add(nF);
		add(gL);
		add(gF);
		add(birthL);
		add(birthF);
		add(button);




		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);

		button.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {


					String ID=idF.getText();
					String Name= nF.getText();
					String PWD=pwF.getText();
					String Email=emailF.getText();
					String Phone=pnF.getText();
					String Gender=gF.getText();
					String Birth=birthF.getText();
				try {
					DatagramSocket ds = new DatagramSocket();//데이터 그램 소켓 생성
					InetAddress ia = InetAddress.getByName("localhost");
					byte[] bf = ID.getBytes();
					DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9992);
					ds.send(dp);
					Arrays.fill(bf,(byte)0);
					bf = PWD.getBytes();
					dp = new DatagramPacket(bf, bf.length, ia, 9992);
					ds.send(dp);
					Arrays.fill(bf,(byte)0);
					bf = Name.getBytes();
					dp = new DatagramPacket(bf, bf.length, ia, 9992);
					ds.send(dp);
					Arrays.fill(bf,(byte)0);
					bf = Email.getBytes();
					dp = new DatagramPacket(bf, bf.length, ia, 9992);
					ds.send(dp);
					Arrays.fill(bf,(byte)0);
					bf = Phone.getBytes();
					dp = new DatagramPacket(bf, bf.length, ia, 9992);
					ds.send(dp);
					Arrays.fill(bf,(byte)0);
					bf = Gender.getBytes();
					dp = new DatagramPacket(bf, bf.length, ia, 9992);
					ds.send(dp);
					Arrays.fill(bf,(byte)0);
					bf = Birth.getBytes();
					dp = new DatagramPacket(bf, bf.length, ia, 9992);
					ds.send(dp);
				}catch(IOException es) {}






				new login();
				setVisible(false);
			}
		});
	}

}
