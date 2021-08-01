/*
 *  2021 (c) aek426rahmani
 * */

package parts;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import main.Controller;
import main.Frame;
import main.ImageLoader;

public class Brick {

		ImageLoader loader;
		
		private int width = 50;
		private int height = 25;
		
		private int x;
		private int y;
		public int level;
		public int originalLevel;
		private boolean addScore = false;
		public boolean dropPowerup = false;
		public boolean hasDied = false;
		public Rectangle bounds;
		
		private Random rand;
		private int powerup = 0;
		
		
		public Brick(int x, int y, int level) {
			this.x = x;
			this.y = y;
			originalLevel = level;
			this.level = level;
			rand = new Random();
			if(level > 0) {
				powerup = rand.nextInt(6);
			}
			bounds = new Rectangle(x, y, width, height);
			loader = new ImageLoader(ImageLoader.brick);
		}
		public Image getImage() {
			return loader.getSubImage(level);
		}
		public void setX(int pos) {
			if(pos >= 0 && pos < Frame.WIDTH) {
				this.x = pos;
			}
		}
		public void setY(int pos) {
			if(pos >= 0 && pos < Frame.HEIGHT) {
				this.y = pos;
			}
		}
		public int getX() {
			return this.x;
		}
		public int getY() {
			return this.y;
		}
		public int getWidth() {
			return this.width;
		}
		public int getHeight() {
			return this.height;
		}
		public void hasCollided() {
			if(level >= 1) {
				level--;
				if(level == 0) {
					hasDied = true;
					if(hasPowerup() > 0) {
						dropPowerup = true;
					}
				}
			}
		}
		public void destroyed(){
			level = 0;
			hasDied = true;
			if(hasPowerup() > 0) {
				dropPowerup = true;
			}
		}
		public int hasPowerup() {
			return powerup;
		}
		public void render(Graphics g) {
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
}
