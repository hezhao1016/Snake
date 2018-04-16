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
 * //贪吃蛇图形界面
 * 
 * @author user
 * 
 */
public class GluttonousSnake extends JFrame implements ActionListener {
	private JButton upButton, downButton, leftButton, rightButton;// 控制方向按钮
	private JTextField score;// 分数
	private SnakeCanvas snake;// 蛇的模型
	/**
	 * 键盘事件 控制蛇头方向
	 */
	private KeyListener keyListener = new KeyAdapter() {
		// 按下键盘时触发
		@Override
		public void keyPressed(KeyEvent e) {
			int flag = -1;
			// 键入 上 下 左 右 或 W S A D，控制蛇头方向
			// 上
			if (e.getKeyCode() == KeyEvent.VK_UP
					| e.getKeyCode() == KeyEvent.VK_W) {
				flag = 1;
			}
			// 下
			else if (e.getKeyCode() == KeyEvent.VK_DOWN
					| e.getKeyCode() == KeyEvent.VK_S) {
				flag = 2;
			}
			// 左
			else if (e.getKeyCode() == KeyEvent.VK_LEFT
					| e.getKeyCode() == KeyEvent.VK_A) {
				flag = 3;
			}
			// 右
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT
					| e.getKeyCode() == KeyEvent.VK_D) {
				flag = 4;
			}
			// 判断是否按下了有效键
			if (flag != -1) {
				snake.setDirect(flag);// 设置蛇头运动方向
				snake.repaint();// 对蛇模型重新画
				snake.timer.start();// 定时器开始
			}
		}
	};

	public GluttonousSnake() {
		super("贪吃蛇");// 设置标题
		this.setSize(725, 515);// 设置大小
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);// 设置退出按键
		this.setLocationRelativeTo(null);// 设置窗口居中
		JPanel p = new JPanel();// 控制按钮和显示分数面板
		p.setBorder(new TitledBorder("控制和显示区"));// 设置这个面板的标题
		this.getContentPane().add(p, "East");// 设置此面板的位置
		p.setLayout(new GridLayout(4, 1));// 设置此面板的布局方式，为网格布局方式
		JPanel p2 = new JPanel();// 在此面板中添加显示分数的面板
		p2.setLayout(new FlowLayout());// 设置为流布局方式
		p2.add(new JLabel("得分："));
		score = new JTextField("0");
		score.setEditable(false);
		p2.add(score);
		p.add(p2);
		// 添加按钮，有图片的并添加事件监听
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

		addMenu();// 添加菜单
		start();
		this.setResizable(false);
		this.setVisible(true);

		/*
		 * 添加键盘事件方式 1、实现KeyListener接口 2、addKeyListener(KeyListener接口的实现类对象 或
		 * KeyAdapter类的子类对象) 3、addKeyListener(内部类[KeyListener接口的实现类 或
		 * KeyAdapter类的子类])
		 */
		this.addKeyListener(keyListener);

		this.requestFocus();// 使当前窗口成为焦点组件
	}

	private void start() {
		snake = new SnakeCanvas();
		this.getContentPane().add(snake);
	}

	private void addMenu() {
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		JMenu game = new JMenu("游戏");
		JMenu help = new JMenu("帮助");
		JMenuItem jitemNew = new JMenuItem("新游戏");
		jitemNew.setActionCommand("new");
		jitemNew.addActionListener(this);
		JMenuItem jitemPause = new JMenuItem("暂停");
		jitemPause.setActionCommand("pause");
		jitemPause.addActionListener(this);
		JMenuItem jitemExit = new JMenuItem("退出");
		jitemExit.setActionCommand("exit");
		jitemExit.addActionListener(this);
		JMenuItem jitemAbout = new JMenuItem("关于");
		jitemAbout.setActionCommand("about");
		jitemAbout.addActionListener(this);
		game.add(jitemNew);
		game.add(jitemPause);
		game.add(jitemExit);
		help.add(jitemAbout);
		game.addSeparator();// 菜单里设置分隔线
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
			if (e.getActionCommand().equalsIgnoreCase("up")) {// 响应向上按钮按下事件
				snake.setDirect(1);// 设置蛇头运动方向
				snake.repaint();// 对蛇模型重新画
				snake.timer.start();// 定时器开始
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
				JOptionPane.showMessageDialog(this, "继续请按“确定”");
				snake.timer.start();
			}
			if (e.getActionCommand().equalsIgnoreCase("about")) {
				snake.timer.stop();
				JOptionPane.showMessageDialog(this, "贪吃蛇 @ 2015 - 何钊");
				snake.timer.start();
			}
		}
	}

}