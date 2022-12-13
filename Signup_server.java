import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.*;
import java.util.Arrays;


public class Signup_server extends Thread{

    public static void main(String[] args) throws Exception{
        byte[] bf = new byte[1500];
        DatagramPacket dp = new DatagramPacket(bf,  bf.length);
        DatagramSocket ds=new DatagramSocket(9992);
        while (true) {



            //패킷받기
            bf = new byte[1500];
            dp = new DatagramPacket(bf, bf.length);
            ds.receive(dp);
            Integer pt = dp.getPort();
            InetAddress ia = dp.getAddress();
            String ID=new String(bf).trim();//아이디

            bf= new byte[1500];
            Arrays.fill(bf,(byte)0);
            dp= new DatagramPacket(bf, bf.length,ia,pt);
            ds.receive(dp);
            String PWD=new String(bf).trim();//비밀번호

            bf= new byte[1500];
            Arrays.fill(bf,(byte)0);
            dp= new DatagramPacket(bf, bf.length,ia,pt);
            ds.receive(dp);
            String Name=new String(bf).trim();//이름

            bf= new byte[1500];
            Arrays.fill(bf,(byte)0);
            dp= new DatagramPacket(bf, bf.length,ia,pt);
            ds.receive(dp);
            String Email=new String(bf).trim();//이메일

            bf= new byte[1500];
            Arrays.fill(bf,(byte)0);
            dp= new DatagramPacket(bf, bf.length,ia,pt);
            ds.receive(dp);
            String Phone=new String(bf).trim();//핸드폰

            bf= new byte[1500];
            Arrays.fill(bf,(byte)0);
            dp= new DatagramPacket(bf, bf.length,ia,pt);
            ds.receive(dp);
            String Gender=new String(bf).trim();//성별

            bf= new byte[1500];
            Arrays.fill(bf,(byte)0);
            dp= new DatagramPacket(bf, bf.length,ia,pt);
            ds.receive(dp);
            String Birth=new String(bf).trim();//생일





            new Thread(new Capitalizer(ds,dp, bf,ID,PWD,Name,Email,Phone,Gender,Birth)).start();//Thread 생성
            Thread.sleep(100);



        }
    }
    private static class Capitalizer implements Runnable{
        private DatagramSocket ds;


        private byte[] bf = new byte[1500];

        private DatagramPacket dp = new DatagramPacket(bf,  bf.length);

        private String ID,PWD,Name,Email,Phone,Gender,Birth;
        Capitalizer(DatagramSocket ds,DatagramPacket dp,byte[] bf,String ID,String PWD,String Name,String Email,String Phone,String Gender,String Birth){
            this.ds=ds;

            this.dp=dp;
            this.bf=bf;
            this.ID=ID;
            this.PWD=PWD;
            this.Name=Name;
            this.Email=Email;
            this.Phone=Phone;
            this.Gender=Gender;
            this.Birth=Birth;




        }

        @Override
        public void run(){

            try{
                Integer pt = dp.getPort();
                InetAddress ia = dp.getAddress();
                Connection con = null;
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/network", "root", "Namja241377@");
                //테이블에 insert함
                String sql = "insert into user(id, password, name, email, phone, gender, birth) values (?,?,?,?,?,?,?);";

                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, ID);
                pstmt.setString(2,PWD);
                pstmt.setString(3,Name);
                pstmt.setString(4,Email);
                pstmt.setString(5,Phone);
                pstmt.setString(6,Gender);
                pstmt.setString(7,Birth);

                int r = pstmt.executeUpdate();









                pstmt.close();
                con.close();
            }
            catch(Exception e){

            }


        }
    }
}