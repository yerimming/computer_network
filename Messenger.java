import javax.swing.*;
import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Messenger implements ActionListener{

    public static JPanel bottomPanel = new JPanel();
    public static JFrame jf = new JFrame();
    static ActionListener listener = new Messenger(null);




    public Messenger(String id) //생성자
    {
        //프레임 만들기
        //JFrame jf = new JFrame();
        jf.setLayout(new BorderLayout());

        //버튼 판넬 만들기
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        //아래 판넬 만들기
        bottomPanel.setBackground(Color.YELLOW);

        //버튼 추가하기
        JButton homeBtn = new JButton("Home");
        JButton friendBtn = new JButton("Friend");
        JButton chatBtn = new JButton("Chat");
        JButton profileBtn = new JButton("my_profile");

        //버튼 판넬에 버튼 추가하기
        btnPanel.add(homeBtn);
        btnPanel.add(friendBtn);
        btnPanel.add(chatBtn);
        btnPanel.add(profileBtn);

        homeBtn.addActionListener(listener);
        friendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new friends(id);
                jf.setVisible(false);
            }
        });
        chatBtn.addActionListener(listener);
        profileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Profile(id);
                jf.setVisible(false);

            }
        });

        //프레임에 버튼 판넬 추가하기
        jf.add(btnPanel,BorderLayout.NORTH);

        //프레임에 아래 판넬 추가하기

        //프레임 설정
        jf.setTitle("Messenger");
        jf.setSize(500,700);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.add(bottomPanel,BorderLayout.CENTER);
       // jf.setVisible(true);
    }

    public void My_profile()
    {

        ImageIcon img = new ImageIcon("./Button_img/profileImg.jpg");
        //프로필 메인 패널 만들기(GridLayout(2,1))
        JPanel proMainPanel = new JPanel(new GridLayout(2,1));
        //프로필 이미지 부분 패널 만들기
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.YELLOW);
        //프로필 정보(이름, 나이, 소속)에 대한 패널 만들기(GridLayout(3,1)
        JPanel infoPanel = new JPanel(new GridLayout(3,2));
        infoPanel.setBackground(Color.YELLOW);

        //이미지 버튼 만들기
        JButton imgBtn = new JButton(img);
        imgBtn.setPreferredSize(new Dimension(200,200));

        //infoPanel에 들어갈 이름 나이 소속에 대한 라벨과 텍스트 필드 만들기
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setFont(new Font("Serif",Font.BOLD,15));
        JLabel ageLabel = new JLabel("Age: ");
        ageLabel.setFont(new Font("Serif",Font.BOLD,15));
        JLabel workLabel = new JLabel("Work: ");
        workLabel.setFont(new Font("Serif",Font.BOLD,15));

        JTextField nameText = new JTextField(15);
        JTextField ageText =  new JTextField(3);
        JTextField workText = new JTextField(15);

        //만든 버튼을 imagePanel에 넣기
        imagePanel.add(imgBtn);

        //만든 라벨과 텍스트 필드를 infoPanel에 넣기
        infoPanel.add(nameLabel);
        infoPanel.add(nameText);
        infoPanel.add(ageLabel);
        infoPanel.add(ageText);
        infoPanel.add(workLabel);
        infoPanel.add(workText);

        //프로필 메인 패널에 imagePanel 넣기
        proMainPanel.add(imagePanel);

        //프로필 메인 패널에 infoPanel 넣기
        proMainPanel.add(infoPanel);

        //바텀 패널에 프로필 메인 패널 넣기
        bottomPanel.add(proMainPanel);

    }

    public void Home()
    {
        ImageIcon nightimg = new ImageIcon("./Button_img/night.png");
        ImageIcon sunimg = new ImageIcon("./Button_img/sun.png");

        JPanel homePanel = new JPanel();
        homePanel.setBackground(Color.YELLOW);
        homePanel.setLayout(new GridLayout(4,1));


        //공공 데이터 넣기 시간, 날씨
        LocalDateTime now = LocalDateTime.now();


        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        int hour = now.getHour();
        int minute = now.getMinute();

      /*if(hour>18 || )
      {
         System.out.println("지금은 밤입니다.");
      }
      else*/

        JLabel timeLabel = new JLabel(formatedNow);
        timeLabel.setFont(new Font("Serif",Font.BOLD,30));
        homePanel.add(timeLabel);

        //기온 구하기
        try{
            //서울시청의 위도와 경도
            String lon = "126.977948";  //경도
            String lat = "37.566386";   //위도

            //OpenAPI call하는 URL
            String urlstr = "http://api.openweathermap.org/data/2.5/weather?"
                    + "lat="+lat+"&lon="+lon
                    +"&appid=e0fed374668ee7110c9bcfa92a7d184d";
            URL url = new URL(urlstr);
            BufferedReader bf;
            String line;
            String result="";

            //날씨 정보를 받아온다.
            bf = new BufferedReader(new InputStreamReader(url.openStream()));

            //버퍼에 있는 정보를 문자열로 변환.
            while((line=bf.readLine())!=null){
                result=result.concat(line);
                //System.out.println(line);
            }

            //문자열을 JSON으로 파싱
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);

            //지역 출력
            System.out.println("지역 : " + jsonObj.get("name"));
            JLabel localLabel = new JLabel("지역: " + jsonObj.get("name"));

            //날씨 출력
            JSONArray weatherArray = (JSONArray) jsonObj.get("weather");
            JSONObject obj = (JSONObject) weatherArray.get(0);
            System.out.println("날씨 : "+obj.get("main"));
            JLabel weatherLabel = new JLabel("날씨 : "+obj.get("main"));

            //온도 출력(절대온도라서 변환 필요)
            JSONObject mainArray = (JSONObject) jsonObj.get("main");
            double ktemp = Double.parseDouble(mainArray.get("temp").toString());
            double temp = ktemp-273.15;
            System.out.printf("온도 : %.2f\n",temp);
            JLabel tempLabel = new JLabel("온도 : "+temp);

            homePanel.add(localLabel);
            homePanel.add(weatherLabel);
            homePanel.add(tempLabel);


            bottomPanel.add(homePanel);
            bf.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnVal = e.getActionCommand();
        bottomPanel.removeAll();
        if(btnVal.equals("Home"))
        {
            Home();
        }
        else if(btnVal.equals("Chat"))
        {
            System.out.println("Chat");
        }


        bottomPanel.updateUI();

    }

}
