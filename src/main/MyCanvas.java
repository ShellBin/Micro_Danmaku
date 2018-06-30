package main;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import model.*;

public class MyCanvas extends Canvas implements Runnable{
	ObjectPool objectpool;
	KeyInput keyinput;
	Image imgBuf;
	Graphics gBuf;
	Random random;
	Title title;
	Score score;
	
	//场景管理
	public int scene;
	static final int SCENE_TITLE = 0;
	static final int SCENE_GAMEMAIN = 1;
	
	
	public boolean gameover;
	int counter;
	
	
	//短按
	static final int SHOT_PRESSED = 1;
	//按下
	static final int SHOT_DOWN = 2;
	int shotkey_state;


	MyCanvas() {
		keyinput = new KeyInput();
		addKeyListener(keyinput);
		setFocusable(true);	//启用以获得焦点
		random = new Random();	//随机数对象
		title = new Title();
		score = new Score();
	}

	/**
	 * 游戏的初始化
	 * 在启动和重启的时候调用
	 * 初始化游戏内变量
	 */
	public void init() {
		objectpool = new ObjectPool();
		Score.loadScore();		
		//进入标题画面
		scene = SCENE_TITLE;
		gameover = false;
		Level.initLevel();	//关卡
		Score.initScore();	//分数
	}
	

	public void initThread() {	//初始化线程
		Thread thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * 绘图过程
	 * 在重绘时调用
	 * 从屏幕外缓冲区复制并显示
	 */
	public void paint(Graphics g) {
		g.drawImage(imgBuf, 0, 0, this);
	}

	
	@Override
	public void run() {
		//创建屏幕外缓冲区
		imgBuf = createImage(500, 500);
		gBuf = imgBuf.getGraphics();
		
		for(counter = 0; ; counter++) {
			shotkey_state = keyinput.checkShotKey();

			gBuf.setColor(Color.white);
			gBuf.fillRect(0, 0, 500, 500);

			//画面切换
			switch (scene) {
				case 0:
				title.drawTitle(gBuf);
				score.drawScore(gBuf);
				score.drawHiScore(gBuf);
					
				if (shotkey_state == SHOT_DOWN) {	//空格键按下进入游戏
					scene = SCENE_GAMEMAIN;
				}
				break;
				case 1:
				gameMain();	//游戏主画面
				break;
			}
			
			repaint();	//重绘
			
			try {
				Thread.sleep(20);
			}
			catch(InterruptedException e)
			{}
		}
	}


	public void update(Graphics g) {	//提高效率不每次清除屏幕重
		paint(g);
	}
	

	void gameMain() {	//游戏开始
		//是否开始游戏
		if (objectpool.isGameover()) {
			title.drawGameover(gBuf);
			if (shotkey_state == SHOT_DOWN) {
				//分数保存
				Score.compareScore();
				//重新初始化游戏
				init();
			}
		}		
		
		//碰撞检测
		objectpool.getColision();
		objectpool.movePlayer(keyinput);
		
		//敌人出现的间隔随等级变短
		if (counter % (100 - Level.getLevel() * 10) == 0) {
			ObjectPool.newEnemy(100 + random.nextInt(300), 0);
		}

		//500帧后等级提升
		if ((counter % 500) == 0) {
			Level.addLevel();
		}
		
		//以相等的间隔发射子弹
		if ((shotkey_state == SHOT_PRESSED)&&(counter % 3 == 0)) {
			objectpool.shotPlayer();
		}

		//游戏对象统一绘制
		objectpool.drawAll(gBuf);
		//得分绘制
		score.drawScore(gBuf);
		score.drawHiScore(gBuf);
		
	}

}
