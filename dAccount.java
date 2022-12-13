import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class dAccount extends JFrame{

	dAccount(){
		
		setTitle("Delete Account");
        setSize(500,700);
        
        JLabel unL = new JLabel("User Name:");
		unL.setBounds(100, 180, 80, 30);
		
		JTextField unF = new JTextField();
		unF.setBounds(200, 180, 200, 30);
		
		JLabel pwL = new JLabel("Password:");
		pwL.setBounds(100, 320, 80, 30);
		
		JPasswordField pwF = new JPasswordField();
		pwF.setBounds(200, 320, 200, 30);
		
		JButton button = new JButton("Delete");
        button.setBounds(100, 450, 300, 30);
        
        add(unL);
        add(unF);
        add(pwL);
        add(pwF);
        add(button);
        
        setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new dAccount();

	}

}
