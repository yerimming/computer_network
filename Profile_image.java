import javax.swing.*;
import java.awt.*;

public class Profile_image extends JFrame {
    Profile_image(ImageIcon icon){
        setTitle("Profile Image");
        setSize(700,700);

        Image img = icon.getImage();
        Image updateImg = img.getScaledInstance(700,700, Image.SCALE_SMOOTH);
        ImageIcon updateIcon = new ImageIcon(updateImg);

        JLabel image = new JLabel();
        image.setIcon(updateIcon);
        image.setBounds(0,0,700,700);

        add(image);

        setLayout(null);
        setVisible(true);
    }
}

