/**
 * Class representing single profile score.
 * @author Tomasz Fijalkowski
 *
 */
public class Score {
	private String profileName;
	private int score;

	/**
	 * Creates score with chosen profile name and score.
	 * @param profileName name of the profile
	 * @param score score to save
	 */
	public Score(String profileName, int score) {
		this.profileName = profileName;
		this.score = score;
	}
	
	/**
	 * Gets name of the profile.
	 * @return name of the profile
	 */
	public String getProfileName() {
		return profileName;
	}
	
	/**
	 * Gets score.
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
}
