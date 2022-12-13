import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class delete_friend extends Thread{
    public static void main(String[] args) throws Exception{

        byte[] bf = new byte[1500];
        DatagramPacket dp = new DatagramPacket(bf,  bf.length);
        byte[] bf_2 = new byte[1500];
        DatagramPacket dp_2 = new DatagramPacket(bf_2,  bf_2.length);



        DatagramSocket ds=new DatagramSocket(9996);


        while (true) {



            //패킷받음
            bf = new byte[1500];
            dp = new DatagramPacket(bf, bf.length);
            ds.receive(dp);
            Integer pt = dp.getPort();
            InetAddress ia = dp.getAddress();
            bf_2 = new byte[1500];
            dp_2 = new DatagramPacket(bf_2, bf_2.length,ia,pt);
            ds.receive(dp_2);





            new Thread(new Capitalizer(ds,dp, bf,dp_2,bf_2)).start();//Thread 생성
            Thread.sleep(100);



        }



    }
    private static class Capitalizer implements Runnable{
        private DatagramSocket ds;


        private byte[] bf = new byte[1500];
        private byte[] bf_2 = new byte[1500];
        private DatagramPacket dp = new DatagramPacket(bf,  bf.length);
        private DatagramPacket dp_2=new DatagramPacket(bf_2,bf_2.length);

        Capitalizer(DatagramSocket ds,DatagramPacket dp,byte[] bf,DatagramPacket dp_2,byte[] bf_2){
            this.ds=ds;

            this.dp=dp;
            this.bf=bf;

            this.dp_2=dp_2;
            this.bf_2=bf_2;

        }

        @Override
        public void run(){

            try{
                String id_get=new String(bf);
                String id2_get=new String(bf_2);
                String id=id_get.trim();//친구 아이디
                String id2=id2_get.trim();//사용자 아이디
                Integer pt = dp.getPort();
                InetAddress ia = dp.getAddress();
                Connection con = null;
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/network", "root", "Namja241377@");
                String sql = "delete from friend where friend_id=? and user_id=?;";//친구 테이블에서 제거

                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1,id);
                pstmt.setString(2,id2);


                int r = pstmt.executeUpdate();










            }
            catch(Exception e){

            }


        }
    }
}
