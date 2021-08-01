/*
 *  2021 (c) aek426rahmani
 * */

package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public static boolean LEFT = false;
	public static boolean RIGHT = false;
	public static boolean UP = false;
	
	public KeyHandler() {
		
	}
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 87 || e.getKeyCode() == 38) {
			UP = true;
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			LEFT = true;
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			RIGHT = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 87 || e.getKeyCode() == 38) {
			UP = false;
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) {
			LEFT = false;
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) {
			RIGHT = false;
		}
	}

}
