import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class to manage profiles and their best scores.
 * 
 * @author Tomasz Fijalkowski
 * 
 *         Please update number of levels!!!! Need file :
 *         "resources/profileFile.txt" to work properly
 */
public class ProfileFileReader {

	static private String selectedProfile = null;

	private final static int NUMBER_OF_LEVELS = 5;
	// private Scanner in = null;
	private final static String FILE_PATH = "resources/profileFile.txt";
	// private File file = null;
	// BufferedWriter bufferWriter = null;
	// FileWriter fileWriter = null;
	// PrintWriter printWriter = null;

	/**
	 * Instance of the class is created, but has no selected profile;
	 */
	public ProfileFileReader() {
		ProfileFileReader.selectedProfile = null;
	}

	/**
	 * Instance of the class is created and profileName is logged profile
	 * 
	 * @param profileName
	 * @throws Exception
	 */
	public ProfileFileReader(String profileName) throws Exception {
		if (!doesProfileExist(profileName)) {
			createNewProfile(profileName);
		}
		ProfileFileReader.selectedProfile = profileName;
	}

	/**
	 * Create new profile in text file with chosen name.
	 * 
	 * @param profileName
	 * @throws Exception - if there is a problem with a file or name is already used
	 */
	@SuppressWarnings("resource")
	public static void createNewProfile(String profileName) throws Exception {
		File file = new File(FILE_PATH);
		File tempFile = new File("resources/tempProf.txt");
		Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, true);
		// BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(fileWriter);

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
		System.gc();

		printWriter.flush();
		printWriter.close();
		file.delete();

		File rename = new File(FILE_PATH);
		tempFile.renameTo(rename);

	}

	/**
	 * Remove profile from the txt file. If name is not in a file then does nothing.
	 * 
	 * @param profileName
	 * @throws IOException - if there is a problem with a file
	 */

	public static void deleteProfile(String profileName) throws IOException {
		File file = new File(FILE_PATH);
		File tempFile = new File("resources/tempProf.txt");
		Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, false);
		PrintWriter printWriter = new PrintWriter(fileWriter);

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
					in.nextInt();
					in.nextInt();
				}
				isRemoved = true;
			}
		}

		in.close();
		System.gc();
		printWriter.flush();
		printWriter.close();
		fileWriter.close();

		file.delete();

		File rename = new File(FILE_PATH);
		tempFile.renameTo(rename);

	}

	/**
	 * Return best profile score for the specified level
	 * 
	 * @param profileName
	 * @param level
	 * @return - best player score
	 * @throws IOException - if there is a problem with a file
	 */
	public static int getBestScore(String profileName, int level) throws IOException {
		File file = new File(FILE_PATH);
		Scanner in = new Scanner(file);

		int bestScore = 0;
		while (in.hasNext()) {
			in.nextInt();
			String profName = in.next();

			for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
				int lvl = in.nextInt();
				int scr = in.nextInt();
				if (profName.equals(profileName) && level == lvl) {
					bestScore = scr;
				}
			}
		}

		in.close();
		System.gc();

		return bestScore;
	}

	/**
	 * Save score if it is new best score for specified level
	 * 
	 * @param profileName
	 * @param level
	 * @param score       - score you want to safe
	 * @throws IOException - if there is a problem with a file
	 */
	public static void saveBestScore(String profileName, int level, int score) throws IOException {
		File file = new File(FILE_PATH);
		File tempFile = new File("resources/tempProf.txt");
		Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, true);
		// BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(fileWriter);

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
		System.gc();
		printWriter.flush();
		printWriter.close();

		file.delete();

		File rename = new File(FILE_PATH);
		tempFile.renameTo(rename);
	}

	public static boolean doesProfileExist(String profileName) throws FileNotFoundException {
		File file = new File(FILE_PATH);
		Scanner in = new Scanner(file);

		boolean exist = false;
		while (in.hasNext()) {
			in.nextInt();
			String profName = in.next();

			if (profName.equals(profileName)) {
				exist = true;
			}

			for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
				in.nextInt();
				in.nextInt();
			}
		}

		in.close();
		System.gc();

		return exist;
	}

	// i'am not sure what it is going to do, probably should be moved to manager
	public static void loginProfile(String profileName) {
		try {
			if (doesProfileExist(profileName)) {
				selectedProfile = profileName;
			} else {
				selectedProfile = "error";
			}
		} catch (FileNotFoundException e) {
			selectedProfile = "error";
		}
	}

	public void logout() {
		selectedProfile = null;
	}

	public static String[] getProfiles() throws FileNotFoundException {
		File file = new File(FILE_PATH);
		Scanner in = new Scanner(file);

		String[] profiles = new String[0];
		int counter = 1;
		while (in.hasNext()) {
			in.nextInt();
			String profName = in.next();

			String[] newWords = new String[counter++];
			for (int i = 0; i < profiles.length; i++) {
				newWords[i] = profiles[i];
			}
			profiles = newWords;
			profiles[counter - 2] = profName;

			for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
				in.nextInt();
				in.nextInt();
			}
		}

		in.close();
		System.gc();

		return profiles;
	}

	/**
	 * @return name of a profile which is logged in
	 */
	public static String getLoggedProfile() {
		return ProfileFileReader.selectedProfile;
	}

	public static int getNumberOfLevels() {
		return ProfileFileReader.NUMBER_OF_LEVELS;
	}
}
