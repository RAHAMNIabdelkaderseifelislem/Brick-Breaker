/*
 *  2021 (c) aek426rahmani
 * */

package handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Controller;

public class MouseHandler implements MouseMotionListener, MouseListener{

	public static boolean MOUSEDOWN = false;
	public static boolean hasPressed = false;
	
	public MouseHandler() {
		
	}
	
	public void mouseClicked(MouseEvent e) {	
//		System.out.println("X: " + e.getX() + "\nY: " + e.getY() + "\n");
	}
	public void mousePressed(MouseEvent e) {
		if(!hasPressed) {
			MOUSEDOWN = true;
			hasPressed = true;
		}
	}
	public void mouseReleased(MouseEvent e) {
		MOUSEDOWN = false;
		hasPressed = false;
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
		MOUSEDOWN = false;
	}
	public void mouseMoved(MouseEvent e) {
		Controller.mousePoint = e.getPoint();
	}
}
