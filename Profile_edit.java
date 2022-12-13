import java.sql.*;
import java.util.Arrays;
import java.io.IOException;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Profile_edit extends JFrame{
	String filePath;
    String fileName;

    Profile_edit(String userid){
        setTitle("Profile Edit");
        setSize(500,700);

        JLabel imageL = new JLabel("Profile image",SwingConstants.CENTER);
        imageL.setBounds(200,70,100,30);
        add(imageL);

        JButton imageB = new JButton("Select Image");
        imageB.setBounds(100,120,300,30);
        add(imageB);

        JLabel nameL = new JLabel("Name: ");
        nameL.setBounds(100,200,100,30);
        add(nameL);

        JTextField nameText = new JTextField(15);
        nameText.setBounds(200,200,200,30);
        add(nameText);

        JLabel stateL = new JLabel("State message: ");
        stateL.setBounds(100,300,100,30);
        add(stateL);

        JTextField stateText = new JTextField(15);
        stateText.setBounds(200,300,200,230);
        add(stateText);

        JButton editB = new JButton("Edit");
        editB.setBounds(200,570,100,30);
        add(editB);

        setLayout(null);
        setVisible(true);

        imageB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Select_image a = new Select_image(userid);
                filePath = a.getFilePath();
                fileName = a.getFileName();
                new Select_Image2(fileName);
                setVisible(false);
            }
        });

        Select_Image2 b = new Select_Image2();
        fileName = b.getFileName();
        
        String Name = nameText.getText();
        String State = stateText.getText();

        editB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Name == null || State == null) {
                    JOptionPane.showMessageDialog(null, "Please enter all values!");

                } else {
                    JOptionPane.showMessageDialog(null, "Modified!");

                }
            }
        });

        editB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                	 DatagramSocket ds = new DatagramSocket();
                     InetAddress ia = InetAddress.getByName("localhost");
                     byte[] bf = Name.getBytes();
                     DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9998);
                     ds.send(dp);
                     Arrays.fill(bf,(byte)0);

                     bf = State.getBytes();
                     dp = new DatagramPacket(bf, bf.length, ia, 9999);
                     ds.send(dp);
                     Arrays.fill(bf,(byte)0);
                     
                     bf = fileName.getBytes();
                     dp = new DatagramPacket(bf, bf.length, ia, 9998);
                     ds.send(dp);
                     Arrays.fill(bf,(byte)0);
                     
                     bf = userid.getBytes();
                     dp = new DatagramPacket(bf, bf.length, ia, 9998);
                     ds.send(dp);

                }catch (Exception ex) {
                    ex.printStackTrace();
                }

                new Profile(userid);
                setVisible(false);
            }
        });
    }

}
