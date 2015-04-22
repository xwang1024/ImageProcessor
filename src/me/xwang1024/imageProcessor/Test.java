package me.xwang1024.imageProcessor;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test {
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame();
		
		BufferedImage img = ImageIO.read(new File("4.jpg"));
		
		JLabel oldImageLb = new JLabel(new ImageIcon(img));
		JLabel newImageLb = new JLabel(new ImageIcon(ImageProcessor.process(img)));
		frame.add(oldImageLb,BorderLayout.WEST);
		frame.add(newImageLb,BorderLayout.EAST);
		frame.setBounds(100, 100, 800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
