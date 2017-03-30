package game.word;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class GamePanel extends JPanel implements ItemListener, Runnable, ActionListener {
	GameWindow gameWindow;
	JLabel la_user;// 게임 로그인 유저명
	JLabel la_score;// 게임 점수
	Choice choice; // 단어 선택 드랍박스
	JTextField t_input;// 게임 입력창
	JButton bt_start, bt_pause, bt_stop; // 게임시작버튼
	String res = "C:/java_workspace2/project0329/res/";

	FileInputStream fis;
	InputStreamReader reader;// 파일을 대상으로 문자스트림
	BufferedReader buffr;// 문자기반 버퍼 스트림

	// 조사한 단어를 담아놓자!! 게임에 써먹기 위해서
	ArrayList<String> wordList = new ArrayList<String>();
	Thread thread;// 단어게임을 진행할 쓰레드
	boolean flag = true;
	boolean isDown = true;
	ArrayList<Word> words = new ArrayList<Word>();

	JPanel p_west; // 왼쪽 컨트롤영역
	JPanel p_center;// 단어 그래픽 처리영역

	public GamePanel(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		setLayout(new BorderLayout());
		p_west = new JPanel();
		// 이 영역은 지금부터 그림을 그릴 영역!!
		p_center = new JPanel() {
			public void paintComponent(Graphics g) {
				// 기존 그림 지우기!!
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 750, 700);

				g.setColor(Color.BLUE);
				// 모든 단어들에 대해서 render() 호출
				for (int i = 0; i < words.size(); i++) {
					words.get(i).render(g);
				}
			}
		};

		la_user = new JLabel("민진호님");
		la_score = new JLabel("0점");
		choice = new Choice();
		t_input = new JTextField(10);
		bt_start = new JButton("시작");
		bt_pause = new JButton("정지");
		bt_stop = new JButton("종료");

		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.ORANGE);

		choice.add("▼ 단어집 선택");
		choice.setPreferredSize(new Dimension(135, 40));
		choice.addItemListener(this);

		bt_start.addActionListener(this);
		bt_pause.addActionListener(this);
		bt_stop.addActionListener(this);

		// 텍스트 필드와 리스너 연결
		t_input.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					//화면에 존재하는 words와 입력값 비교하여
					//맞으면, words에서 객체 삭제
					String value=t_input.getText();
					
					for(int i=0; i<words.size(); i++){
						if(words.get(i).name.equals(value)){
							words.remove(i);
						}
					}
				}
			}
		});

		p_west.add(la_user);
		p_west.add(choice);
		p_west.add(t_input);
		p_west.add(bt_start);
		p_west.add(bt_pause);
		p_west.add(bt_stop);
		p_west.add(la_score);

		add(p_west, BorderLayout.WEST);
		add(p_center);

		// setBackground(Color.MAGENTA);
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
			// System.out.println(res+name);
			try {
				fis = new FileInputStream(res + name);//res의 파일경로와 choice된 네임을 같이 합쳐서 경로로
				// 설정한다
				reader = new InputStreamReader(fis, "utf-8");
				// 스트림을 버퍼 처리 수준까지 올림!!
				buffr = new BufferedReader(reader);
				String data;
				//기존에 wordList를 비운다!!!
				wordList.removeAll(wordList);
				while (true) {
					data = buffr.readLine();// 한줄
					if (data == null)
						break;
					System.out.println(data);
					wordList.add(data);
				}
				// 준비된 단어를 화면에 보여주기
				System.out.println(wordList.size());
				createWord();

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

	public void createWord() {
		for (int i = 0; i < wordList.size(); i++) {
			String name = wordList.get(i);
			Word word = new Word(name, ((i * 75) + 10), 100);
			words.add(word); // 워드 객체명단 만들기
		}
	}

	// 게임시작
	public void startGame() {
		if (thread == null) {
			flag = true;// while문 작동하도록...
			isDown = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	// 게임중지 or 계속
	public void pauseGame() {
		isDown = !isDown;

	}

	/*
	 * 1. wordList(단어들이 들어있는) 비우기 
	 * 2. words(Word 인스턴스들이 들어있는) 비우기 
	 * 3. choice 초기화
	 * (index=0) 
	 * 4. flag=false 
	 * 5. thread를 null로 다시 초기화 
	 * --결국 처음으로 돌아가자!!!
	 */

	// 게임 정료
	public void stopGame() {
		wordList.removeAll(wordList);
		words.removeAll(words);
		choice.select(0); // 첫번째 요소 강제 선택
		flag = false; // while문 중지 목적
		thread = null;
	}

	public void itemStateChanged(ItemEvent e) {
		getWord();
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_start) {
			startGame();
		} else if (obj == bt_pause) {
			pauseGame();
		} else if (obj == bt_stop) {
			stopGame();
		}
	}

	public void run() {
		while (flag) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (isDown == true) {
				// 모든 단어들에 대해서 tick()
				for (int i = 0; i < words.size(); i++) {
					words.get(i).tick();
				}
				p_center.repaint();
			}
		}
	}

}
