import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	int width = 800, height = 800;
	
	private Game game = new Game();
	private GameFrame gf;
	private ComputerSet CS;
	private ValidLocation val = new ValidLocation();
	private MouseListener ml = new PanelListener();
	private MouseMotionListener mml = new PanelListener();
	private Point mp;
	public static String[][] setted;
	
	public static String ComColor;
	public static String UserColor;
	
	private ArrayList<Point> comL = new ArrayList<Point>();
	private ArrayList<Point> userL = new ArrayList<Point>();
	
	private BasicStroke stroke = new BasicStroke(4);
	private Color color = new Color(255, 19, 73);
	public static int isBlack = 0;
	Image img;

	public GamePanel() {
		this.setLayout(null);
		this.setBounds(0, 0, width, height);
		this.setBackground(new Color(220,179,92));
		addinfo();
		repaint();
		setted = new String[19][19];
		GamePanel.setted[9][9] = "Black";
		this.addMouseListener(ml);
		this.addMouseMotionListener(mml);
	}
	
	public void addinfo() {			//add information about exact location for go tile
		for (int i = 40; i <= width - 40; i+=40)
			val.x.add(i);
		for (int j = 40; j <= height - 40; j+=40)
			val.y.add(j);
		//create valid point
		val.createValidpoint();
	}
	
	public void setcolor(String comcolor, String usercolor) {
		ComColor = comcolor;
		UserColor = usercolor;
		
		if (ComColor.equals("White")) {
			comL = game.getWsp();
			userL = game.getBsp();
		}
		else {
			comL = game.getBsp();
			userL = game.getWsp();
		}
	}
	
	public Game getGame() {return game;}
	
	public void paint(Graphics g) {
		super.paint(g);
		drawline(g);
		drawstones(g);
		drawlaststones(g);
		
		if ((Turn().equals(UserColor) && mp == null)|| (Turn().equals(ComColor)))
			return;
		
		drawrecent(g);
	}
	
	private void drawrecent(Graphics g) {
		if (Turn().equals("White"))		//check which stone should be drawn
			img = Stone.white;
		else
			img = Stone.black;
		
		if (Turn().equals(UserColor))
			g.drawImage(img, mp.x - 20, mp.y - 20, null);
	}
	
	private void drawstones(Graphics g) {	//draw stones of go tile which already placed
		for (Point p : game.getBsp())
			g.drawImage(Stone.black, p.x - 20, p.y - 20, null);
		for (Point p : game.getWsp())
			g.drawImage(Stone.white, p.x - 20, p.y - 20, null);
	}
	
	private void drawline(Graphics g) {		//draw grid line of go tile
		for (int i = 40; i <= width - 40; i+=40)
			g.drawLine(i, 40, i, height - 40);
			
		for (int j = 40; j <= height - 40; j+=40)
			g.drawLine(40, j, width - 40, j);
	}
	
	private void drawlaststones(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(stroke);
		g2.setColor(color);
		
		if (!Turn().equals(ComColor)) {
			g2.drawOval(comL.get(comL.size() -1).x - 21, comL.get(comL.size() -1).y -21, Stone.STONE_SIZE + 1, Stone.STONE_SIZE + 1);
			if (comL.size() != 1)
				g2.drawOval(comL.get(comL.size() -2).x - 21, comL.get(comL.size() -2).y -21, Stone.STONE_SIZE + 1, Stone.STONE_SIZE + 1);
		}
	}
	
	public String Turn() {
		if (isBlack % 4 <= 1)
			return "White";
		else
			return "Black";
	}
	
	public void start(GameFrame gf) {
		this.gf = gf;
		CS = new ComputerSet(this);
		
		if (ComColor.equals("White")) {
			Cturn();
		}
	}
	
	public void winnerCheck(Point a) {
		
		String stone = game.judge(a, Turn());
		if (stone != null) {	//if there is winner
			PlayerPanel.winner.showwinner(stone);
			gameover();
		}
		else {
			isBlack++;
			if (Turn().equals(ComColor)) {
				//System.out.println("Now AI turn");
				Cturn();
			}
		}
	}
	
	public void Cturn() {
		CS.putStone();
		
		if (comL.size() < 5) {
			isBlack+=2;
			repaint();
			return;
		}
		
		
		String win = game.judge(comL.get(comL.size() - 1), Turn());
		if (win == null) {
			win = game.judge(comL.get(comL.size() - 2), Turn());
			if (win != null) {
				PlayerPanel.winner.showwinner(win);
				gameover();
			}
		}
		else {
			PlayerPanel.winner.showwinner(win);
			gameover();
		}
		
		isBlack+=2;
		repaint();
	}
	
	public class PanelListener implements MouseListener, MouseMotionListener{

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent e) {
			Point a = val.trimpoint(e.getPoint());	//make mouse position to exact position of grid line
			
			if (a == null)
				return;
			
			if(userL.contains(a) || comL.contains(a)) {
				//if any stones are already placed
				JOptionPane.showMessageDialog(null, "You can't put there!", "WARNING", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if (Turn().equals("White")) {
				game.addWhiteStone(new Point(a));
				setted[(a.y - 40) / 40][(a.x - 40) / 40] = "White";
			}
			else {
				game.addBlackStone(new Point(a));
				setted[(a.y - 40) / 40][(a.x - 40) / 40] = "Black";
			}
				
			repaint();
			//repaint to show who's turn is
			gf.getPP().repaint();
			
			winnerCheck(mp);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

		@Override
		public void mouseDragged(MouseEvent arg0) {}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			Point p = val.trimpoint(e.getPoint());
			mp = p;
			repaint();
		}
	}

	//if the game is over, remove all listener so that any players are not available to play game
	public void gameover() {
		repaint();
		this.removeMouseListener(ml);
		this.removeMouseMotionListener(mml);
	}
}
