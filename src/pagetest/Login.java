package pagetest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JPanel{
	JPanel container, p_center,p_south;//전체, 그리드, 로그인 버튼
	JLabel la_id, la_pw;
	JTextField t_id, t_pw;
	JButton bt;
	
	public Login(){
		container = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		la_id = new JLabel("ID");
		la_pw = new JLabel("Password");
		t_id = new JTextField(20);
		t_pw = new JTextField(20);
		bt =new JButton("로그인");
		
		container.setLayout(new BorderLayout());
		p_center.setLayout(new GridLayout(2, 2));
		p_center.add(la_id);
		p_center.add(t_id);
		p_center.add(la_pw);
		p_center.add(t_pw);
		p_south.add(bt);
		
		container.add(p_center, BorderLayout.CENTER);
		container.add(p_south, BorderLayout.SOUTH);
		add(container);
		setPreferredSize(new Dimension(600, 500));
		setBackground(Color.YELLOW);
	}

}
