/*
 *  2021 (c) aek426rahmani
 * */

package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import files.Files;
import handlers.MouseHandler;
import parts.Brick;

public class CreateLevel {

	private Brick mouseBrick;
	private Brick[] bricks;
	private Brick[] shopBricks = new Brick[7];
	private Rectangle[] gameOptions = { new Rectangle(5, 365, 75, 30), new Rectangle(5, 415, 50, 50),
			new Rectangle(90, 440, 30, 30), new Rectangle(150, 440, 30, 30) };
	private Image paintBucket;
	private Image eraser;
	private Image backArrow;

	private int[][] gridPos = new int[8][10];

	private int brickCount = 0;
	private int level;

	private boolean isErasing = false;
	private boolean isPainting = false;

	public CreateLevel(int level) {
		this.level = level;
		paintBucket = new ImageLoader(ImageLoader.PaintBucket).getImage();
		eraser = new ImageLoader(ImageLoader.Eraser).getImage();
		backArrow = new ImageLoader(ImageLoader.arrow).getImage();
		bricks = new Brick[80];
		gridPos = Files.readLevel(level);
		for (int i = 0; i < gridPos.length; i++) {
			for (int j = 0; j < gridPos[0].length; j++) {
				bricks[brickCount] = new Brick(j * 50, i * 25, gridPos[i][j]);
				brickCount++;
			}
		}
		int prevX = 80;
		for (int i = 0; i < shopBricks.length; i++) {
			shopBricks[i] = new Brick(prevX, 410, i + 1);
			prevX += shopBricks[0].getWidth() + 7;
		}
	}

	public void tick() {
		if (mouseBrick != null) {
			if (Controller.mousePoint.y > (shopBricks.length + 1) * shopBricks[0].getHeight()) {
				mouseBrick.setX(Controller.mousePoint.x - mouseBrick.getWidth() / 2);
				mouseBrick.setY(Controller.mousePoint.y - mouseBrick.getHeight() / 2);
			} else {
				mouseBrick.setX((Controller.mousePoint.x / mouseBrick.getWidth()) * mouseBrick.getWidth());
				mouseBrick.setY((Controller.mousePoint.y / mouseBrick.getHeight()) * mouseBrick.getHeight());
			}
		}
		if (MouseHandler.MOUSEDOWN) {
			if (mouseBrick != null) {
				paintBricks();
			}
			for (int i = 0; i < shopBricks.length; i++) {
				if (Controller.mousePoint.x > shopBricks[i].getX()
						&& Controller.mousePoint.x < shopBricks[i].getX() + shopBricks[i].getWidth()) {
					if (Controller.mousePoint.y > shopBricks[i].getY()
							&& Controller.mousePoint.y < shopBricks[i].getY() + shopBricks[i].getHeight()) {
						mouseBrick = new Brick(Controller.mousePoint.x, Controller.mousePoint.y, shopBricks[i].level);
						isErasing = false;
					}
				}
			}
			if (gameOptions[0].contains(Controller.mousePoint)) {
				// Play the game
				Files.SaveLevel(level, gridPos);
				Controller.switchStates(Controller.STATE.GAME, level + 25);
			}
			if (gameOptions[1].contains(Controller.mousePoint)) {
				// Go back
				Files.SaveLevel(level, gridPos);
				Controller.switchStates(Controller.STATE.PICKUSERLEVEL);
			}
			if (gameOptions[2].contains(Controller.mousePoint)) {
				// Paint Bucket
				isPainting = !isPainting;
				isErasing = false;
			}
			if (gameOptions[3].contains(Controller.mousePoint)) {
				// Eraser
				mouseBrick = new Brick(Controller.mousePoint.x, Controller.mousePoint.y, 0);
				isErasing = !isErasing;
				isPainting = false;
			}
			MouseHandler.MOUSEDOWN = false;
		}
	}

