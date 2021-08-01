/*
 *  2021 (c) aek426rahmani
 * */

package parts;

import java.awt.Image;
import java.awt.Rectangle;

import main.Frame;
import main.ImageLoader;

public class Powerup {

	public int powerup;
	public Rectangle bounds;
	
	private int x, y;
	private int width = 25;
	private int height = 25;
	public boolean remove = false;
	private int speed = 2;
	private Image image;
	
	
	public Powerup(int x, int y, int type) {
		powerup = type;
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, width, height);
		if(powerup == 1) {
			image = new ImageLoader(ImageLoader.PUMultiBall).getImage();
		}
		if(powerup == 2) {
			image = new ImageLoader(ImageLoader.PUGrowth).getImage();
		}
		if(powerup == 3) {
			image = new ImageLoader(ImageLoader.PUFireball).getImage();
		}
	}
	public void tick() {
		if(y < Frame.HEIGHT) {
			y += speed;
			bounds = new Rectangle(x, y, width, height);
		}else {
			remove = true;
		}
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public Image getImage() {
		return image;
	}
}
