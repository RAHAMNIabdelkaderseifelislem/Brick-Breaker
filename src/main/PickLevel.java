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

import files.Files;
import files.Level;
import handlers.MouseHandler;

public class PickLevel {
	
	private Image arrow;
	private Image arrow2;
	private Image locked;
	
	private int page = 1;
	
	Rectangle[] levels = {new Rectangle(50, 200, 75, 75), new Rectangle(150, 200, 75, 75),
						  new Rectangle(250, 200, 75, 75), new Rectangle(350, 200, 75, 75),
						  new Rectangle(50, 300, 75, 75), new Rectangle(150, 300, 75, 75),
						  new Rectangle(250, 300, 75, 75), new Rectangle(350, 300, 75, 75)};
	
	Rectangle userLevel = new Rectangle(50, 390, 380, 25);
	
	
	Rectangle[] arrows = {new Rectangle(5, Frame.HEIGHT-70, 50, 50),
						  new Rectangle(Frame.WIDTH-60, Frame.HEIGHT-70, 50, 50)};
	
	

	public PickLevel() {
		arrow = new ImageLoader(ImageLoader.arrow).getImage();
		arrow2 = new ImageLoader(ImageLoader.arrow2).getImage();
		locked = new ImageLoader(ImageLoader.locked).getImage();
		Level.lockedLevels = Files.readFile(Files.LEVELPATH);
	}
	public void tick(){
		if(MouseHandler.MOUSEDOWN) {
			for(int i = 0; i < levels.length; i++) {
				if(levels[i].contains(Controller.mousePoint)) {
					if(Level.lockedLevels[i+(8*(page-1))] == false) {
						Controller.switchStates(Controller.STATE.GAME, i*page);
					}
				}
			}
			for(int i = 0; i < arrows.length; i++) {
				if(arrows[i].contains(Controller.mousePoint)) {
					if(i == 0) {
						if(page > 1) {
							page--;
						}else {
							Controller.switchStates(Controller.STATE.MENU);
						}
					}else{
						if(page < 3) {
							page++;
						}
					}
					
				}
			}
			if(userLevel.contains(Controller.mousePoint)) {
				Controller.switchStates(Controller.STATE.PICKUSERLEVEL);
			}
			MouseHandler.MOUSEDOWN = false;
		}
	}
	public void render(Graphics g) {
		g.setFont(Controller.bigFont);
		Graphics2D g2 = (Graphics2D)g;
	    GradientPaint blueToBlack = new GradientPaint(0, 0, Color.CYAN, 0, 600, Color.BLUE);
	    g2.setPaint(blueToBlack);
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		g.setColor(Color.black);
		g.drawString("Choose Level", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Choose Level")/2, 80);
		g.drawString("Page " + page, Frame.WIDTH/2-g.getFontMetrics().stringWidth("Page " + page)/2, Frame.HEIGHT-40);
		for(int i = 0; i < levels.length; i++) {
			g.setColor(Color.black);
			g.drawString("" + (i+(8*(page-1))), levels[i].x+35, levels[i].y+45);
			if(levels[i].contains(Controller.mousePoint)) {
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
			}
			
			g.drawRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
		}
		for(int i = 0; i < levels.length; i++) {
			if(Level.lockedLevels[i+(8*(page-1))] == true) {
				g.drawImage(locked, levels[i].x, levels[i].y, levels[i].width, levels[i].height, null);
			}
		}
		g.drawRect(userLevel.x, userLevel.y, userLevel.width, userLevel.height);
		g.drawString("User Created Levels", Frame.WIDTH/2-g.getFontMetrics().stringWidth("User Created Levels")/2, 410);
		if(userLevel.contains(Controller.mousePoint)) {
			g.setColor(new Color(200, 200, 200, 100));
			g.fillRect(userLevel.x, userLevel.y, userLevel.width, userLevel.height);
			g.setColor(Color.black);
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
			}else {
				g.drawImage(arrow2, arrows[i].x, arrows[i].y, arrows[i].width, arrows[i].height, null);
			}
		}
	}
}
