package model;
import java.awt.*;
import java.awt.event.*;

import main.GameObject;

public class Particle extends GameObject {
	double direction;
	double speed;
	double speedX;
	double speedY;
	int size;
	
	Particle() {
		active = false;
	}
	
	public void move() {
		x += speedX;
		y += speedY;
		size--;
		
		if ( (x < 0)||(500 < x)||(y < 0)||(500 < y) ) {
			active = false;
		}else if (size < 0) {
			active = false;
		}
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Color.gray);
		g.drawOval((int)(x-size/2), (int)(y-size/2), size, size);
	}
	
	//在这里进行初始化
	public void activate(double ix, double iy, double idirection, double ispeed) {
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		active = true;
		size = 30;
		
		//为了更高的速度，吧极坐标转xy
		double radian;
		radian = Math.toRadians(direction);	//度数转弧度
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
	}
}
