import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class login_server extends Thread{

    public static void main(String[] args) throws Exception{

        byte[] bf = new byte[1500];
        DatagramPacket dp = new DatagramPacket(bf,  bf.length);
        byte[] bf_2 = new byte[1500];
        DatagramPacket dp_2 = new DatagramPacket(bf_2,  bf_2.length);



        DatagramSocket ds=new DatagramSocket(9990);


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
                String password_get=new String(bf_2);
                String id=id_get.trim();//아이디
                String password=password_get.trim();//비밀번호
                Integer pt = dp.getPort();
                InetAddress ia = dp.getAddress();

                //sql접속
                String SQL = "SELECT password FROM user WHERE id=?;";//아이디가 입력받은 것과 같은 것의 비밀번호 찾음
                String dbURL = "jdbc:mysql://localhost/network";
                String dbID = "root";
                String dbPW = "Namja241377@";

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(dbURL, dbID, dbPW);



                        PreparedStatement pstmt = conn.prepareStatement(SQL);
                        pstmt.setString(1, id);
                        //쿼리 실행
                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next()) { // 다음 것이 있나? true/false로 반환
                            if (rs.getString(1).contentEquals(password)) {//비밀번호가 입력된 것과 일치하는지 확인
                                String a="1";
                                byte[] bf_3=a.getBytes();
                                DatagramPacket dp_3=new DatagramPacket(bf_3,bf_3.length,ia,pt);
                                ds.send(dp_3);//로그인되었음 메시지 전송
                            } else {
                                String a="0";
                                byte[] bf_3=a.getBytes();
                                DatagramPacket dp_3=new DatagramPacket(bf_3,bf_3.length,ia,pt);
                                ds.send(dp_3);//비밀번호 틀림 메시지 전송
                            }
                        }
                        else {
                            String a = "-1";
                            byte[] bf_3 = a.getBytes();
                            DatagramPacket dp_3 = new DatagramPacket(bf_3, bf_3.length, ia, pt);
                            ds.send(dp_3);// 아이디가 존재하지 않음 메시지 전송
                        }








            }
            catch(Exception e){

            }


        }
    }
}
