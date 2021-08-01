/*
 *  2021 (c) aek426rahmani
 * */

package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	private BufferedImage img;
	public static String titleForeground = "images\\BrickBreakerForeground.png";
	public static String titleScreenBackground = "images\\BrickBreakerBackground.png";
	public static String paddle = "images\\Paddle.png";
	public static String brick = "images\\Bricks.png";
	public static String ball = "images\\Ball.png";
	public static String fireBall = "images\\FireBall.png";
	public static String arrow = "images\\Arrow.png";
	public static String arrow2 = "images\\Arrow2.png";
	public static String locked = "images\\locked.png";
	public static String PUMultiBall = "images\\PUMulitBall.png";
	public static String PUGrowth = "images\\PUGrowth.png";
	public static String PUFireball = "images\\PUFireball.png";
	public static String PaintBucket = "images\\PaintBucket.png";
	public static String Eraser = "images\\Eraser.png";
	
	public ImageLoader(String path) {
		try {
			img = ImageIO.read(new File(path));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BufferedImage getImage() {
		return img;
	}
	public BufferedImage getSubImage(int section) {
		return img.getSubimage(0, section*25, 50, 25);
	}
}
