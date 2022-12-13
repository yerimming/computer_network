import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.*;
import java.util.Arrays;

public class Profile_edit_server extends Thread {
	
	public static void main(String[] args) throws Exception{
		byte[] bf = new byte[1500];
        DatagramPacket dp = new DatagramPacket(bf,  bf.length);
        DatagramSocket ds=new DatagramSocket(9998);
        while (true) {
        	
        	bf = new byte[1500];
            dp = new DatagramPacket(bf, bf.length);
            ds.receive(dp);
            Integer pt = dp.getPort();
            InetAddress ia = dp.getAddress();
            String Name = new String(bf).trim();

            bf= new byte[1500];
            Arrays.fill(bf,(byte)0);
            dp= new DatagramPacket(bf, bf.length,ia,pt);
            ds.receive(dp);
            String State = new String(bf).trim();
            
            bf= new byte[1500];
            Arrays.fill(bf,(byte)0);
            dp= new DatagramPacket(bf, bf.length,ia,pt);
            ds.receive(dp);
            String fileName = new String(bf).trim();
            
            bf= new byte[1500];
            Arrays.fill(bf,(byte)0);
            dp= new DatagramPacket(bf, bf.length,ia,pt);
            ds.receive(dp);
            String ID = new String(bf).trim();

            new Thread(new Capitalizer(ds,dp, bf, Name, State, fileName, ID)).start();
            Thread.sleep(100);



        }

	}
	
	private static class Capitalizer implements Runnable{
        private DatagramSocket ds;


        private byte[] bf = new byte[1500];

        private DatagramPacket dp = new DatagramPacket(bf,  bf.length);

        private String Name, State, fileName, ID;
        
        Capitalizer(DatagramSocket ds, DatagramPacket dp,byte[] bf, String Name, String State, String fileName, String ID){
        	this.ds = ds;
            this.dp = dp;
            this.bf = bf;
            this.Name = Name;
            this.State = State;
            this.fileName = fileName;
            this.ID = ID;

        }

        @Override
        public void run(){
        	try {
                Integer pt = dp.getPort();
                InetAddress ia = dp.getAddress();
                Connection con = null;
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/network", "root", "1234");
                String SQL = "update user user set name = ?, msg = ?, profile_img = ? where id = ?";
                
                PreparedStatement pstmt = con.prepareStatement(SQL);
                pstmt.setString(1,Name);
                pstmt.setString(2,State);
                pstmt.setString(3,fileName);
                pstmt.setString(4,ID);

                pstmt.executeUpdate();
                
                pstmt.close();
                con.close();
              
        	}catch(Exception e){
        		e.printStackTrace();
            }

        }


	}


}
