import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.*;
import java.util.Arrays;

import static javax.swing.JOptionPane.showMessageDialog;


public class friends extends JFrame{

    public friends(String id){//id입력받음

        super("friend");//프레임 이름
        setSize(500,700);//사이즈
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);



        JButton btn1=new JButton("친구추가");//친구추가 버튼
        JTextField frd=new JTextField(10);
        frd.setBounds(20,40,340,30);
        this.add(frd);
        JButton btn2=new JButton("새로고침");//새로고침 버튼
        JButton btn3=new JButton("홈");//홈버튼
        btn2.setBounds(20,300,100,30);
        btn3.setBounds(150,300,100,30);




        btn1.setBounds(360,40,100,30);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String frdName=frd.getText();//친구이름 얻기
                try {
                    DatagramSocket ds = new DatagramSocket();//데이터 그램 소켓 생성
                    InetAddress ia = InetAddress.getByName("localhost");
                    byte[] bf = frdName.getBytes();
                    DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9993);//데이터 패킷 생성
                    ds.send(dp);//서버로 친구 아이디 전송
                    Arrays.fill(bf,(byte)0);
                    bf=id.getBytes();
                    dp=new DatagramPacket(bf, bf.length, ia, 9993);
                    ds.send(dp);//서버로 자신의 아이디 전송
                    ds.close();
                }catch(Exception ex2){
                    System.out.println(ex2.getMessage());
                }
            }
        });
        btn2.addActionListener(new ActionListener() {//새로고침 눌렀을 때
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new friends(id);

            }
        });
        btn3.addActionListener(new ActionListener() {//홈버튼 눌렀을 때
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Messenger messenger = new Messenger(id);
                messenger.jf.setVisible(true);
            }
        });
        this.add(btn1);
        setVisible(true);
        this.add(btn2);
        setVisible(true);
        this.add(btn3);
        setVisible(true);

        PopupMenu pm=new PopupMenu();//팝업메뉴 선언
        this.add(pm);
        MenuItem mi1=new MenuItem("talk");
        MenuItem mi2=new MenuItem("delete friend");
        MenuItem mi3=new MenuItem("send file");
        MenuItem mi4=new MenuItem("profile");

        pm.add(mi1);

        pm.add(mi2);
        pm.add(mi3);
        pm.add(mi4);
        try {
            DatagramSocket ds = new DatagramSocket();//데이터 그램 소켓 생성
            InetAddress ia = InetAddress.getByName("localhost");
            byte[] bf = id.getBytes();
            DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9991);
            ds.send(dp);
            bf=new byte[1500];
            dp=new DatagramPacket(bf,bf.length);



            String[] friends= {"", "", "", "", "", "", "",};//친구 이름을 받을 문자열



            ds.receive(dp);
            int a=(int)bf[0];

            Arrays.fill(bf,(byte)0);

            for(int j=0;j<a;j++){

                ds.receive(dp);
                friends[j] = new String(bf).trim();
                System.out.println(friends[j]);

                Arrays.fill(bf,(byte)0);



            }
            ds.close();

            JList list=new JList(friends);
            list.setBounds(20,80,450,200);



            list.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    pm.show(friends.this,e.getXOnScreen(),e.getYOnScreen());
                    mi1.addActionListener(new ActionListener() {//대화하기 눌렀을 때
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new ChatGUIClient(id,String.valueOf(list.getSelectedValue()));//채팅창 열기
                        }
                    });
                    mi2.addActionListener(new ActionListener() {//친구 삭제 눌렀을 때
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                DatagramSocket ds = new DatagramSocket();//데이터 그램 소켓 생성
                                InetAddress ia = InetAddress.getByName("localhost");
                                byte[] bf = String.valueOf(list.getSelectedValue()).getBytes();
                                DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9996);
                                ds.send(dp);//친구 아이디 전송
                                Arrays.fill(bf, (byte) 0);
                                bf = id.getBytes();
                                dp = new DatagramPacket(bf, bf.length, ia, 9996);
                                ds.send(dp);//자신의 아이디 전송
                                ds.close();
                            } catch(Exception ex2){
                                System.out.println(ex2.getMessage());
                            }
                        }
                    });
                    mi3.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println(list.getSelectedValue()+"3");
                        }
                    });
                    mi4.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println(list.getSelectedValue()+"4");
                        }
                    });

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            list.setVisible(true);
            this.add(list);
            list.revalidate();
            list.repaint();



        }catch(Exception ex2){
            System.out.println(ex2.getMessage());
        }






    }


}