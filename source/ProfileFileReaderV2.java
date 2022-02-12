import java.util.ArrayList;

public class ProfileFileReaderV2 {
	private static final String FILE_PATH = "resources/profileFile.txt";
	private static String selectedProfile = null;
	private static int numberOfLevels = 0;
	private static ArrayList<Profile> profiles;
	private static ArrayList<String> levelNames;

	public static void loadData() {

	}

	public static void safeDataToFile() {

	}

	public static void createNewProfile(String profileName) {
		profiles.add(new Profile(profileName));
	}

	public static void deleteProfile(String profileName) {
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				profiles.remove(p);
			}
		}
	}
	
	public static int getBestScore(String profileName, String levelName) {
		int bestScore = 0;
		for (Profile p : profiles) {
			if (p.getProfileName().equals(profileName)) {
				p.getBestScore(levelName);
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
	
	public static void loginProfile(String profileName) throws Exception {
		if (doesProfileExist(profileName)) {
			selectedProfile = profileName;
		} else {
			throw new Exception("Profile does not exist");
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
}
