package Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatRoomIconPanel extends JPanel {
   JLabel imageLabel = new JLabel();

	public ChatRoomIconPanel() {
		setLayout(new BorderLayout());
		setSize(new Dimension(120, 120));
		
		ImageIcon ii = new ImageIcon("peach.gif");
		imageLabel.setIcon(ii);;
		add(imageLabel, java.awt.BorderLayout.CENTER);
	}
}
