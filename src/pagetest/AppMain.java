package pagetest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppMain extends JFrame implements ActionListener{
	JPanel p_north;
	JPanel p_center;
	JButton[] btn=new JButton[3];
	Login login;
	Lavel la;
	Button bt;
	String[] path = { "/login.png", "/content.png", "/etc.png" };
	URL[] url = new URL[3];
	
	
	public AppMain(){
		p_north = new JPanel();
		p_center = new JPanel();
		for(int i=0; i<path.length; i++){
			url[i]=this.getClass().getResource(path[i]);
			btn[i]= new JButton(new ImageIcon(url[i]));
			p_north.add(btn[i]);
			btn[i].addActionListener(this);
		}
		
		
		
		
		login = new Login();
		la = new Lavel();
		bt =new Button();
		
		add(p_north, BorderLayout.NORTH);
	
		
	
		
		p_center.add(login);
		p_center.add(la);
		p_center.add(bt);
		add(p_center);
		
		setVisible(true);
		setSize(600,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==btn[0]){
			login.setVisible(true);
			la.setVisible(false);
			bt.setVisible(false);
		}else if(obj==btn[1]){
			login.setVisible(false);
			la.setVisible(true);
			bt.setVisible(false);
		}else if(obj==btn[2]){
			login.setVisible(false);
			la.setVisible(false);
			bt.setVisible(true);
		}
		}
		
	
	
	public static void main(String[] args) {
		new AppMain();

	}



}
