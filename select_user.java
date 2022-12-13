
import java.sql.*;
import java.util.Arrays;
import java.io.IOException;
import java.net.*;

public class select_user {
    String img;
    String name;
    String msg;


	select_user(String userid){

		try {
			DatagramSocket ds = new DatagramSocket();
			
			InetAddress ia = InetAddress.getByName("localhost");
			byte[] bf=new byte[1500];
            bf=userid.getBytes();
			DatagramPacket dp = new DatagramPacket(bf, bf.length, ia, 9997);
			ds.send(dp);
            bf=new byte[1500];
			Arrays.fill(bf, (byte)0);
            dp = new DatagramPacket(bf, bf.length,ia,9997);


			ds.receive(dp);
            img = new String(bf).trim();
            byte[] bf_2=new byte[1500];
            Arrays.fill(bf_2, (byte)0);

            dp = new DatagramPacket(bf_2, bf_2.length,ia,9997);
            ds.receive(dp);
            name= new String(bf_2).trim();
            byte[] bf_3=new byte[1500];
            Arrays.fill(bf_3, (byte)0);

            dp = new DatagramPacket(bf_3, bf_3.length,ia,9997);
            ds.receive(dp);
            msg = new String(bf_3).trim();



		
		}catch(IOException es) {}

    }
    public String getImg(){
        return img;
    }

    public String getName(){
        return name;
    }

    public String getMsg(){
        return msg;
    }




}

