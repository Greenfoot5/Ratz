import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws Exception {
//		String sButton = "Button@45c1f6a4[styleClass=button]'Main  Menu'";
//		System.out.println(sButton.charAt(35));
//		System.out.println(sButton.substring(35, sButton.length() - 1));
		
		//String[] s = HighScores.getTopScores("4");
		//System.out.println(s[0]);
//
//		for (String str: s) {
//			System.out.println(str);
//
//		}
//		System.out.println();
		//HighScores.deleteProfile("Test");
		//HighScores.safeScore("Test", 222222, "lev"); // works
		
		System.out.println(ProfileFileReader.getNumberOfLevels());
	}
}
