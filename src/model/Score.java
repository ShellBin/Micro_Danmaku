package model;

import java.awt.*;
import java.io.*;

//得分的控制和显示
public class Score {
	static int myscore;
	static int hiscore;
	Font scoreFont;
	
	Score() {
		scoreFont = new Font("SimHei", Font.BOLD, 10); 	//黑体
		myscore = 0;
	}
	

	public void drawScore(Graphics g) {	//得分的显示
		g.setColor(Color.black);
		g.setFont(scoreFont);
		g.drawString("得分:"+myscore, 30, 30);
	}
	
	public void drawHiScore(Graphics g) {	//最高分显示
		g.setColor(Color.black);
		g.setFont(scoreFont);
		g.drawString("最高分:"+hiscore, 420, 30);
	}
	
	
	public static void addScore(int gain) {	//计分添加
		myscore += gain;
	}
	

	public static void compareScore() {	//最高分更新处理
		if (myscore > hiscore) {
			hiscore = myscore;
			saveScore();
		}
	}


	/**
	 * 得分保存为 data.dat
	 * 暂时停用的特性
	 */
	static void saveScore() {
//            DataOutputStream dout = new DataOutputStream(new FileOutputStream("data.dat"));
//            dout.writeInt(hiscore);
//            dout.close();
	}

	/**
	 * 从data.dat读取分数
	 * 暂时停用是特性
	 */
	static void loadScore() {
//            DataInputStream din = new DataInputStream(new FileInputStream("data.dat"));
//            hiscore = din.readInt();
//            din.close();
	}


	public static void initScore() {
		myscore = 0;
	}
}
