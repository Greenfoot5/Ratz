import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class to manage high scores.
 * @author Tomasz Fijalkowski
 * 
 * Need file : "resources/highScores.txt" to work properly
 */
public class HighScores {
	
	// Attributes handling reading and changing files
//	private Scanner in = null;
	private String filePath = "resources/highScores.txt";
//	private File file = null;
//	BufferedWriter bufferWriter = null;
//	FileWriter fileWriter = null;
//	PrintWriter printWriter = null;

	public HighScores() {
	}

	/** 
	 * @param level - level of the game
	 * @return array of string containing up to ten pairs - (profileName, score)
	 * @throws FileNotFoundException if there is a problem with a file
	 */
	public String[] getTopScores(int level) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner in = new Scanner(file);

		String[] topScores = new String[10];
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
	 * @param profile - profile name
	 * @param score - achieved score
	 * @param level - level of the game
	 * @throws IOException - if there is a problem with a file
	 */
	public void safeScore(String profile, int score, int level) throws IOException {

		File file = new File(filePath);
		File tempFile = new File("resources/temp.txt");
		Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, true);
		//bufferWriter = new BufferedWriter(fileWriter);
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
						printWriter.println(lvl + " " + posToFix + " " + profName + " " + scr);
						shouldBeAbove = true;
					}
				} else if (score > scr) {
					if (shouldBeAbove && !isUsed) {
						printWriter.println(level + " " + posToFix + " " + profile + " " + score);
						printWriter.println(lvl + " " + posToFix + " " + profName + " " + scr);
						isUsed = true;
					} else if (!shouldBeAbove && !isUsed) {
						printWriter.println(level + " " + posToFix + " " + profile + " " + score);
						printWriter.println(lvl + " " + posToFix + " " + profName + " " + scr);
						isUsed = true;
					} else if (!isUsed) {
						printWriter.println(level + " " + posToFix + " " + profile + " " + score);
						printWriter.println(lvl + " " + posToFix + " " + profName + " " + scr);
						isUsed = true;
					} else {
						printWriter.println(lvl + " " + posToFix + " " + profName + " " + scr);
					}
				} else {
					printWriter.println(lvl + " " + pos + " " + profName + " " + scr);
				}
			} else if (lvl > level && !isUsed) {
				isUsed = true;
				printWriter.println(level + " " + posToFix + " " + profile + " " + score);
				printWriter.println(lvl + " " + pos + " " + profName + " " + scr);
			} else {
				printWriter.println(lvl + " " + pos + " " + profName + " " + scr);
			}
		}
		
		if (!isUsed) {
			printWriter.println(level + " " + posToFix + " " + profile + " " + score);
		}

		in.close();
		printWriter.flush();
		printWriter.close();
		file.delete();

		File rename = new File(filePath);
		tempFile.renameTo(rename);
		fixPositions();
	}

	/**
	 * Method fixing an order of positions.
	 * @throws IOException - if there is a problem with a files
	 */
	private void fixPositions() throws IOException {
		File file = new File(filePath);
		File tempFile = new File("resources/temp.txt");
		Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, true);
		//bufferWriter = new BufferedWriter(fileWriter);
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
				printWriter.println(lvl + " " + previousPos + " " + profName + " " + scr);
			} else {
				previousPos += 1;
				if (previousPos <= 10) {
					printWriter.println(lvl + " " + previousPos + " " + profName + " " + scr);
				}
			}

		}

		in.close();
		printWriter.flush();
		printWriter.close();
		file.delete();
		// file = tempFile;

		File rename = new File(filePath);
		// rename.createNewFile();
		tempFile.renameTo(rename);

	}
}
