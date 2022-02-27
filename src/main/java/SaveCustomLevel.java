import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author steff
 * This class saves a custom level made by the user to file
 * Also has a satic method to allow for these files to be deleted
 *
 */
public class SaveCustomLevel {
	private String name; //name of level
	
	private int width;
	private int height;
	
	private Tile[][] map = new Tile[width][height];
	
	private int maxRats;
	private int parTime;
	
	private int[] dropRates;
	
	private final ArrayList<Rat> rats;

	private boolean wasSaved;
	
	/**
	 * Constructs a save custom level object
	 * @param name of the custom level
	 * @param width of the custom level
	 * @param height of the custom level
	 * @param map of Tile objects representing the level board
	 * @param maxRats int of lose condition
	 * @param parTime int of win condition
	 * @param dropRates int[] array for item drop rate
	 */
	public SaveCustomLevel(String name, int width, int height, Tile[][] map, int maxRats, int parTime, int[] dropRates) {
		setName(name);
		setWidth(width);
		setHeight(height);
		setMap(map);
		setMaxRats(maxRats);
		setParTime(parTime);
		setDropRates(dropRates);
		rats = getRats();
		createNewLevelFile(name);
	}
	
	/**
	 * Sets the name of the level
	 */
	private void setName(String name) {
		if (name.contains("inProgress")) {
			name = name.replaceAll("inProgress", "New");
		}
		this.name = name.replaceAll("\\s+", "");
	}
	
	/**
	 * Sets the width of the level
	 */
	private void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Sets the height of the level
	 */
	private void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Sets the map of the level
	 */
	private void setMap(Tile[][] map) {
		this.map = map;
	}
	
	/**
	 * Sets the max rats of the level
	 */
	private void setMaxRats(int maxRats) {
		this.maxRats = maxRats;
	}
	
	/**
	 * Sets the winning time of the level
	 */
	private void setParTime(int parTime) {
		this.parTime = parTime;
	}
	
	/**
	 * Sets the item drop rates for inventory
	 */
	private void setDropRates(int[] dropRates) {
		this.dropRates = dropRates;
	}
	
	/**
	 * Gets the name of the level
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the width of the level
	 * @return width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Gets the height of the level
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Gets the Tile map of the level
	 * @return map
	 */
	public Tile[][] getMap() {
		return this.map;
	}
	
	/**
	 * Gets the max rats of the level
	 * @return maxRats
	 */
	public int getMaxRats() {
		return this.maxRats;
	}
	
	/**
	 * Gets the winning condition of the level
	 * @return parTime
	 */
	public int getParTime() {
		return this.parTime;
	}
	
	/**
	 * Gets the item drop rates
	 * @return dropRates
	 */
	public int[] getDropRates() {
		return this.dropRates;
	}
	
	/**
	 * Gets the path name of the level file
	 * @return String 
	 */
	public String getPathName() {
		return name + ".txt";
	}
	
	/**
	 * Gets the amount of rats on the level
	 * @return an ArrayList of Rat objects
	 */
	public ArrayList<Rat> getRats() {
		ArrayList<Rat> rats = new ArrayList<>();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (map[x][y].getOccupantRats().size() != 0) {
					ArrayList<Rat> occRats = (map[x][y].getOccupantRats());
					rats.addAll(occRats);
				}
			}
		}
		return rats;
	}
	
	/**
	 * Creates new level file with name, checks if there is already a file with the name
	 */
	public void createNewLevelFile(String name) {
		try {
			File f = new File(getPathName());
			if (f.createNewFile()) {
				System.out.println("Created new file " + name);
				writeNewFile(f, name);
				wasSaved=true;
			}
			else {
				System.out.println("Already exists");
				wasSaved = false;
			}
		} catch (IOException e) {
			System.out.println("An error occurred");
		}
	}
	
	/**
	 * Writes to a new file in a format able to be read by level reader
	 * @param f file being passed
	 */
	public void writeNewFile(File f, String name) throws IOException {
		FileWriter p = new FileWriter(getPathName());
		p.write(width + "," + height + "," + maxRats + "," + parTime);
		p.write("\n");
		for (int i = 0; i < 8; i++) {
			p.write(dropRates[i]+"");
			if (i != 7)
				p.write(",");
		}
		p.write("\n");
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (map[x][y] instanceof Grass) {
					p.write("G");
				}
				if (map[x][y] instanceof GrassB) {
					p.write("g");
				}
				else if (map[x][y] instanceof Path) {
					p.write("P");
				}
				else if (map[x][y] instanceof PathB) {
					p.write("p");
				}
				else if (map[x][y] instanceof Tunnel) {
					p.write("T");
				}
				else if (map[x][y] instanceof TunnelB) {
					p.write("t");
				}
			}
			p.write("\n");
		}
		for (Rat rat : rats) {
			p.write("(");
			p.write(LevelFileReader.ratToStr(rat));
			p.write(")");
			p.write("\n");
		}
		p.close();
	}
	
	/**
	 * Static method to delete a file 
	 * @param name of the file being deleted
	 */
	public static void deleteFile(String name) {
		File f = new File("resources\\" + name + ".txt");
		if (f.delete()) {
			System.out.println(name + " deleted");
		}
		else {
			System.out.println("Failed to delete");
		}
	}

	/**
	 * 
	 * @return boolean of if the level has been saved or not.
	 */
	public boolean wasSaved() {
		return wasSaved;
	}
}
