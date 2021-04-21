/*
 * En cell föds om den har exakt tre grannar. Som grannar räknas direkt intill-liggande rutor horisontellt, lodrätt eller diagonalt.
 * En cell dör om den har färre än två grannar (isolering) eller om den har fler än tre grannar (trängsel).
 * - Wikipedia
 * */

public class GameLogic {
	
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMBS = 20;
	private boolean[][] grid;
	
	GameLogic() {
		grid = new boolean[NUM_COLUMBS][NUM_ROWS];
		//setting up sample shape
		grid[8][8] = true;
		grid[8][9] = true;
		grid[9][9] = true;
		grid[9][10] = true;
		grid[7][10] = true;
	}
	
	private boolean isWithinBounds(int x, int y) {
		return x >= 0 && x < NUM_COLUMBS && y >= 0 && y < NUM_COLUMBS;		
	}

	private int countNeigbors(int x, int y) {
		final int[][] neighbors = {{-1,-1}, {0,-1}, {1,-1}, {-1,0}, {1,0}, {-1,1}, {0,1}, {1,1}};
		int neighborCount = 0;
		
		for(int[] offset: neighbors) {
			final int neighborX = x + offset[0];
			final int neighborY = y + offset[1];
			if(isWithinBounds(neighborX, neighborY)) {
				if(grid[neighborX][neighborY]) {
					neighborCount++;
				}
			}
		}
		return neighborCount;
	}
	
	public void updateLife() {
		final boolean[][] nextGenGrid = new boolean[NUM_COLUMBS][NUM_ROWS];
		
		//sets nextGenGrid to grid
		for(int x = 0; x < NUM_COLUMBS; x++) {
			for(int y = 0; y < NUM_ROWS; y++) {
				nextGenGrid[x][y] = grid[x][y];
			} 
		}
		
		//rules for survival
		for(int x = 0; x < NUM_ROWS; x++) {
			for(int y = 0; y < NUM_COLUMBS; y++) {
				if(countNeigbors(x, y) == 3) {
					nextGenGrid[x][y] = true;
				} else if(countNeigbors(x, y) < 2) {
					nextGenGrid[x][y] = false;
				} else if(countNeigbors(x, y) > 3) {
					nextGenGrid[x][y] = false;
				}
			} 
		}
		grid = nextGenGrid;
	}
	
	public void flipCell(int x, int y) {
		grid[x][y] = !grid[x][y];
	}
	
	public void fillCell(int x, int y) {
		grid[x][y] = true;
	}

	public void clearGrid() {
		grid = new boolean[NUM_ROWS][NUM_COLUMBS];
	}
	
	public boolean[][] getGrid() {
		return grid;
	}
}
