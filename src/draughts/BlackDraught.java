package draughts;

import javax.swing.ImageIcon;

public class BlackDraught extends DraughtPosition {

   public BlackDraught(Coordinate c) {
      draughtState = new NormalStateBlack();
      position = c;
      value = BLACK_VALUE_NORMAL;
      stringRep = "X";
   }
     
   
   public int getColor() {
      return BLACK;
   }
   
   
  
   
   
   public void makeKing() {
      draughtState = new QueenState();
      value = BLACK_VALUE_KING;
      stringRep = "B";
   }
   
   
   public boolean isKing() {
      return (value == BLACK_VALUE_KING);
   }
   
     
   public boolean kingRow() {
      return ( (position.get() >= 29) && (position.get() <= 32) );
   }
   
   
   public DraughtPosition copy() {
      DraughtPosition newDraught = new BlackDraught(position);
      if (value == BLACK_VALUE_KING)
         newDraught.makeKing();
      return newDraught;
   }          

    @Override
    public ImageIcon getIcon() {
        return  null;
    }
}
