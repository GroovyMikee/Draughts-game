package draughts;

public class MoveJump extends Move {
 
   public MoveJump(DraughtPosition draught, Coordinate destination) {
      this.draught = draught;
      this.destination = destination;
   }
  
  
   public boolean isJump() {
      return true;
   }
   
   
   public Move copy() {
      return new MoveJump(draught.copy(), destination);
   }
   
   
   // Return a copy of this move from the newBoard.   
   public Move copy(Board newBoard) {
      return new MoveJump(newBoard.getDraught(draught.getPosition()),
                          destination);
   }
      
  
   // Returns the coordinate of the captured draught of this move.
   public Coordinate capturedCoordinate() {
      if (draught.getPosition().row() - destination.row() == 2) { // Up.
         if (draught.getPosition().column() - destination.column() == 2) //Up,left.
            return draught.getPosition().upLeftMove();
         else
            return draught.getPosition().upRightMove();
      }
      else { // Down.
         if (draught.getPosition().column() - destination.column() == 2)//Down,left.
            return draught.getPosition().downLeftMove();
         else
            return draught.getPosition().downRightMove();
      }
   }
  
   
   public String toString() {
      String s = "";
      if (draught.getColor() == DraughtPosition.BLACK) s = "Black-J:"; else s = "White-J:";
      s = s + "(" + draught.getPosition() + "-" + destination + ")";
      return s; 
   }
}
