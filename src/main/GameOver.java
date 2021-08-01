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

public class GameOver {

	private Rectangle mainMenu;
	private Image background;
	
	public GameOver() {
		mainMenu = new Rectangle(Frame.WIDTH/2-60, 380, 150, 55);
		background = new ImageLoader(ImageLoader.titleScreenBackground).getImage();
	}
	public void tick() {
		if(mainMenu.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
			Controller.switchStates(Controller.STATE.MENU);
		}
	}
	public void render(Graphics g) {
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		g.setColor(Color.black);
		g.setFont(Controller.bigFont);
		g.drawString("You Lose", Frame.WIDTH/2-g.getFontMetrics().stringWidth("You Lose")/2, 150);
		g.drawString("Score: " + Controller.score, Frame.WIDTH/2-g.getFontMetrics().stringWidth("Score" + Controller.score)/2, 225);
		g.drawRect(mainMenu.x, mainMenu.y, mainMenu.width, mainMenu.height);
		if(mainMenu.contains(Controller.mousePoint)) {
			g.setColor(new Color(255, 255, 255, 150));
			g.fillRect(mainMenu.x, mainMenu.y, mainMenu.width, mainMenu.height);
			g.setColor(Color.BLACK);
		}
		g.drawString("Main Menu", mainMenu.x + 15, mainMenu.y + 30);
	}
}
