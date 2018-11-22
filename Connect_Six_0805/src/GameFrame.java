import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

//the game frame
public class GameFrame extends JFrame implements ActionListener{
	public static int Width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int Height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static int Fswidth = Width/5;
	public static int Fewidth = Width*4/5;
	public static int Fsheight = Height/8;
	public static int Feheight = Height*7/8;
	
	private Stone st = new Stone();
	private GamePanel gp = new GamePanel();
	private SelectColor sc = new SelectColor(this);
	public PlayerPanel pp = new PlayerPanel();
	
	public GameFrame() {
		super("Connect SIX");
		setframe();
		sc.start.addActionListener(this);
		this.add(sc);
	}
	
	public void setframe() {
		this.setLayout(null);
		this.setBounds(0, 0, Width /*+ 14*/, Height/* + 30*/);
	}
	
	public void gamestart() {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public GamePanel getGP() {return gp;}
	public PlayerPanel getPP() {return pp;}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sc.start) {
			getContentPane().removeAll();
			this.add(gp);
			this.add(pp);
			revalidate();
			repaint();
			gp.start(this);
		}
	}
	
	public static void main(String[] args) {
		GameFrame gf = new GameFrame();
		gf.gamestart();
	}
}