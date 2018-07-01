package model;

public class Level {	//关卡管理，每500帧提高一关
	static int level;	

	public static int getLevel(){
		return level;
	}
	
	public static void addLevel() {	//关卡增加方法，最高10关
		if (level < 10) {
			level++;
		}
		System.out.println("level:"+level);	//debug用
	}

	public static void initLevel()	//初始化恢复等级0
	{
		level = 0;
	}
}
