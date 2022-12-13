import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.*;
import java.util.Arrays;

public class pwd_change extends JFrame{

	pwd_change(String ID,String EMAIL,String PHONE){
		setTitle("Password Change");
        setSize(500,700);

        JLabel pwdL1 = new JLabel("New Password");
        pwdL1.setBounds(90, 200, 100, 30);
        JTextField pwdF1 = new JTextField(20);
        pwdF1.setBounds(200, 200, 200, 30);

        JLabel pwdL2 = new JLabel("Check Password");
        pwdL2.setBounds(90, 280, 100, 30);
        JTextField pwdF2 = new JTextField(20);
        pwdF2.setBounds(200, 280, 200, 30);

        JButton button = new JButton("Change");
        button.setBounds(100, 400, 300, 30);

        add(pwdL1);
        add(pwdF1);
        add(pwdL2);
        add(pwdF2);
        add(button);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);

		 button.addActionListener(new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
                    try {
                        DatagramSocket ds = new DatagramSocket();//데이터 그램 소켓 생성
                        InetAddress ia = InetAddress.getByName("localhost");
                        byte[] bf = ID.getBytes();
                        DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9995);
                        ds.send(dp);
                        Arrays.fill(bf,(byte)0);

                        bf = EMAIL.getBytes();
                        dp = new DatagramPacket(bf, bf.length, ia, 9995);
                        ds.send(dp);
                        Arrays.fill(bf,(byte)0);
                        bf =PHONE.getBytes();
                        dp = new DatagramPacket(bf, bf.length, ia, 9995);
                        ds.send(dp);
                        bf =pwdF1.getText().getBytes();
                        dp = new DatagramPacket(bf, bf.length, ia, 9995);
                        ds.send(dp);

                        new login();
                        setVisible(false);
                    }catch(IOException es) {}
	            }
	        });
	}



}
