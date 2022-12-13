import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.*;
import java.util.Arrays;


public class pwdChange_server extends Thread{

    public static void main(String[] args) throws Exception{
        byte[] bf = new byte[1500];
        DatagramPacket dp = new DatagramPacket(bf,  bf.length);
        DatagramSocket ds=new DatagramSocket(9995);
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
            String PWD=new String(bf).trim();//새로운 비밀번호







            new Thread(new Capitalizer(ds,dp, bf,ID,Email,Phone,PWD)).start();//Thread 생성
            Thread.sleep(100);



        }
    }
    private static class Capitalizer implements Runnable{
        private DatagramSocket ds;


        private byte[] bf = new byte[1500];

        private DatagramPacket dp = new DatagramPacket(bf,  bf.length);

        private String ID,Email,Phone,PWD;
        Capitalizer(DatagramSocket ds,DatagramPacket dp,byte[] bf,String ID,String Email,String Phone,String PWD){
            this.ds=ds;

            this.dp=dp;
            this.bf=bf;
            this.ID=ID;

            this.Email=Email;
            this.Phone=Phone;
            this.PWD=PWD;





        }

        @Override
        public void run(){

            try{


                //sql연동
               Connection con = null;

                con = DriverManager.getConnection("jdbc:mysql://localhost/network", "root", "Namja241377@");
                //조건에 맞는 비밀번호를 찾아 업데이트
                String sql = String.format("update user set password='%s' where id = '%s' and email='%s' and phone='%s'",PWD,ID,Email,Phone);

                PreparedStatement pstmt=con.prepareStatement(sql);

                int r=pstmt.executeUpdate();









                pstmt.close();
                con.close();
            }
            catch(Exception e){

            }


        }
    }
}