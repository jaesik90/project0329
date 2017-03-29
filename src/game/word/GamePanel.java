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
	JLabel la_user;// ���� �α��� ������
	JLabel la_score;// ���� ����
	Choice choice; // �ܾ� ���� ����ڽ�
	JTextField t_input;// ���� �Է�â
	JButton bt_start, bt_pause; // ���ӽ��۹�ư
	String res = "C:/java_workspace2/project0329/res/";

	FileInputStream fis;
	InputStreamReader reader;// ������ ������� ���ڽ�Ʈ��
	BufferedReader buffr;// ���ڱ�� ���� ��Ʈ��

	// ������ �ܾ ��Ƴ���!! ���ӿ� ��Ա� ���ؼ�
	ArrayList<String> wordList = new ArrayList<String>();
	Thread thread;// �ܾ������ ������ ������
	boolean flag=true;
	ArrayList<Word> words = new ArrayList<Word>();

	JPanel p_west; // ���� ��Ʈ�ѿ���
	JPanel p_center;// �ܾ� �׷��� ó������

	public GamePanel(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		setLayout(new BorderLayout());
		p_west = new JPanel();
		// �� ������ ���ݺ��� �׸��� �׸� ����!!
		p_center = new JPanel() {
			public void paintComponent(Graphics g) {
				//���� �׸� �����!!
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 750, 700);
				
				g.setColor(Color.BLUE);
				//��� �ܾ�鿡 ���ؼ� render() ȣ��
				for(int i=0; i<words.size(); i++){
					words.get(i).render(g);
				}
			}
		};

		la_user = new JLabel("����ȣ��");
		la_score = new JLabel("0��");
		choice = new Choice();
		t_input = new JTextField(10);
		bt_start = new JButton("����");
		bt_pause = new JButton("����");

		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.ORANGE);

		choice.add("�� �ܾ��� ����");
		choice.setPreferredSize(new Dimension(135, 40));
		choice.addItemListener(this);

		bt_start.addActionListener(this);
		bt_pause.addActionListener(this);

		p_west.add(la_user);
		p_west.add(choice);
		p_west.add(t_input);
		p_west.add(bt_start);
		p_west.add(bt_pause);
		p_west.add(la_score);

		add(p_west, BorderLayout.WEST);
		add(p_center);

		// setBackground(Color.MAGENTA);
		setVisible(false);// ������ ���� ����
		setPreferredSize(new Dimension(900, 700));
		getCategory();

	}

	// ���̽� ������Ʈ�� ä���� ���ϸ� �����ϱ�
	public void getCategory() {
		File file = new File(res);

		// ����+���丮 �����ִ� �迭��ȯ
		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String name = files[i].getName();
				String[] arr = name.split("\\.");
				if (arr[1].equals("txt")) {// �޸����̶��
					choice.add(name);
				}
			}
		}

	}

	// �ܾ� �о����!!
	public void getWord() {
		int index = choice.getSelectedIndex();
		if (index != 0) {// ù��° ��Ҵ� ����
			String name = choice.getSelectedItem();
			// System.out.println(res+name);//res�� ���ϰ�ο� choice�� ������ ���� ���ļ� ��η�
			// �����Ѵ�
			try {
				fis = new FileInputStream(res + name);
				reader = new InputStreamReader(fis, "utf-8");
				// ��Ʈ���� ���� ó�� ���ر��� �ø�!!
				buffr = new BufferedReader(reader);
				String data;
				while (true) {
					data = buffr.readLine();// ����
					if (data == null)
						break;
					System.out.println(data);
					wordList.add(data);
				}
					//�غ�� �ܾ ȭ�鿡 �����ֱ�
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
	
	public void createWord(){
		for(int i=0; i<wordList.size();i++){
			String name=wordList.get(i);
			Word word = new Word(name, ((i*75)+10), 100);
			words.add(word); //���� ��ü��� �����
		}
	}
	
	// ���ӽ���
	public void startGame(){
		if(thread==null){
		thread = new Thread(this);
		thread.start();
		}
	}

	// ��������
	public void pauseGame() {

	}
	
	
	public void itemStateChanged(ItemEvent e) {
		getWord();
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_start) {
			startGame();
		} else if (obj == bt_pause) {

		}
	}

	public void run() {
		while(flag){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//��� �ܾ�鿡 ���ؼ� tick()
			for(int i=0; i<words.size(); i++){
				words.get(i).tick();
			}
			p_center.repaint();
			
		}
	}

}
