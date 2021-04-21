import static org.junit.Assert.*;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GraphicsPanelTest {
	
	boolean[][] emptyExcept22;
	GraphicsPanel graphicsPanel;
	
	@Before
	public void setUp() {
		emptyExcept22 = new boolean[GameLogic.NUM_COLUMBS][GameLogic.NUM_ROWS];
		emptyExcept22[2][2] = true;
		
		graphicsPanel = new GraphicsPanel();
		graphicsPanel.setGraphicsGrid(emptyExcept22);
	}

	@Test
	public void testPaintComponent_fillAndDrawRectWhenRequired() {
		int size = GraphicsPanel.CELL_SIZE;
		Graphics graphicsMock = Mockito.mock(Graphics.class);
		graphicsPanel.paintComponent(graphicsMock);
		
		Mockito.verify(graphicsMock).fillRect(2 * size, 2 * size, size, size); // fillRect when there is a cell
		Mockito.verify(graphicsMock).drawRect(5 * size, 5 * size, size, size); // drawRect when there is no cell
	}
	
	@Test
	public void smoke() {
		//testing the setter and getter
		assertArrayEquals(graphicsPanel.getGraphicsGrid(), emptyExcept22); // get returns the same 2d array
		assertTrue(emptyExcept22[2][2]); // set actually sets the element to true
	}
}