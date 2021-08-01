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

public class WinScreen {

	private Rectangle mainMenu;
	private Image background;
	
	private boolean unlockedNewLevel = false;
	
	public WinScreen() {
		mainMenu = new Rectangle(Frame.WIDTH/2-60, 380, 150, 55);
		background = new ImageLoader(ImageLoader.titleScreenBackground).getImage();
		if(Controller.level+1 < Level.MAX_LEVEL && Level.lockedLevels[Controller.level+1]) {
			unlockedNewLevel = true;
		}
		if(Controller.level < Level.MAX_LEVEL-1) {
			if(Level.lockedLevels[Controller.level+1] == true) {
				Level.lockedLevels[Controller.level+1] = false;
				Files.SaveProgress(Level.lockedLevels);
			}
		}
	}
	public void tick() {
		if(mainMenu.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
			Controller.switchStates(Controller.STATE.MENU);
		}
	}
	public void render(Graphics g) {
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		g.setColor(Color.BLACK);
		g.setFont(Controller.bigFont);
		g.drawString("You Win!!", Frame.WIDTH/2-g.getFontMetrics().stringWidth("You Win!!")/2, 150);
		g.drawString("Score: " + Controller.score, Frame.WIDTH/2-g.getFontMetrics().stringWidth("Score" + Controller.score)/2, 225);
		if(unlockedNewLevel) {
			g.setColor(Color.orange);
			g.drawString("New Level Unlocked!!", Frame.WIDTH/2-g.getFontMetrics().stringWidth("New Level Unlocked!!")/2, 285);
			g.setColor(Color.black);
		}
		g.drawRect(mainMenu.x, mainMenu.y, mainMenu.width, mainMenu.height);
		if(mainMenu.contains(Controller.mousePoint)) {
			g.setColor(new Color(200, 200, 200, 100));
			g.fillRect(mainMenu.x, mainMenu.y, mainMenu.width, mainMenu.height);
			g.setColor(Color.black);
		}
		g.drawString("Main Menu", mainMenu.x + 15, mainMenu.y + 30);
	}
}
