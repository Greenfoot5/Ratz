import java.util.ArrayList;

/**
 * Class representing profile and their scores.
 * @author Tomasz Fijalkowski
 *
 */
public class Profile {
	private final String profileName;
	private final ArrayList<ProfileScore> profileScores;
	
	/**
	 * Creates profile with chosen name provided scores.
	 * @param profileName name of the profile
	 * @param profileScores list of the scores for profile to contain
	 */
	public Profile(String profileName, ArrayList<ProfileScore> profileScores) {
		this.profileName = profileName;
		this.profileScores = profileScores;
	}
	
	/**
	 * Creates profile with chosen name with default scores (all 0).
	 * @param profileName
	 */
	public Profile(String profileName) {

		this.profileName = profileName;
		ArrayList<String> levelNames = ProfileFileReader.getLevelNames();

		profileScores = new ArrayList<>();
		for (String s: levelNames) {
			profileScores.add(new ProfileScore(s, 0));
		}
	}
	
	/**
	 * Gets best score of the profile in selected level.
	 * @param levelName name of the level
	 * @return best score
	 */
	public int getBestScore(String levelName) {
		int bestScore = 0;
		
		for (ProfileScore s: profileScores) {
			if (s.getLevelName().equals(levelName)) {
				bestScore = s.getScore();
			}
		}
		return bestScore;
	}

	/**
	 * Save score if it is better than current score.
	 * @param levelName name of the level
	 * @param score score to save
	 */
	public void saveBestScore(String levelName, int score) {
		for (ProfileScore s: profileScores) {
			if (s.getLevelName().equals(levelName) && score > s.getScore()) {
				s.updateScore(score);
			}
		}
	}
	/**
	 * Gets list of profile scores.
	 * @return list of profile scores
	 */
	public ArrayList<ProfileScore> getProfileScores() {
		return profileScores;
	}
	
	/**
	 * Gets name of the profile.
	 * @return name of the profile
	 */
	public String getProfileName() {
		return profileName;
	}
	
	/**
	 * Create new score with chosen level name and score 0.
	 * @param levelName name of the level
	 */
	public void createNewLevel(String levelName) {
		profileScores.add(new ProfileScore(levelName, 0));
	}
	
	/**
	 * Deletes score with chosen name of the level.
	 * @param levelName name of the level
	 */
	public void deleteLevel(String levelName) {
		ProfileScore scoreToRemove = null;
		for (ProfileScore score: profileScores) {
			if (score.getLevelName().equals(levelName)) {
				scoreToRemove = score;
			}
		}
		profileScores.remove(scoreToRemove);
	}
}
