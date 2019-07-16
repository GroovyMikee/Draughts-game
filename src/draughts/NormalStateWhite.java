package draughts;

import java.io.Serializable;

/** Implements the state pattern for the metods findValidMoves and 
 * findValidJumps in class Draught.
 */
public class NormalStateWhite implements DraughtState,Serializable {
   
   // Attaches valid moves to the validMoves list. Returns true if a valid jump
   // exist.
   public boolean findValidMoves(final DraughtPosition draught, final Board board,
                                 MoveList validMoves) {
      if (! findValidJumps(draught, board, validMoves)) {
         // If no valid jump exist then look for valid moves.
         if (GameSearch.validWhiteMove(draught.getPosition(), draught.getPosition().upLeftMove(),
             board))
            validMoves.add(new MoveNormal(draught, draught.getPosition().upLeftMove()));
            
         if (GameSearch.validWhiteMove(draught.getPosition(), draught.getPosition().upRightMove(),
             board))
            validMoves.add(new MoveNormal(draught, draught.getPosition().upRightMove()));
         return false;
     }
     else
      return true; 
      
   }   
   
   
   public boolean findValidJumps(DraughtPosition c, Board board, MoveList validJumps) {
      boolean found = false;
      if (GameSearch.validWhiteJump(c.getPosition(), c.getPosition().upLeftJump(), 
          board)) {
         validJumps.add(new MoveJump(c, c.getPosition().upLeftJump()));
         found = true;
      }
      
      if (GameSearch.validWhiteJump(c.getPosition(), c.getPosition().upRightJump(), 
          board)) {
         validJumps.add(new MoveJump(c, c.getPosition().upRightJump()));
         found = true;
      }
      return found;
   }
}

