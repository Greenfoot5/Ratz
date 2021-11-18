import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HighScores {
	private Scanner in = null;
	private File file = new File("highScores.txt");
	
	public HighScores() {
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// return array of string containing up to ten pairs - (profileName, score)
	public String[] getTopScores(int level) {
		return null;
	}
	
	// method will safe score if it will be in top 10
	public void safeScore(String profile, int level) {
		
	}
}
