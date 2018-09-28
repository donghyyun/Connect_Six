import java.awt.Point;
import java.util.ArrayList;

public class ComputerSet {
	
	
	private ArrayList<Point> userL = new ArrayList<Point>();
	private ArrayList<Point> comL = new ArrayList<Point>();
	
	private Point[] put = new Point[2];
	private int count = 1;
	//private GamePanel gp;
	private String ComColor;
	private Judge judge;
	private JudgeAttack attack;
	//private JudgeDefense defense;
	
	
	public ComputerSet(GamePanel gp) {
		//this.gp = gp;
		this.ComColor = GamePanel.ComColor;
		
		if (ComColor.equals("Black")) {
			userL = gp.getGame().getWsp();
			comL = gp.getGame().getBsp();
		}
		else {
			userL = gp.getGame().getBsp();
			comL = gp.getGame().getWsp();
		}
		
		judge = new Judge(gp);
		attack = new JudgeAttack(gp);
		//defense = new JudgeDefense(gp);
	}
	
	public void putStone() {
		put[0] = null;	put[1] = null;
		
		mustAttack();
		if (put[0] != null)
			return;
		
		mustDefence();
		if (put[1] != null)
			return;
		
		attack.attack();
		put [0] = attack.lt.p1;
		put [1] = attack.lt.p2;
		//threeBythree();
		attack();
		
		for (int i = 0; i < 2; i++) {
			comL.add(put[i]);
			GamePanel.setted[put[i].y/40 - 1][put[i].x/ 40 - 1] = ComColor;
		}
		//printsetted();
	}
	
	public void printsetted() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++)
				System.out.print(GamePanel.setted[i][j] + "\t");
			System.out.println();
		}
	}
	
	
	public void mustAttack() {
		ArrayList<Point> pts;
		
		if (comL.size() < 2)
			return;
		
		pts = judge.mustAttackJudge();
		
		if (pts == null) {
				System.out.println(">>Attack: null");
				return;
		}
		
		System.out.println(">>attack_1: not Null");
		put[0] = new Point ((pts.get(0).x + 1) * 40, (pts.get(0).y + 1) * 40);
		put[1] = new Point ((pts.get(1).x + 1) * 40, (pts.get(1).y + 1) * 40);
		
		comL.add(put[0]);
		comL.add(put[1]);
	}
	
	public void mustDefence() {
		String defP;
		
		if (userL.size() < 2)
			return;
		
		for (int i = 0; i < 2; i++) {
			defP = judge.mustDefenceJudge(userL.get(userL.size() - 1));
			if (defP == null) {
				defP = judge.mustDefenceJudge(userL.get(userL.size() - 2));
				if (defP == null) {
					System.out.println(">>defence: null");
					return;
				}

				else
					System.out.println(">>defence_2: " + defP);
			}
			else
				System.out.println(">>defence_1: " + defP);
			
			put[i] = mustPoint();
			if (put[i] != null)
				comL.add(put[i]);
		}
	}
	
	public void threeBythree() {
		
	}
	
	public Point mustPoint() {
		ArrayList<Point> el1 = judge.getEmpty1();
		ArrayList<Point> el2 = judge.getEmpty2();
		
		if (el1.size() >= 2 && el2.size() >= 2) {			
			for (int i = 0; i < el1.size() - 1; i++) {
				if (el1.get(i).distance(el1.get(i + 1)) > 57)
					return el1.get(i); // or el1.get(i - 1);
			}
			
			for (int i = 0; i < el2.size() - 1; i++) {
				if (el1.get(i).distance(el2.get(i + 1)) > 57)
					return el2.get(i);
			}
		}
		else if (el1.size() >= 2)
			return el1.get(midEmptyPoint(el1));
		else if (el2.size() >= 2)
			return el2.get(midEmptyPoint(el2));
		else if (el1.size() == 1 && el2.size() == 1) {
			return el1.get(0);	//or el2.get(0)						
		}
		return null;
	}
	
	public int midEmptyPoint (ArrayList<Point> empty) {
		for (int i = 0; i < empty.size() - 1; i++) {
			if (empty.get(i).distance(empty.get(i + 1)) > 57)
				return i;
		}
		
		return 0; // or else
	}
	
	
	public void attack() {
		int avl = 2;
		int start = 0;
		
		if (put[0] != null && put[1] != null)
			return;
		else if (put[0] != null && put[1] == null)
			start = 1;
		
		for (int i = start; i < avl; i++) {
			do {
				int x = 40 *((int)(Math.random() * 10) + 1);
				int y = 40 *((int)(Math.random() * 10) + 1);
				put[i] = new Point(x,y);
			} while (put[i].equals(null) || userL.contains(put[i]) || comL.contains(put[i]));
			
			System.out.println(">>random" + i + "x, y: " + put[i].x +", " + put[i].y);
		}
	}
}
