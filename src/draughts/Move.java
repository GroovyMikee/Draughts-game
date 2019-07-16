package draughts;

import java.io.Serializable;

public abstract class Move implements Serializable{   
   protected Coordinate destination;
   protected DraughtPosition draught;
   protected Move next = null;
      
   public abstract boolean isJump();
   public abstract String toString();
   public abstract Move copy(Board newBoard);
   public abstract Move copy();
   
   
   public DraughtPosition getDraught() {
      return draught;
   }
   
   
   public Coordinate getDestination() {
      return destination;
   }


   // For the MoveList class.
   public void setNext(Move next) {
      this.next = next;   
   }
   
   
   // For the MoveList class.
   public Move getNext() {
      return next;
   }
}
