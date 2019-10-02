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
	public static int startCell_X=50;//��һ����ʼ�ĵ�ͼ���
	public static int startCell_Y=50;
	public static boolean isClickLeft=false;
	public static boolean isClickRight=false;
	public static boolean isMarked=false;//���Ƿ񱻱��
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
	//��ȡ��ָ��ָ������
	public static Point getFingerPoint(){
		return new Point(fingerPoint.x+15, fingerPoint.y+15);
	}
	//������ָ
	private Toolkit tk=Toolkit.getDefaultToolkit();
	Image fingerImage;
	public void drawFinger(Graphics g) {
		fingerImage= tk.getImage(this.getClass().getClassLoader().getResource("image/"+fingerImageStr[fingerIndex]));
		g.drawImage(fingerImage, fingerPoint.x, fingerPoint.y,null);
	}
	//���ɵ�ͼ
	Cell cell;
	Random random=new Random();
	public static int rowNum=14;//��
	public static int columnNum=28;//��
	public static int mineNum=70;//�����׵�����
	public static int mineIndex;//�׵�����
	public void creatCell(){
		//����һ���������ף�ȫ���Ǽ���
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
		
		//�������mineNum����
		while(mineNum>0){
			mineIndex=random.nextInt(cells.size());
			if(!cells.get(mineIndex).isMine){
				cells.get(mineIndex).isMine=true;
				mineNum--;
			}
		}
		
		//�õ�ÿ����Χ�׵�����
		for (int i = 0; i <cells.size(); i++) {
			cell=cells.get(i);
			cell.mineNum= cell.getMineNum();
		}
	}
	
	//��Ϸ��ʼ
	Cell tempCell;
	public void Run(Graphics g){
		//������ͼ
		for (int i = 0; i < cells.size(); i++) {
			tempCell=cells.get(i);
			tempCell.drawCell(g);
		}
		//������꣬����ָ
		drawFinger(g);
	}
	
	//�ж��߳�,�Ƿ����
	class JudgeThread extends Thread{
		Cell temp;
		@Override
		public void run() {
			while(state==GameState.Run){
				try {
					for (int i = 0; i < cells.size(); i++) {
						temp=cells.get(i);
						//��ȡ�����¼�
						if(temp.getCell()!=null&&isClickLeft==true){
							temp.isClicked=true;
						}
						//��ȡ������¼�
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
		this.setTitle("ɨ��V-1.03.49,DYH");
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.addWindowListener(new Window());
		this.addMouseListener(new Mouse());
		this.addMouseMotionListener(new Mouse());//���ָ��������ƶ�������
		//����ʾ����ͷ������ͷһ����ͼƬ
		Image curImg=Toolkit.getDefaultToolkit().getImage("");
		Cursor cursor=Toolkit.getDefaultToolkit().createCustomCursor(curImg,fingerPoint,"");
		this.setCursor(cursor);
		//����ʾ����ͷ//
		
		new MyThread().start();
		new JudgeThread().start();
		
		creatCell();
		
		
	}
}
