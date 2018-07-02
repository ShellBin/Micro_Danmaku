package main;
import java.awt.*;

import model.*;

/**
*玩家，子弹，敌人等对象状态管理
*碰撞检测
*/
public class ObjectPool {
	boolean GodMode = false;	//无敌模式（调试用）
	
	static EnemyBullet[] EnemyBullet;	//提前生成保存敌弹队列
	static Enemy[] enemy;	//提前生成保存敌机队列
	static PlayerBullet[] PlayerBullet;	//预生成玩家弹列
	static Particle[] particle;	//预生成爆炸效果
	Player player;	//玩家实例化
	
	//碰撞盒大小范围
	static final int DIST_PLAYER_TO_EnemyBullet = 8;
	static final int DIST_PLAYER_TO_ENEMY = 16;
	static final int DIST_ENEMY_TO_PlayerBullet = 16;
	
	//单位最大数量设定
	static final int EnemyBullet_MAX = 100;
	static final int ENEMY_MAX = 50;
	static final int PARTICLE_MAX = 400;
	static final int PlayerBullet_MAX = 10;
	

	ObjectPool() {
		//初始化玩家
		player = new Player(250, 400, 4);	//位置及速度
		player.active = true;
		
		//数量限制
		EnemyBullet = new EnemyBullet[EnemyBullet_MAX];
		for(int i = 0; i < EnemyBullet.length; i++) {
				EnemyBullet[i] = new EnemyBullet();
		}

		//数量限制
		enemy = new Enemy[ENEMY_MAX];
		for(int i = 0; i < enemy.length; i++) {
				enemy[i] = new Enemy(player);
		}
		
		//数量限制
		PlayerBullet = new PlayerBullet[PlayerBullet_MAX];
		for(int i = 0; i < PlayerBullet.length; i++) {
				PlayerBullet[i] = new PlayerBullet();
		}

		//数量限制
		particle = new Particle[PARTICLE_MAX];
		for(int i = 0; i < particle.length; i++) {
				particle[i] = new Particle();
		}
	}

	public void drawAll(Graphics g) {	//绘制所有游戏对象
        doGameObjects(g, EnemyBullet);
        doGameObjects(g, enemy);
        doGameObjects(g, PlayerBullet);
        doGameObjects(g, particle);
		player.draw(g);
	}

    public void doGameObjects(Graphics g, GameObject[] objary) {	//按照排列规则
        for (int i = 0; i < objary.length; i++) {			
            if (objary[i].active == true) {
                objary[i].move();
                objary[i].draw(g);
            }
        }
    }
    
    /**
     * 敌弹初始化
     * 参数 ix(double)：生成处x坐标
     * 参数 iy(double)：生成处y坐标
     * 参数 idirection(double)：移动的方向
     * 参数 ispeed(double)：移动的速度
     * 参数 子弹ID(double)：(如果没有空缺：-1)
     */
	public static int newEnemyBullet(double ix, double iy, double idirection, double ispeed) {
		for (int i = 0; i < EnemyBullet_MAX; i++) {
			if ((EnemyBullet[i].active) == false) {
				EnemyBullet[i].activate(ix, iy, idirection, ispeed);
				return i;
			}
		}
		return -1;		//没了（x
	}

    /**
     * 敌人的生成、初始化
     * 参数 ix(double)：生成处x坐标
     * 参数 iy(double)：生成处y坐标
     * 参数 子弹ID(double)：(如果没有空缺：-1)
     */
	public static int newEnemy(double ix, double iy) {
		for (int i = 0; i < ENEMY_MAX; i++) {
			if ((enemy[i].active) == false) {
				enemy[i].activate(ix, iy);
				return i;
			}
		}
		return -1;
	}

    /**
     * 玩家弹的生成、初始化
     * 参数 ix(double)：生成处x坐标
     * 参数 iy(double)：生成处y坐标
	 * 返回 玩家弹ID：(如果没有空缺：-1)
     */
	public static int newPlayerBullets(double ix, double iy) {
		for (int i = 0; i < PlayerBullet_MAX; i++) {
				if ((PlayerBullet[i].active) == false) {
					PlayerBullet[i].activate(ix, iy);
					return i;
				}		
		}
			return -1;
	}

    /**
     * 爆炸生成、初始化
     * 参数 ix(double)：生成处x坐标
     * 参数 iy(double)：生成处y坐标
     * 参数 idirection(double)：移动的方向
     * 参数 ispeed(double)：移动的速度
	 * 返回 爆炸效果ID：(如果没有空缺：-1)
     */
	public static int newParticle(double ix, double iy, double idirection, double ispeed) {
		for (int i = 0; i < PARTICLE_MAX; i++) {
			if ((particle[i].active) == false) {
				particle[i].activate(ix, iy, idirection, ispeed);
				return i;
			}
		}
		return -1;
	}

	public void shotPlayer() {	//玩家发射子弹
		//只在可见时可以发射
		if (player.active){
			newPlayerBullets(player.x, player.y);
		}
	}

	public void movePlayer(KeyInput keyinput) {	//玩家移动控制
		player.move(keyinput.getXDirection(), keyinput.getYDirection());
	}
	
	/**
	 * 两点之间距离测定
	 * 参数 ix(double)：ga 对象A
	 * 参数 ix(double)：gb 对象B
	 * 返回：距离
	 */
	public double getDistance(GameObject ga, GameObject gb) {
		//勾股定理
		double Xdiff = Math.abs(ga.x - gb.x);
		double Ydiff = Math.abs(ga.y - gb.y);
		return Math.sqrt(Math.pow(Xdiff,2) + Math.pow(Ydiff,2));
	}


	public void getColision() {	//碰撞检测
		//敌人子弹与玩家的碰撞
        for (int i = 0; i < EnemyBullet.length && GodMode == false; i++) {
			if ((EnemyBullet[i].active)&&(player.active)) {	//中弹判断
				if (getDistance(player, EnemyBullet[i]) < DIST_PLAYER_TO_EnemyBullet) {
					player.active = false;	//玩家消失
					for (int j = 0; j < 360; j += 20) {	//产生爆炸
						newParticle(player.x, player.y, j, 2);
					}
					EnemyBullet[i].active = false;	//子弹消失
				}
			}
        }

		//玩家子弹和敌人的碰撞检测
        for (int i = 0; i < enemy.length; i++) {
			if (enemy[i].active == true){
				for (int j = 0; j < PlayerBullet.length; j++) {
					if (PlayerBullet[j].active == true){
						//中弹判断
						if (getDistance(enemy[i], PlayerBullet[j]) < DIST_ENEMY_TO_PlayerBullet) {
							newParticle(PlayerBullet[j].x, PlayerBullet[j].y, 270, 2);
							//敌军生命减少
							enemy[i].hit();
							//子弹消失
							PlayerBullet[j].active = false;
						}
					}
				}
			}
        }

		//敌人和玩家的碰撞检测
        for (int i = 0; i < enemy.length && GodMode == false; i++) {
			if ((enemy[i].active)&&(player.active)) {
				//碰撞判断
				if (getDistance(player, enemy[i]) < DIST_PLAYER_TO_ENEMY) {
					//玩家消失
					player.active = false;
					//爆炸效果
					for (int j = 0; j < 360; j += 20) {
						newParticle(player.x, player.y, j, 2);
					}
				}
			}
        }

	}
	
	/**
	 * 返回游戏结果
	 */
	public boolean isGameover() {
		return !player.active;
	}	
	
}
