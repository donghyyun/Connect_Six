import java.awt.Point;
import java.util.ArrayList;

public class JudgeMust {
	
	private ArrayList<Point> UserL;
	private ArrayList<Point> ComL;	
	
	private ArrayList<Point> Empty1 = new ArrayList<Point>();
	private ArrayList<Point> Empty2 = new ArrayList<Point>();
	private ArrayList<Point> tbt = new ArrayList<Point>();
	
	
	public JudgeMust(GamePanel gp) {		
		if (GamePanel.ComColor.equals("Black")) {
			UserL = gp.getGame().getWsp();
			ComL = gp.getGame().getBsp();
		}
		else {
			UserL = gp.getGame().getBsp();
			ComL = gp.getGame().getWsp();
		}
	}
	
	public ArrayList<Point> getEmpty1() {return Empty1;}
	public ArrayList<Point> getEmpty2() {return Empty2;}
	public ArrayList<Point> getTbt() {return tbt;}
	
	//sliding window
	public ArrayList<Point> mustAttackJudge() {
		
		ArrayList<Point> emptyL = new ArrayList<Point>();
		int com = 0;
		
		for (int iy = 0; iy < 19; iy++) {
			for (int ix = 0; ix < 14; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy][ix + k] != null) {
						if (GamePanel.setted[iy][ix + k].equals(GamePanel.ComColor))
							com++;
					}
					else
						emptyL.add(new Point(ix + k, iy));
					
					if (com + emptyL.size() == 6 && emptyL.size() <= 2) 
						return emptyL;
				}
				com = 0;
				emptyL.clear();
			}
		}
		
		for (int iy = 0; iy < 14; iy++) {
			for (int ix = 0; ix < 19; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy + k][ix] != null) {
						if (GamePanel.setted[iy + k][ix].equals(GamePanel.ComColor))
							com++;
					}
					else
						emptyL.add(new Point(ix, iy + k));
					
					if (com + emptyL.size() == 6 && emptyL.size() <= 2) 
						return emptyL;
					
				}
				com = 0;
				emptyL.clear();
			}
		}
		
		for (int iy = 0; iy < 14; iy++) {
			for (int ix = 0; ix < 14; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy + k][ix + k] != null) {
						if (GamePanel.setted[iy + k][ix + k].equals(GamePanel.ComColor))
							com++;
					}
					else
						emptyL.add(new Point(ix + k, iy + k));
					
					if (com + emptyL.size() == 6 && emptyL.size() <= 2) 
						return emptyL;
					
				}
				com = 0;
				emptyL.clear();
			}
		}
		
		for (int iy = 0; iy < 14; iy++) {
			for (int ix = 5; ix < 19; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy + k][ix - k] != null) {
						if (GamePanel.setted[iy + k][ix - k].equals(GamePanel.ComColor))
							com++;
					}
					else
						emptyL.add(new Point(ix - k, iy + k));
					
					if (com + emptyL.size() == 6 && emptyL.size() <= 2) 
						return emptyL;
					
				}
				com = 0;
				emptyL.clear();
			}
		}
		return null;
	}
	
	public String mustDefenceJudge(Point p) {
		Empty1.clear();
		Empty2.clear();
		
		int a = judgeUp(p);
		int b = judgeDown(p);
		
		if (a + b - (Empty1.size() + Empty2.size()) >= 5 && (Empty1.size() >= 1 || Empty2.size() >= 1)) {
			System.out.println(">>>Defence JudgeUpDown:\t<" + p.x + ", " + p.y + ">" + "\nJudgeUp: " + a +"\tJudgeDown: " + b);
			print_emptylist();
			return "UpDown";
		}
		else {
			Empty1.clear();
			Empty2.clear();
		}
		
		a = judgeLeft(p);
		b = judgeRight(p);
		
		if(a + b - (Empty1.size() + Empty2.size()) >= 5 && (Empty1.size() >= 1 || Empty2.size() >= 1)) {
			System.out.println(">>>Defence JudgeLeftRight:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeft: " + a +"\tJudgeRight: " + b);
			return "LeftRight";
		}
		else {
			Empty1.clear();
			Empty2.clear();
		}
		
		a = judgeLeftUp(p);
		b = judgeRightDown(p);
		
		if(a + b - (Empty1.size() + Empty2.size()) >= 5 && (Empty1.size() >= 1 || Empty2.size() >= 1)) {
			System.out.println(">>>Defence JudgeLeftUp:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeftUp: " + a +"\tJudgeRightDown: " + b);
			return "LeftUp";
		}
			
		else {
			Empty1.clear();
			Empty2.clear();
		}
		
		a = judgeLeftDown(p);
		b = judgeRightUp(p);
		
		if(a + b - (Empty1.size() + Empty2.size()) >= 5 && (Empty1.size() >= 1 || Empty2.size() >= 1)) {
			System.out.println(">>>Defence JudgeRightUp:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeftDown: " + a +"\tJudgeRightUp: " + b);
			return "RightUp";
		}
		else {
			Empty1.clear();
			Empty2.clear();
		}
		
		return null;
	}
	
	//sliding window
	/*public ArrayList<Point> threeBythree () {
		ArrayList<Point> emptyL = new ArrayList<Point>();
		int user = 0;
		int com = 0;
		int count = 0;
		
		for (int iy = 0; iy < 19; iy++) {
			for (int ix = 0; ix < 14; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy][ix + k] != null) {
						if (GamePanel.setted[iy][ix + k].equals(GamePanel.ComColor))
							com++;
						else
							user++;
					}
					else
						emptyL.add(new Point(ix + k, iy));
					
					if (com + emptyL.size() == 6 && emptyL.size() <= 2) 
						return emptyL;
				}
				com = 0;
				emptyL.clear();
			}
		}
		
		for (int iy = 0; iy < 14; iy++) {
			for (int ix = 0; ix < 19; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy + k][ix] != null) {
						if (GamePanel.setted[iy + k][ix].equals(GamePanel.ComColor))
							com++;
					}
					else
						emptyL.add(new Point(ix, iy + k));
					
					if (com + emptyL.size() == 6 && emptyL.size() <= 2) 
						return emptyL;
					
				}
				com = 0;
				emptyL.clear();
			}
		}
		
		for (int iy = 0; iy < 14; iy++) {
			for (int ix = 0; ix < 14; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy + k][ix + k] != null) {
						if (GamePanel.setted[iy + k][ix + k].equals(GamePanel.ComColor))
							com++;
					}
					else
						emptyL.add(new Point(ix + k, iy + k));
					
					if (com + emptyL.size() == 6 && emptyL.size() <= 2) 
						return emptyL;
					
				}
				com = 0;
				emptyL.clear();
			}
		}
		
		for (int iy = 0; iy < 14; iy++) {
			for (int ix = 5; ix < 19; ix++) {
				for (int k = 0; k < 6; k++) {
					if (GamePanel.setted[iy + k][ix - k] != null) {
						if (GamePanel.setted[iy + k][ix - k].equals(GamePanel.ComColor))
							com++;
					}
					else
						emptyL.add(new Point(ix - k, iy + k));
					
					if (com + emptyL.size() == 6 && emptyL.size() <= 2) 
						return emptyL;
					
				}
				com = 0;
				emptyL.clear();
			}
		}
		return null;
	}*/
	
	/*public int threeByThree (Point p) {
		Empty1.clear();
		Empty2.clear();
		int count = 0;
		
		int a = judgeUp(p);
		int b = judgeDown(p);
		
		if (a + b - (Empty1.size() + Empty2.size()) == 4 && (Empty1.size() + Empty2.size() >= 4))  {
			System.out.println(">>>3 * 3 JudgeUpDown:\t<" + p.x + ", " + p.y + ">" + "\nJudgeUp: " + a +"\tJudgeDown: " + b);
			//print_emptylist();
			count++;
			for (int i = 0; i < Empty1.size(); i++)
				tbt.add(Empty1.get(i));
			for (int i = 0; i < Empty2.size(); i++)
				tbt.add(Empty2.get(i));
		}
		Empty1.clear();
		Empty2.clear();
		
		a = judgeLeft(p);
		b = judgeRight(p);
		
		if(a + b - (Empty1.size() + Empty2.size()) == 4 && (Empty1.size() + Empty2.size() >= 1)) {
			System.out.println(">>>3 * 3 JudgeLeftRight:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeft: " + a +"\tJudgeRight: " + b);
			count++;
			for (int i = 0; i < Empty1.size() - 2; i++)
				tbt.add(Empty1.get(i));
			for (int i = 0; i < Empty2.size() - 2; i++)
				tbt.add(Empty2.get(i));
		}
		Empty1.clear();                                                            
		Empty2.clear();
		
		a = judgeLeftUp(p);
		b = judgeRightDown(p);
		
		if(a + b - (Empty1.size() + Empty2.size()) == 4 && (Empty1.size() >= 1 || Empty2.size() >= 1)) {
			System.out.println(">>>3 * 3 JudgeLeftUp:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeftUp: " + a +"\tJudgeRightDown: " + b);
			count++;
			for (int i = 0; i < Empty1.size() - 2; i++)
				tbt.add(Empty1.get(i));
			for (int i = 0; i < Empty2.size() - 2; i++)
				tbt.add(Empty2.get(i));
		}
		Empty1.clear();
		Empty2.clear();
		
		
		a = judgeLeftDown(p);
		b = judgeRightUp(p);
		
		if(a + b - (Empty1.size() + Empty2.size()) == 4 && (Empty1.size() >= 1 || Empty2.size() >= 1) && (Empty1.size() <= 3 || Empty2.size() <= 3)) {
			System.out.println(">>>3 * 3 JudgeRightUp:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeftDown: " + a +"\tJudgeRightUp: " + b);
			count++;
			for (int i = 0; i < Empty1.size() - 2; i++)
				tbt.add(Empty1.get(i));
			for (int i = 0; i < Empty2.size() - 2; i++)
				tbt.add(Empty2.get(i));
		}
		Empty1.clear();
		Empty2.clear();
		
		return count;
	}*/
	
	/*public int mustAttackJudge(Point p) {
		Empty1.clear();
		Empty2.clear();
		
		int std = 5;
		int []value = {0, 0, 0, 0};
		int max = 0;
		int maxindex = -1;
		
		int a = judgeUp(p, true);
		int b = judgeDown(p, true);
		
		if (a + b - (upE.size() + downE.size()) >= std) {
			System.out.println(">>>Attack JudgeUpDown:\t<" + p.x + ", " + p.y + ">" + "\nJudgeUp: " + a +"\tJudgeDown: " + b);
			print_emptylist();
			value[0] =  a + b - (upE.size() + downE.size());
		}
		
		a = judgeLeft(p, true);
		b = judgeRight(p, true);
		if(a + b - (leftE.size() + rightE.size()) >= std) {
			System.out.println(">>>Attack JudgeLeftRight:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeft: " + a +"\tJudgeRight: " + b);
			print_emptylist();
			value[1] = a + b - (leftE.size() + rightE.size());
		}
		
		a = judgeLeftUp(p, true);
		b = judgeRightDown(p, true);
		if(a + b - (leftupE.size() + rightdownE.size()) >= std) {
			System.out.println(">>>Attack JudgeLeftUp:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeftUp: " + a +"\tJudgeRightDown: " + b);
			print_emptylist();
			value[2] = a + b - (leftupE.size() + rightdownE.size());
		}
		
		a = judgeLeftDown(p, true);
		b = judgeRightUp(p, true);
		if(a + b - (leftdownE.size() + rightupE.size()) >= std) {
			System.out.println(">>>Attack JudgeRightUp:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeftDown: " + a +"\tJudgeRightUp: " + b);
			print_emptylist();
			value[3] = a + b - (leftdownE.size() + rightupE.size());
		}		
		//System.out.println(p + orient);
		
		for (int i = 0; i <value.length; i++) {
			if (value[i] > max) {
				max = value[i];
				maxindex = i;
			}
		}
		
		switch (maxindex) {
		case 0:
			Empty1 = upE;
			Empty2 = downE;
			break;
		case 1:
			Empty1 = leftE;
			Empty2 = rightE;
			break;
		case 2:
			Empty1 = leftupE;
			Empty2 = rightdownE;
			break;
		case 3:
			Empty1 = rightupE;
			Empty2 = leftdownE;
		}
		return max;
	}*/

	public void print_emptylist() {
		System.out.println("#Empty1: ");
		for (Point a: Empty1) {
			System.out.println(a.x/40 + ", " + a.y/40);
		}
		
		
		System.out.println("#Empty2: ");
		for (Point a: Empty2) {
			System.out.println(a.x/40 + ", " + a.y/40);
		}
	}
	
	public int judgeUp(Point p) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("Up empty: <" + p.x/40 + ", " + p.y/40 + ">");
			Empty1.add(p);
			if (Empty1.size() >= 3) {
				if (Empty1.get(Empty1.size() - 1).distance(Empty1.get(Empty1.size() - 2)) < 57 && 
						Empty1.get(Empty1.size() - 2).distance(Empty1.get(Empty1.size() - 3)) < 57)
				return 1;
			}
		}
		
		if (ComL.contains(p))
			return 0;
		
		return 1 + judgeUp(new Point(p.x, p.y - 40));
	}
	
	public int judgeDown(Point p) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("Down empty: <" + p.x/40 + ", " + p.y/40 + ">");
			Empty2.add(p);
			if (Empty2.size() >= 3) {
				if (Empty2.get(Empty2.size() - 1).distance(Empty2.get(Empty2.size() - 2)) < 57 && 
						Empty2.get(Empty2.size() - 2).distance(Empty2.get(Empty2.size() - 3)) < 57)
				return 1;
			}	
		}
		
		if (ComL.contains(p))
			return 0;
		
		return 1 + judgeDown(new Point(p.x, p.y + 40));
	}
	
	public int judgeLeft(Point p) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("Left empty: <" + p.x/40 + ", " + p.y/40 + ">");
			Empty1.add(p);
			if (Empty1.size() >= 3) {
				if (Empty1.get(Empty1.size() - 1).distance(Empty1.get(Empty1.size() - 2)) < 57 && 
						Empty1.get(Empty1.size() - 2).distance(Empty1.get(Empty1.size() - 3)) < 57)
				return 1;
			}
		}
		
		if (ComL.contains(p))
			return 0;
		
		return 1 + judgeLeft(new Point(p.x - 40, p.y)); 
	}
	
	public int judgeRight(Point p) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("Right empty: <" + p.x/40 + ", " + p.y/40 + ">");
			Empty2.add(p);
			if (Empty2.size() >= 3) {
				if (Empty2.get(Empty2.size() - 1).distance(Empty2.get(Empty2.size() - 2)) < 57 && 
						Empty2.get(Empty2.size() - 2).distance(Empty2.get(Empty2.size() - 3)) < 57)
				return 1;
			}
		}

		if (ComL.contains(p))
			return 0;
		
		return 1 + judgeRight(new Point(p.x + 40, p.y)); 
	}
	
	public int judgeRightUp(Point p) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("RightUp empty: <" + p.x/40 + ", " + p.y/40 + ">");
			Empty2.add(p);
			if (Empty2.size() >= 3) {
				if (Empty2.get(Empty2.size() - 1).distance(Empty2.get(Empty2.size() - 2)) < 57 && 
						Empty2.get(Empty2.size() - 2).distance(Empty2.get(Empty2.size() - 3)) < 57)
				return 1;
			}
		}

		if (ComL.contains(p))
			return 0;
		
		return 1 + judgeRightUp(new Point(p.x + 40, p.y - 40)); 
	}
	
	public int judgeRightDown(Point p) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("RightDown empty: <" + p.x/40 + ", " + p.y/40 + ">");
			Empty2.add(p);
			if (Empty2.size() >= 3) {
				if (Empty2.get(Empty2.size() - 1).distance(Empty2.get(Empty2.size() - 2)) < 57 && 
						Empty2.get(Empty2.size() - 2).distance(Empty2.get(Empty2.size() - 3)) < 57)
				return 1;
			}
		}

		if (ComL.contains(p))
			return 0;
		
		return 1 + judgeRightDown(new Point(p.x + 40, p.y + 40)); 
	}
	
	public int judgeLeftUp(Point p) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("LeftUp empty: <" + p.x/40 + ", " + p.y/40 + ">");
			Empty1.add(p);
			if (Empty1.size() >= 3) {
				if (Empty1.get(Empty1.size() - 1).distance(Empty1.get(Empty1.size() - 2)) < 57 && 
						Empty1.get(Empty1.size() - 2).distance(Empty1.get(Empty1.size() - 3)) < 57)
				return 1;
			}
		}

		if (ComL.contains(p))
			return 0;
		
		return 1 + judgeLeftUp(new Point(p.x - 40, p.y - 40)); 
	}
	
	public int judgeLeftDown(Point p) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("LeftDown empty: <" + p.x/40 + ", " + p.y/40 + ">");
			Empty1.add(p);
			if (Empty1.size() >= 3) {
				if (Empty1.get(Empty1.size() - 1).distance(Empty1.get(Empty1.size() - 2)) < 57 && 
						Empty1.get(Empty1.size() - 2).distance(Empty1.get(Empty1.size() - 3)) < 57)
				return 1;
			}
		}

		if (ComL.contains(p))
			return 0;
		
		return 1 + judgeLeftDown(new Point(p.x - 40, p.y + 40)); 
	}
}
