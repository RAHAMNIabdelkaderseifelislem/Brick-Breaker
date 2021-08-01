/*
 *  2021 (c) aek426rahmani
 * */

package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Files {
	
	private static String filePath = getDefaultDirectory() + "/BrickBreaker/";
	public static String LEVELPATH = getDefaultDirectory() + "/BrickBreaker/Levels.txt";
	
	public Files() {
		
	}
	public static void init() {
		createDir(filePath);
		createFile(LEVELPATH);
		for(int i = 0; i < 8; i++) {
			createLevel(filePath + "CustomLevel" + i + ".txt");
		}
	}
	
	public static boolean[] readFile(String filePath) {
		boolean[] lockedLevels = new boolean[Level.MAX_LEVEL+1];
		File file = new File(filePath);
		if (file.exists()) {
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			int line = 0;
			while (scanner.hasNextBoolean()) {
				lockedLevels[line] = scanner.nextBoolean();
				line++;
			}
			scanner.close();
			return lockedLevels;
		} else {
			createFile(filePath);
			return lockedLevels;
		}
		
	}

	public static int[][] readLevel(int level) {
		File file = new File(filePath + "CustomLevel" + level + ".txt");
		int[][] grid = new int[8][10];
		if (file.exists()) {
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while(scanner.hasNextLine()) {
		         for (int i=0; i<grid.length; i++) {
		            String[] line = scanner.nextLine().trim().split(" ");
		            for (int j=0; j<line.length; j++) {
		               grid[i][j] = Integer.parseInt(line[j]);
		            }
		         }
		      }
			scanner.close();
//			 System.out.println(Arrays.deepToString(grid));
			return grid;
		} else {
			createFile(filePath + "CustomLevel" + level + ".txt");
			return grid;
		}
		
	}

	public static void SaveProgress(boolean[] scores) {
		deleteFile(filePath + "Levels.txt");
		createFile(filePath + "Levels.txt");
		writeFile(new File(filePath + "Levels.txt"), scores);
	}

	public static void SaveLevel(int level, int[][] grid) {
		deleteFile(filePath + "CustomLevel" + level + ".txt");
		createLevel(filePath + "CustomLevel" + level + ".txt");
		writeLevel(new File(filePath + "CustomLevel" + level + ".txt"), level, grid);
	}
	
	public static void writeLevel(File file, int level, int[][] grid) {

		FileWriter writer;
		try {
			writer = new FileWriter(file);
			for (int i = 0; i < grid.length; i++) { //go by row
				for(int j = 0; j < grid[0].length; j++) {
					writer.write(grid[i][j] + " ");
				}
				writer.write("\n"); //line brake to next row
			}
			System.out.println("Writing to file");
			writer.close();
		} catch (IOException e) {
		}
	}

	public static void createFile(String filePath) {
			File path = new File(filePath);
			if (!path.exists()) {
				try {
					path.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				boolean[] lockedLevels = new boolean[Level.MAX_LEVEL+1];
				for(int i = 1; i < lockedLevels.length; i++) {
					lockedLevels[i] = true;
				}
				lockedLevels[0] = false;
				writeFile(path, lockedLevels);
		}
	}
	public static void createLevel(String filePath) {
		File path = new File(filePath);
		if (!path.exists()) {
			try {
				path.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			int[][] grid = new int[8][10];
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[i].length; j++) {
					grid[i][j] = 0;
				}
			}
			writeLevel(path, grid);
		}
	}

	public static void createDir(String filePath) {
		File path = new File(filePath);
		if (!path.exists()) {
			path.mkdir();
		}
	}

	public static void writeFile(File file, boolean[] lockedLevels) {
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			for (int i = 0; i < lockedLevels.length; i++) {
				writer.write(lockedLevels[i] + "\n");
			}
			System.out.println();
			writer.close();
		} catch (IOException e) {
		}
	}
	public static void writeLevel(File file, int[][] grid) {
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			for (int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[i].length; j++) {
					writer.write("" + grid[i][j] + " ");
				}
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
		}
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	public static String getDefaultDirectory() {
		// Saves to either %appdata% on win or Application Support on mac
		String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN")) {
			return System.getenv("APPDATA");
		}
		if (OS.contains("MAC")) {
			return System.getProperty("user.home") + "/Library/Application Support";
		}
		return System.getProperty("user.home");
	}
}
