package draughts;

import java.io.Serializable;

/** Implements the state pattern for the metods findValidMoves and 
 * findValidJumps in class Draught.
 */
public class QueenState implements DraughtState,Serializable {
   
   public boolean findValidMoves(final DraughtPosition draught, final Board board,
                                 MoveList validMoves) {
      if (! findValidJumps(draught, board, validMoves)) {
      
         if (GameSearch.validKingMove(draught.getPosition(), draught.getPosition().downLeftMove(),
             board)) 
            validMoves.add(new MoveNormal(draught, draught.getPosition().downLeftMove()));
           
         if (GameSearch.validKingMove(draught.getPosition(), draught.getPosition().downRightMove(),
             board))
            validMoves.add(new MoveNormal(draught, draught.getPosition().downRightMove()));
        
         if (GameSearch.validKingMove(draught.getPosition(), draught.getPosition().upLeftMove(),
             board))
            validMoves.add(new MoveNormal(draught, draught.getPosition().upLeftMove()));
            
         if (GameSearch.validKingMove(draught.getPosition(), draught.getPosition().upRightMove(),
             board))
            validMoves.add(new MoveNormal(draught, draught.getPosition().upRightMove()));
         return false;
      }
      else
         return true;
   }
   
   
   public boolean findValidJumps(final DraughtPosition c, final Board board,
                                 MoveList validJumps) {
      boolean found = false;                              
      if (GameSearch.validKingJump(c.getPosition(), c.getPosition().downLeftJump(), 
          board)) {
         validJumps.add(new MoveJump(c, c.getPosition().downLeftJump()));
         found = true;
      }
      
      if (GameSearch.validKingJump(c.getPosition(), c.getPosition().downRightJump(), 
          board)) {
         validJumps.add(new MoveJump(c, c.getPosition().downRightJump()));
         found = true;
      }
    
      if (GameSearch.validKingJump(c.getPosition(), c.getPosition().upLeftJump(), 
          board)) {
         validJumps.add(new MoveJump(c, c.getPosition().upLeftJump()));
         found = true;
      }
      
      if (GameSearch.validKingJump(c.getPosition(), c.getPosition().upRightJump(), 
          board)) {
         validJumps.add(new MoveJump(c, c.getPosition().upRightJump()));
         found = true;
      }
      return found;
   }
}
