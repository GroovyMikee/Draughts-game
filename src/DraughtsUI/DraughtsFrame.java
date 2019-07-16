package DraughtsUI;

import draughts.Board;
import draughts.DraughtPosition;
import draughts.Coordinate;
import draughts.Move;
import draughts.MoveIterator;
import draughts.MoveJump;
import draughts.MoveList;
import draughts.MoveNormal;
import draughts.GameSearch;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

public class DraughtsFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

    DraughtsPanel pan;
    private DraughtPosition multipleJumpsDraught = null;
    ArrayList<Board> boardHistory = new ArrayList<Board>();
    private int clickedHere = 0;
    int FromPawnIndex = 0;
    int ToPawnIndex = 0;
    private static final int FROM = 1;
    private static final int TO = 2;
    private static final int FROM_MULTIPLE = 3;
    private static final int TO_MULTIPLE = 4;
    private static final int COMPUTER_THINKS = 5;
    private static final int NOT_STARTED = 6;
    private int userColor = DraughtPosition.WHITE;
    private int computerColor = DraughtPosition.BLACK;
    private Coordinate from;
    private int thinkDepth = 2;
    private boolean alreadyMoved;
    private boolean moving;
    private int nbrBacks = 0;
    static AudioClip music;
    private int nbrBack = 0;
    private int nbrForward = 0;
    private boolean isBack = false;
    String output = "";
    int currentPositionInBoradHistory = 0;
    private boolean isForward = false;
    static int algorithm = 1;
    boolean playMusic = true;

    JMenuBar menuBar;

    public DraughtsFrame() {

        Toolkit toolkit = getToolkit();
        setTitle("Draughts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        pan = new DraughtsPanel();
        Dimension size = toolkit.getScreenSize();
        setSize(605, 650);//605
        setResizable(false);
        setLocationRelativeTo(null);
        createMenu();
        add(pan);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setVisible(true);

        URL url = this.getClass().getResource("/assets/musicBackground.wav");
        music = Applet.newAudioClip(url);
        //if (playMusic) {
        //    music.loop();
       // }
    }

    public void createMenu() {
        //Create the menu bar.
        menuBar = new JMenuBar();

        // create Algorithms menu item
        JMenu algorithm = new JMenu("algorithms");
        ButtonGroup algorithmGroup = new ButtonGroup(); // group for radio buttons

        JRadioButtonMenuItem rbMinMax = new JRadioButtonMenuItem("MinMax");
        rbMinMax.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MinMax");
                DraughtsFrame.algorithm = 1;
            }
        });
        JRadioButtonMenuItem rbMMAlfaBeta = new JRadioButtonMenuItem("MinMaxAlfaBeta");
        rbMMAlfaBeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MinMaxAlfaBeta");
                DraughtsFrame.algorithm = 2;
            }
        });
        // add radio buttons to the group, to check only one of them
        algorithmGroup.add(rbMinMax);
        algorithmGroup.add(rbMMAlfaBeta);
        // set one of the radio buttons checked
        if(DraughtsFrame.algorithm == 1){
            rbMinMax.setSelected(true);
            rbMMAlfaBeta.setSelected(false);
        }
        else{
            rbMinMax.setSelected(false);
            rbMMAlfaBeta.setSelected(true);
        }
        // add radio buttons to the menu algorithm onglet
        algorithm.add(rbMinMax);
        algorithm.add(rbMMAlfaBeta);
        // add algorithm item to the menu bar
        menuBar.add(algorithm);
        JMenu levels = new JMenu("depth search");
        menuBar.add(levels);
        ButtonGroup levelsGroup = new ButtonGroup(); // group for radio buttons

        JRadioButtonMenuItem EightLevel = new JRadioButtonMenuItem("8");
        EightLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("8");

            }
        });

        JRadioButtonMenuItem SeventhLevel = new JRadioButtonMenuItem("7");
        SeventhLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("7");

            }
        });

        JRadioButtonMenuItem SixthLevel = new JRadioButtonMenuItem("6");
        SixthLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("6");

            }

        });
        JRadioButtonMenuItem FifthLevel = new JRadioButtonMenuItem("5");
        FifthLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("5");

            }

        });
        JRadioButtonMenuItem FourthLevel = new JRadioButtonMenuItem("4");
        FourthLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("4");

            }

        });
        JRadioButtonMenuItem ThirdLevel = new JRadioButtonMenuItem("3");
        ThirdLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("3");

            }

        });
        JRadioButtonMenuItem SecondLevel = new JRadioButtonMenuItem("2");
        SecondLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("2");

            }

        });
        JRadioButtonMenuItem FirstLevel = new JRadioButtonMenuItem("1");
        FirstLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("1");

            }

        });
        // add radio buttons to the group, to check only one of them
        levelsGroup.add(EightLevel);
        levelsGroup.add(SeventhLevel);
        levelsGroup.add(SixthLevel);
        levelsGroup.add(FifthLevel);
        levelsGroup.add(FourthLevel);
        levelsGroup.add(ThirdLevel);
        levelsGroup.add(SecondLevel);
        levelsGroup.add(FirstLevel);

        levels.add(EightLevel);
        levels.add(SeventhLevel);
        levels.add(SixthLevel);
        levels.add(FifthLevel);
        levels.add(FourthLevel);
        levels.add(ThirdLevel);
        levels.add(SecondLevel);
        levels.add(FirstLevel);

        if(thinkDepth == 8){
            EightLevel.setSelected(true);
            SeventhLevel.setSelected(false);
            SixthLevel.setSelected(false);
            FifthLevel.setSelected(false);
            FourthLevel.setSelected(false);
            ThirdLevel.setSelected(false);
            SecondLevel.setSelected(false);
            FirstLevel.setSelected(false);
        }
        else if(thinkDepth == 7){
            EightLevel.setSelected(false);
            SeventhLevel.setSelected(true);
            SixthLevel.setSelected(false);
            FifthLevel.setSelected(false);
            FourthLevel.setSelected(false);
            ThirdLevel.setSelected(false);
            SecondLevel.setSelected(false);
            FirstLevel.setSelected(false);
        }else if(thinkDepth == 6){
            EightLevel.setSelected(false);
            SeventhLevel.setSelected(false);
            SixthLevel.setSelected(true);
            FifthLevel.setSelected(false);
            FourthLevel.setSelected(false);
            ThirdLevel.setSelected(false);
            SecondLevel.setSelected(false);
            FirstLevel.setSelected(false);
        }
        else if(thinkDepth == 5) {
            EightLevel.setSelected(false);
            SeventhLevel.setSelected(false);
            SixthLevel.setSelected(false);
            FifthLevel.setSelected(true);
            FourthLevel.setSelected(false);
            ThirdLevel.setSelected(false);
            SecondLevel.setSelected(false);
            FirstLevel.setSelected(false);
        }
        else if(thinkDepth == 4) {
            EightLevel.setSelected(false);
            SeventhLevel.setSelected(false);
            SixthLevel.setSelected(false);
            FifthLevel.setSelected(false);
            FourthLevel.setSelected(true);
            ThirdLevel.setSelected(false);
            SecondLevel.setSelected(false);
            FirstLevel.setSelected(false);
        }
        else if(thinkDepth == 3) {
            EightLevel.setSelected(false);
            SeventhLevel.setSelected(false);
            SixthLevel.setSelected(false);
            FifthLevel.setSelected(false);
            FourthLevel.setSelected(false);
            ThirdLevel.setSelected(true);
            SecondLevel.setSelected(false);
            FirstLevel.setSelected(false);
        }
        else if(thinkDepth == 2) {
            EightLevel.setSelected(false);
            SeventhLevel.setSelected(false);
            SixthLevel.setSelected(false);
            FifthLevel.setSelected(false);
            FourthLevel.setSelected(false);
            ThirdLevel.setSelected(false);
            SecondLevel.setSelected(true);
            FirstLevel.setSelected(false);
        }
        else if(thinkDepth == 1) {
            EightLevel.setSelected(false);
            SeventhLevel.setSelected(false);
            SixthLevel.setSelected(false);
            FifthLevel.setSelected(false);
            FourthLevel.setSelected(false);
            ThirdLevel.setSelected(false);
            SecondLevel.setSelected(false);
            FirstLevel.setSelected(true);
        }
