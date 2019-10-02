package com.dyh.mine;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

public class MineSweep extends Frame{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=1500;
	public static final int HEIGHT=800;
	public static Point fingerPoint=new Point(WIDTH/2, HEIGHT/2);
	public static String[] fingerImageStr={"finger.png","fingerClick.png"};
	public static int fingerIndex=0;
	public static ArrayList<Cell> cells=new ArrayList<Cell>();
	public static int startCell_X=50;//第一个开始的地图起点
	public static int startCell_Y=50;
	public static boolean isClickLeft=false;
	public static boolean isClickRight=false;
	public static boolean isMarked=false;//雷是否被标记
	public static GameState state=GameState.Run;
	class MyThread extends Thread{
		@Override
		public void run() {
			while (true) {
				try {
					repaint();
					sleep(1);
				} catch (Exception e) {
					
				}
			}
		}
	}
	
	Image bgImage;
	Graphics bg;
	@Override
	public void update(Graphics g) {
		bgImage=this.createImage(WIDTH, HEIGHT);
		bg=bgImage.getGraphics();
		paint(bg);
		bg.dispose();
		g.drawImage(bgImage, 0, 0,null);
	}
	
	@Override
	public void paint(Graphics g) {
		Run(g);
		
	}
	//获取手指的指针坐标
	public static Point getFingerPoint(){
		return new Point(fingerPoint.x+15, fingerPoint.y+15);
	}
	//画出手指
	private Toolkit tk=Toolkit.getDefaultToolkit();
	Image fingerImage;
	public void drawFinger(Graphics g) {
		fingerImage= tk.getImage(this.getClass().getClassLoader().getResource("image/"+fingerImageStr[fingerIndex]));
		g.drawImage(fingerImage, fingerPoint.x, fingerPoint.y,null);
	}
	//生成地图
	Cell cell;
	Random random=new Random();
	public static int rowNum=14;//行
	public static int columnNum=28;//列
	public static int mineNum=70;//设置雷的数量
	public static int mineIndex;//雷的索引
	public void creatCell(){
		//产生一定数量的雷，全都是假雷
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				cell=new Cell(i,j,cells.size(),startCell_X, startCell_Y,50,50, false,false,false,9);
				cells.add(cell);
				cell.ownCell=cell;
				startCell_X+=cell.width;
			}
			startCell_X=50;
			startCell_Y+=cell.height;
		}
		
		//随机产生mineNum个雷
		while(mineNum>0){
			mineIndex=random.nextInt(cells.size());
			if(!cells.get(mineIndex).isMine){
				cells.get(mineIndex).isMine=true;
				mineNum--;
			}
		}
		
		//得到每个周围雷的数量
		for (int i = 0; i <cells.size(); i++) {
			cell=cells.get(i);
			cell.mineNum= cell.getMineNum();
		}
	}
	
	//游戏开始
	Cell tempCell;
	public void Run(Graphics g){
		//画出地图
		for (int i = 0; i < cells.size(); i++) {
			tempCell=cells.get(i);
			tempCell.drawCell(g);
		}
		//画出光标，即手指
		drawFinger(g);
	}
	
	//判断线程,是否踩雷
	class JudgeThread extends Thread{
		Cell temp;
		@Override
		public void run() {
			while(state==GameState.Run){
				try {
					for (int i = 0; i < cells.size(); i++) {
						temp=cells.get(i);
						//获取单击事件
						if(temp.getCell()!=null&&isClickLeft==true){
							temp.isClicked=true;
						}
						//获取被标记事件
						if(temp.getCell()!=null&&isClickRight==true){
							if(temp.isMarked==true){
								temp.isMarked=false;
								temp.imageIndex=11;
							}
							else if(temp.isMarked==false){
								temp.isMarked=true;
								temp.imageIndex=9;
							}
							isClickRight=false;
						}
					}
					sleep(41);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public MineSweep() {
		this.setTitle("扫雷V-1.03.49,DYH");
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.addWindowListener(new Window());
		this.addMouseListener(new Mouse());
		this.addMouseMotionListener(new Mouse());//添加指定的鼠标移动侦听器
		//不显示鼠标箭头，给箭头一个空图片
		Image curImg=Toolkit.getDefaultToolkit().getImage("");
		Cursor cursor=Toolkit.getDefaultToolkit().createCustomCursor(curImg,fingerPoint,"");
		this.setCursor(cursor);
		//不显示鼠标箭头//
		
		new MyThread().start();
		new JudgeThread().start();
		
		creatCell();
		
		
	}
}
