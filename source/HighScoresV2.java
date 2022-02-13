import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScoresV2 {
	// Path to a file storing data about scores.
	private static final String FILE_PATH = "resources/highScores2.txt";

	// Maximum number of saved scores per level.
	private static final int NUMBER_OF_TOP_SCORES = 10;

	private static ArrayList<LevelScores> levelsScores = new ArrayList<>();
	
	public static void loadData() throws FileNotFoundException {
		File file = new File(FILE_PATH);
		Scanner in = new Scanner(file);

		String levelName = "";
		levelsScores.clear();
		while (in.hasNext()) {
			String profileName = in.next(); // or level name
			int score = in.nextInt(); // or pointer to next level data
			
			if(score == -1) {
				levelName = profileName;
				LevelScores levelScores = new LevelScores(levelName);
				levelsScores.add(levelScores);
			} else {
				Score scoreToLoad = new Score(profileName, score);
				levelsScores.get(levelsScores.size() - 1).safeScore(scoreToLoad);;
			}
			
		}

		in.close();
		System.gc();
	}
	
	public static void saveDataToFile() throws IOException {
		File file = new File(FILE_PATH);
		File tempFile = new File("resources/temp.txt");
		//Scanner in = new Scanner(file);
		FileWriter fileWriter = new FileWriter(tempFile, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		
		for (LevelScores lvlScr: levelsScores) {
			printWriter.println(lvlScr.getLevelName() + " -1");
			Score[] scoresToSave = lvlScr.getTopScores();
			for (Score scr: scoresToSave) {
				if (scr != null) {
					printWriter.println(scr.getProfileName() + " " + scr.getScore());
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
	 * Gets the tops scores for a level.
	 *
	 * @param level level of the game
	 * @return array of Score
	 */
	public static Score[] getTopScores(String levelName) {
		Score[] topScores = null;
		for (LevelScores lvlScr : levelsScores) {
			if (lvlScr.getLevelName().equals(levelName)) {
				topScores = lvlScr.getTopScores();
			}
		}
		return topScores;
	}

	/**
	 * Method will try to safe a score, if it is in top 10.
	 *
	 * @param profile name of a profile
	 * @param score   achieved score you want to safe
	 * @param level   level of the game
	 */
	public static void saveScore(String profileName, int score, String levelName) {
		for (LevelScores lvlScr : levelsScores) {
			if (lvlScr.getLevelName().equals(levelName)) {
				lvlScr.safeScore(new Score(profileName, score));
				;
			}
		}
	}

	/**
	 * Delete every profile score from a file.
	 *
	 * @param profileName name of a profile
	 */
	public static void deleteProfile(String profileName) {
		for (LevelScores lvlScr : levelsScores) {
			lvlScr.deleteProfile(profileName);
		}
	}
	
	public static void createLevel(String levelName) {
		levelsScores.add(new LevelScores(levelName));
	}

}
