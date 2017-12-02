package WouldYouTalk;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    private JPanel contentPane;
    private JTextField tf_ID;
    private JTextField tf_PW;

    private JScrollPane scrollPane;
    private ImageIcon logo;

    private Network network;
    
    public LoginView() {
        init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Would You Talk");
        setBounds(0, 0, 428, 618);

        //logo = new ImageIcon("C:\\Users\\YongHoonJJo\\eclipse-workspace\\wouldYouTalk\\src\\WouldYouTalk/wouldYouTalk_Logo.png");
        logo = new ImageIcon("src/WouldYouTalk/wouldYouTalk_Logo.png");
        contentPane = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(logo.getImage(), 0, 0, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // container의 배치관리자 제거

        tf_ID = new JTextField("h01");
        tf_ID.setBounds(100, 333, 228, 43);
        contentPane.add(tf_ID);
        tf_ID.setColumns(10);

        tf_PW = new JTextField("1234");
        tf_PW.setBounds(100, 405, 228, 43);
        contentPane.add(tf_PW);
        tf_PW.setColumns(10);

        JButton loginBtn = new JButton("로그인");
        loginBtn.setBounds(153, 475, 145, 43);
        loginBtn.addActionListener(new loginActionListener());
        contentPane.add(loginBtn);
        
        scrollPane = new JScrollPane(contentPane);
        setContentPane(scrollPane);
    }
    
    private class loginActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		String id = tf_ID.getText();
    		String pw = tf_PW.getText();
    		boolean isIdValid = true;
    		// ID-PW 유효성 검사
    		// 서버에 있는 전체 유저에서 비교.. 해싱이 편할듯
    		
    		if(network == null) {
    			network = new Network();
    		}
    		
    		isIdValid = network.login(id, pw);
    		
    		if(isIdValid) {
	    		MainView mainView = new MainView();
	    		setVisible(false); // LoginView 
	    		
	    		network.setMainView(mainView.getListFriends());
	    		network.threadStart();
	    		 
	    		mainView.setVisible(true);
	    	}
    		else {
    			// ID 혹은 PW 가 일치하지 않음.
    		}
    	}
    }
}
