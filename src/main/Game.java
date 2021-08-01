/*
 *  2021 (c) aek426rahmani
 * */

package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import files.Files;
import files.Level;
import handlers.KeyHandler;
import parts.Ball;
import parts.Brick;
import parts.Paddle;
import parts.Powerup;

public class Game extends JPanel{
	private static final long serialVersionUID = 1L;
	
	
	private Paddle paddle;
	private Ball[] balls;
	private Brick[] bricks;
	private Image lives;
	
	private Powerup[] powerups;
	private int[][] gridPos = new int[8][10];
 	private int score = 0;
 	private boolean hasStarted = false;
 	private boolean debug = false;
 	private int livesLeft = 2;
 	private int totalBallCount = 1;
 	private int totalBricks = 0;
 	private int level;
 	
	public Game(int level) {
		paddle = new Paddle(Frame.WIDTH/2-50, 400);
		balls = new Ball[3];
		balls[0] = new Ball(paddle.getX()+paddle.width/2-12, paddle.getY()-paddle.height/2-10, false);
		totalBallCount = 1;
		this.level = level;
		gridPos = Level.getLevel(level);
		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				if(gridPos[i][j] != 0) {
					totalBricks++;
				}
			}
		}
		init();
	}
	public void init() {
		int count = 0;
		lives = new ImageLoader(ImageLoader.ball).getImage();
		bricks = new Brick[80];//60 is max
		powerups = new Powerup[5];
		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				bricks[count] = new Brick(j*50, i*25, gridPos[i][j]);
				count++;
			}
		}
	}
	public void tick() {
		if(hasStarted) {
			for(int i = 0; i < balls.length; i++) {
				if(balls[i] != null) {
					balls[i].tick();
					balls[i].checkBrickCollision(bricks);
					balls[i].checkPaddleCollision(paddle);
					if(balls[i].getY() > Frame.HEIGHT-50) {
						if(totalBallCount <= 1) {
						hasStarted = false;
						balls[i] = new Ball(paddle.getX()+paddle.width/2-12, paddle.getY()-paddle.height/2-10, false);
						livesLeft -= 1;
							if(livesLeft < 0) { //game over
								Controller.score = score;
								Controller.switchStates(Controller.STATE.GAMEOVER);
							}
						}else {
							balls[i] = null;
							totalBallCount--;
						}
					}
				}
			}
			for(int i = 0; i < bricks.length; i++) {
				if(bricks[i].hasDied) {
					totalBricks--;
					score += bricks[i].originalLevel;
					if(totalBricks == 0) {
						Controller.score = score;
						Controller.switchStates(Controller.STATE.WINSCREEN);
					}
					bricks[i].hasDied = false;
				}
				if(bricks[i].dropPowerup) {
					for(int j = 0; j < powerups.length; j++) {
						if(powerups[j] == null) {
							powerups[j] = new Powerup(bricks[i].getX(), bricks[i].getY(), bricks[i].hasPowerup());
							bricks[i].dropPowerup = false;
							break;
						}
					}
				}
				bricks[i].dropPowerup = false;
			}
			for(int i = 0; i < powerups.length; i++) {
				if(powerups[i] != null) {
					powerups[i].tick();
					if(powerups[i].remove) {
						powerups[i] = null;
					}
					if(paddle.isColliding(powerups[i])) {	
						if(powerups[i].powerup == 1) { //Add another ball
							for(int j = 0; j < balls.length; j++) {
								if(balls[j] == null) {
									balls[j] = new Ball(Frame.WIDTH/2, 350, false);
									totalBallCount++;
									break;
								}
							}
						}
						if(powerups[i].powerup == 2) { //grow paddle
							paddle.width += 15;
						}
						if(powerups[i].powerup == 3) { //fire ball
							for(int j = 0; j < balls.length; j++) {
								if(balls[j] != null) {
									balls[j].setOnFire(4);
									break;
								}
							}
						}
						powerups[i] = null;
					}
				}
			}
		}else if(!hasStarted) {
			for(int i = 0; i < balls.length; i++) {
				if(balls[i] != null) {
					balls[i].setX(paddle.getX()+paddle.width/2-balls[i].width/2);
				}
			}
			for(int i = 0; i < powerups.length; i++) {
				if(powerups[i] != null) {
					powerups[i] = null;
				}
			}
			if(KeyHandler.UP) {
				hasStarted = true;
			}
		}
		if(KeyHandler.LEFT) {
			paddle.moveLeft();
		}
		if(KeyHandler.RIGHT) {
			paddle.moveRight();
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, 500, 500);
		int count = 0;
		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				if(gridPos[i][j] > 0) {
					g.drawImage(bricks[count].getImage(), bricks[count].getX(), bricks[count].getY(), null);
				}	
				count++;
			}
		}
		for(int i = 0; i < powerups.length; i++) {
			if(powerups[i] != null) {
					g.drawImage(powerups[i].getImage(), powerups[i].getX(), powerups[i].getY(), powerups[i].getWidth(), powerups[i].getWidth(), null);
			}
		}
		
		g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), null);
		for(int i = 0; i < balls.length; i++) {
			if(balls[i] != null) {
				if(!balls[i].onFire) {		
					g.drawImage(balls[i].getImage(), balls[i].getX(), balls[i].getY(), null);
				}else {
					g.drawImage(balls[i].getImage(), balls[i].getX(), balls[i].getY(), null);
				}
			}
		}
		
		//Debugging
		g.setColor(Color.WHITE);
		if(debug) {
			for(int i = 0; i < bricks.length; i++) {
				bricks[i].render(g);
			}
		}
		//End debugging
		g.setColor(Color.yellow);
		g.fillRect(0, Frame.HEIGHT-50, Frame.WIDTH, 50);
		g.setColor(Color.white);
		g.setFont(Controller.smallFont);
		g.drawString("Score: " + score, 20, Frame.HEIGHT-32);
		g.drawImage(lives, 100, Frame.HEIGHT-45, 15, 15, null);
		g.drawString(": " + livesLeft, 120, Frame.HEIGHT-32);
	}
}
