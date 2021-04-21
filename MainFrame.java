import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	private static final int TIMER_WAIT = 700;
	private final GameLogic gameLogic;
	private final GraphicsPanel graphicsPanel;
	private final JButton playButton;
	private Timer timer;

	MainFrame() {
		
		gameLogic = new GameLogic();
		graphicsPanel = new GraphicsPanel();
		playButton = new JButton("Play");
		JButton clearButton = new JButton("Clear");
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(playButton);
		buttonPanel.add(clearButton);
		
		graphicsPanel.addMouseListener(this);
		graphicsPanel.addMouseMotionListener(this);
		playButton.addActionListener(this);
		clearButton.addActionListener(this);

		this.setTitle("Game Of Life");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int xDim = GraphicsPanel.CELL_SIZE * GameLogic.NUM_COLUMBS;
		int yDim = GraphicsPanel.CELL_SIZE * GameLogic.NUM_ROWS;
		graphicsPanel.setPreferredSize(new Dimension(xDim, yDim));
		this.add(graphicsPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.NORTH);
		this.pack();
		
		//draw start position graphics 
		redrawGraphics();
	}
	
	private void redrawGraphics() {
		graphicsPanel.setGraphicsGrid(gameLogic.getGrid());
		graphicsPanel.repaint();
	}

	private void drawNextGen() {
		gameLogic.updateLife();
		redrawGraphics();
	}
	
	private void pauseTimer() {
	    timer.cancel();
	}

	private void startTimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				drawNextGen();
			}
		}, 0, TIMER_WAIT);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if("Play".equals(e.getActionCommand())) {
			startTimer();
			playButton.setText("Stop");
		} else if("Stop".equals(e.getActionCommand())) {
			pauseTimer();
			playButton.setText("Play");
		} else if("Clear".equals(e.getActionCommand())) {
			gameLogic.clearGrid();
			redrawGraphics();
		}
	}
	
	private int[] mousePosToCell(int xPixel, int yPixel) {
		int[] element = new int[2];
		element[0] = xPixel / GraphicsPanel.CELL_SIZE;
		element[1] = yPixel / GraphicsPanel.CELL_SIZE;
		return element;
	} 
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int[] clickedCell = mousePosToCell(e.getX(), e.getY());
		gameLogic.flipCell(clickedCell[0], clickedCell[1]);
		redrawGraphics();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int[] clickedCell = mousePosToCell(e.getX(), e.getY());
		gameLogic.fillCell(clickedCell[0], clickedCell[1]);
		redrawGraphics();
	}

	public static void main(String[] args){
		new MainFrame();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
}
