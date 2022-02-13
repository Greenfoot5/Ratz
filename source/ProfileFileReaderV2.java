import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ProfileFileReaderV2 {
	private static final String FILE_PATH = "resources/profileFile2.txt";
	private static String selectedProfile = null;
	private static int numberOfLevels;
	private static ArrayList<Profile> profiles = new ArrayList<>();
	private static ArrayList<String> levelNames = new ArrayList<>();

	/**
	 * Load data from text file to memory. 
	 * Should be use only once at the start of the program.
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
	 * Saves data from memory to a file.
	 * Can be used many times (but it might be use only while switching the scenes).
	 * Strongly recommended to use at the end of the program (otherwise changes are lost).
	 * @throws IOException
	 */
	public static void saveDataToFile() throws IOException {
		File file = new File(FILE_PATH);
		File tempFile = new File("resources/tempProf.txt");
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
					printWriter.print(score.getLevelName() + " " + score.getScore() +  " ");
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
	 * @param profileName	name of a profile
	 */
	public static void createNewProfile(String profileName) {
		if (doesProfileExist(profileName)) {
			throw new IllegalArgumentException("Profile already exist");
		}
		profiles.add(new Profile(profileName));
	}

	/**
	 * Delete profile from memory.
	 * If name is not in the memory then does nothing.
	 * 
	 * @param profileName	name of a profile
	 */
	public static void deleteProfile(String profileName) {
		Profile profileToRemove = null;
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				profileToRemove = p;
			}
		}
		profiles.remove(profileToRemove);
	}

	/**
	 * Return best profile score for the specified level.
	 *
	 * @param profileName	name of a profile
	 * @param level			level which you want to safe score
	 * @return best 		player score
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
	 * @param profileName	name of a profile
	 * @param level			level which you want to safe score
	 * @param score 		score you want to safe
	 */
	public static void saveBestScore(String profileName, String levelName, int score) {
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				p.saveBestScore(levelName, score);
			}
		}
	}

	/**
	 * Check if a profile exist in database.
	 *
	 * @param profileName				name of a profile
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
	 * @param profileName	name of a profile
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
	 * @return array list of level names
	 */
	public static ArrayList<String> getLevelNames() {
		return levelNames;
	}

	/**
	 * Gets number of levels.
	 * @return number of levels
	 */
	public static int getNumberOfLevels() {
		return numberOfLevels;
	}

	/**
     * Gets the name of the profile currently logged in
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
	 * @param levelName	name of the level to delete
	 * @throws IllegalArgumentException if level not exist
	 */
	public static void deleteLevel(String levelName) throws IllegalArgumentException {
		if (doesLevelExist(levelName)) {
			String levelToRemove = null;
			for (String lvlName: levelNames) {
				if (lvlName.equals(levelName)) {
					levelToRemove = lvlName;
				}
			}
			levelNames.remove(levelToRemove);
			
			for (Profile p : profiles) {
				p.deleteLevel(levelName);
			}
		} else {
			throw new IllegalArgumentException("Level does not exist");
		}
	}
	
	/**
	 * Checks if level exist in database.
	 * @param levelName  name of the level to check
	 * @return true if level exist, false otherwise
	 */
	public static boolean doesLevelExist(String levelName) {
		boolean exists = false;
		for (String lvlName: levelNames) {
			if (lvlName.equals(levelName)) {
				exists = true;
			}
		}
		return exists;
	}
}
