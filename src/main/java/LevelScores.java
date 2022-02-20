
/**
 * Class to manage level's best scores.
 *
 * @author Tomasz Fijalkowski
 * @version 1.0
 */
public class LevelScores {
	private String levelName;
	private static final int NUMBER_OF_TOP_SCORES = 10;
	private Score[] scores;

	/**
	 * Create new level without scores.
	 * @param levelName name of the level
	 */
	public LevelScores(String levelName) {
		this.levelName = levelName;
		scores = new Score[NUMBER_OF_TOP_SCORES];
	}
	
	/**
	 * Save a score in memory if it is in top 10.
	 * @param newScore score to save
	 */
	public void saveScore(Score newScore) {
		Score tempScore = null;
		Score tempScore2;
		boolean inserted = false;
		for (int i = 0; i < NUMBER_OF_TOP_SCORES; i++) {
			if (((scores[i] != null && newScore.getScore() > scores[i].getScore())
					|| scores[i] == null) && !inserted) {
				tempScore = scores[i];
				scores[i] = newScore;
				inserted = true;
			} else if (inserted) {
				tempScore2 = scores[i];
				scores[i] = tempScore;
				tempScore = tempScore2;
			}
		}

	}
	
	/**
	 * Deletes every score which belongs to given profile.
	 * @param profileName name of the profile
	 */
	public void deleteProfile(String profileName) {
		for (int i = 0; i < NUMBER_OF_TOP_SCORES; i++) {
			if (scores[i] != null && scores[i].getProfileName().equals(profileName)){
				scores[i] = null;
			}
		}
		Score[] tempScores = new Score[NUMBER_OF_TOP_SCORES];
		int tempIterator = 0;
		for (int i = 0; i < NUMBER_OF_TOP_SCORES; i++) {
			if (scores[i] != null){
				tempScores[tempIterator] = scores[i];
				tempIterator++;
			}
		}
		scores = tempScores;
	}
	
	/**
	 * Gets array of top scores.
	 * @return array of top scores
	 */
	public Score[] getTopScores() {
		return scores;
	}
	
	/**
	 * Gets name of the level
	 * @return name of the level
	 */
	public String getLevelName() {
		return levelName;
	}
}
