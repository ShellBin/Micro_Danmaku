package model;

import java.awt.*;

//得分的控制和显示
public class Score {
	static int myscore;
	static int hiscore;
	Font scoreFont;
	
	public Score() {
		scoreFont = new Font("SimHei", Font.BOLD, 15); 	//黑体
		myscore = 0;
	}	

	public void drawScore(Graphics g) {	//得分的显示
		g.setColor(Color.white);
		g.setFont(scoreFont);
		g.drawString("得分:"+myscore, 10, 30);	
	}
	
	public void drawHiScore(Graphics g) {	//最高分显示
		g.setColor(Color.white);
		g.setFont(scoreFont);
		g.drawString("最高分:"+hiscore, 350, 30);
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
	 * 得分保存为 data.dat（未实现）
	 */
	static void saveScore() {
		
	}

	/**
	 * 从data.dat读取分数（未实现）
	 */
	public static void loadScore() {
		
	}


	public static void initScore() {
		myscore = 0;
	}
}
