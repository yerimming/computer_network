
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;


public class Profile extends JFrame{
    String Pimg;
    String name;
    String birth;
    String msg;
    Profile(String userid){

        setTitle("Profile");
        setSize(500,700);

        //JPanel profileP = new JPanel();

        Pimg = new select_user(userid).getImg();
        name = new select_user(userid).getName();
        msg = new select_user(userid).getMsg();
        System.out.println(Pimg);
        System.out.println(name);
        ImageIcon icon = new ImageIcon(
                Profile.class.getResource(Pimg)
        );


        Image img = icon.getImage();
        Image updateImg = img.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon updateIcon = new ImageIcon(updateImg);

        JButton imgBtn = new JButton();
        imgBtn.setBounds(150,50,200,200);
        imgBtn.setPreferredSize(new Dimension(200,200));

        imgBtn.setIcon(updateIcon);

        JLabel idLabel = new JLabel("ID: ");
        idLabel.setBounds(150,300,100,20);

        JLabel idL = new JLabel(userid);
        idL.setBounds(250,300,200,20);

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(150,380,100,20);

        JLabel nameL = new JLabel("Park network");
        nameL.setBounds(250,380,300,20);

        JLabel stateLabel = new JLabel("State Message: ");
        stateLabel.setBounds(120,450,370,20);

        JLabel stateL = new JLabel("wow");
        stateL.setBounds(250,450,100,20);

        JButton profile_edit = new JButton("Profile Edit");
        profile_edit.setBounds(70, 620,360,35);

        add(imgBtn);

        add(nameLabel);
        add(idLabel);
        add(stateLabel);
        add(nameL);
        add(idL);
        add(stateL);
        add(profile_edit);

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        imgBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Profile_image(icon);
            }
        });

        profile_edit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Profile_edit(userid);
                setVisible(false);
            }
        });
    }

    public static void main(String[] args){
        new Profile("network4");
    }
}

