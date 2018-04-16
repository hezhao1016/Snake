package cn.jbit.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * 蛇模型  
 * @author user
 *
 */
public class SnakeCanvas extends MapCanvas implements ActionListener {
	private int number = 5;// 蛇身的初始长度
	public Timer timer;// 定时器
	private Body[] bodys;// 蛇身
	private Body food;// 食物
	public int score = 0;// 分数
	private int speed = 250; //定时器延时时间 /毫秒
	int direct = 3;// 蛇头初始方向
	private int[][] mapflag = new int[455][455];// 地图标记

	/**
	 * 初始化
	 */
	public void init() {
		this.direct = 3;
		this.number = 5;
		this.bodys = new Body[this.number];
		mapflag = new int[455][455];
		for (int i = 0, x = 300; i < this.number; i++, x += 30) {
			bodys[i] = new Body(x, 180);
			mapflag[bodys[i].x][bodys[i].y] = 2;// 如果是蛇体 设标记为2
		}
		food = creatFood();// 创建食物

	}

	public SnakeCanvas() {
		init();
		timer = new Timer(speed, this);// 创建定时器对象
		timer.start();// 定时器启动
	}

	private class Body {// 蛇头成员
		int x, y;

		Body(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public void paint(Graphics g) {// 画图
		super.paint(g);
		if (bodys == null) {
			return;
		}
		for (int i = 0; i < this.number; i++) {
			if (i == 0) {// 设置蛇头颜色不同
				g.setColor(Color.blue);
			} else {
				g.setColor(Color.black);
			}
			mapflag[bodys[i].x][bodys[i].y] = 2;
			g.fillOval(bodys[i].x, bodys[i].y, 30, 30);// 画蛇体
		}

		g.setColor(Color.red);
		g.fillOval(food.x, food.y, 30, 30);// 画食物
	}

	public Body creatFood() {
		int x = 0, y = 0;
		do {// 随机生成食物位置

			Random r = new Random();// 创建随机数对象
			x = r.nextInt(450);// 在0到所给的数中随机产生一个伪随机数
			y = r.nextInt(450);
			// System.out.println(mapflag[x][y]+"!");
		} while (x % 30 != 0 || y % 30 != 0 || mapflag[x][y] == 2);// 保证位置不是蛇身的部分，并且确保整齐性
		// System.out.println(x + " " + y);
		mapflag[x][y] = 1;// 将食物标记为1，下面就可以通过标记的不同来判断是吃到食物还是自身，从而进行不同的操作
		return new Body(x, y);
	}

	public void actionPerformed(ActionEvent e) {
		if (bodys == null) {
			return;
		}
		if (!isMove()) {// 如果移动失败，游戏结束
			JOptionPane.showMessageDialog(this, "Game Over !");
			bodys = null;
			timer.stop();
			return;
		}
		repaint();
	}

	/**
	 * 蛇移动，原理：仅需对蛇头进行操作，然后将蛇头之前的位置赋值给现在蛇身的第二个部分，
	 * 让之前的第二个部分复制个现在的第三个部分。。。。以此类推，模拟蛇运动（这是关键）
	 */
	public void move(int x, int y) {
		Body[] b = new Body[bodys.length];
		for (int i = 0; i < this.number; i++) {
			b[i] = new Body(bodys[i].x, bodys[i].y);
		}
		this.bodys[0].x = x;
		this.bodys[0].y = y;
		for (int i = 1; i < this.number; i++) {
			this.bodys[i] = b[i - 1];
		}
		setMapflag();

	}

	private void setMapflag() {// 地图标记的刷新
		mapflag = new int[455][455];
		mapflag[food.x][food.y] = 1;
		for (int i = 0; i < this.number; i++) {
			mapflag[bodys[i].x][bodys[i].y] = 2;
		}
	}

	/**
	 * 判断是否移动成功
	 * @return
	 */
	public boolean isMove() {
		if (bodys == null) {
			return false;
		}
		int x = bodys[0].x;
		int y = bodys[0].y;
		switch (this.direct) {// 通过方向的不同对蛇头进行不同的操作
		case 1:
			y -= 30;
			break;
		case 2:
			y += 30;
			break;
		case 3:
			x -= 30;
			break;
		case 4:
			x += 30;
			break;
		}
		if (x < 0 || y >= 450 || x >= 450 || y < 0) {// 越界问题
			return false;
		}
		if (mapflag[x][y] == 1) {// 标记为1 ，则为蛇头，要添加蛇身长度
			this.number++;
			addSnake(x, y);// 添加蛇长
			// System.out.println("*");
			return true;
		} else if (mapflag[x][y] == 2) {// 标记为2，则为蛇体，即自己吃到自己，游戏结束
		// System.out.println("^");
			return false;
		} else {
			// System.out.println("%");
			move(x, y);
			return true;
		}
	}

	/**
	 * 添加蛇身，类似move（）
	 * @param x
	 * @param y
	 */
	private void addSnake(int x, int y) {
		this.score++;
		Body[] b = new Body[bodys.length];
		for (int i = 0; i < this.number - 1; i++) {
			b[i] = new Body(bodys[i].x, bodys[i].y);
		}
		bodys = new Body[this.number];
		this.bodys[0] = new Body(x, y);
		for (int i = 1; i < this.number; i++) {
			this.bodys[i] = b[i - 1];
		}
		setMapflag();
		food = creatFood();
	}

	/**
	 * 设置蛇头方向
	 * @param i
	 */
	public void setDirect(int i) {
		this.direct = i;
	}

}