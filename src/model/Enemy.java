package model;
import java.awt.*;

import main.*;

//敌人类
public class Enemy extends GameObject {
    int counter = 0; 		//生存时间
    int life = 0; 			//生命值
    int type = 0; 			//敌人类型
    boolean able = false;	//判定点
    Player player; 			//玩家的构造方法
    boolean startshoot; 	//开始标记
    int shootnum;

    public Enemy(Player iplayer) {
        player = iplayer;	//取玩家位置
        active = false;		//避免初始化时被激活
    }

    /**
     * 实例化
     * 参数 ix(double)：生成处的X坐标
     * 参数 iy(double)：生成处的Y坐标
     */
    public void activate(double ix, double iy) {
        x = ix;
        y = iy;
        type = (int) (Math.random() + 0.5);
        active = true;	//启用敌方子弹
        life = 5;		//敌人有5个生命值
        counter = 0;

        shootnum = Level.getLevel();
        startshoot = false;
    }

    public void hit() {	//当与玩家子弹碰撞时
        life--;			//减生命值
        able = true;

        if (life < 0) {	//不同敌人不同得分

            switch (type) {
            	case 0: Score.addScore(10);
                break;
            	case 1: Score.addScore(20);
                break;
            	default:
            }

            //敌机爆炸效果
            ObjectPool.newParticle(x, y, 45, 2);
            ObjectPool.newParticle(x, y, 135, 2);
            ObjectPool.newParticle(x, y, 225, 2);
            ObjectPool.newParticle(x, y, 315, 2);
            active = false;
        }
    }

    public void move() {	//循环一次执行一次
        //按类型划分
        switch (type) {
        	case 0:
            move_enemy0();
            break;
        	case 1:
            move_enemy1();
            break;
        	default:
        }
    }

    void move_enemy0() {	//敌机动作效果1（左右摆动）
        counter++;
        y++;
        //左右摆动
        x += Math.sin(y / 20);
        //从画面消失
        if ((500 < y)) {
            active = false;
        }
        //间隔一定时间发射子弹
        if ((counter % 80) == 0) {
            EnemyBullet.FireRound(x, y);
        }
    }

    void move_enemy1() //敌机动作效果2（上方弹出后退出）
     {
        counter++;

        double p = 60; //悬停时长
        double q = 200; //悬停位置

        y = ((-q / Math.pow(p, 2) * Math.pow((counter - p), 2)) + q);	//二次函数表示运动轨迹

        //从画面消失
        if ((-30 > y)) {
            active = false;
        }

        //间隔一定时间发射子弹
        if ((counter % 60) == 0) {
            startshoot = true;
        }

        //如果玩家正在射击，就多次发射和玩家等级相当的子弹
        if (startshoot) {
            if (((counter % 5) == 0) && (shootnum > 0)) {
                EnemyBullet.FireAim(x, y, player);
                shootnum--;
            }
        }
    }

    public void draw(Graphics g) { //循环一次绘制一次
        if (able) {
            g.setColor(Color.orange);	//如果被玩家击中的话，将颜色设为黄色
        } else {
            switch (type) {
            	case 0:
                g.setColor(Color.white);
                break;            
                case 1:
                g.setColor(Color.white);
                break;
                default:
            }
        }

        able = false;
        //绘制正方形
        g.drawRect((int) x - 16, (int) y - 16, (int) 32, (int) 32);
    }
}
