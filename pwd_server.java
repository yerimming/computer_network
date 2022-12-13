import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.*;
import java.util.Arrays;


public class pwd_server extends Thread{

    public static void main(String[] args) throws Exception{
        byte[] bf = new byte[1500];
        DatagramPacket dp = new DatagramPacket(bf,  bf.length);
        DatagramSocket ds=new DatagramSocket(9994);
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







            new Thread(new Capitalizer(ds,dp, bf,ID,Email,Phone)).start();//Thread 생성
            Thread.sleep(100);



        }
    }
    private static class Capitalizer implements Runnable{
        private DatagramSocket ds;


        private byte[] bf = new byte[1500];

        private DatagramPacket dp = new DatagramPacket(bf,  bf.length);

        private String ID,Email,Phone;
        Capitalizer(DatagramSocket ds,DatagramPacket dp,byte[] bf,String ID,String Email,String Phone){
            this.ds=ds;

            this.dp=dp;
            this.bf=bf;
            this.ID=ID;

            this.Email=Email;
            this.Phone=Phone;





        }

        @Override
        public void run(){

            try{
                Integer pt = dp.getPort();
                InetAddress ia = dp.getAddress();
                Connection con = null;
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/network", "root", "Namja241377@");
                String sql = "SELECT id FROM user WHERE id=? and email=? and phone=?";//조건에 맞는 아이디 찾기

                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, ID);

                pstmt.setString(2,Email);
                pstmt.setString(3,Phone);


                ResultSet rs = pstmt.executeQuery();

                Arrays.fill(bf,(byte)0);


                if(rs.next()){
                    bf="1".getBytes();
                    dp=new DatagramPacket(bf, bf.length,ia,pt);
                    ds.send(dp);//아이디가 존재한다고 보내기



                }
                else{
                    bf="0".getBytes();
                    dp=new DatagramPacket(bf, bf.length,ia,pt);
                    ds.send(dp);//아이디가 존재하지 않는다고 보내기
                }










                pstmt.close();
                con.close();
            }
            catch(Exception e){

            }


        }
    }
}
