
public class Score {
	//private String levelName;
	private String profileName;
	private int score;

	public Score(String profileName, int score) {
		//this.levelName = levelName;
		this.profileName = profileName;
		this.score = score;
	}
	
//	public String getLevelName() {
//		return levelName;
//	}
	
	public String getProfileName() {
		return profileName;
	}
	
	public int getScore() {
		return score;
	}
	
}
