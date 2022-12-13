

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.*;
import java.util.Arrays;

public class select_user_server extends Thread {
	public static void main(String[] args) throws Exception{
		byte[] bf = new byte[1500];
		DatagramPacket dp = new DatagramPacket(bf, bf.length);
		DatagramSocket ds = new DatagramSocket(9997);

		while(true) {
			bf = new byte[1500];
			dp = new DatagramPacket(bf,bf.length);
			ds.receive(dp);
			Integer pt = dp.getPort();
			InetAddress ia = dp.getAddress();
			String ID = new String(bf).trim();
			bf=new byte[1500];
			Arrays.fill(bf, (byte)0);

			new Thread(new Capitalizer(ds,dp,bf,ID)).start();
			Thread.sleep(100);
		}
	}

	private static class Capitalizer implements Runnable{
		private DatagramSocket ds;

		private byte[] bf = new byte[1500];

		private DatagramPacket dp = new DatagramPacket(bf, bf.length);

		private String ID;

		Capitalizer(DatagramSocket ds, DatagramPacket dp, byte[] bf, String ID){
			this.ds = ds;
			this.dp = dp;
			this.bf = bf;
			this.ID = ID;
		}

		@Override
		public void run() {
			try {
				Integer pt = dp.getPort();
				InetAddress ia = dp.getAddress();
				Connection con = null;
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/network", "root", "Namja241377@");
				String sql = "select* from user where id = ?;";



				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, ID);

				ResultSet rs = pstmt.executeQuery();

				rs.next();
					String i = rs.getString("profile_img");
					System.out.println(i);
					bf = i.getBytes();
					dp = new DatagramPacket(bf, bf.length, ia, pt);
					ds.send(dp);





				byte[] bf_2=new byte[1500];


					String n = rs.getString("name");
					System.out.println(n);
					bf_2 = n.getBytes();
					dp = new DatagramPacket(bf_2, bf_2.length, ia, pt);
					ds.send(dp);






				byte[] bf_3=new byte[1500];


					String m = rs.getString("msg");
					System.out.println(m);
					bf_3 = m.getBytes();
					dp = new DatagramPacket(bf_3, bf_3.length, ia, pt);
					ds.send(dp);


				pstmt.close();

				con.close();


			}catch(Exception e){

			}


		}
	}

}