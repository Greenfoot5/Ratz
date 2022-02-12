
public class ProfileScore {
	private String levelName;
	private int score;

	public ProfileScore(String levelName, int score) {
		this.levelName = levelName;
		this.score = score;
	}
	
	public String getLevelName() {
		return levelName;
	}
	
	public int getScore() {
		return score;
	}
	
	public void updateScore(int score) {
		this.score = score;
	}
}
