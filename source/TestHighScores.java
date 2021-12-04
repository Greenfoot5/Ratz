import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Class to test HighScores class, and show it's use.
public class TestHighScores {
	public static void main(String[] args) throws IOException {
		HighScores hs = new HighScores();
		ProfileFileReader pfr = new ProfileFileReader();

		// adding scores to the file
		// hs.safeScore("A", 17, 1);

		String[] s = pfr.getProfiles();

//		for (int i = 0; i < s.length; i++) {
//			for (int j = 1; j <= pfr.getNumberOfLevels(); j++) {
//				if (pfr.getBestScore(s[i], j) > 0) {
//					hs.safeScore(s[i], pfr.getBestScore(s[i], j), j);
//				}
//			}
//		}

		String[] tp = hs.getTopScores(1);
		// getting scores from the file
		for (int i = 0; i < 10; i++) {
			System.out.println(tp[i]);
		}
	}
}
