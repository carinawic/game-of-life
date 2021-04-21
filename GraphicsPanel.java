import java.awt.*;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel {
	
	public static final int CELL_SIZE = 30;
	private boolean[][] graphicsGrid;
	
	public GraphicsPanel() {
		graphicsGrid = new boolean[GameLogic.NUM_COLUMBS][GameLogic.NUM_ROWS];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		
		for(int x = 0; x < GameLogic.NUM_COLUMBS; x++) {
			for(int y = 0; y < GameLogic.NUM_ROWS; y++) {
				//if cell is inhabited then fillRect, else drawRect
				if(graphicsGrid[x][y]) {
					g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				} else {
					g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				}
			} 
		}
	}
	
	public boolean[][] getGraphicsGrid() {
		return graphicsGrid;
	}
	
	public void setGraphicsGrid(boolean[][] newGrid) {
		graphicsGrid = newGrid;
	}
}