import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameLogicTest {
	GameLogic gameLogic;
	
	@Before
	public void setUp() {
		gameLogic = new GameLogic();
		gameLogic.clearGrid();
		//grid is clear at the start of each test
	}
	
	private boolean getCell(int x,int y) {
		return gameLogic.getGrid()[x][y];
	}
	
	@Test
	public void updateLife_cellsLessThanTwoNeighborsDie() {
		gameLogic.fillCell(4, 4);
		gameLogic.fillCell(8, 8);
		gameLogic.fillCell(8, 9); //8,8 is neighbor with 8,9
		gameLogic.updateLife();
		assertFalse(getCell(4, 4));
		assertFalse(getCell(8, 8));
	}
	
	@Test
	public void updateLife_cellsThreeNeighborsLive() {
		gameLogic.fillCell(4, 4);
		gameLogic.fillCell(4, 5); //4,4 is neighbor with 4,5
		gameLogic.fillCell(4, 3); //4,4 is neighbor with 4,3
		gameLogic.fillCell(5, 5); //4,4 is neighbor with 5,5
		gameLogic.updateLife();
		assertTrue(getCell(4, 4));
	}
	
	@Test
	public void updateLife_cellsMoreThanThreeNeighborsDie() {
		gameLogic.fillCell(4, 4);
		gameLogic.fillCell(4, 5); //4,4 is neighbor with 4,5
		gameLogic.fillCell(4, 3); //4,4 is neighbor with 4,3
		gameLogic.fillCell(5, 5); //4,4 is neighbor with 5,5
		gameLogic.fillCell(3, 3); //4,4 is neighbor with 3,3
		gameLogic.updateLife();
		assertFalse(getCell(4, 4));
	}

	@Test
	public void flipCell_flipsTheCellsAliveOrDeadStatus() {
		gameLogic.fillCell(4,4);
		gameLogic.flipCell(4,4);
		gameLogic.flipCell(8,8);
		assertFalse(getCell(4, 4));		
		assertTrue(getCell(8, 8));
	}

	@Test
	public void clearGrid_clearsTheGrid() {
		boolean[][] empty = new boolean[GameLogic.NUM_COLUMBS][GameLogic.NUM_COLUMBS];
		assertArrayEquals(gameLogic.getGrid(), empty);
	}
	
	@Test
	public void smoke() { // we test setter and getter
		// we set only cell 5,5 to true
		gameLogic.fillCell(5, 5);

		//getCell returns expected values
		assertTrue(getCell(5, 5)); 
		assertFalse(getCell(8, 8));
	}
}