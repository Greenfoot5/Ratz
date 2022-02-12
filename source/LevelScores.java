
public class LevelScores {
	private String levelName;
	private static final int NUMBER_OF_TOP_SCORES = 10;
	private Score[] scores;

	
	public LevelScores(String levelName) {
		this.levelName = levelName;
		scores = new Score[NUMBER_OF_TOP_SCORES];
	}
	
	public void safeScore(Score newScore) {
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
	
	public Score[] getTopScores() {
		return scores;
	}
	
	public String getLevelName() {
		return levelName;
	}
}
