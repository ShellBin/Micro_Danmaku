package model;
import java.awt.*;
import java.awt.event.*;

import main.*;

//敌机子弹类
public class EnemyBullet extends GameObject {
	double direction;
	double speed;
	double speedX;
	double speedY;
	
	EnemyBullet() {	//敌军子弹构造方法
		active = false;
	}
	
	public void move() {
		x += speedX;
		y += speedY;
		
		if ( (x < 0)||(500 < x)||(y < 0)||(500 < y) ) {
			active = false;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect((int)(x-3), (int)(y-3), (int)6, (int)6);
	}

	/**
	 * 移动处理
	 * 参数 ix(double)：生成处的X坐标 
	 * 参数 iy(double)：生成处的Y坐标
	 * 参数ispeed(double)：速度
	 * 参数idirection(double)：方向(0-360)
	 */	
	public void activate(double ix, double iy, double idirection, double ispeed) {
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		active = true;
				
		//提高处理速度，从极坐标转到xy
		double radian;
		radian = Math.toRadians(direction);	//度数转弧度
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
	}

	public static void FireRound(double x, double y) {	//全方位子弹攻击
		for (int i = 0; i < 360; i += 60 )
		{
			ObjectPool.newEnemyBullet(x, y, i, 3);
		}
	}

	//向玩家方向发射子弹
	public static void FireAim(double x, double y, Player player) {
		double degree = Math.toDegrees(Math.atan2(player.y - y, player.x - x));
		ObjectPool.newEnemyBullet(x, y, degree, 4);
		ObjectPool.newEnemyBullet(x, y, degree+20, 4);
		ObjectPool.newEnemyBullet(x, y, degree-20, 4);
	}
}