	public void paintBricks() {

		if (mouseBrick.getY() < (shopBricks.length + 1) * shopBricks[0].getHeight()-1) {
			if (isPainting) {
				for (int i = 0; i < gridPos.length; i++) {
					for (int j = 0; j < gridPos[i].length; j++) {
						gridPos[i][j] = mouseBrick.level;
						bricks[j + (i*10)] = new Brick(j*mouseBrick.getWidth(),i*mouseBrick.getHeight(), mouseBrick.level);
					}
				}
			} else {
				int tempX = mouseBrick.getX() / mouseBrick.getWidth() % 10;
				int tempY = mouseBrick.getY() / mouseBrick.getHeight() % 8;
				System.out.println("xPos: " + tempX + "\nYPos: " + tempY + "\n");
				if (isErasing) {
					gridPos[tempY][tempX] = 0;
					bricks[tempX + (tempY * 10)] = new Brick(
							(Controller.mousePoint.x / mouseBrick.getWidth()) * mouseBrick.getWidth(),
							(Controller.mousePoint.y / mouseBrick.getHeight()) * mouseBrick.getHeight(), 0);
				} else {
					gridPos[tempY][tempX] = mouseBrick.level;
					bricks[tempX + (tempY * 10)] = new Brick(
							(Controller.mousePoint.x / mouseBrick.getWidth()) * mouseBrick.getWidth(),
							(Controller.mousePoint.y / mouseBrick.getHeight()) * mouseBrick.getHeight(),
							mouseBrick.level);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);

		// Draw Brick Grid
		g.setColor(Color.white);
		for (int i = 0; i < gridPos.length; i++) {
			for (int j = 0; j < gridPos[0].length; j++) {
				g.drawRect(j * bricks[0].getWidth(), i * bricks[0].getHeight(), bricks[0].getWidth(),
						bricks[0].getHeight());
			}
		}
		// Draw Pieces
		int count = 0;
		for (int i = 0; i < gridPos.length; i++) {
			for (int j = 0; j < gridPos[0].length; j++) {
				if (gridPos[i][j] > 0) {
					g.drawImage(bricks[count].getImage(), bricks[count].getX(), bricks[count].getY(), null);
				}
				count++;
			}
		}
		// End Draw Brick Grid

		// Draw Shop Background
		g.fillRect(0, Frame.HEIGHT - 100, Frame.WIDTH, 100);
		g.setColor(Color.WHITE);
		g.drawString("Play!", gameOptions[0].x + 10, gameOptions[0].y + 25);
		g.setColor(Color.black);
		for (int i = 0; i < gameOptions.length; i++) {
			if (gameOptions[i].contains(Controller.mousePoint)) {
				g.drawRect(gameOptions[i].x, gameOptions[i].y, gameOptions[i].width, gameOptions[i].height);
			}
		}
		g.drawImage(backArrow, gameOptions[1].x, gameOptions[1].y, gameOptions[1].width, gameOptions[1].height, null);
		g.drawImage(paintBucket, gameOptions[2].x, gameOptions[2].y, gameOptions[2].width, gameOptions[2].height, null);
		g.drawImage(eraser, gameOptions[3].x, gameOptions[3].y, gameOptions[3].width, gameOptions[3].height, null);
		for (int i = 0; i < shopBricks.length; i++) {
			g.drawImage(shopBricks[i].getImage(), shopBricks[i].getX(), shopBricks[i].getY(), shopBricks[i].getWidth(),
					shopBricks[i].getHeight(), null);
		}
		// Selected eraser/paint bucket
		if (isPainting) {
			g.drawRect(gameOptions[2].x, gameOptions[2].y, gameOptions[2].width, gameOptions[2].height);
		}
		if (isErasing) {
			g.drawRect(gameOptions[3].x, gameOptions[3].y, gameOptions[3].width, gameOptions[3].height);
		}

		// Draw mouse
		if (mouseBrick != null) {
			g.drawImage(mouseBrick.getImage(), mouseBrick.getX(), mouseBrick.getY(), mouseBrick.getWidth(),
					mouseBrick.getHeight(), null);
		}
	}
}
