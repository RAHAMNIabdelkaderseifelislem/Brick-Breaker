/*
 *  2021 (c) aek426rahmani
 * */

package main;

import javax.swing.JFrame;

public class Frame {

	private static JFrame frame;
	
	public static int WIDTH = 500;
	public static int HEIGHT = 500;
	
	public static void main(String[] args) {
		frame = new JFrame("Brick Breaker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Controller());
		frame.pack();	
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}
