package pagetest;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Button extends JPanel{
	JButton bt;
	public Button(){
		bt= new JButton("열어보기");
		add(bt);
		
		setPreferredSize(new Dimension(700, 500));
		setBackground(Color.LIGHT_GRAY);
	}
}
