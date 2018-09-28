import java.awt.Point;
import java.util.ArrayList;

public class Judge {
	
	private ArrayList<Point> UserL;
	private ArrayList<Point> ComL;
	
	private ArrayList<String> window = new ArrayList<String>();
	
	
	private ArrayList<Point> Empty1 = new ArrayList<Point>();
	private ArrayList<Point> Empty2 = new ArrayList<Point>();
	
	private ArrayList<Point> upE = new ArrayList<Point>();
	private ArrayList<Point> downE = new ArrayList<Point>();
	private ArrayList<Point> leftE = new ArrayList<Point>();
	private ArrayList<Point> rightE = new ArrayList<Point>();
	private ArrayList<Point> leftupE = new ArrayList<Point>();
	private ArrayList<Point> rightupE = new ArrayList<Point>();
	private ArrayList<Point> leftdownE = new ArrayList<Point>();
	private ArrayList<Point> rightdownE = new ArrayList<Point>();
	//private ArrayList<Point> 
	
	
	public Judge(GamePanel gp) {		
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
		
		String orient = null;
		
		int a = judgeUp(p);
		int b = judgeDown(p);
		
		if (a + b - (Empty1.size() + Empty2.size()) >= 5 && (Empty1.size() >= 1 || Empty2.size() >= 1)) {
			System.out.println(">>>Defence JudgeUpDown:\t<" + p.x + ", " + p.y + ">" + "\nJudgeUp: " + a +"\tJudgeDown: " + b);
			print_emptylist();
			return orient = "UpDown";
		}
		else {
			Empty1.clear();
			Empty2.clear();
		}
		
		a = judgeLeft(p);
		b = judgeRight(p);
		
		if(a + b - (Empty1.size() + Empty2.size()) >= 5 && (Empty1.size() >= 1 || Empty2.size() >= 1)) {
			System.out.println(">>>Defence JudgeLeftRight:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeft: " + a +"\tJudgeRight: " + b);
			return orient = "LeftRight";
		}
		else {
			Empty1.clear();
			Empty2.clear();
		}
		
		a = judgeLeftUp(p);
		b = judgeRightDown(p);
		
		if(a + b - (Empty1.size() + Empty2.size()) >= 5 && (Empty1.size() >= 1 || Empty2.size() >= 1)) {
			System.out.println(">>>Defence JudgeLeftUp:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeftUp: " + a +"\tJudgeRightDown: " + b);
			return orient = "LeftUp";
		}
			
		else {
			Empty1.clear();
			Empty2.clear();
		}
		
		a = judgeLeftDown(p);
		b = judgeRightUp(p);
		
		if(a + b - (Empty1.size() + Empty2.size()) >= 5 && (Empty1.size() >= 1 || Empty2.size() >= 1)) {
			System.out.println(">>>Defence JudgeRightUp:\t<" + p.x + ", " + p.y + ">" + "\nJudgeLeftDown: " + a +"\tJudgeRightUp: " + b);
			return orient = "RightUp";
		}
		else {
			Empty1.clear();
			Empty2.clear();
		}
		
		return null;
	}
	
