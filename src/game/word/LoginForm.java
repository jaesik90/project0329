/*로그인 화면을 담당할 클래스 정의*/
package game.word;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends JPanel implements ActionListener {
	GameWindow gameWindow;
	JPanel container; // BorderLayout 적용
	JPanel p_center; // GridLayout 적용
	JPanel P_south; // 남쪽에 버튼이 들어갈 예정
	JLabel la_id, la_pw;
	JTextField t_id;
	JPasswordField t_pw;
	JButton bt;
	GamePanel gamePanel;

	public LoginForm(GameWindow gameWindow) {
		this.gameWindow=gameWindow;
		container = new JPanel();
		p_center = new JPanel();
		P_south = new JPanel();
		la_id = new JLabel("Id");
		la_pw = new JLabel("Password");
		t_id = new JTextField("batman",15);
		t_pw = new JPasswordField("1234",15);
		bt = new JButton("로그인");

		container.setLayout(new BorderLayout());
		p_center.setLayout(new GridLayout(2, 2));
		p_center.add(la_id);
		p_center.add(t_id);
		p_center.add(la_pw);
		p_center.add(t_pw);
		P_south.add(bt);

		container.add(p_center);
		container.add(P_south, BorderLayout.SOUTH);
		add(container);

		bt.addActionListener(this);
		
		setPreferredSize(new Dimension(400, 100));
		setBackground(Color.YELLOW);
	}
	public void loginCheck(){
		String id=t_id.getText();
		String pw=new String(t_pw.getPassword()); //char[] 배열반환
		if(id.equals("batman")&&pw.equals("1234")){
			JOptionPane.showMessageDialog(this, "로그인 성공");
			gameWindow.setPage(1);
			
		}else
			JOptionPane.showMessageDialog(this, "로그인 정보가 올바르지 않습니다.");
	}
	public void actionPerformed(ActionEvent e) {
		loginCheck();
	}
}
