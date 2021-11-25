import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class to manage profiles and their best scores.
 * @author Tomasz Fijalkowski
 * 
 * Please update number of levels!!!!
 * Need file : "resources/profileFile.txt" to work properly
 */
public class ProfileFileReader {

	private final static int NUMBER_OF_LEVELS = 10;
	private Scanner in = null;
	private String filePath = "resources/profileFile.txt";
	private File file = null;
	BufferedWriter bufferWriter = null;
	FileWriter fileWriter = null;
	PrintWriter printWriter = null;

	public ProfileFileReader() {
	}

	/**
	 * Create new profile in text file with chosen name.
	 * @param profileName
	 * @throws Exception - if there is a problem with a file or name is already used
	 */
	public void createNewProfile(String profileName) throws Exception {
		file = new File(filePath);
		File tempFile = new File("resources/tempProf.txt");
		in = new Scanner(file);
		fileWriter = new FileWriter(tempFile, true);
		bufferWriter = new BufferedWriter(fileWriter);
		printWriter = new PrintWriter(bufferWriter);

		int lastProfNumber = 1;
		boolean isAlreadyUsedName = false;

		while (in.hasNext()) {
			int profNumber = in.nextInt();
			String profName = in.next();

			if (profName.equals(profileName)) {
				isAlreadyUsedName = true;
			}

			lastProfNumber = profNumber + 1;

			printWriter.println(profNumber + " " + profName);

			for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
				int lvl = in.nextInt();
				int scr = in.nextInt();

				printWriter.print(lvl + " " + scr + " ");
			}
			printWriter.println();

		}

		if (!isAlreadyUsedName) {
			printWriter.println(lastProfNumber + " " + profileName);

			for (int i = 1; i <= NUMBER_OF_LEVELS; i++) {
				printWriter.print(i + " " + "0 ");
			}
		} else {
			throw new IllegalArgumentException("Profile name is already used");
		}

		in.close();
		printWriter.flush();
		printWriter.close();
		file.delete();

		File rename = new File(filePath);
		tempFile.renameTo(rename);

	}

	/**
	 * Remove profile from the txt file. If name is not in a file then does nothing.
	 * @param profileName 
	 * @throws IOException - if there is a problem with a file
	 */
	public void deleteProfile(String profileName) throws IOException {
		file = new File(filePath);
		File tempFile = new File("resources/tempProf.txt");
		in = new Scanner(file);
		fileWriter = new FileWriter(tempFile, true);
		bufferWriter = new BufferedWriter(fileWriter);
		printWriter = new PrintWriter(bufferWriter);

		boolean isRemoved = false;
		while (in.hasNext()) {
			int profNumber = in.nextInt();
			String profName = in.next();

			if (!profName.equals(profileName)) {
				if (isRemoved) {
					printWriter.println((profNumber - 1) + " " + profName);
				} else {
					printWriter.println(profNumber + " " + profName);
				}

				for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
					int lvl = in.nextInt();
					int scr = in.nextInt();

					printWriter.print(lvl + " " + scr + " ");
				}
				printWriter.println();
			} else {
				for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
					int lvl = in.nextInt();
					int scr = in.nextInt();
				}
				isRemoved = true;
			}
		}

		in.close();
		printWriter.flush();
		printWriter.close();
		file.delete();

		File rename = new File(filePath);
		tempFile.renameTo(rename);
	}

	/**
	 * Return best profile score for the specified level
	 * @param profileName
	 * @param level 
	 * @return - best player score
	 * @throws IOException - if there is a problem with a file
	 */
	public int getBestScore(String profileName, int level) throws IOException {
		file = new File(filePath);
		in = new Scanner(file);

		int bestScore = 0;
		while (in.hasNext()) {
			int profNumber = in.nextInt();
			String profName = in.next();

			for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
				int lvl = in.nextInt();
				int scr = in.nextInt();
				if (profName.equals(profileName) && level == lvl) {
					bestScore = scr;
				}
			}
		}
		return bestScore;
	}

	/**
	 * Save score if it is new best score for specified level
	 * @param profileName
	 * @param level
	 * @param score - score you want to safe
	 * @throws IOException - if there is a problem with a file
	 */
	public void saveBestScore(String profileName, int level, int score) throws IOException {
		file = new File(filePath);
		File tempFile = new File("resources/tempProf.txt");
		in = new Scanner(file);
		fileWriter = new FileWriter(tempFile, true);
		bufferWriter = new BufferedWriter(fileWriter);
		printWriter = new PrintWriter(bufferWriter);

		while (in.hasNext()) {
			int profNumber = in.nextInt();
			String profName = in.next();

			printWriter.println(profNumber + " " + profName);

			for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
				int lvl = in.nextInt();
				int scr = in.nextInt();

				if (profName.equals(profileName) && level == lvl && score > scr) {
					printWriter.print(lvl + " " + score + " ");
				} else {
					printWriter.print(lvl + " " + scr + " ");
				}
			}
			printWriter.println();
		}

		in.close();
		printWriter.flush();
		printWriter.close();
		file.delete();

		File rename = new File(filePath);
		tempFile.renameTo(rename);
	}

	// i'am not sure what it is going to do, probably should be moved to manager
	public void loginProfile(String profileName) {

	}
}
