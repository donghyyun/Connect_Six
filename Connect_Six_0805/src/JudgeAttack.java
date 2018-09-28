import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JudgeAttack {
   Location lt = new Location();
   String color;
   ArrayList<Point>comL = new ArrayList<>();
   ArrayList<Point>userL = new ArrayList<>();
   
   ArrayList<AScore> scoreL = new ArrayList<AScore>();
   
   private static final int RANGE = 8;
   
   Point temp1 = null;
   Point temp2 = null;
   
   Boolean Horizontal = true;
   Boolean Vertical = true;
   Boolean Slash = true;
   Boolean BackSlash = true;
   

   JudgeAttack(GamePanel gp) {
      if (GamePanel.ComColor.equals("Black")) {
			userL = gp.getGame().getWsp();
			comL = gp.getGame().getBsp();
		}
		else {
			userL = gp.getGame().getBsp();
			comL = gp.getGame().getWsp();
		}
      
      this.color = GamePanel.UserColor;
   }
   
   public ArrayList<AScore> getScoreL() {return scoreL;}
   
   public void printBoard() {
      System.out.println("-------------------------------------------------------------------------------");
      System.out.println("-------------------------------------------------------------------------------");
      for(int i = 0; i < 19; i++) {
         for(int j = 0; j < 19; j++) {
            if(GamePanel.setted[i][j] != null)
               System.out.println("[" + i + "][" + j + "]" +GamePanel.setted[i][j]);
         }
      }
   }
   
   public void attack() {
	   scoreL.clear();
	   
	   double Max1 = 0.0;
	   double Max2 = 0.0;
	   AScore s = new AScore();
	   double score = 0.0;
      
      Point p1 = new Point(0,0);
      Point p2 = new Point(0,0);      
      int index1 = 0;
      int index2 = 0;
      
      
      if(comL.size() >= 2) {
  	    temp1 = comL.get(comL.size() - 1);
  	    //System.out.println("temp1 " + temp1);
  	    temp2 = comL.get(comL.size() - 2);
  	   // System.out.println("temp2 " + temp2);
  	 }
  	 else if (comL.size() == 1){
  		temp1 = comL.get(comL.size() - 1);
  		temp2 = comL.get(comL.size() - 1);
  		 //System.out.println("temp1 " + temp1);
  	 }
  	 else {
  		 lt.p1 = new Point(440, 400);
  		 lt.p2 = new Point(400, 440);
  		 return;
  	 }

	 
	 index1 = temp1.x / 40 - 1;
	 index2 = temp1.y / 40 - 1;
      
      for(int i = -5; i <= 5; i++) {
         for(int j = -5; j <= 5; j++) {
            if((index1 + j) < 0 || (index1 + j) > 18 || (index2 + i) < 0 || (index2 + i) > 18) {
               continue;
            }
            
            if((i == 0 && j == 0)) {
               //System.out.println("Continued 1 : " + "["+ (temp1.y / 40 - 1 + i) + "] ["+(temp1.x / 40 - 1 + j) +"] = " + GamePanel.setted[temp1.y / 40 - 1 + i][temp1.x / 40 - 1 + j]);
               continue;
            }
            
            if (GamePanel.setted[index2 + i][index1 + j] != null) {
               continue;
            }
            
            p1.x = temp1.x + j * 40;
            p1.y = temp1.y + i * 40; 
            //System.out.println("["+ (p1.x / 40 - 1) + "] ["+(p1.y / 40 - 1) +"] = " + GamePanel.setted[p1.y / 40 - 1][p1.x / 40 - 1]);
            score = evaScore(p1);
            //System.out.println("["+ (p1.x / 40 - 1) + "] ["+(p1.y / 40 - 1) +"] = " + score);
            if(Max1 <= score) {
                  Max1 = score;
                  s.p = new Point((p1.x), (p1.y));
                  s.score = Max1;
                  scoreL.add(new AScore(s.p, s.score));
                  //System.out.println("[" + (p1.x / 40 - 1) + "][" + (p1.y / 40 - 1) + "] : " + s.score );
            }
         }
      }
      /*System.out.println("Point1: " + s.p);
      lt.p1 = s.p;
      GamePanel.setted[lt.p1.y / 40 - 1][lt.p1.x / 40 - 1] = GamePanel.ComColor;*/
      
      /*if (comL.size() == 1) {
    	  GamePanel.setted[lt.p1.y / 40 - 1][lt.p1.x / 40 - 1] = null;
    	  return;
      }*/
    	  
      //System.out.println("-----------------------------------------------------------");
      index1 = temp2.x / 40 - 1;
      index2 = temp2.y / 40 - 1;
      for(int i = -5; i <= 5; i++) {
         for(int j = -5; j <= 5; j++) {
            if(index1 + j < 0 || index1 + j > 18 || index2 + i < 0 ||index2 + i > 18)
               continue;
            
            if((i == 0 && j == 0)) {
               //System.out.println("Continued 2 : " + "["+ (temp2.y / 40 - 1 + i) + "] ["+(temp2.x / 40 - 1 + j) +"] = " +GamePanel.setted[temp2.y / 40 - 1 + i][temp2.x / 40 - 1 + j]);
               continue;
            }
            
            if (GamePanel.setted[index2 + i][index1 + j] != null)
               continue;
            
            p2.x = temp2.x + j * 40;
            p2.y = temp2.y + i * 40;
            //System.out.println("["+ (p2.x / 40 - 1) + "] ["+(p2.y / 40 - 1) +"] = " +GamePanel.setted[p2.y / 40 - 1][p2.x / 40 - 1]);
            score = evaScore(p2);
            //System.out.println("["+ (p2.x / 40 - 1) + "] [" + (p2.y / 40 - 1) +"] = " + score);
            if(Max2 <= score) {
                  Max2 = score;
                  s.p = new Point((p2.x), (p2.y));
                  s.score = Max2;
                  scoreL.add(new AScore(s.p, s.score));
                  //System.out.println("[" + (p2.x / 40 - 1) + "][" + (p2.y / 40 - 1) + "] : " + s.score );
            }
         }
      }
      
      Collections.sort(scoreL, new Comparator<AScore>() {
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
      
      
      /*System.out.println(">>>>Attack ScoreL<<<<");
      for (AScore as: scoreL) {
    	  System.out.println(as.p + "\t" + as.score);
      }*/
      
      for (int i = 0; i < scoreL.size() - 1; i++) {
    	  if (scoreL.get(i).p.equals(scoreL.get(i + 1).p))
    		  scoreL.remove(i + 1);
      }
      
      /*System.out.println("After remove");
      for (AScore as: scoreL) {
    	  System.out.println(as.p + "\t" + as.score);
      }*/
      
      lt.p1 = scoreL.get(0).p;
      //System.out.println("Point1: " + lt.p1);
      
      int index = 1;
      
      while (index < scoreL.size()) {
    	  //System.out.println("score" + scoreL.get(index).p);
    	  if (scoreL.get(index).p.equals(lt.p1)) {
    		  index++;
    	  }
    	  else {
    		  lt.p2 = scoreL.get(index).p;
    		  break;
    	  }
      }
      //System.out.println("Point2: " + lt.p2);
      //GamePanel.setted[lt.p1.y / 40 - 1][lt.p1.x / 40 - 1] = null;
   }
   
   
   
   public double evaScore(Point temp) {
      double count = 0.0;
      double score_sum = 0.0;
      
      double ch = checkHorizontal(temp);
      double cv = checkVertical(temp);
      double cs = checkSlash(temp);
      double cbs = checkBackSlash(temp);
      //System.out.println(Horizontal + " " + Vertical + " " + Slash + " " + BackSlash + " ");
      if(!Horizontal)
         count += 1.0;
      if(!Vertical)
         count += 1.0;
      if(!Slash)
         count += 1.0;
      if(!BackSlash)
         count += 1.0;
      
      if(count == 1.0)
         score_sum = 1.0 * (ch + cv + cs + cbs);
      if(count == 2.0)
         score_sum = 1.00000181862 * (ch + cv + cs + cbs);
      if(count == 3.0)
         score_sum = 1.00000363725 * (ch + cv + cs + cbs);
      if(count == 4.0)
         score_sum = 1.00000726562 * (ch + cv + cs + cbs);
      
      Horizontal = true; Vertical = true; Slash = true; BackSlash = true;
      return score_sum;
   }
   
   public double checkVertical(Point p) {
      double score = 1.0;
      
      for(int i = 1; i < RANGE; i++) {
         if(((p.x - 40 * i) < 40)) {
            break;
         }
         if(GamePanel.setted[(p.y/40 - 1)][(p.x/40 - 1)-i] == null)
            score *= 2.0;
         else if((GamePanel.setted[(p.y/40 - 1)][p.x/40 - 1 -i].equals(color))) {
        	 score *= 1.0;
        	 break;
         }
            
         else {
            //System.out.println("HI");
            Horizontal = false;
            score *= (double)Math.pow(2, 12 - i);
            //break;
         }
         
         //System.out.println( "[" + ((p.x/40 - 1)-i) + "][" + (p.y/40 - 1) +  "]" + GamePanel.setted[(p.x/40 - 1)-i][p.y/40 - 1]);
      }
      
      for(int i = 1; i < RANGE; i++) {
         if((p.x + 40 * i) > (19 * 40)) {
            break;
         }
         if(GamePanel.setted[(p.y/40 - 1)][(p.x/40 - 1) + i] == null)
            score *= 2.0;
         else if(GamePanel.setted[(p.y/40 - 1)][p.x/40 - 1 +i].equals(color)) {
        	 score *= 1.0;
        	 break;
         }
         else {
            //System.out.println("HI");
            Horizontal = false;
            score *= (double)Math.pow(2, 12 - i);
            //break;
         }
         //System.out.println( "[" + ((p.x/40 - 1)+i) + "][" + (p.y/40 - 1) +  "]" + GamePanel.setted[(p.x/40 - 1)+i][p.y/40 - 1]);
      }
      //System.out.println("Horizontal : " + score);
      return score;
   }
   
   public double checkHorizontal(Point p) {
      double score = 1.0;
      for(int i = 1; i < RANGE; i++) {
         if((p.y - 40 * i) < 40){
            break;
         }
         if(GamePanel.setted[(p.y/40 - 1) - i][(p.x/40 - 1)] == null)
            score *= 2.0;
         else if(GamePanel.setted[p.y/40 - 1-i][(p.x/40 - 1)].equals(color)){
        	 score *= 1.0;
        	 break;
         }
         else {
            //System.out.println("HI");
            Vertical = false;
            score *= (double)Math.pow(2, 12 - i);
            //break;
         }
         //System.out.println( "[" + (p.x/40 - 1) + "][" + ((p.y/40 - 1)-i) +  "]" + GamePanel.setted[p.x/40 - 1][(p.y/40 - 1)-i]);
      }
      
      for(int i = 1; i < RANGE; i++) {
         if((p.y + 40 * i) > 19 * 40)
            break;
         if(GamePanel.setted[p.y/40 - 1 + i][(p.x/40 - 1) ] == null)
            score *= 2.0;
         else if(GamePanel.setted[p.y/40 - 1 + i][(p.x/40 - 1)].equals(color)){
        	 score *= 1.0;
        	 break;
         }
         else {
            //System.out.println("HI");
            Vertical = false;
            score *= (double)Math.pow(2, 12 - i);
            //break;
         }
         //System.out.println( "[" + (p.x/40 - 1) + "][" + ((p.y/40 - 1)+i) +  "]" + GamePanel.setted[p.x/40 - 1][(p.y/40 - 1) + i]);
      }
      //System.out.println("Vertical : " + score);
      return score;
   }
   
   public double checkSlash(Point p) {
      double score = 1.0;
      for(int i = 1; i < RANGE; i++) {
         if(((p.x - 40 * i) < 40) || ((p.y + 40 * i) > 19 * 40))
            break;
         if(GamePanel.setted[(p.y/40 - 1)+i][(p.x/40 - 1)-i] == null)
            score *= 2.0;
         else if(GamePanel.setted[(p.y/40 - 1)+i][(p.x/40 - 1)-i].equals(color)) {
        	 score *= 1.0;
        	 break;
         }
         else {
            //System.out.println("HI");
            Slash = false;
            score *= (double)Math.pow(2, 12 - i);
            //break;
         }
         //System.out.println( "[" + ((p.x/40 - 1)+i) + "][" + ((p.y/40 - 1)-i) +  "]" + GamePanel.setted[(p.x/40 - 1)+i][p.y/40 - 1-i]);
      }
      
      for(int i = 1; i < RANGE; i++) {
         if((p.x + 40 * i) > (19 * 40) || ((p.y - 40 * i) < 40))
            break;
         if(GamePanel.setted[(p.y/40 - 1)-i][(p.x/40 - 1)+i] == null)
            score *= 2.0;
         else if(GamePanel.setted[(p.y/40 - 1)-i][(p.x/40 - 1)+i].equals(color)){
        	 score *= 1.0;
        	 break;
         }
         else {
            //System.out.println("HI");
            Slash = false;
            score *= (double)Math.pow(2, 12 - i);
            //break;
         }
         //System.out.println( "[" + ((p.x/40 - 1)-i) + "][" + ((p.y/40 - 1)+i) +  "]" + GamePanel.setted[(p.x/40 - 1)-i][(p.y/40 - 1)+i]);
      }
      //System.out.println("Slash : " + score);
      return score;
   }
   
   public double checkBackSlash(Point p) {
      double score = 1.0;
      for(int i = 1; i < RANGE; i++) {
         if((p.x + 40 * i) > (19 * 40) || (p.y + 40 * i) > 19 * 40)
            break;
         if(GamePanel.setted[(p.y/40 - 1)+i][(p.x/40 - 1)+i] == null)
            score *= 2.0;
         else if(GamePanel.setted[(p.y/40 - 1)+i][(p.x/40 - 1)+i].equals(color)){
        	 score *= 1.0;
        	 break;
         }
         else {
            //System.out.println("HI");
            score *= (double)Math.pow(2, 12 - i);
            BackSlash = false;
            //break;
         }
         //System.out.println( "[" + ((p.x/40 - 1)+i) + "][" + ((p.y/40 - 1)+i) +  "]" + GamePanel.setted[(p.x/40 - 1)+i][(p.y/40 - 1)+i]);
      }
      
      for(int i = 1; i < RANGE; i++) {
         if(((p.x - 40 * i) < 40) || ((p.y - 40 * i) < 40))
            break;
         if(GamePanel.setted[(p.y/40 - 1)-i][(p.x/40 - 1)-i] == null)
            score *= 2.0;
         else if(GamePanel.setted[(p.y/40 - 1)-i][(p.x/40 - 1)-i].equals(color)) {
        	 score *= 1.0;
        	 break;
         }
         else {
            //System.out.println("HI");
            score *= (double)Math.pow(2, 12 - i);
            BackSlash = false;
            //break;
         }
         //System.out.println( "[" + ((p.x/40 - 1)-i) + "][" + ((p.y/40 - 1)-i) +  "]" + GamePanel.setted[(p.x/40 - 1)-i][(p.y/40 - 1)-i]);
      }
      //System.out.println("BackSlash : " + score);
      return score;
   }
}


