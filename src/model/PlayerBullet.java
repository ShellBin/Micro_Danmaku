package model;
import java.awt.*;

import main.*;

//玩家子弹类
public class PlayerBullet extends GameObject {

	public PlayerBullet() {	//玩家子弹构造方法
		active = false;
	}
	
	public void move() {	//玩家子弹的移动
        y -= 15;
		//移动出屏幕后从画面消失
		if ( (y < 0) ){
			active = false;
		}
	}
	
	public void draw(Graphics g) {	//循环一次执行一次
		g.setColor(Color.blue);
		//绘制长方形
		g.drawRect((int)x-3, (int)y-10, (int)6, (int)20);
	}
	
	//在玩家位置进行初始化
	public void activate(double ix, double iy) {
		x = ix;
		y = iy;
		active = true;	//可视化子弹
	}
}
