package draughts;

import javax.swing.ImageIcon;

/**
 * Represent a white piece, which means that the piece moves upwards.
 */
public class WhiteDraught extends DraughtPosition {

    public WhiteDraught(Coordinate c) {
        draughtState = new NormalStateWhite();
        position = c;
        value = WHITE_VALUE_NORMAL;
        stringRep = "O";
    }

    public int getColor() {
        return WHITE;
    }

    public ImageIcon getIcon() {
        return null;
    }

    public void makeKing() {
        draughtState = new QueenState();
        value = WHITE_VALUE_KING;
        stringRep = "W";
    }

    public boolean kingRow() {
        return ((position.get() >= 1) && (position.get() <= 4));
    }

    public boolean isKing() {
        return (value == WHITE_VALUE_KING);
    }

    public DraughtPosition copy() {
        DraughtPosition newDraught = new WhiteDraught(position);
        if (value == WHITE_VALUE_KING) {
            newDraught.makeKing();
        }
        return newDraught;
    }
}
