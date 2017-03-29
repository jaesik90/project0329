package game.word;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel implements ItemListener {
	GameWindow gameWindow;
	JLabel la_user;// 게임 로그인 유저명
	JLabel la_score;// 게임 점수
	Choice choice; // 단어 선택 드랍박스
	JTextField t_input;// 게임 입력창
	JButton bt_start, bt_pause; // 게임시작버튼
	String res = "C:/java_workspace2/project0329/res/";

	FileInputStream fis;
	InputStreamReader reader;// 파일을 대상으로 문자스트림
	BufferedReader buffr;// 문자기반 버퍼 스트림

	// 조사한 단어를 담아놓자!! 게임에 써먹기 위해서
	ArrayList<String> wordList = new ArrayList<String>();

	JPanel p_west; // 왼쪽 컨트롤영역
	JPanel p_center;// 단어 그래픽 처리영역

	public GamePanel(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		setLayout(new BorderLayout());
		p_west = new JPanel();
		// 이 영역은 지금부터 그림을 그릴 영역!!
		p_center = new JPanel() {
			public void paint(Graphics g) {
				g.drawString("고등어", 200, 400);
			}
		};

		la_user = new JLabel("민진호님");
		la_score = new JLabel("0점");
		choice = new Choice();
		t_input = new JTextField(10);
		bt_start = new JButton("시작");
		bt_pause = new JButton("정지");

		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.ORANGE);

		choice.add("▼ 단어집 선택");
		choice.setPreferredSize(new Dimension(135, 40));
		choice.addItemListener(this);

		p_west.add(la_user);
		p_west.add(choice);
		p_west.add(t_input);
		p_west.add(bt_start);
		p_west.add(bt_pause);
		p_west.add(la_score);

		add(p_west, BorderLayout.WEST);
		add(p_center);

		//setBackground(Color.MAGENTA);
		setVisible(false);// 최초의 등장 안함
		setPreferredSize(new Dimension(900, 700));
		getCategory();
		
		
	}

	// 초이스 컴포넌트에 채워질 파일명 조사하기
	public void getCategory() {
		File file = new File(res);

		// 파일+디렉토리 섞여있는 배열반환
		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String name = files[i].getName();
				String[] arr = name.split("\\.");
				if (arr[1].equals("txt")) {// 메모장이라면
					choice.add(name);
				}
			}
		}

	}

	// 단어 읽어오기!!
	public void getWord() {
		int index = choice.getSelectedIndex();
		if (index != 0) {// 첫번째 요소는 빼고
			String name = choice.getSelectedItem();
			// System.out.println(res+name);//res의 파일경로와 choice된 네임을 같이 합쳐서 경로로
			// 설정한다
			try {
				fis = new FileInputStream(res + name);
				reader = new InputStreamReader(fis, "utf-8");
				// 스트림을 버퍼 처리 수준까지 올림!!
				buffr = new BufferedReader(reader);
				String data;
				while (true) {
					data = buffr.readLine();// 한줄
					if (data == null)
						break;
					System.out.println(data);
					wordList.add(data);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (buffr != null) {
					try {
						buffr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
		getWord();
	}
}
