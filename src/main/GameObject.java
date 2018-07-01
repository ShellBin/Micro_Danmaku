package main;
import java.awt.*;


public abstract class GameObject {

	public boolean active;	//实例有效标记位
	public double x;	//X坐标
	public double y;	//Y坐标
	
	public abstract void move();	//单步执行
	public abstract void draw(Graphics g);	//绘制
	
}