/*
        JMenu Music = new JMenu("Music");
        menuBar.add(Music);

        JRadioButtonMenuItem OnOff = new JRadioButtonMenuItem("Stop");
        OnOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMusic = false;
                music.stop();
            }
        });
        Music.add(OnOff);
*/
        JMenu options = new JMenu("Options");
        menuBar.add(options);
        // options

        JMenuItem backward = new JMenuItem("backward", null);
        backward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Do backward ");
                System.out.println("The Current Position when i will Back " + currentPositionInBoradHistory);
                if ((currentPositionInBoradHistory - (++nbrBack)) >= 0) {
                    isBack = true;
                    pan.boardO = boardHistory.get(currentPositionInBoradHistory - nbrBack);
                    currentPositionInBoradHistory = currentPositionInBoradHistory - nbrBack;
                    pan.repaint();
                    nbrBack = 0;
                } else {
                    nbrBack = 0;
                }
            }
        });
        JMenuItem forward = new JMenuItem("forward", null);
        forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Do forward ");
                if (currentPositionInBoradHistory + (++nbrForward) < boardHistory.size()) {
                    pan.boardO = boardHistory.get(currentPositionInBoradHistory + nbrForward);
                    currentPositionInBoradHistory = currentPositionInBoradHistory + nbrForward;
                    nbrForward = 0;
                }else {
                    nbrForward = 0;
                }
                pan.repaint();

            }
        });
        JMenuItem resetBoard = new JMenuItem("reset board", null);
        resetBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Do resetBoard ");
                pan.pawns.clear();
                pan.boardO.initialize();
                boardHistory.clear();
                currentPositionInBoradHistory = 0;
                isBack = false;
                pan.repaint();

            }
        });
        options.add(backward);
        options.add(forward);
        options.add(resetBoard);
        this.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        new DraughtsFrame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {


        int test = 0;
        for (int i = 0; i < pan.allBoardPoints.size(); i++) {
            if (e.getX() > (int) pan.allBoardPoints.get(i).getX()
                    && e.getX() < (int) (pan.allBoardPoints.get(i).getX() + 75)
                    && e.getY() - 40 < (int) (pan.allBoardPoints.get(i).getY() + 75)
                    && e.getY() - 40 > (int) (pan.allBoardPoints.get(i).getY())) {
                test = (i + 1);
                break;
            }
        }

        for (int i = 0; i < pan.pawns.size(); i++) {

            if (e.getX() > (int) pan.pawns.get(i).point.getX() && e.getX() < (int) (pan.pawns.get(i).point.getX() + 75)
                    && e.getY() - 27 < (int) (pan.pawns.get(i).point.getY() + 75) && e.getY() - 27 > (int) (pan.pawns.get(i).point.getY())) {
                clickedHere = i;
                break;
            }
        }

        MoveList validMoves;
        validMoves = GameSearch.findAllValidMoves(pan.boardO, userColor);
        pan.possiblemovesindex.clear();
        for (int i = 0; i < validMoves.size(); i++) {
            if ((test - 1) >= 0 && pan.boardO.getDraught(new Coordinate(test)) != null && validMoves.get(i).getDraught().getPosition() == pan.boardO.getDraught(new Coordinate(test)).getPosition()) {
                pan.possiblemovesindex.add(validMoves.get(i).getDestination().get());
                pan.repaint();
            }

        }

        if (e.getX() > 690 && e.getX() < 690 + 54 && e.getY() - 27 > 530 && e.getY() - 27 < 530 + 54) {
            pan.pawns.clear();
            pan.boardO.initialize();
            boardHistory.clear();
            currentPositionInBoradHistory = 0;
            isBack = false;
            pan.repaint();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (alreadyMoved) {
            FromPawnIndex = clickedHere + 1;
            for (int i = 0; i < pan.allBoardPoints.size(); i++) {

                if (e.getX() > (int) pan.allBoardPoints.get(i).getX() && e.getX() < (int) (pan.allBoardPoints.get(i).getX() + 75)
                        && e.getY() - 27 < (int) (pan.allBoardPoints.get(i).getY() + 75) && e.getY() - 27 > (int) (pan.allBoardPoints.get(i).getY())) {
                    ToPawnIndex = i + 1;
                    break;
                }
            }

            if (clickedHere >= 0) {
                moveUser(new Coordinate((pan.pawns.get(clickedHere).posindex)), new Coordinate(ToPawnIndex));
            }
            pan.newBoard = true;
            pan.repaint();
            clickedHere = -48;
            setCursor(Cursor.DEFAULT_CURSOR);
            alreadyMoved = false;
            moving = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        alreadyMoved = true;
        setCursor(Cursor.CROSSHAIR_CURSOR);
        pan.possiblemovesindex.clear();
        pan.bestmovesfromhelp.clear();

        if (!moving) {
            for (int i = 0; i < pan.pawns.size(); i++) {
                if (e.getX() > (int) pan.pawns.get(i).point.getX()
                        && e.getX() < (int) (pan.pawns.get(i).point.getX() + 75)
                        && e.getY() - 27 < (int) (pan.pawns.get(i).point.getY() + 75)
                        && e.getY() - 27 > (int) (pan.pawns.get(i).point.getY())) {
                    clickedHere = i;
                    break;
                }
            }
        }

        if (clickedHere >= 0) {
            pan.newBoard = false;
            moving = true;
            pan.pawns.get(clickedHere).setP(new Point(e.getX() - 75 / 2, e.getY() - 40 - 75 / 2));
            pan.repaint();

        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void moveUser(Coordinate from, Coordinate to) {
        pan.turn = "your turn";

        Move move = validateUserMove(from, to);
        if (move == null) {
            System.out.println(" The Move is not Valid ");
            outputText("Invalid move.");
        } else if (move.isJump()) {

            if (isBack) {
                int currentBoard = 0;
                currentBoard = currentPositionInBoradHistory;
                removeBordsAfter(currentPositionInBoradHistory + 1);
                isBack = false;
                currentPositionInBoradHistory = boardHistory.size() + 1;
            } else if (boardHistory.size() == 0) {
                currentPositionInBoradHistory = 0;
                boardHistory.add(pan.boardO);
            }
            pan.boardO = GameSearch.executeUserJump(move, pan.boardO);
            multipleJumpsDraught = pan.boardO.getDraught(move.getDestination());
            if (mandatoryJump(multipleJumpsDraught, pan.boardO)) {
                outputText("A multiple jump must be completed.");
            } else {
                computerMoves();
            }
        } else // Normal move.
            if (GameSearch.existJump(pan.boardO, userColor)) {
                outputText("Invalid move. If you can jump, you must.");
            } else {

                if (isBack) {
                    int currentBoard = 0;
                    currentBoard = currentPositionInBoradHistory;
                    removeBordsAfter(currentPositionInBoradHistory + 1);
                    isBack = false;

                    currentPositionInBoradHistory = boardHistory.size() + 1;
                } else if (boardHistory.size() == 0) {
                    currentPositionInBoradHistory = 0;
                    boardHistory.add(pan.boardO);
                }
                pan.boardO = GameSearch.executeMove(move, pan.boardO);
                pan.user_move = move.toString();
                computerMoves();
            }
    }

    public Move validateUserMove(Coordinate from, Coordinate to) {
        Move move = null;
        DraughtPosition draught = pan.boardO.getDraught(from);
        if (draught == null) {
        }
        if (draught != null) {
            if (userColor == DraughtPosition.WHITE) {
                if (draught.getColor() == DraughtPosition.WHITE) {
                    if (draught.getValue() == DraughtPosition.WHITE_VALUE_KING) {
                        if (Math.abs(from.row() - to.row()) == 1) {

                            if (GameSearch.validKingMove(from, to, pan.boardO)) {
                                move = new MoveNormal(draught, to);
                            }
                        } else if (GameSearch.validKingJump(from, to, pan.boardO)) {

                            move = new MoveJump(draught, to);
                        }
                    } else // Normal white draught.
                        if (from.row() - to.row() == 1) {

                            if (GameSearch.validWhiteMove(from, to, pan.boardO)) {
                                move = new MoveNormal(draught, to);
                            }
                        } else if (GameSearch.validWhiteJump(from, to, pan.boardO)) {
                            move = new MoveJump(draught, to);
                        }
                }
            } else // User is black.
                if (draught.getColor() == DraughtPosition.BLACK) {
                    if (draught.getValue() == DraughtPosition.BLACK_VALUE_KING) {
                        if (Math.abs(from.row() - to.row()) == 1) {
                            if (GameSearch.validKingMove(from, to, pan.boardO)) {
                                move = new MoveNormal(draught, to);
                            }
                        } else if (GameSearch.validKingJump(from, to, pan.boardO)) {
                            move = new MoveJump(draught, to);
                        }
                    } else // Normal black draught.
                        if (to.row() - from.row() == 1) {
                            if (GameSearch.validBlackMove(from, to, pan.boardO)) {
                                move = new MoveNormal(draught, to);
                            }
                        } else if (GameSearch.validBlackJump(from, to, pan.boardO)) {
                            move = new MoveJump(draught, to);
                        }
                }
        }
        return move;
    }

    private void outputText(String s) {
        output = "\n>>> " + s;
        System.out.println("" + (output));
    }

    // Returns true if draught can make a jump.
    private boolean mandatoryJump(DraughtPosition draught, Board board) {
        MoveList movelist = new MoveList();
        draught.findValidJumps(movelist, board);
        if (movelist.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    // The computer thinks....
    public void computerMoves() {
        pan.turn = " Computer turn ";
        MoveList validMoves = GameSearch.findAllValidMoves(pan.boardO, computerColor);
        if (validMoves.size() == 0) {
            JOptionPane.showMessageDialog((Component) this, "\nCongratulations!"
                    + "You win\n", "Draughts", JOptionPane.INFORMATION_MESSAGE);
            outputText("You win.");

        } else {

            pan.boardO.getHistory().reset();
            Board comBoard = null;
            if (algorithm == 2) {
                comBoard = GameSearch.minimaxAB(pan.boardO, thinkDepth, computerColor,
                        GameSearch.minusInfinityBoard(),
                        GameSearch.plusInfinityBoard());
            }
            if (algorithm == 1) {
                comBoard = GameSearch.minimax(pan.boardO, thinkDepth, computerColor);
            }
            Move move = comBoard.getHistory().first();

            pan.boardO = GameSearch.executeMove(move, pan.boardO);

            if (!isBack && !isForward) {
                boardHistory.add(pan.boardO);
                currentPositionInBoradHistory = boardHistory.size() - 1;
            }
            MoveIterator iterator = pan.boardO.getHistory().getIterator();
            String moves = "";
            while (iterator.hasNext()) {
                moves = moves + iterator.next();
                if (iterator.hasNext()) {
                    moves = moves + " , ";
                }
            }
            pan.computer_move = moves;
            int s = moves.indexOf("(");
            int ss = moves.indexOf(")");
            String[] values = moves.substring(s + 1, ss).split("-");
            outputText("the computer make this move : " + moves);
            validMoves = GameSearch.findAllValidMoves(pan.boardO, userColor);
            if (validMoves.size() == 0) {
                JOptionPane.showMessageDialog((Component) this, "Computer wins!", "Draughts", JOptionPane.INFORMATION_MESSAGE);
                outputText("Sorry. The computer wins.");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void removeBordsAfter(int i) {
        int taille = boardHistory.size();
        for (int k = taille - 1; k >= i; k--) {
            boardHistory.remove(k);
        }
    }

    private void importGamefromSavegames(String toString) {
        ArrayList<Board> importedHistory = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("savegames/" + toString);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            importedHistory = (ArrayList<Board>) in.readObject();
            boardHistory = importedHistory;
            currentPositionInBoradHistory = boardHistory.size() - 1;
            pan.boardO = boardHistory.get(boardHistory.size() - 1);
            pan.repaint();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
    }

    private void SaveHistoryOfGame(String toString) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("savegames/" + toString + ".game");

        // création d'un "flux objet" avec le flux fichier
        ObjectOutputStream os = new ObjectOutputStream(fos);
        try {
            //écriture de l'objet dans le flux de sortie
            os.writeObject(boardHistory);
            // vider le tampon
            os.flush();
        } finally {
            //fermeture des flux
            try {
                os.close();
            } finally {
                fos.close();
            }
        }
    }

}
