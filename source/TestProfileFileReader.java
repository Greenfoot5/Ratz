import java.io.IOException;

// Class to test ProfileFileReader class, and show it's use.
public class TestProfileFileReader {
	public static void main (String[] args) throws Exception {
		ProfileFileReader pfr = new ProfileFileReader();
		// /* if compiled once (txt file has a data) this segment should be commented (because of exception when name is already used)
		pfr.createNewProfile("NATALIA");
		pfr.createNewProfile("NATALIA2");
		pfr.createNewProfile("aaaa");
		pfr.createNewProfile("aaaa2");
		pfr.deleteProfile("aaaa");
		pfr.saveBestScore("aaaa2", 1, 12);
		// */
		System.out.println(pfr.getBestScore("aaaa2", 1));
		System.out.println(pfr.getBestScore("aaaa2", 2));
	}
}
