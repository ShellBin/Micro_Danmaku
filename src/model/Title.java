package model;
import java.awt.*;

//标题和游戏结束画面绘制
public class Title {
	int count;	//动画计数器
	Font titleFont;
	Font infoFont;
	

	public Title() {	//为标题实例化字体类
		count = 0;
		titleFont = new Font("SimHei", Font.BOLD, 20);	//黑体
		infoFont = new Font("SimHei", Font.BOLD, 10);
	}
	

	public void drawTitle(Graphics g) {	//游戏开始界面
		g.setColor(Color.black);
		count++;
		g.setFont(titleFont);
		g.drawString("SimpleNative Danmaku",130,150);
		g.drawString("Press SPACE",180,350);
		
	}

	
	public void drawGameover(Graphics g) {	//游戏结束界面
		g.setColor(Color.black);
		count++;
		g.setFont(titleFont);
		g.drawString("GAMEOVER",200,150);
	}
	
}

