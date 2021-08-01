/*
 *  2021 (c) aek426rahmani
 * */

package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import handlers.MouseHandler;
import parts.Ball;
import parts.Brick;
import parts.Paddle;

public class MainMenu {

	private Rectangle[] bounds = {new Rectangle(65, 295, 210, 45),
								  new Rectangle(65, 345, 210, 45)};
	private Image titleScreenForeground;
	private Image titleScreenBackground;
	private Paddle paddle;
	private Ball ball;
	private Brick[] bricks;
	
	private int[][] gridPos =  {{7, 7, 7, 7, 7, 7, 7, 7},
								{6, 6, 6, 6, 6, 6, 6, 6},
								{5, 5, 5, 5, 5, 5, 5, 5},
								{4, 4, 4, 4, 4, 4, 4, 4},
								{3, 3, 3, 3, 3, 3, 3, 3},
								{2, 2, 2, 2, 2, 2, 2, 2}};
	
	public MainMenu() {
		titleScreenForeground = new ImageLoader(ImageLoader.titleForeground).getImage();
		titleScreenBackground = new ImageLoader(ImageLoader.titleScreenBackground).getImage();
		paddle = new Paddle(Frame.WIDTH/2-50, 400);
		ball = new Ball(paddle.getX()+paddle.width/2-12, paddle.getY()-paddle.height/2-10, false);
		bricks = new Brick[48];//60 is max
		int count = 0;
		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				bricks[count] = new Brick(j*50+50, i*25+50, gridPos[i][j]);
				count++;
			}
		}
	}
	public void tick() {
		for(int i = 0; i < bounds.length; i++) {
			if(bounds[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) {
					Controller.switchStates(Controller.STATE.PICKLEVEL);
				}else {
					visitSite();
				}
			}
		}
		tickGame();
	}
	public void visitSite() {
		try {
			 Desktop desktop = java.awt.Desktop.getDesktop();
			  URI oURL = new URI("https://aek426rahmani.000webhostapp.com");
			  desktop.browse(oURL);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	private void tickGame() {
			ball.tick();
			ball.checkBrickCollision(bricks);
			ball.checkPaddleCollision(paddle);
			if(ball.getY() > Frame.HEIGHT-50) {
				ball = new Ball(paddle.getX()+paddle.width/2-12, paddle.getY()-paddle.height/2-10, false);
			}
		if((ball.getX() < paddle.getX()+paddle.getWidth()/2) && paddle.getX() > 50) {
			paddle.moveLeft();
		}
		if((ball.getX() > paddle.getX()+paddle.getWidth()/2) && paddle.getX()+paddle.getWidth() < 450) {
			paddle.moveRight();
		}
	}
	public void render(Graphics g) {
		g.drawImage(titleScreenBackground, 0, 0, Frame.WIDTH, Frame.WIDTH, null);
		g.setColor(Color.black);
		for(int i = 0; i < bounds.length; i++) {
			if(bounds[i].contains(Controller.mousePoint)) {
				g.drawRect(bounds[i].x, bounds[i].y, bounds[i].width, bounds[i].height);
			}
		}
		int count = 0;
		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				if(gridPos[i][j] > 0) {
					g.drawImage(bricks[count].getImage(), bricks[count].getX(), bricks[count].getY(), null);
				}	
				count++;
			}
		}
		g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), null);
		g.drawImage(ball.getImage(), ball.getX(), ball.getY(), null);
		g.drawImage(titleScreenForeground, 0, 0, Frame.WIDTH, Frame.WIDTH, null);
	}
}
