package draughts;
 
import java.io.Serializable;
import javax.swing.ImageIcon; 

public abstract class DraughtPosition extends Position implements Serializable{
   
   public static final int BLACK = 1;
   public static final int WHITE = 2;
   public static final int WHITE_VALUE_NORMAL = 2;
   public static final int BLACK_VALUE_NORMAL = -2;
   public static final int WHITE_VALUE_KING = 3;
   public static final int BLACK_VALUE_KING = -3;
   
   protected DraughtState draughtState;
   protected Coordinate position;
   protected int value;
   protected String stringRep;
   
   public abstract boolean isKing();
   public abstract int getColor();
   public abstract void makeKing();
   public abstract DraughtPosition copy();
   public abstract boolean kingRow();
   public abstract ImageIcon getIcon();
   
   
   public Coordinate getPosition() {
      return position;
   } 
   
   
   public void setPosition(Coordinate c) {
      position = c;
   }
   
   
   public int getValue() {
      return value;
   }
   
   
   public boolean findValidMoves(MoveList moveList, final Board board) {
      return draughtState.findValidMoves(this, board, moveList);
   }
   
   
   public boolean findValidJumps(MoveList moveList, final Board board) {
      return draughtState.findValidJumps(this, board, moveList);
   }
   
   
   public String toString() {
      return stringRep;  
   }
    
}
