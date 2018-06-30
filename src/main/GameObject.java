package main;
import java.awt.*;


public abstract class GameObject {

	public boolean active;	//实例有效标记位
	public double x;	//X坐标
	public double y;	//Y坐标
	
	abstract void move();	//抽象逐步执行方法
	abstract void draw(Graphics g);	//抽象绘制方法
	
}
