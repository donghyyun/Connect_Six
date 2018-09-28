import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComputerSet {
	
	
	private ArrayList<Point> userL = new ArrayList<Point>();
	private ArrayList<Point> comL = new ArrayList<Point>();
	private ArrayList<Point> commonL = new ArrayList<Point>();
	
	private ArrayList<AScore> sumL = new ArrayList<AScore>();
	
	private Point[] put = new Point[2];
	
	private String ComColor;
	private JudgeMust judgemust;
	private JudgeAttack judgeattack;
	private JudgeDefence judgedefence;
	
	
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
		
		judgemust = new JudgeMust(gp);
		judgeattack = new JudgeAttack(gp);
		judgedefence = new JudgeDefence(gp);
	}
	
	public void putStone() {
		put[0] = null;	put[1] = null;
		
		mustAttack();
		if (put[0] != null)
			return;
		
		mustDefence();
		if (put[1] != null)
			return;
		
		//threeBythree();
		
		judgeattack.attack();
		judgedefence.defence();
		setPoint();
		
		if (put[0] == null) {
			if (commonL.size() >= 2) {
				put[0] = commonL.get(0);
				put[1] = commonL.get(1);
			}
			else if (commonL.size() == 1) {
				int index = 0;
				put[0] = commonL.get(0);
				while (judgeattack.scoreL.get(index).p.equals(put[0]) && index < judgeattack.scoreL.size())
					index++;
				put[1] = judgeattack.scoreL.get(index).p;
			}
			else {
				put[0] = judgeattack.lt.p1;
				if (judgeattack.getScoreL().size() >= 2) {
					put[1] = judgeattack.getScoreL().get(1).p;
				}
				else
					put[1] = judgedefence.lt.p1;
			}
		}
		else {
			if (commonL.size() >= 1) {
				int index = 0;
				while (commonL.get(index).equals(put[0]))
				put[1] = commonL.get(0);
			}
			else {
				//int index = 0;
				put[1] = judgeattack.getScoreL().get(0).p;
				/*while (judgeattack.scoreL.get(index).p.equals(put[0]) && index < judgeattack.scoreL.size())
					index++;
				put[1] = judgedefence.scoreL.get(index).p;*/
			}
		}
		
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
		System.out.println(">>>ATTACK\n");
		
		pts = judgemust.mustAttackJudge();
		if (pts == null) {
			System.out.println("\n<<<ATTACK: NULL");
			return;
		}
		
		put[0] = new Point ((pts.get(0).x + 1) * 40, (pts.get(0).y + 1) * 40);
		put[1] = new Point ((pts.get(1).x + 1) * 40, (pts.get(1).y + 1) * 40);
		
		comL.add(put[0]);
		comL.add(put[1]);
	}
	
	public void mustDefence() {
		String defP;
		
		if (userL.size() < 2)
			return;
		
		System.out.println(">>>MUST DEFENCE\n");
		
		for (int i = 0; i < 2; i++) {
			defP = judgemust.mustDefenceJudge(userL.get(userL.size() - 1));
			if (defP == null) {
				defP = judgemust.mustDefenceJudge(userL.get(userL.size() - 2));
				if (defP == null) {
					System.out.println("\n<<<MUST DEFENCE: NULL");
					return;
				}
				else
					System.out.println("<<<MUST DEFENCE_2");
			}
			else
				System.out.println("<<<MUST DEFENCE_1");
			
			put[i] = mustPoint(defP);
			
			if (put[i] != null) {
				comL.add(put[i]);
				GamePanel.setted[put[i].y/40 - 1][put[i].x/ 40 - 1] = ComColor;
			}
		}
	}
	
	public Point mustPoint(String defP) {
		ArrayList<Point> el1 = judgemust.getEmpty1();
		ArrayList<Point> el2 = judgemust.getEmpty2();
		
		if (el1.size() >= 2 && el2.size() >= 2) {			
			for (int i = 0; i < el1.size() - 1; i++) {
				if (el1.get(i).distance(el1.get(i + 1)) > 57)
					return el1.get(i); //return el1.get(i); or el1.get(i + 1);
			}
			
			for (int i = 0; i < el2.size() - 1; i++) {
				if (el1.get(i).distance(el2.get(i + 1)) > 57)
					return el2.get(i); //return el2.get(i); or el2.get(i + 1);
			}
		}
		else if (el1.size() >= 2) {
			Point a = el1.get(midEmptyPoint(el1));
			if (isDefenced(a))
				return a;
			else if (el2.size() > 0)
				return el2.get(0);
			return el1.get(midEmptyPoint(el1));
		}			
		else if (el2.size() >= 2) {
			Point a = el2.get(midEmptyPoint(el2));
			if (isDefenced(a))
				return a;
			else if (el1.size() > 0)
				return el1.get(0);
			return el2.get(midEmptyPoint(el2));
		}
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
	
	public boolean isDefenced(Point p) {
		GamePanel.setted[p.y/40 - 1][p.x/40 - 1] = GamePanel.ComColor;
		
		int empty = 0;
		int user = 0;
		
		for (int iy = 0; iy < 19; iy++) {
			for (int ix = 0; ix < 14; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy][ix + k] != null) {
						if (GamePanel.setted[iy][ix + k].equals(GamePanel.UserColor))
							user++;
					}
					else
						empty++;
					
					if (user + empty == 6 && empty <= 2) {
						GamePanel.setted[p.y/40 - 1][p.x/40 - 1] = null;
						return false;
					}
						
				}
				user = 0;
				empty = 0;
			}
		}
		
		for (int iy = 0; iy < 14; iy++) {
			for (int ix = 0; ix < 19; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy + k][ix] != null) {
						if (GamePanel.setted[iy + k][ix].equals(GamePanel.UserColor))
							user++;
					}
					else
						empty++;
					
					if (user + empty == 6 && empty <= 2) {
						GamePanel.setted[p.y/40 - 1][p.x/40 - 1] = null;
						return false;
					}
				}
				user = 0;
				empty = 0;
			}
		}
		
		for (int iy = 0; iy < 14; iy++) {
			for (int ix = 0; ix < 14; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy + k][ix + k] != null) {
						if (GamePanel.setted[iy + k][ix + k].equals(GamePanel.UserColor))
							user++;
					}
					else
						empty++;
					
					if (user + empty == 6 && empty <= 2) {
						GamePanel.setted[p.y/40 - 1][p.x/40 - 1] = null;
						return false;
					}
				}
				user = 0;
				empty = 0;
			}
		}
		
		for (int iy = 0; iy < 14; iy++) {
			for (int ix = 5; ix < 19; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy + k][ix - k] != null) {
						if (GamePanel.setted[iy + k][ix - k].equals(GamePanel.UserColor))
							user++;
					}
					else
						empty++;
					
					if (user + empty == 6 && empty <= 2) {
						GamePanel.setted[p.y/40 - 1][p.x/40 - 1] = null;
						return false;
					}
				}
				user = 0;
				empty = 0;
			}
		}
		GamePanel.setted[p.y/40 - 1][p.x/40 - 1] = null;
		return true;
	}
	
	public void setPoint() {
		commonL.clear();
		
		ArrayList<AScore> attackL = judgeattack.getScoreL();
		ArrayList<AScore> defenceL = judgedefence.getScoreL();
		
		int range1 = min(attackL.size(), 3);
		int range2 = min(defenceL.size(), 3);
		
		for (int i = 0; i < range1; i++) {
			for (int j = 0; j < range2; j++) {
				if (attackL.get(i).p.equals(defenceL.get(j).p))
					commonL.add(attackL.get(i).p);
			}
		}
		
		if (commonL.size() > 0) {
			System.out.println(">>>Common Point");
			for (Point a: commonL) {
				System.out.println(a);
			}
		}
		else {
			System.out.println(">>>Common Point : NULL");
			sumL.clear();
			for (AScore as : attackL) {
				sumL.add(as);
			}
			for (AScore as : defenceL) {
				sumL.add(as);
			}
			
			Collections.sort(sumL, new Comparator<AScore>() {
				@Override
				public int compare(AScore as1, AScore as2) {
					// TODO Auto-generated method stub
					if (as1.getScore() < as2.getScore())
						return 1;
					else if (as1.getScore() > as2.getScore())
						return -1;
					
					return 0;
				}
		      });
		}
			
	}
	
	public int min(int a, int b) {return (a > b)? b : a;}
	
	
	public void randomPut() {
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
			
			System.out.println(">>>RANDOM PUT" + i + "x, y: " + put[i].x +", " + put[i].y);
		}
	}
	
	/*public void threeBythree() {
		int count = 0;
		
		if (userL.size() < 5)
			return;
		
		System.out.println(">>>THREE BY THREE");
		judgemust.threeBythree();
		System.out.println("last stone: " + count);
		
		if (count < 2) {
			judgemust.threeBythree();
			System.out.println(" + second last stone: " + count);
			if (count < 2) {
				System.out.println("<<<THREE BY THREE: NULL");
				return;
			}
			else {
				System.out.println("<<<THREE BY THREE_2:");
				tbtPoint();
			}
		}
		else {
			System.out.println("<<<THREE BY THREE_1:");
			tbtPoint();
		}
		
	}*/
	
	public void tbtPoint() {
		ArrayList<Point> tbt = judgemust.getTbt();
		System.out.println(">>>TBT Point<<<");
		for (Point a: tbt)
			System.out.println(a);
	}
	
}
