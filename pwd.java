import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.*;

public class pwd extends JFrame {

	pwd(){
        setTitle("Pwd Finder");
        setSize(500,700);

        JLabel IdLabel = new JLabel("ID");
        IdLabel.setBounds(100,200,80,30);
        add(IdLabel);
        JTextField Idtext = new JTextField();
        Idtext.setBounds(200,200,200,30);
        add(Idtext);

        JLabel mailLabel = new JLabel("E-mail");
        mailLabel.setBounds(100,250,80,30);
        add(mailLabel);

        JTextField mailtext = new JTextField(20);
        mailtext.setBounds(200,250,200,30);
        add(mailtext);

        JLabel phoneLabel = new JLabel("Phone number");
        phoneLabel.setBounds(80,300,100,30);
        add(phoneLabel);

        JTextField phonetext = new JTextField(20);
        phonetext.setBounds(200,300,200,30);
        add(phonetext);

        JButton button = new JButton("NEXT");
        button.setBounds(170,400,150,30);
        add(button);

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID=Idtext.getText();
                String Email=mailtext.getText();
                String Phone=phonetext.getText();
                try {
                    DatagramSocket ds = new DatagramSocket();//데이터 그램 소켓 생성
                    InetAddress ia = InetAddress.getByName("localhost");
                    byte[] bf = ID.getBytes();
                    DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9994);
                    ds.send(dp);
                    Arrays.fill(bf,(byte)0);

                    bf = Email.getBytes();
                    dp = new DatagramPacket(bf, bf.length, ia, 9994);
                    ds.send(dp);
                    Arrays.fill(bf,(byte)0);
                    bf = Phone.getBytes();
                    dp = new DatagramPacket(bf, bf.length, ia, 9994);
                    ds.send(dp);
                    Arrays.fill(bf,(byte)0);
                    dp=new DatagramPacket(bf, bf.length,ia,9994);

                    ds.receive(dp);
                    String a=new String(bf).trim();

                    if(a.equals("1")){

                        new pwd_change(Idtext.getText(),mailtext.getText(),phonetext.getText());
                        setVisible(false);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "정확한 정보를 입력하시오.");
                    }




                }catch(IOException es) {}
            }
        });
        
    }



}
