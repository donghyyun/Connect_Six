import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class SelectColor extends JPanel implements ActionListener{
	public static int Width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int Height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private JLabel title;
	private JButton black, white;
	JButton start;
	private JLabel user, computer;
	private LineBorder border;
	private Boolean selected = false;
	private GameFrame gf;
	
	SelectColor(GameFrame gf) {
		this.gf = gf;
		
		this.setBackground(new Color(160,160,160));
		this.setLayout(null);
		this.setBounds(0, 0, Width*3/5 + 14, Height*3/4 + 30);
		border = new LineBorder(new Color(66, 66, 60), 4, true);
		
		title = new JLabel("Select Color");
		title.setBounds(this.getWidth()/4 + 42, this.getHeight()/10, 500, 100);
		title.setFont(new Font("Arial", Font.BOLD, 80));
		
		user = new JLabel("YOU");
		user.setFont(new Font("Arial", Font.BOLD, 30));
		user.setForeground(new Color(255, 255, 86));
		
		computer = new JLabel("COMPUTER");
		computer.setFont(new Font("Arial", Font.BOLD, 30));
		computer.setForeground(new Color(255, 255, 86));
		
		user.setVisible(false);
		computer.setVisible(false);
		
		black = new JButton(new ImageIcon(".\\src\\Black.png"));
		white = new JButton(new ImageIcon(".\\src\\White.png"));
		
		black.setBorderPainted(false);  
		black.setContentAreaFilled(false);
		
		white.setBorderPainted(false); 
		white.setContentAreaFilled(false);
			
		black.setBounds(203, 270, 270, 270);
		white.setBounds(693, 270, 270, 270);
		
		black.addActionListener(this);
		white.addActionListener(this);
		
		start = new JButton("Start");
		start.setBounds(483, 650, 200, 80);
		start.setFont(new Font("Arial", Font.BOLD, 30));
		start.setBorder(border);
		start.setBackground(new Color(160,160,160));
		start.setEnabled(false);
		start.setFocusable(false);
		
		this.add(title);
		this.add(black);
		this.add(white);
		this.add(user);
		this.add(computer);
		this.add(start);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == black) {
			if(!selected) {
				int ans = JOptionPane.showConfirmDialog(null, "흑돌로 게임하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);
				if(ans == 0) {
					black.setFocusable(false);
					white.setEnabled(false);
					user.setBounds(298, 540, 100, 70);
					computer.setBounds(743, 540, 300, 70);
					user.setVisible(true);
					computer.setVisible(true);
					start.setEnabled(true);
					selected = true;
					gf.getGP().setcolor("White", "Black");
				}
			}
			else
				JOptionPane.showMessageDialog(null, "이미 선택되었습니다!", "경고", JOptionPane.ERROR_MESSAGE);

		}
		
		if(e.getSource() == white) {
			if(!selected) {
				int ans = JOptionPane.showConfirmDialog(null, "백돌로 게임하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);
				if(ans == 0) {
					white.setFocusable(false);
					black.setEnabled(false);
					computer.setBounds(257, 570, 300, 70);
					user.setBounds(787, 570, 100, 70);
					user.setVisible(true);
					computer.setVisible(true);
					start.setEnabled(true);
					selected = true;
					gf.getGP().setcolor("Black", "White");
				}
			}
			else
				JOptionPane.showMessageDialog(null, "이미 선택되었습니다!", "경고", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}
