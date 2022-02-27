/**
 * Class representing score of profile in level.
 * @author tfija
 *
 */
public class ProfileScore {
	private final String levelName;
	private int score;

	/**
	 * Creates score with chosen level name and score.
	 * @param levelName name of the level
	 * @param score score to dave
	 */
	public ProfileScore(String levelName, int score) {
		this.levelName = levelName;
		this.score = score;
	}
	
	/**
	 * Gets the name of the level.
	 * @return name of the level
	 */
	public String getLevelName() {
		return levelName;
	}
	
	/**
	 * Gets score.
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Updates score.
	 * @param score updated score
	 */
	public void updateScore(int score) {
		this.score = score;
	}
}
