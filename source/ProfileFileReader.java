import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProfileFileReader {
	private Scanner in = null;
	private File file = new File("profiles.txt");
	
	public ProfileFileReader() {
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// create new profile in text file with chosen name
	public void createNewProfile(String profileName) {
		
	}
	
	// remove profile from the txt file
	public void deleteProfile(String profileName) {
		
	}
	
	// return best profile score for the specified level
	public int getBestScore(String profileName, int level) {
		return 0;
	}
	
	// save score if it is new best score for specified level
	public void saveBestScore(String profileName, int level) {
		
	}
	
	// i'am not sure what it is going to do, probably should be moved to manager
	public void loginProfile(String profileName) {
		
	}
}
