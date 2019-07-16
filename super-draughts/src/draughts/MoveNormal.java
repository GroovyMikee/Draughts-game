package draughts;

public class MoveNormal extends Move {
   
   public MoveNormal(DraughtPosition draught, Coordinate destination) {
      this.draught = draught;
      this.destination = destination;
   }
   
   
   public boolean isJump() {
      return false;
   }
   
   
   public Move copy() {
      return new MoveNormal(draught.copy(), destination);
   }

   
   // Return a copy of this move from the newBoard.   
   public Move copy(Board newBoard) {
      return new MoveNormal(newBoard.getDraught(draught.getPosition()),
                            destination);
   }
   
   
   public String toString() {
      String s = "Move: ";
      if (draught.getColor() == DraughtPosition.BLACK) s = "Black:"; else s = "White:";
      s = s + "(" + draught.getPosition() + "-" + destination + ")";
      return s; 
   }
}
