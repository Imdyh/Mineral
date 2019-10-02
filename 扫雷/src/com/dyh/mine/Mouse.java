package com.dyh.mine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter{
	@Override
	public void mouseMoved(MouseEvent e) {
		MineSweep.fingerPoint= e.getPoint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		MineSweep.fingerPoint= e.getPoint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		//µã»÷×ó¼ü
		case MouseEvent.BUTTON1:
			MineSweep.isClickLeft=true;
			break;
		//µã»÷ÓÒ¼ü
		case MouseEvent.BUTTON3:
			MineSweep.fingerIndex=1;
			MineSweep.isClickRight=true;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		//ÊÍ·Å×ó¼ü
		case MouseEvent.BUTTON1:
			MineSweep.isClickLeft=false;
			break;
		//ÊÍ·ÅÓÒ¼ü
		case MouseEvent.BUTTON3:
			MineSweep.fingerIndex=0;
			MineSweep.isClickRight=false;
			break;
		default:
			break;
		}
	}
}

