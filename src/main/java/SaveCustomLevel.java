import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	
	private ArrayList<Rat> rats;

	private boolean wasSaved;
	
	/**
	 * Constructs a save custom level object
	 * @param name of the custom level
	 * @param width of the cutstom level
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
	 * @param name
	 */
	private void setName(String name) {
		if (name.contains("inProgress")) {
			name = name.replaceAll("inProgress", "New");
		}
		this.name = name.replaceAll("\\s+", "");
	}
	
	/**
	 * Sets the width of the level
	 * @param width
	 */
	private void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Sets the height of the level
	 * @param height
	 */
	private void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Sets the map of the level
	 * @param map
	 */
	private void setMap(Tile[][] map) {
		this.map = map;
	}
	
	/**
	 * Sets the max rats of the level
	 * @param maxRats
	 */
	private void setMaxRats(int maxRats) {
		this.maxRats = maxRats;
	}
	
	/**
	 * Sets the winning time of the level
	 * @param parTime
	 */
	private void setParTime(int parTime) {
		this.parTime = parTime;
	}
	
	/**
	 * Sets the item drop rates for inventory
	 * @param dropRates
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
	 * @return
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
		ArrayList<Rat> rats = new ArrayList<Rat>();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (map[x][y].getOccupantRats().size() == 0) {
					
				}
				else {
					ArrayList<Rat> occRats = (map[x][y].getOccupantRats());
					for (int i = 0; i < occRats.size(); i++) {
						rats.add(occRats.get(i));
					}
				}
			}
		}
		return rats;
	}
	
	/**
	 * Creates new level file with name, checks if 
	 * if there is already a file with the name
	 * @param name
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
			System.out.println("An error occured");
		}
	}
	
	/**
	 * Writes to a the new file in a format able to be read by level reader
	 * @param f file being passed
	 * @throws IOException 
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
				else if (map[x][y] instanceof Path) {
					p.write("P");
				}
				else if (map[x][y] instanceof Tunnel) {
					p.write("T");
				}
			}
			p.write("\n");
		}
		for (int i = 0; i < rats.size(); i++) {
			p.write("(");
			p.write(LevelFileReader.ratToStr(rats.get(i)));
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
