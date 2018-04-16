package cn.jbit.snake;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * //̰����ͼ�ν���
 * 
 * @author user
 * 
 */
public class GluttonousSnake extends JFrame implements ActionListener {
	private JButton upButton, downButton, leftButton, rightButton;// ���Ʒ���ť
	private JTextField score;// ����
	private SnakeCanvas snake;// �ߵ�ģ��
	/**
	 * �����¼� ������ͷ����
	 */
	private KeyListener keyListener = new KeyAdapter() {
		// ���¼���ʱ����
		@Override
		public void keyPressed(KeyEvent e) {
			int flag = -1;
			// ���� �� �� �� �� �� W S A D��������ͷ����
			// ��
			if (e.getKeyCode() == KeyEvent.VK_UP
					| e.getKeyCode() == KeyEvent.VK_W) {
				flag = 1;
			}
			// ��
			else if (e.getKeyCode() == KeyEvent.VK_DOWN
					| e.getKeyCode() == KeyEvent.VK_S) {
				flag = 2;
			}
			// ��
			else if (e.getKeyCode() == KeyEvent.VK_LEFT
					| e.getKeyCode() == KeyEvent.VK_A) {
				flag = 3;
			}
			// ��
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT
					| e.getKeyCode() == KeyEvent.VK_D) {
				flag = 4;
			}
			// �ж��Ƿ�������Ч��
			if (flag != -1) {
				snake.setDirect(flag);// ������ͷ�˶�����
				snake.repaint();// ����ģ�����»�
				snake.timer.start();// ��ʱ����ʼ
			}
		}
	};

	public GluttonousSnake() {
		super("̰����");// ���ñ���
		this.setSize(725, 515);// ���ô�С
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);// �����˳�����
		this.setLocationRelativeTo(null);// ���ô��ھ���
		JPanel p = new JPanel();// ���ư�ť����ʾ�������
		p.setBorder(new TitledBorder("���ƺ���ʾ��"));// ����������ı���
		this.getContentPane().add(p, "East");// ���ô�����λ��
		p.setLayout(new GridLayout(4, 1));// ���ô����Ĳ��ַ�ʽ��Ϊ���񲼾ַ�ʽ
		JPanel p2 = new JPanel();// �ڴ�����������ʾ���������
		p2.setLayout(new FlowLayout());// ����Ϊ�����ַ�ʽ
		p2.add(new JLabel("�÷֣�"));
		score = new JTextField("0");
		score.setEditable(false);
		p2.add(score);
		p.add(p2);
		// ��Ӱ�ť����ͼƬ�Ĳ�����¼�����
		upButton = new JButton("", new ImageIcon("up.png"));
		upButton.setActionCommand("up");
		upButton.addActionListener(this);
		downButton = new JButton("", new ImageIcon("down.png"));
		downButton.setActionCommand("down");
		downButton.addActionListener(this);
		leftButton = new JButton("", new ImageIcon("left.png"));
		leftButton.setActionCommand("left");
		leftButton.addActionListener(this);
		rightButton = new JButton("", new ImageIcon("right.png"));
		rightButton.setActionCommand("right");
		rightButton.addActionListener(this);
		p.add(upButton);
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 2));
		p1.add(leftButton);
		p1.add(rightButton);
		p.add(p1);
		p.add(downButton);

		addMenu();// ��Ӳ˵�
		start();
		this.setResizable(false);
		this.setVisible(true);

		/*
		 * ��Ӽ����¼���ʽ 1��ʵ��KeyListener�ӿ� 2��addKeyListener(KeyListener�ӿڵ�ʵ������� ��
		 * KeyAdapter����������) 3��addKeyListener(�ڲ���[KeyListener�ӿڵ�ʵ���� ��
		 * KeyAdapter�������])
		 */
		this.addKeyListener(keyListener);

		this.requestFocus();// ʹ��ǰ���ڳ�Ϊ�������
	}

	private void start() {
		snake = new SnakeCanvas();
		this.getContentPane().add(snake);
	}

	private void addMenu() {
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		JMenu game = new JMenu("��Ϸ");
		JMenu help = new JMenu("����");
		JMenuItem jitemNew = new JMenuItem("����Ϸ");
		jitemNew.setActionCommand("new");
		jitemNew.addActionListener(this);
		JMenuItem jitemPause = new JMenuItem("��ͣ");
		jitemPause.setActionCommand("pause");
		jitemPause.addActionListener(this);
		JMenuItem jitemExit = new JMenuItem("�˳�");
		jitemExit.setActionCommand("exit");
		jitemExit.addActionListener(this);
		JMenuItem jitemAbout = new JMenuItem("����");
		jitemAbout.setActionCommand("about");
		jitemAbout.addActionListener(this);
		game.add(jitemNew);
		game.add(jitemPause);
		game.add(jitemExit);
		help.add(jitemAbout);
		game.addSeparator();// �˵������÷ָ���
		help.addSeparator();
		menubar.add(game);
		menubar.add(help);
	}

	public static void main(String[] args) {
		new GluttonousSnake();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("exit")) {
			System.exit(EXIT_ON_CLOSE);
		}
		if (e.getSource() instanceof JButton) {
			if (e.getActionCommand().equalsIgnoreCase("up")) {// ��Ӧ���ϰ�ť�����¼�
				snake.setDirect(1);// ������ͷ�˶�����
				snake.repaint();// ����ģ�����»�
				snake.timer.start();// ��ʱ����ʼ
				return;
			}
			if (e.getActionCommand().equalsIgnoreCase("down")) {
				snake.setDirect(2);
				snake.repaint();
				snake.timer.start();
				return;
			}
			if (e.getActionCommand().equalsIgnoreCase("left")) {
				snake.setDirect(3);
				snake.repaint();
				snake.timer.start();
				return;
			}
			if (e.getActionCommand().equalsIgnoreCase("right")) {
				snake.setDirect(4);
				snake.repaint();
				snake.timer.start();
				return;
			}
		}
		if (e.getSource() instanceof JMenuItem) {
			if (e.getActionCommand().equalsIgnoreCase("new")) {
				// this.getContentPane().remove(snake);
				snake.init();
				snake.repaint();
				snake.timer.start();
			}
			if (e.getActionCommand().equalsIgnoreCase("pause")) {
				snake.timer.stop();
				JOptionPane.showMessageDialog(this, "�����밴��ȷ����");
				snake.timer.start();
			}
			if (e.getActionCommand().equalsIgnoreCase("about")) {
				snake.timer.stop();
				JOptionPane.showMessageDialog(this, "̰���� @ 2015 - ����");
				snake.timer.start();
			}
		}
	}

}