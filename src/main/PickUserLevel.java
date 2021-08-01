/*
 *  2021 (c) aek426rahmani
 * */

package main;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import handlers.MouseHandler;

public class PickUserLevel {
	
	private Image arrow;
		
	Rectangle[] levels = {new Rectangle(50, 200, 75, 75), new Rectangle(150, 200, 75, 75),
						  new Rectangle(250, 200, 75, 75), new Rectangle(350, 200, 75, 75),
						  new Rectangle(50, 300, 75, 75), new Rectangle(150, 300, 75, 75),
						  new Rectangle(250, 300, 75, 75), new Rectangle(350, 300, 75, 75)};
	
	Rectangle userLevel = new Rectangle(50, 390, 380, 25);
	
	
	Rectangle[] arrows = {new Rectangle(5, Frame.HEIGHT-70, 50, 50)};
	
	

	public PickUserLevel() {
		arrow = new ImageLoader(ImageLoader.arrow).getImage();
	}
	public void tick(){
		if(MouseHandler.MOUSEDOWN) {
			for(int i = 0; i < levels.length; i++) {
				if(levels[i].contains(Controller.mousePoint)) {
					Controller.switchStates(Controller.STATE.CREATELEVEL, i);
				}
			}
			for(int i = 0; i < arrows.length; i++) {
				if(arrows[i].contains(Controller.mousePoint)) {
					Controller.switchStates(Controller.STATE.MENU);
				}
			}
			MouseHandler.MOUSEDOWN = false;
		}
	}
	public void render(Graphics g) {
		g.setFont(Controller.bigFont);
		Graphics2D g2 = (Graphics2D)g;
	       GradientPaint blueToBlack = new GradientPaint(0, 0, Color.CYAN,
	            0, 600, Color.BLUE);
	        g2.setPaint(blueToBlack);
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		g.setColor(Color.black);
		g.drawString("Choose User Created", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Choose User Created")/2, 80);
		g.drawString("Level", Frame.WIDTH/2-g.getFontMetrics().stringWidth("LEvel")/2, 100);
		for(int i = 0; i < levels.length; i++) {
			g.setColor(Color.black);
			g.drawString("" + (i), levels[i].x+35, levels[i].y+45);
			if(levels[i].contains(Controller.mousePoint)) {
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
			}
			
			g.drawRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
		}
		
		for(int i = 0; i < arrows.length; i++) {
			if(arrows[i].contains(Controller.mousePoint)) {
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(arrows[i].x, arrows[i].y, arrows[i].width, arrows[i].height);
			}
			g.setColor(Color.black);
			g.drawRect(arrows[i].x, arrows[i].y, arrows[i].width, arrows[i].height);
			if(i == 0) {
			g.drawImage(arrow, arrows[i].x, arrows[i].y, arrows[i].width, arrows[i].height, null);
			}
		}
	}
}
