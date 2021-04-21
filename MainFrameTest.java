import static org.junit.Assert.*;

import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

public class MainFrameTest {
	
	private MainFrame mainFrame;
	private JPanel buttonPanel;
	private GraphicsPanel graphicsPanel;
	
	@Before
	public void setUp() {
		mainFrame = new MainFrame();
		buttonPanel = (JPanel) mainFrame.getContentPane().getComponent(1);
		graphicsPanel = (GraphicsPanel) mainFrame.getContentPane().getComponent(0);
		
	}

	@Test
	public void testActionPerformed_clearButton_clearsTheGrid() {
		JButton clearButton = (JButton) buttonPanel.getComponent(1);
		
		clearButton.doClick(); //we press "Clear"
		
		boolean[][] empty = new boolean[GameLogic.NUM_COLUMBS][GameLogic.NUM_ROWS];
		assertArrayEquals(empty, graphicsPanel.getGraphicsGrid());
	}
	
	@Test
	public void testTimerFunctions_playButton_cellsStartChangingAndStopChangingRespectively() throws InterruptedException {
		JButton playButton = (JButton) buttonPanel.getComponent(0); 
		
		playButton.doClick(); //we press "Play"
		
		boolean[][] playGrid = graphicsPanel.getGraphicsGrid();
		Thread.sleep(700);
		boolean[][] playGridAfterSomeTime = graphicsPanel.getGraphicsGrid();
		
		assertEquals("Stop", playButton.getText()); //now the button says "Stop"
		assertFalse(Arrays.equals(playGrid, playGridAfterSomeTime)); //the grid changes over time
		
		playButton.doClick(); //we press "Stop"

		boolean[][] stopGrid = graphicsPanel.getGraphicsGrid();
		Thread.sleep(700);
		boolean[][] stopGridAfterSomeTime = graphicsPanel.getGraphicsGrid();
		
		assertEquals("Play", playButton.getText()); //now the button says "Play"
		assertArrayEquals(stopGrid, stopGridAfterSomeTime); //the grid no longer changes over time
	}
	
	@Test
	public void testMouseClickedAndMouseDragged_drawOnPanel() {
		//2,4 and 15,15 are false at first
		assertFalse(graphicsPanel.getGraphicsGrid()[2][4]); 
		assertFalse(graphicsPanel.getGraphicsGrid()[15][15]);
		
		//we create a new mouseevent
		MouseEvent mouseEventClick = new MouseEvent(graphicsPanel, MouseEvent.MOUSE_CLICKED, 
				System.currentTimeMillis(), 0, GraphicsPanel.CELL_SIZE * 2, GraphicsPanel.CELL_SIZE * 4, 1, false);
		//we simulate a mouseClick
		graphicsPanel.getMouseListeners()[0].mouseClicked(mouseEventClick);
		assertTrue(graphicsPanel.getGraphicsGrid()[2][4]);
		
		//we create a new mouseevent
		MouseEvent mouseEventDrag = new MouseEvent(graphicsPanel, MouseEvent.MOUSE_DRAGGED, 
				System.currentTimeMillis(), 0, GraphicsPanel.CELL_SIZE * 15, GraphicsPanel.CELL_SIZE * 15, 1, false);
		//we simulate a mouseDrag
		graphicsPanel.getMouseMotionListeners()[0].mouseDragged(mouseEventDrag);
		assertTrue(graphicsPanel.getGraphicsGrid()[15][15]);
	}

	@Test
	public void testMouseClick_removeCell() {	
		//8,8 is true at first because of our start pattern
		assertTrue(graphicsPanel.getGraphicsGrid()[8][8]); 
		
		MouseEvent mouseEventClick = new MouseEvent(graphicsPanel, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 30*8, 30*8, 1, false);
		graphicsPanel.getMouseListeners()[0].mouseClicked(mouseEventClick);
		
		assertFalse(graphicsPanel.getGraphicsGrid()[8][8]); //8,8 is now false after click
	}
	
	@Test
	public void testConstructor_StartingPatternExists() {	
		String reminderText = "Seems you changed the starting pattern. Remember to also change these test cases.";
		//these are the starting pattern cells
		assertTrue(reminderText, graphicsPanel.getGraphicsGrid()[8][8]); 
		assertTrue(reminderText, graphicsPanel.getGraphicsGrid()[8][9]); 
		assertTrue(reminderText, graphicsPanel.getGraphicsGrid()[9][9]); 
		assertTrue(reminderText, graphicsPanel.getGraphicsGrid()[9][10]); 
		assertTrue(reminderText, graphicsPanel.getGraphicsGrid()[7][10]); 
	}
}
