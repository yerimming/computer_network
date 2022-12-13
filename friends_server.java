import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.*;
import java.util.Arrays;


public class friends_server extends Thread{

    public static void main(String[] args) throws Exception{
        byte[] bf = new byte[1500];
        DatagramPacket dp = new DatagramPacket(bf,  bf.length);
        DatagramSocket ds=new DatagramSocket(9991);
        while (true) {




            bf = new byte[1500];
            dp = new DatagramPacket(bf, bf.length);
            ds.receive(dp);//아이디 받음
            Integer pt = dp.getPort();
            InetAddress ia = dp.getAddress();
            String id=new String(bf).trim();





            new Thread(new Capitalizer(ds,dp, bf,id)).start();//Thread 생성
            Thread.sleep(100);



        }
    }
    private static class Capitalizer implements Runnable{
        private DatagramSocket ds;


        private byte[] bf = new byte[1500];

        private DatagramPacket dp = new DatagramPacket(bf,  bf.length);

        private String id;
        Capitalizer(DatagramSocket ds,DatagramPacket dp,byte[] bf,String id){
            this.ds=ds;

            this.dp=dp;
            this.bf=bf;
            this.id=id;



        }

        @Override
        public void run(){

            try{
                Integer pt = dp.getPort();
                InetAddress ia = dp.getAddress();
                //sql연동
                Connection con = null;
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/network", "root", "Namja241377@");
                String sql = String.format("select friend_id from network.friend where user_id = '%s';",id);

                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                ResultSet rset = stmt.executeQuery(sql);
                rset.last();
                int a=rset.getRow();//친구 수 저장
                Arrays.fill(bf,(byte)0);
                bf[0]=(byte)a;
                dp=new DatagramPacket(bf, bf.length,ia,pt);
                ds.send(dp);//친구 수 전송
                rset.first();
                Arrays.fill(bf,(byte)0);

                //친구 이름 전송
                bf=rset.getString("friend_id").trim().getBytes();

                dp=new DatagramPacket(bf, bf.length,ia,pt);
                ds.send(dp);

                while(rset.next()){
                    Arrays.fill(bf,(byte)0);

                    bf=rset.getString("friend_id").trim().getBytes();

                    dp=new DatagramPacket(bf, bf.length,ia,pt);
                    ds.send(dp);


                }







                rset.close();
                stmt.close();
                con.close();
            }
            catch(Exception e){

            }


        }
    }
}
