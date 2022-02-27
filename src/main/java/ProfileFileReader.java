import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to manage profiles and their best scores.
 * 
 * @author Tomasz Fijalkowski
 * 
 */
public class ProfileFileReader {
	private static final String FILE_PATH = "src/main/resources/profileFile2.txt";
	private static String selectedProfile = null;
	private static int numberOfLevels;
	private static final ArrayList<Profile> profiles = new ArrayList<>();
	private static final ArrayList<String> levelNames = new ArrayList<>();

	/**
	 * Load data from text file to memory. Should be use only once at the start of
	 * the program.
	 * 
	 * @throws FileNotFoundException file not found
	 */
	public static void loadData() throws FileNotFoundException {
		File file = new File(FILE_PATH);
		Scanner in = new Scanner(file);

		profiles.clear();
		levelNames.clear();

		numberOfLevels = in.nextInt();

		for (int i = 0; i < numberOfLevels; i++) {
			levelNames.add(in.next());
		}
		// String profileName = "";

		while (in.hasNext()) {
			String levelName = in.next(); // or profile name
			int score = in.nextInt(); // or pointer to next profile data

			if (score == -1) {
				profiles.add(new Profile(levelName));
			} else {
				profiles.get(profiles.size() - 1).saveBestScore(levelName, score);
			}

		}

		in.close();
		System.gc();
	}

	/**
	 * Saves data from memory to a file. Can be used many times (but it might be use
	 * only while switching the scenes). Strongly recommended using at the end of
	 * the program (otherwise changes are lost).
	 *
	 */
	public static void saveDataToFile() throws IOException {
		File file = new File(FILE_PATH);
		File tempFile = new File("tempProf.txt");
		// Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		printWriter.println(getNumberOfLevels());

		for (String levelName : levelNames) {
			printWriter.print(levelName + " ");
		}

		for (Profile profile : profiles) {
			printWriter.println("\n" + profile.getProfileName() + " -1");
			ArrayList<ProfileScore> profilesToSave = profile.getProfileScores();
			for (ProfileScore score : profilesToSave) {
				if (score != null) {
					printWriter.print(score.getLevelName() + " " + score.getScore() + " ");
				}
			}
		}

		printWriter.flush();
		printWriter.close();
		file.delete();

		File rename = new File(FILE_PATH);
		tempFile.renameTo(rename);
	}

	/**
	 * Create new profile with chosen name.
	 * 
	 * @param profileName name of a profile
	 */
	public static void createNewProfile(String profileName) {
		if (doesProfileExist(profileName)) {
			throw new IllegalArgumentException("Profile already exist");
		} else {
			profiles.add(new Profile(profileName));
			new File("src/main/resources/levels/saved_games/" + profileName).mkdir();
			new File("src/main/resources/saved_games_images/" + profileName).mkdir();

		}
	}

