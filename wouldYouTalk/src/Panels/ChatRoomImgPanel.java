package Panels;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import WouldYouTalk.UserInfo;

public class ChatRoomImgPanel extends JPanel {
	private ImageIcon img;
	private ImageIcon imgSized;
	private JLabel imgLabel;
	private byte[] bytes;
	
	public ChatRoomImgPanel(UserInfo userInfo, boolean fromMe, String msgTime, byte[] bb) {
		setLayout(null);
		bytes = bb;
		
		try {
			FileOutputStream out = new FileOutputStream("out.jpg");
			out.write(bb);
			Thread.sleep(1000); 
			out.close();
		} 
		catch (Exception ee) {
			System.out.println("fail to FileOutputStream ");
		}
		
		try {
			File imgFile = new File("out.jpg");
			BufferedImage bImg = ImageIO.read(imgFile);
			img = new ImageIcon(bImg);
		}
		catch (Exception e3) {
			System.out.println("fail to File()");
		}
		
		Image tmp = img.getImage();
		Image newImg = tmp.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
		imgSized = new ImageIcon(newImg);
		imgLabel = new JLabel(imgSized);
		imgLabel.setBounds(0, 0, 150, 150);
		add(imgLabel);
		
		imgLabel.addMouseListener(new DownMouseAdapter());
	} 
	
	private class DownMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			System.out.println("Downloading img...");
			
			File saveFile;
			String savePathName;
			String fileName = "downloadImg.JPG";  //파일이름 임의로 지정

			JFileChooser chooser = new JFileChooser();// 객체 생성
			chooser.setCurrentDirectory(new File("C:\\")); // 맨처음경로를 C로 함
			chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY); // 디렉토리만 선택가능

			int re = chooser.showSaveDialog(null);
			if (re == JFileChooser.APPROVE_OPTION) { //디렉토리를 선택했으면
				saveFile = chooser.getSelectedFile(); //선택된 디렉토리 저장하고
				savePathName = saveFile.getAbsolutePath() + "\\" + fileName;  //디렉토리결과+파일이름
				
				try {
					FileOutputStream out = new FileOutputStream(savePathName); 
					out.write(bytes);
					Thread.sleep(500);
					out.close();
				}
				catch (Exception ez) {
					System.out.println("fail to download a img");
				}
				System.out.println("success to download a img");
				System.out.println(savePathName);
				JOptionPane.showMessageDialog(null, "이미지 저장이 완료되었습니다.","Success to download", JOptionPane.WARNING_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.","Warnning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
		}
	}
}
