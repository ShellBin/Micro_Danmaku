package main;
import javax.swing.JFrame;
import java.awt.*;

public class Game{
    static JFrame jf;
	public static void main(String[] args){
		jf=new JFrame();
		jf.setTitle("Micro Danmaku");
		jf.setDefaultCloseOperation(3);
		jf.setResizable(false);
		jf.setBounds((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-640)/2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-640)/2,500,500);
		MyCanvas mc = new MyCanvas();
		jf.add(mc);
		jf.setVisible(true);
		
		mc.init();
		mc.initThread();
	}
}

