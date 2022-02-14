public class CreateCustomLevelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int height = 2;
		int width = 2;
		
		Tile[][] map = new Tile[height][width];
		
		int parTime = 10;
		int maxRats = 10;
		int[] dropRates = {1,1,1,1,1,1,1,1};
		
		Grass g = new Grass();
		Tunnel t = new Tunnel();
		Path p = new Path();
		map[0][0] = g;
		map[0][1] = g;
		map[1][0] = t;
		map[1][1] = p;
		
		
		AdultMale r = new AdultMale(1, Rat.Direction.NORTH, 0, 1, 1, true);
		map[0][0].addOccupantRat(r);
		SaveCustomLevel s = new SaveCustomLevel("BEEFYSTU", width, height, map, maxRats, parTime, dropRates);
	}

}
