package cn.jbit.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * ��ģ��  
 * @author user
 *
 */
public class SnakeCanvas extends MapCanvas implements ActionListener {
	private int number = 5;// ����ĳ�ʼ����
	public Timer timer;// ��ʱ��
	private Body[] bodys;// ����
	private Body food;// ʳ��
	public int score = 0;// ����
	private int speed = 250; //��ʱ����ʱʱ�� /����
	int direct = 3;// ��ͷ��ʼ����
	private int[][] mapflag = new int[455][455];// ��ͼ���

	/**
	 * ��ʼ��
	 */
	public void init() {
		this.direct = 3;
		this.number = 5;
		this.bodys = new Body[this.number];
		mapflag = new int[455][455];
		for (int i = 0, x = 300; i < this.number; i++, x += 30) {
			bodys[i] = new Body(x, 180);
			mapflag[bodys[i].x][bodys[i].y] = 2;// ��������� ����Ϊ2
		}
		food = creatFood();// ����ʳ��

	}

	public SnakeCanvas() {
		init();
		timer = new Timer(speed, this);// ������ʱ������
		timer.start();// ��ʱ������
	}

	private class Body {// ��ͷ��Ա
		int x, y;

		Body(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public void paint(Graphics g) {// ��ͼ
		super.paint(g);
		if (bodys == null) {
			return;
		}
		for (int i = 0; i < this.number; i++) {
			if (i == 0) {// ������ͷ��ɫ��ͬ
				g.setColor(Color.blue);
			} else {
				g.setColor(Color.black);
			}
			mapflag[bodys[i].x][bodys[i].y] = 2;
			g.fillOval(bodys[i].x, bodys[i].y, 30, 30);// ������
		}

		g.setColor(Color.red);
		g.fillOval(food.x, food.y, 30, 30);// ��ʳ��
	}

	public Body creatFood() {
		int x = 0, y = 0;
		do {// �������ʳ��λ��

			Random r = new Random();// �������������
			x = r.nextInt(450);// ��0�������������������һ��α�����
			y = r.nextInt(450);
			// System.out.println(mapflag[x][y]+"!");
		} while (x % 30 != 0 || y % 30 != 0 || mapflag[x][y] == 2);// ��֤λ�ò�������Ĳ��֣�����ȷ��������
		// System.out.println(x + " " + y);
		mapflag[x][y] = 1;// ��ʳ����Ϊ1������Ϳ���ͨ����ǵĲ�ͬ���ж��ǳԵ�ʳ�ﻹ�������Ӷ����в�ͬ�Ĳ���
		return new Body(x, y);
	}

	public void actionPerformed(ActionEvent e) {
		if (bodys == null) {
			return;
		}
		if (!isMove()) {// ����ƶ�ʧ�ܣ���Ϸ����
			JOptionPane.showMessageDialog(this, "Game Over !");
			bodys = null;
			timer.stop();
			return;
		}
		repaint();
	}

	/**
	 * ���ƶ���ԭ���������ͷ���в�����Ȼ����ͷ֮ǰ��λ�ø�ֵ����������ĵڶ������֣�
	 * ��֮ǰ�ĵڶ������ָ��Ƹ����ڵĵ��������֡��������Դ����ƣ�ģ�����˶������ǹؼ���
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

	private void setMapflag() {// ��ͼ��ǵ�ˢ��
		mapflag = new int[455][455];
		mapflag[food.x][food.y] = 1;
		for (int i = 0; i < this.number; i++) {
			mapflag[bodys[i].x][bodys[i].y] = 2;
		}
	}

	/**
	 * �ж��Ƿ��ƶ��ɹ�
	 * @return
	 */
	public boolean isMove() {
		if (bodys == null) {
			return false;
		}
		int x = bodys[0].x;
		int y = bodys[0].y;
		switch (this.direct) {// ͨ������Ĳ�ͬ����ͷ���в�ͬ�Ĳ���
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
		if (x < 0 || y >= 450 || x >= 450 || y < 0) {// Խ������
			return false;
		}
		if (mapflag[x][y] == 1) {// ���Ϊ1 ����Ϊ��ͷ��Ҫ���������
			this.number++;
			addSnake(x, y);// ����߳�
			// System.out.println("*");
			return true;
		} else if (mapflag[x][y] == 2) {// ���Ϊ2����Ϊ���壬���Լ��Ե��Լ�����Ϸ����
		// System.out.println("^");
			return false;
		} else {
			// System.out.println("%");
			move(x, y);
			return true;
		}
	}

	/**
	 * �����������move����
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
	 * ������ͷ����
	 * @param i
	 */
	public void setDirect(int i) {
		this.direct = i;
	}

}