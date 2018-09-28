import java.awt.Point;

class AScore {
   Point p = null;
   double score = 0.0;
   
   AScore() {
	   
   }
   
   AScore(Point p, double score){
	   this.p = p;
	   this.score = score;
   }
   
   public double getScore() {return score;}
}