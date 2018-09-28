import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

//the game frame
public class GameFrame extends JFrame implements ActionListener{
	public static int Width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int Height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
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
		this.setBounds(Width/5, Height/8, Width*3/5 + 14, Height*3/4 + 30);
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
	
	/*public void regame() {
		JPanel content = (JPanel)this.getContentPane();
		content.removeAll();
		revalidate();
	}*/
}