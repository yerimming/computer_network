import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Select_image extends JFrame {
    JFrame frm = new JFrame("Select File");
    String filePath; // ���� ���
    String fileName; // ���� �̸�

    Toolkit toolkit = getToolkit(); // �̹����� �������� ���ؼ�

    String id;

    Select_image(String userid){
        id = userid;
        // ��ü ������
        frm.setSize(500,700);
        frm.setLocationRelativeTo(null);
        frm.setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.WHITE);

        // ��� ------------------------------------------------------------------
        JPanel upper = new JPanel();
        upper.setPreferredSize(new Dimension(500,100));

        // �߾� ------------------------------------------------------------------
        JPanel center = new JPanel();
        center.setPreferredSize(new Dimension(500,500));

        JButton selectButton = new JButton("Select File"); // ���� ���� ��ư
        selectButton.setPreferredSize(new Dimension(200, 100));
        selectButton.setBackground(Color.WHITE);
        selectButton.addActionListener(new OpenActionListener());

        center.add(selectButton);

        // �ϴ� ------------------------------------------------------------------
        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(500,100));



        // ��ü �����ӿ� �߰�
        contentPane.add(upper, BorderLayout.NORTH);
        contentPane.add(center, BorderLayout.CENTER);
        contentPane.add(bottom, BorderLayout.SOUTH);

        frm.add(contentPane);
        frm.setVisible(true);
    }


    class OpenActionListener implements ActionListener {
        JFileChooser chooser;
        OpenActionListener(){
            chooser = new JFileChooser();
        }
        public void actionPerformed(ActionEvent e){
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
            chooser.setFileFilter(filter);

            int ret = chooser.showOpenDialog(null);
            if(ret != JFileChooser.APPROVE_OPTION){
                JOptionPane.showMessageDialog(null, "No file selected", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            filePath = chooser.getSelectedFile().getPath(); // ������ ���� ���
            fileName = chooser.getSelectedFile().getName();

            System.out.println(fileName);
            new Profile_edit(id);
            frm.setVisible(false);
        }
    }

    public String getFilePath(){
        return filePath;
    }

    public String getFileName(){
        return fileName;
    }


    public static void main(String[] args) {
        new Select_image("network4");
    }
}
