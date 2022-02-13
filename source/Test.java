import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) throws Exception {
//		String sButton = "Button@45c1f6a4[styleClass=button]'Main  Menu'";
//		System.out.println(sButton.charAt(35));
//		System.out.println(sButton.substring(35, sButton.length() - 1));

		// String[] s = HighScores.getTopScores("4");
		// System.out.println(s[0]);
//
//		for (String str: s) {
//			System.out.println(str);
//
//		}
//		System.out.println();
		// HighScores.deleteProfile("Test");
		// HighScores.safeScore("Test", 222222, "lev"); // works

		// System.out.println(ProfileFileReader.getNumberOfLevels());

//		LevelScores l = new LevelScores("lvl1");
//		Score s1 = new Score("pr1", 100);
//		Score s2 = new Score("pr2", 200);
//		Score s3 = new Score("pr3", 300);
//		Score s4 = new Score("pr4", 150);
//		Score s5 = new Score("pr5", 250);
//		Score s6 = new Score("pr6", 250);
//		l.safeScore(s1);
//		l.safeScore(s2);
//		l.safeScore(s3);
//		l.safeScore(s4);
//		l.safeScore(s5);
//		l.safeScore(s6);
//		l.safeScore(s6);
//		l.safeScore(s6);
//		l.safeScore(s6);
//		l.safeScore(s6);
//		l.safeScore(s6);
//		l.safeScore(s6);
//		 l.deleteProfile("pr3");

//		Score[] scrs = l.getTopScores();
//
//		for (int i = 0; i < 10; i++) {
//			if (scrs[i] == null) {
//				System.out.println(scrs[i] == null);
//			} else {
//				System.out.println(scrs[i].getProfileName());
//			}
//		}
//		System.out.println(l.getTopScores()[0].getProfileName());
//		System.out.println(l.getTopScores()[1] == null);
//		System.out.println(l.getTopScores()[2] == null);
//		HighScoresV2.loadData();
//		Score[] s = HighScoresV2.getTopScores("level-1");
//		for (Score str: s) {
//			if(str != null)
//			System.out.println(str.getProfileName() + " " + str.getScore());
//
//		}
//		HighScoresV2.safeScore("Smb", 600, "level-5");
//		Score[] s2 = HighScoresV2.getTopScores("level-5");
//		System.out.println(s2.length);
//
//		for (Score str: s2) {
//			if(str != null)
//			System.out.println(str.getProfileName() + " " + str.getScore());
//
//		}
//
//		HighScoresV2.safeDataToFile();
//		System.out.println("done");
		//ProfileFileReaderV2.saveBestScore("James", "level-1", 20304);
//		ProfileFileReaderV2.createNewProfile("Mart");
//		ProfileFileReaderV2.createNewProfile("Steffan");
//		ProfileFileReaderV2.deleteProfile("Dom");
		
//		ArrayList<Profile> p = ProfileFileReaderV2.getProfiles();
//		for (Profile pr: p) {
//			System.out.println(pr.getProfileName());
//
//		}
//		System.out.println(ProfileFileReaderV2.getBestScore("Tony", "level-1"));
		//System.out.println(ProfileFileReaderV2.getLoggedProfile());
		//System.out.println(ProfileFileReaderV2.doesProfileExist("a"));
		
//		ProfileFileReaderV2.loadData();
//		ProfileFileReaderV2.deleteLevel("newLvl");
//		ProfileFileReaderV2.saveDataToFile();
		//System.out.println(ProfileFileReaderV2.doesProfileExist("Doam"));
		
		HighScoresV2.loadData();
		HighScoresV2.deleteLevel("newLevel");
		HighScoresV2.saveDataToFile();;
		
	}
}