	public int mustAttackJudge(Point p) {
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
	}
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//include itself, count the number of same color stones for every direction
	public int judgeUp(Point p, boolean attack) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("Up empty: <" + p.x/40 + ", " + p.y/40 + ">");
			upE.add(p);
			if (upE.size() >= 3) {
				if (upE.get(upE.size() - 1).distance(upE.get(upE.size() - 2)) < 57 && 
						upE.get(upE.size() - 2).distance(upE.get(upE.size() - 3)) < 57)
				return 1;
			}
		}
		
		if (!attack) {
			if (ComL.contains(p))
				return 0;
		}
		else {
			if (UserL.contains(p))
				return 0;
		}
		
		return 1 + judgeUp(new Point(p.x, p.y - 40), attack);
	}
	
	public int judgeDown(Point p, boolean attack) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("Down empty: <" + p.x/40 + ", " + p.y/40 + ">");
			downE.add(p);
			if (downE.size() >= 3) {
				if (downE.get(downE.size() - 1).distance(downE.get(downE.size() - 2)) < 57 && 
						downE.get(downE.size() - 2).distance(downE.get(downE.size() - 3)) < 57)
				return 1;
			}
		}
		
		if (!attack) {
			if (ComL.contains(p))
				return 0;
		}
		else {
			if (UserL.contains(p))
				return 0;
		}
		
		return 1 + judgeDown(new Point(p.x, p.y + 40), attack);
	}
	
	public int judgeLeft(Point p, boolean attack) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("Left empty: <" + p.x/40 + ", " + p.y/40 + ">");
			leftE.add(p);
			if (leftE.size() >= 3) {
				if (leftE.get(leftE.size() - 1).distance(leftE.get(leftE.size() - 2)) < 57 && 
						leftE.get(leftE.size() - 2).distance(leftE.get(leftE.size() - 3)) < 57)
				return 1;
			}
		}
		
		if (!attack) {
			if (ComL.contains(p))
				return 0;
		}
		else {
			if (UserL.contains(p))
				return 0;
		}
		
		return 1 + judgeLeft(new Point(p.x - 40, p.y), attack); 
	}
	
	public int judgeRight(Point p, boolean attack) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("Right empty: <" + p.x/40 + ", " + p.y/40 + ">");
			rightE.add(p);
			if (rightE.size() >= 3) {
				if (rightE.get(rightE.size() - 1).distance(rightE.get(rightE.size() - 2)) < 57 && 
						rightE.get(rightE.size() - 2).distance(rightE.get(rightE.size() - 3)) < 57)
				return 1;
			}
		}

		if (!attack) {
			if (ComL.contains(p))
				return 0;
		}
		else {
			if (UserL.contains(p))
				return 0;
		}
		
		return 1 + judgeRight(new Point(p.x + 40, p.y), attack); 
	}
	
	public int judgeRightUp(Point p, boolean attack) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("RightUp empty: <" + p.x/40 + ", " + p.y/40 + ">");
			rightupE.add(p);
			if (rightupE.size() >= 3) {
				if (rightupE.get(rightupE.size() - 1).distance(rightupE.get(rightupE.size() - 2)) < 57 && 
						rightupE.get(rightupE.size() - 2).distance(rightupE.get(rightupE.size() - 3)) < 57)
				return 1;
			}
		}

		if (!attack) {
			if (ComL.contains(p))
				return 0;
		}
		else {
			if (UserL.contains(p))
				return 0;
		}
		
		return 1 + judgeRightUp(new Point(p.x + 40, p.y - 40), attack); 
	}
	
	public int judgeRightDown(Point p, boolean attack) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("RightDown empty: <" + p.x/40 + ", " + p.y/40 + ">");
			rightdownE.add(p);
			if (rightdownE.size() >= 3) {
				if (rightdownE.get(rightdownE.size() - 1).distance(rightdownE.get(rightdownE.size() - 2)) < 57 && 
						rightdownE.get(rightdownE.size() - 2).distance(rightdownE.get(rightdownE.size() - 3)) < 57)
				return 1;
			}
		}

		if (!attack) {
			if (ComL.contains(p))
				return 0;
		}
		else {
			if (UserL.contains(p))
				return 0;
		}
		
		return 1 + judgeRightDown(new Point(p.x + 40, p.y + 40), attack); 
	}
	
	public int judgeLeftUp(Point p, boolean attack) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("LeftUp empty: <" + p.x/40 + ", " + p.y/40 + ">");
			leftupE.add(p);
			if (leftupE.size() >= 3) {
				if (leftupE.get(leftupE.size() - 1).distance(leftupE.get(leftupE.size() - 2)) < 57 && 
						leftupE.get(leftupE.size() - 2).distance(leftupE.get(leftupE.size() - 3)) < 57)
				return 1;
			}
		}

		if (!attack) {
			if (ComL.contains(p))
				return 0;
		}
		else {
			if (UserL.contains(p))
				return 0;
		}
		
		return 1 + judgeLeftUp(new Point(p.x - 40, p.y - 40), attack); 
	}
	
	public int judgeLeftDown(Point p, boolean attack) {
		if (p.x > 760 || p.y > 760 || p.x < 40 || p.y < 40)
			return 0;
		
		if (!UserL.contains(p) && !ComL.contains(p)) {
			//System.out.println("LeftDown empty: <" + p.x/40 + ", " + p.y/40 + ">");
			leftdownE.add(p);
			if (leftdownE.size() >= 3) {
				if (leftdownE.get(leftdownE.size() - 1).distance(leftdownE.get(leftdownE.size() - 2)) < 57 && 
						leftdownE.get(leftdownE.size() - 2).distance(leftdownE.get(leftdownE.size() - 3)) < 57)
				return 1;
			}
		}

		if (!attack) {
			if (ComL.contains(p))
				return 0;
		}
		else {
			if (UserL.contains(p))
				return 0;
		}
		
		return 1 + judgeLeftDown(new Point(p.x - 40, p.y + 40), attack); 
	}
}
