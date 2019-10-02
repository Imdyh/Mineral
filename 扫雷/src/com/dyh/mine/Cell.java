package com.dyh.mine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

public class Cell {
	//�ڸ����ж�Ӧ��λ�ã�����
	public int i;
	public int j;
	//С����
	public int x;
	public int y;
	public int width;
	public int height;
	public boolean isMarked;//�Ƿ񱻱��
	public boolean isMine;//�ǲ���һ����
	public boolean isAlive;
	public int mineNum=0;//��Χ���׸����������0�ͽ���Χ��ȫ����ʾ����
	public boolean isClicked;//�Ƿ��Ѿ��㿪��
	public int index;
	public int imageIndex;
	public Cell ownCell;
	public String[] cellImageStr ={"zero.gif","one.gif","two.gif","three.gif","four.gif","five.gif","six.gif","seven.gif","eight.gif",
									"normal.gif","ask.gif","flag.gif","explodedMine.gif","mine.gif","highLight.gif"};
	public ArrayList<Cell> cells=new ArrayList<Cell>();//��Χ��������׵Ķ���
	public Cell(int i,int j,int index,int x, int y, int width, int height, boolean isMarked, boolean isMine,boolean isClicked,int imageIndex) {
		super();
		this.i=i;
		this.j=j;
		this.index=index;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isMarked = isMarked;
		this.isMine = isMine;
		this.isClicked=isClicked;
		this.isAlive=true;
		this.imageIndex=imageIndex;
	}
	Toolkit tk=Toolkit.getDefaultToolkit();
	Image image;
	Random random=new Random();
	int cleanNum;//������������
	Cell cell;
	public void drawCell(Graphics g){
		image=tk.getImage(this.getClass().getClassLoader().getResource("image/"+cellImageStr[imageIndex]));
		g.drawImage(image, x, y, width, height,null);
		click();
	}
	
	//ɨ����Χ�ĵ���,bin�ж��Ƿ�ը
	public void click(){
		if(isClicked==true){
			//�����һ�����۾ͱ�ը�����������ط�������ʾ����
			if(isMine==true){
				imageIndex=12;
				for (int i = 0; i < MineSweep.cells.size(); i++) {
					if(i!=index){
						cell= MineSweep.cells.get(i);
						if(cell.isMine){
							cell.imageIndex=13;
						}
					}
				}
				MineSweep.state=GameState.End;
			}else {
				//��������һ������ף����ж���Χ�׵�����������ʾ����
				if(mineNum==0){
					for(int i=0;i<cells.size();i++){
						cell=cells.get(i);
						if(!cell.isMarked){
							cell.imageIndex=0;
							cell.isClicked=true;
						}
					}
					cells.clear();
				}
				if(mineNum>0){
					imageIndex=mineNum;
				}
			}
		}
	}
	
	
	//��ȡ��Χ�׵�����
	public int getMineNum(){
		Cell tempCell;
		//�ж��ĸ�����λ��
		if(index==0){
			//4	
			tempCell=MineSweep.cells.get(index);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				//������
				cells.add(tempCell);
			}
			//7
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
			}else {
				cells.add(tempCell);
			}
			//8
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			return mineNum;
		}
		if(index==MineSweep.columnNum-1){
			//4	
			tempCell=MineSweep.cells.get(index-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//6
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//7
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			return mineNum;
		}
		if(index==MineSweep.cells.size()-MineSweep.columnNum){
			//2
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//3
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//5
			tempCell=MineSweep.cells.get(index+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			return mineNum;
		}
		if(index==MineSweep.cells.size()-1){
			//1		
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//2
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//4	
			tempCell=MineSweep.cells.get(index-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			return mineNum;
		}
		
		//**�ж��ĸ��ǵ�����λ��
		
		//**�ж�������
		//�ϱ�
		if(index>0&&index<MineSweep.columnNum-1){
			//4	
			tempCell=MineSweep.cells.get(index-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//5
			tempCell=MineSweep.cells.get(index+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//6
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//7
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//8
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			return mineNum;
		}
		//���
		if(index>0&&index%MineSweep.columnNum==0&&index<MineSweep.cells.size()-MineSweep.columnNum){
			//2
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//3
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				cells.add(tempCell);
			}
			//5
			tempCell=MineSweep.cells.get(index+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				cells.add(tempCell);
			}
			//7
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				cells.add(tempCell);
			}
			//8
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				cells.add(tempCell);
			}
			return mineNum;
		}
		//�ұ�
		if(index>MineSweep.columnNum-1&&(index+1)%MineSweep.columnNum==0&&index<MineSweep.cells.size()-1){
			//1		
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				cells.add(tempCell);
			}
			//2
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//4	
			tempCell=MineSweep.cells.get(index-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//6
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//7
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			return mineNum;
		}
		
		//�ױ�
		if(index>MineSweep.cells.size()-MineSweep.columnNum&&index<MineSweep.cells.size()-1){
			//1		
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//2
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//3
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//4	
			tempCell=MineSweep.cells.get(index-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//5
			tempCell=MineSweep.cells.get(index+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			return mineNum;
		}
		//**�ж�������
		else {		
			//1	
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//2
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//3
			tempCell=MineSweep.cells.get(index-MineSweep.columnNum+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//4	
			tempCell=MineSweep.cells.get(index-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//5
			tempCell=MineSweep.cells.get(index+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//6
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum-1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//7
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			//8
			tempCell=MineSweep.cells.get(index+MineSweep.columnNum+1);
			if(tempCell.isMine==true){
				mineNum+=1;
				
			}else {
				cells.add(tempCell);
			}
			return mineNum;
		}
		
	}
	
	
	//��ȡcell�����ж��Ƿ��������
	public Cell getCell(){
		if(MineSweep.getFingerPoint().x>x&&MineSweep.getFingerPoint().x<x+width
				&&MineSweep.getFingerPoint().y>y&&MineSweep.getFingerPoint().y<y+height){
			return ownCell;
		}else {
			return null;
		}
	}
}
