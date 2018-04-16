package cn.jbit.snake;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 * 蛇运行的地图  
 * @author user
 *
 */
public class MapCanvas extends Canvas {

	public void paint(Graphics g) {
		g.setColor(Color.red);
		for (int i = 30; i <= 450; i += 30) {
			g.drawLine(0, i, 450, i);
		}
		for (int i = 30; i <= 450; i += 30) {
			g.drawLine(i, 0, i, 450);
		}
		g.drawLine(0, 0, 450, 0);
		g.drawLine(0, 450, 450, 450);
		g.drawLine(0, 0, 0, 450);
		g.drawLine(450, 0, 450, 450);
	}

}