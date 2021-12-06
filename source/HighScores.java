import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class to manage high scores.
 *
 * @author Tomasz Fijalkowski
 */
public class HighScores {

	/**
	 * Path to a file storing data about scores.
	 */
	private static String FILE_PATH = "resources/highScores.txt";

	/**
	 * Maximum number of saved scores per level.
	 */
	private final static int NUMBER_OF_TOP_SCORES = 10;

	/** 
	 * @param level	level of the game.
	 *
	 * @return array of string containing up to 
	 * 		   ten pairs - (profileName, score)
	 * @throws FileNotFoundException if there is a problem with a file
	 */
	public static String[] getTopScores(int level) throws FileNotFoundException {
		File file = new File(FILE_PATH);
		Scanner in = new Scanner(file);

		String[] topScores = new String[NUMBER_OF_TOP_SCORES];
		while (in.hasNext()) {
			int lvl = in.nextInt();
			int pos = in.nextInt();
			String profName = in.next();
			int scr = in.nextInt();
			if (lvl == level) {
				topScores[pos - 1] = profName + " " + scr;
			}
		}

		in.close();
		System.gc();

		return topScores;
	}

	/**
	 * Method will try to safe a score, if it is in top 10.
	 * @param profile	name of a profile
	 * @param score		achieved score you want to safe
	 * @param level		level of the game
	 * @throws IOException - if there is a problem with a file
	 */
	public static void safeScore(String profile, int score, int level) throws IOException {

		File file = new File(FILE_PATH);
		File tempFile = new File("resources/temp.txt");
		Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		boolean shouldBeAbove = false;
		boolean isUsed = false;
		final int posToFix = 0;

		while (in.hasNext()) {
			int lvl = in.nextInt();
			int pos = in.nextInt();
			String profName = in.next();
			int scr = in.nextInt();

			if (lvl == level) {
				if (score == scr) {
					if (!isUsed) {
						printWriter.println(lvl + " " 
					+ posToFix + " " + profName + " " + scr);
						shouldBeAbove = true;
					}
				} else if (score > scr) {
					if (shouldBeAbove && !isUsed) {
						printWriter.println(level + " " 
							+ posToFix + " " + profile + " " + score);
						printWriter.println(lvl + " " 
							+ posToFix + " " + profName + " " + scr);
						isUsed = true;
					} else if (!shouldBeAbove && !isUsed) {
						printWriter.println(level + " " 
							+ posToFix + " " + profile + " " + score);
						printWriter.println(lvl + " " 
							+ posToFix + " " + profName + " " + scr);
						isUsed = true;
					} else if (!isUsed) {
						printWriter.println(level + " " 
								+ posToFix + " " + profile + " " + score);
						printWriter.println(lvl + " " 
							+ posToFix + " " + profName + " " + scr);
						isUsed = true;
					} else {
						printWriter.println(lvl + " " 
							+ posToFix + " " + profName + " " + scr);
					}
				} else {
					printWriter.println(lvl + " " 
							+ pos + " " + profName + " " + scr);
				}
			} else if (lvl > level && !isUsed) {
				isUsed = true;
				printWriter.println(level + " " 
						+ posToFix + " " + profile + " " + score);
				printWriter.println(lvl + " " 
						+ pos + " " + profName + " " + scr);
			} else {
				printWriter.println(lvl + " " 
						+ pos + " " + profName + " " + scr);
			}
		}

		if (!isUsed) {
			printWriter.println(level + " " 
					+ posToFix + " " + profile + " " + score);
		}

		in.close();
		printWriter.flush();
		printWriter.close();
		file.delete();

		File rename = new File(FILE_PATH);
		tempFile.renameTo(rename);
		fixPositions();
	}

	/**
	 * Method fixing an order of positions.
	 * @throws IOException	if there is a problem with a files
	 */
	private static void fixPositions() throws IOException {
		File file = new File(FILE_PATH);
		File tempFile = new File("resources/temp.txt");
		Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		int previousLevel = 0;
		int previousPos = 0;
		while (in.hasNext()) {
			int lvl = in.nextInt();
			in.nextInt();
			String profName = in.next();
			int scr = in.nextInt();
			if (lvl > previousLevel) {
				previousLevel = lvl;
				previousPos = 1;
				printWriter.println(lvl + " " 
						+ previousPos + " " + profName + " " + scr);
			} else {
				previousPos += 1;
				if (previousPos <= NUMBER_OF_TOP_SCORES) {
					printWriter.println(lvl + " " 
							+ previousPos + " " + profName + " " + scr);
				}
			}

		}

		in.close();
		printWriter.flush();
		printWriter.close();
		file.delete();

		File rename = new File(FILE_PATH);
		tempFile.renameTo(rename);

	}

	/**
	 * Delete every profile score from a file
	 * @param profilName	name of a profile
	 * @throws IOException	if there is a problem with a file
	 */
	public static void deleteProfile(String profilName) throws IOException {

		File file = new File(FILE_PATH);
		File tempFile = new File("resources/temp.txt");
		Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		final int posToFix = 0;

		while (in.hasNext()) {
			int lvl = in.nextInt();
			in.nextInt();
			String profName = in.next();
			int scr = in.nextInt();

			if (!profName.equals(profilName)) {
				printWriter.println(lvl + " " 
						+ posToFix + " " + profName + " " + scr);
			}
		}

		in.close();
		printWriter.flush();
		printWriter.close();
		file.delete();

		File rename = new File(FILE_PATH);
		tempFile.renameTo(rename);
		fixPositions();
	}
	
	public static int getNumberOfScores() {
		return NUMBER_OF_TOP_SCORES;
	}
}
