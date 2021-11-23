import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Class to test HighScores class, and show it's use.
public class TestHighScores {
	public static void main(String[] args) throws IOException {
		HighScores hs = new HighScores();
		
		//adding scores to the file
		hs.safeScore("A", 17, 1);
		hs.safeScore("A", 20, 1);
		hs.safeScore("A", 18, 1);
		hs.safeScore("A", 23, 1);
		hs.safeScore("B", 34, 1);
		hs.safeScore("B", 11, 2);
		hs.safeScore("C", 18, 1);
		hs.safeScore("D", 16, 1);
		hs.safeScore("E", 34, 2);
		hs.safeScore("F", 2, 2);
		hs.safeScore("B", 15, 1);

		String[] tp = hs.getTopScores(1);
		// getting scores from the file
		for (int i = 0; i < 10; i++) {
			System.out.println(tp[i]);
		}
	}
}
