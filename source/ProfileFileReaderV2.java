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

	public static void loadData() throws FileNotFoundException {
		File file = new File(FILE_PATH);
		Scanner in = new Scanner(file);

		profiles.clear();
		levelNames.clear();

		setNumberOfLevels(in.nextInt());

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

	public static void createNewProfile(String profileName) {
		if (doesProfileExist(profileName)) {
			throw new IllegalArgumentException("Profile already exist");
		}
		profiles.add(new Profile(profileName));
	}

	public static void deleteProfile(String profileName) {
		Profile profileToRemove = null;
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				// profiles.remove(p);
				profileToRemove = p;
			}
		}
		profiles.remove(profileToRemove);
	}

	public static int getBestScore(String profileName, String levelName) {
		int bestScore = 0;
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				bestScore = p.getBestScore(levelName);
			}
		}
		return bestScore;
	}

	public static void saveBestScore(String profileName, String levelName, int score) {
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				p.saveBestScore(levelName, score);
			}
		}
	}

	public static boolean doesProfileExist(String profileName) {
		boolean exist = false;
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				exist = true;
			}
		}
		return exist;
	}

	public static void loginProfile(String profileName) throws IllegalArgumentException {
		if (doesProfileExist(profileName)) {
			selectedProfile = profileName;
		} else {
			throw new IllegalArgumentException("Profile does not exist");
		}
	}

	public static void logout() {
		selectedProfile = null;
	}

	public static ArrayList<Profile> getProfiles() {
		return profiles;
	}

	public static ArrayList<String> getLevelNames() {
		return levelNames;
	}

	public static int getNumberOfLevels() {
		return numberOfLevels;
	}

	public static String getLoggedProfile() {
		return selectedProfile;
	}

	public static void setNumberOfLevels(int numOfLevels) {
		numberOfLevels = numOfLevels;
	}
	
	public static void createNewLevel(String levelName) {
		for (Profile p : profiles) {
			p.createNewLevel(levelName);
		}
	}
	
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
