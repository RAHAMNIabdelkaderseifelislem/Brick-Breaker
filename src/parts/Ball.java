/*
 *  2021 (c) aek426rahmani
 * */

package parts;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import main.Frame;
import main.ImageLoader;

public class Ball{

		ImageLoader loader;
		
		public int width = 23;
		public int height = 23;
		
		private int x;
		private int y;
		private double dx = 0.25;
		private double dy = -6;
		private int dir = 0;
		
		private int speed = 5;
		
		public Rectangle bounds;
		private Random rand;
		private Timer timer;
		
		public boolean onFire = false;
		private int fireSec = 0;
		
		
		public Ball(int x, int y, boolean onFire) {
			this.x = x;
			this.y = y;
			this.onFire = onFire;
			rand = new Random();
			this.speed = speed;
			dx = 0;
			bounds = new Rectangle(x, y, width, height);
			loader = new ImageLoader(ImageLoader.ball);
			timer = new Timer();
		}
		public Image getImage() {
			return loader.getImage();
		}
		public void setX(int pos) {
			if(pos > 0 && pos < Frame.WIDTH) {
				this.x = pos;
			}
		}
		public void setY(int pos) {
			if(pos > 0 && pos < Frame.WIDTH) {
				this.y = pos;
			}
		}
		public int getX() {
			return this.x;
		}
		public int getY() {
			return this.y;
		}
		public void setOnFire(int seconds) {
			if(!onFire) {
				fireSec = seconds;
				onFire = true;
		        timer.schedule(new RemindTask(), seconds*1000);
		        loader = new ImageLoader(ImageLoader.fireBall);
			}
		}
		class RemindTask extends TimerTask {
	        public void run() {
	        	onFire = false;
	        	loader = new ImageLoader(ImageLoader.ball);
	        }
	    }
		public void tick() {
	            if (x + dx < 0 || x + width + dx > Frame.WIDTH) {
	                dx *= -1;
	            }
	            if (y + dy < 0 || y + height + dy > Frame.HEIGHT) {
	            	dy *= -1;
	            	if(dx == 0) {
	            		dx = 1.5;
	            	}
	            }
	            x += dx;
	            y += dy;
	            bounds = new Rectangle(x, y, width, height);
	            
		}
		public void checkBrickCollision(Brick[] bricks) {
				for(int i = 0; i < bricks.length; i++) {
					if(bricks[i].level > 0) {
						if(bounds.intersects(bricks[i].bounds)) {
							if(!onFire) {
							switchDirections(bricks[i]);
							bricks[i].hasCollided();
							}else {
								bricks[i].destroyed();
							}
						}
					}
				}
		}
		public void checkPaddleCollision(Paddle paddle) {
			if(bounds.intersects(paddle.bounds)) {
				hitPaddle(paddle);
			}
		}
		public void switchDirections(Brick brick) {
			int random = rand.nextInt(3);
			double randomSpeed = rand.nextInt(3);
			if(random == 1) {
				if(dx < 10) {
					dx += randomSpeed;
				}else {
					dx -= randomSpeed;
				}
			}
			if(random == 2) {
				if(dx > -10) {
					dx += randomSpeed;
				}else {
					dx -= randomSpeed;
				}
			}
				if (x + dx < brick.getX() || x + width + dx > brick.getX()+brick.getWidth()) {
	                dx *= -1;
	            }
	            if (y + dy < brick.getY() || y + height + dy > brick.getY()+brick.getHeight()) {
	                dy *= -1;
	            }
	            x += dx;
	            y += dy;
	            bounds = new Rectangle(x, y, width, height);
			
		}
		public void hitPaddle(Paddle paddle) {
			if(y+height-5 < paddle.getY()) {
				if (x + dx < paddle.getX() || x + width + dx > paddle.getX()+paddle.getWidth()) {
	                dx *= -1;
	            }
	            if (y + dy < paddle.getY() || y + height + dy > paddle.getY()+ paddle.getHeight()) {
	                dy *= -1;
	            }
			}
            x += dx;
            y += dy;
		}
		public void render(Graphics g) {
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
}