	/**
	 * Delete profile from memory. If name is not in the memory then does nothing.
	 * 
	 * @param profileName name of a profile
	 */
	public static void deleteProfile(String profileName) {
		Profile profileToRemove = null;
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				profileToRemove = p;

				String directory = "src/main/resources/levels/saved_games/" + profileName;
				File directoryPath = new File(directory);
				String[] contents = directoryPath.list();

				if (contents != null) {
					for (String content : contents) {
						new File(directory + "/" + content).delete();
					}
				}
				new File(directory).delete();
				
				directory = "src/main/resources/saved_games_images/" + profileName;
				directoryPath = new File(directory);
				contents = directoryPath.list();

				if (contents != null) {
					for (String content : contents) {
						new File(directory + "/" + content).delete();
					}
				}
				new File(directory).delete();

			}
		}
		profiles.remove(profileToRemove);
	}

	/**
	 * Return best profile score for the specified level.
	 *
	 * @param profileName name of a profile
	 * @param levelName   level which you want to safe score
	 * @return best player score
	 */
	public static int getBestScore(String profileName, String levelName) {
		int bestScore = 0;
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				bestScore = p.getBestScore(levelName);
			}
		}
		return bestScore;
	}

	/**
	 * Save score if it is new best score for specified level.
	 *
	 * @param profileName name of a profile
	 * @param levelName   level which you want to safe score
	 * @param score       score you want to safe
	 */
	public static void saveScore(String profileName, String levelName, int score) {
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				p.saveBestScore(levelName, score);
			}
		}
	}

	/**
	 * Check if a profile exist in database.
	 *
	 * @param profileName name of a profile
	 * @return true if profile exist, false otherwise
	 */
	public static boolean doesProfileExist(String profileName) {
		boolean exist = false;
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				exist = true;
			}
		}
		return exist;
	}

	/**
	 * Login to a selected profile.
	 *
	 * @param profileName name of a profile
	 */
	public static void loginProfile(String profileName) throws IllegalArgumentException {
		if (doesProfileExist(profileName)) {
			selectedProfile = profileName;
		} else {
			throw new IllegalArgumentException("Profile does not exist");
		}
	}

	/**
	 * Logout the user.
	 */
	public static void logout() {
		selectedProfile = null;
	}

	/**
	 * Get all registered profiles names.
	 *
	 * @return String array containing every profile name
	 */
	public static String[] getProfiles() {
		String[] profilesString = new String[profiles.size()];
		for (int i = 0; i < profilesString.length; i++) {
			profilesString[i] = profiles.get(i).getProfileName();
		}
		return profilesString;
	}

	/**
	 * Get all registered profiles.
	 *
	 * @return Arraylist containing every profile object
	 */
	public static ArrayList<Profile> getProfilesObject() {
		return profiles;
	}

	/**
	 * Gets array list of level names.
	 * 
	 * @return array list of level names
	 */
	public static ArrayList<String> getLevelNames() {
		return levelNames;
	}

	public static ArrayList<String> getDefaultLevelsNames() {
		// Creating a File object for directory
		File directoryPath = new File("src/main/resources/levels/default_levels");
		System.out.println(directoryPath.getAbsolutePath());

		// List of all files and directories
		String[] contents = directoryPath.list();
		ArrayList<String> levels = new ArrayList<>();

		assert contents != null;
		for (String content : contents) {
			String substring = content.substring(0, content.length() - 4);
			levels.add(substring);

			System.out.println(substring + "  --214124rewf");
		}

		return levels;
	}

	public static ArrayList<String> getCreatedLevelsNames() {
		// Creating a File object for directory
		File directoryPath = new File("src/main/resources/levels/created_levels");

		// List of all files and directories
		String[] contents = directoryPath.list();
		ArrayList<String> levels = new ArrayList<>();

		assert contents != null;
		for (String content : contents) {
			String substring = content.substring(0, content.length() - 4);
			levels.add(substring);

			System.out.println(substring + "  --214124rewf");
		}

		return levels;
	}

	public static ArrayList<String> getSavedGamesNames(String profileName) {
		// Creating a File object for directory
		File directoryPath = new File("src/main/resources/levels/saved_games/" + profileName);

		// List of all files and directories
		String[] contents = directoryPath.list();
		ArrayList<String> levels = new ArrayList<>();

		if (contents != null) {
			for (String content : contents) {
				levels.add(content.substring(0, content.length() - 4));
				System.out.println(content.substring(0, content.length() - 4) + "  --214124rewf");
			}
		}

		return levels;
	}

	/**
	 * Gets number of levels.
	 * 
	 * @return number of levels
	 */
	public static int getNumberOfLevels() {
		return numberOfLevels;
	}

	/**
	 * Gets the name of the profile currently logged in
	 * 
	 * @return name of a profile which is logged in
	 */
	public static String getLoggedProfile() {
		return selectedProfile;
	}
//  Not in use right now
//	/**
//	 * Sets
//	 * @param numOfLevels
//	 */
//	public static void setNumberOfLevels(int numOfLevels) {
//		numberOfLevels = numOfLevels;
//	}

	/**
	 * Create new level in database with chosen name.
	 * 
	 * @param levelName name of the level
	 */
	public static void createNewLevel(String levelName) {
		for (Profile p : profiles) {
			p.createNewLevel(levelName);
		}
		numberOfLevels++;
		levelNames.add(levelName);
	}

	/**
	 * Deletes level from database.
	 * 
	 * @param levelName name of the level to delete
	 * @throws IllegalArgumentException if level not exist
	 */
	public static void deleteLevel(String levelName) throws IllegalArgumentException {
		if (doesLevelExist(levelName)) {
			String levelToRemove = null;
			for (String lvlName : levelNames) {
				if (lvlName.equals(levelName)) {
					levelToRemove = lvlName;
				}
			}
			levelNames.remove(levelToRemove);

			for (Profile p : profiles) {
				p.deleteLevel(levelName);
			}
			numberOfLevels--;
		} else {
			throw new IllegalArgumentException("Level does not exist");
		}
	}

	/**
	 * Checks if level exist in database.
	 * 
	 * @param levelName name of the level to check
	 * @return true if level exist, false otherwise
	 */
	public static boolean doesLevelExist(String levelName) {
		boolean exists = false;
		for (String lvlName : levelNames) {
			if (lvlName.equals(levelName)) {
				exists = true;
			}
		}
		return exists;
	}
}
