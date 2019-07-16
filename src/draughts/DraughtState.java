package draughts;

/** 
 * State pattern for the metods findValidMoves and findValidJumps in class Draught.
 * The two methods delegate the methods to a DraughtState object that can either
 * be NormalStateWhite, NormalStateBlack or QueenState. This way an draught can
 * change state when it becomes a king.
 */
public interface DraughtState {
   public boolean findValidMoves(DraughtPosition draught, Board board,
                                 MoveList validMoves);

   public boolean findValidJumps(DraughtPosition draught, Board board,
                                 MoveList validJumps);
}
