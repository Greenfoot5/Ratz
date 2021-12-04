import java.io.File;
import java.io.IOException;

// Class to test ProfileFileReader class, and show it's use.
public class TestProfileFileReader {
	public static void main (String[] args) throws Exception {
	//	ProfileFileReader pfr = new ProfileFileReader();
		reset();
	}
	
	public static void reset() {
		ProfileFileReader pfr = new ProfileFileReader();
		try {
			//pfr.createNewProfile("testProfile2137");
			pfr.loginProfile("Tomasz");

			//pfr.getBestScore(Dom, 1);
			//System.out.println(pfr.getBestScore("Dom", 3));
			
			//pfr.saveBestScore("Dom", 4, 1234);
			//System.out.println(pfr.getBestScore("Dom", 4));

			
		//	File file = new File("resources/profileFile.txt");
			//file.
			//System.out.println(file.delete());
		//	System.out.println(file.exists() + "exist");

		} catch (Exception e) {
			System.out.println("catch");

		}
	}
}
