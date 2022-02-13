import java.util.ArrayList;

public class Profile {
	private String profileName;
	private ArrayList<ProfileScore> profileScores;
	
	public Profile(String profileName, ArrayList<ProfileScore> profileScores) {
		this.profileName = profileName;
		this.profileScores = profileScores;
	}
	
	public Profile(String profileName) {

		this.profileName = profileName;
		ArrayList<String> levelNames = ProfileFileReaderV2.getLevelNames();

		profileScores = new ArrayList<>();
		for (String s: levelNames) {
			profileScores.add(new ProfileScore(s, 0));
		}
	}
	
	public int getBestScore(String levelName) {
		int bestScore = 0;
		
		for (ProfileScore s: profileScores) {
			if (s.getLevelName().equals(levelName)) {
				bestScore = s.getScore();
			}
		}
		return bestScore;
	}

	public void saveBestScore(String levelName, int score) {
		for (ProfileScore s: profileScores) {
			if (s.getLevelName().equals(levelName) && score > s.getScore()) {
				s.updateScore(score);
			}
		}
	}
	
	public ArrayList<ProfileScore> getProfileScores() {
		return profileScores;
	}
	
	public String getProfileName() {
		return profileName;
	}
	
	public void createNewLevel(String levelName) {
		profileScores.add(new ProfileScore(levelName, 0));
	}
}
