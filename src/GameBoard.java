
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class GameBoard extends JPanel {

    public static final int BOARD_LENGTH = 8;

    private final ArrayList<ArrayList<Square>> boardData;
    public static final int PLAYER_NONE = 0;
    public static final int PLAYER_BLUE = 1;
    public static final int PLAYER_RED = 2;
    public static final int PLAYER_DEAD = 3;
    public static final int PLAYER_RED_SELECTED = 4;
    public static final int PLAYER_BLUE_SELECTED = 5;
    public static final int WRONG_PLAYER_SELECTED_RED_TURN = 6;
    public static final int WRONG_PLAYER_SELECTED_BLUE_TURN = 7;
    public static final int WRONG_PLAYER_SELECTED = 8;
//    public static final int PLAYER_RED_KING = 7;
//    public static final int PLAYER_BLUE_KING = 8;

    int redPiecesLeft = 12;
    int bluePiecesLeft = 12;
    int firstClickRow;
    int firstClickColumn;
    int secondClickRow;
    int secondClickColumn;

    Square firstClick = null;
    Square secondClick = null;
    // Square thirdClick = null;
    Square redDeadPiece = null;
    Square blueDeadPiece = null;
    Square isSelected = null;
    Square findPiece = null;
    boolean isRedTurn = false;

    public GameBoard(int x, int y, int width, int height) {
        this.setBackground(Color.BLUE);
        GridLayout gridLayout = new GridLayout(BOARD_LENGTH, BOARD_LENGTH);
        this.setLayout(gridLayout);
        this.boardData = new ArrayList<>();

        boolean black = false;
        boolean setPlayer = false;

        for (int row = 0; row < BOARD_LENGTH; row++) {
            ArrayList<Square> currentRow = new ArrayList<>();
            for (int column = 0; column < BOARD_LENGTH; column++) {
                int player = PLAYER_NONE;
                if (setPlayer) {
                    if (row < 3) {
                        player = PLAYER_RED;
                    } else if (row >= 5) {
                        player = PLAYER_BLUE;
                    }
                }
                Square square;
                if (black) {
                    square = new Square(Color.BLACK, player, false);
                }
                else {
                    square = new Square(Color.WHITE, player, false);
                }
                int finalRow = row;
                int finalColumn = column;

                Square finalSquare = square;
                square.addActionListener((event) -> {
                    if (firstClick == null) {
                        firstClick = finalSquare;
                        firstClickRow = finalRow;
                        firstClickColumn = finalColumn;
                        isSelected = firstClick;
                        if (isRedTurn){
                            if (firstClick.isValidSquare() && firstClick.isRedPlayer() && !firstClick.isEmptySquare()) {
                                isSelected.setPlayer(PLAYER_RED_SELECTED, firstClickRow, firstClickColumn);
                            }
                            else if(firstClick.isEmptySquare() || !firstClick.isValidSquare()){
//                                isSelected.setPlayer(WRONG_PLAYER_SELECTED,firstClickRow,firstClickColumn);
//                                isSelected.setPlayer(PLAYER_NONE,firstClickRow,firstClickColumn);
                                firstClick = null;
                            }
                        }
                        else {
                            if (firstClick.isBluePlayer() && firstClick.isValidSquare() && !firstClick.isEmptySquare()) {
                                isSelected.setPlayer(PLAYER_BLUE_SELECTED, firstClickRow, firstClickColumn);
                            }
                            else if(firstClick.isEmptySquare() || !firstClick.isValidSquare()) {
//                                isSelected.setPlayer(WRONG_PLAYER_SELECTED, firstClickRow, firstClickColumn);
//                                isSelected.setPlayer(PLAYER_NONE,firstClickRow,firstClickColumn);
                                firstClick = null;
                            }
                        }
                    }
                    else{
                        secondClick = finalSquare;
                        secondClickRow = finalRow;
                        secondClickColumn = finalColumn;
                        // If user presses on twice on the same color/player.
                        if((firstClick.isRedSelectedPlayer() && (secondClick.isRedPlayer() || secondClick.isRedSelectedPlayer())) || (firstClick.isBlueSelectedPlayer() &&
                                (secondClick.isBluePlayer() || secondClick.isBlueSelectedPlayer()))){
                            if(firstClick.isRedSelectedPlayer()) firstClick.setPlayer(PLAYER_RED,firstClickRow,secondClickRow);
                            else if (firstClick.isBlueSelectedPlayer()) firstClick.setPlayer(PLAYER_BLUE,firstClickRow,secondClickRow);
                        }
                    }       // If both user clicks are not empty.
                    if (firstClick != null && secondClick != null && bluePiecesLeft > 0 && redPiecesLeft > 0) {
                        boolean isRedFirstClickValid = isRedTurn && (firstClick.isRedPlayer() || firstClick.isRedSelectedPlayer()) ;
                        boolean isBlueFirstClickValid = !isRedTurn && (firstClick.isBluePlayer() || firstClick.isBlueSelectedPlayer());
                        redDeadPiece = firstClick;
                        blueDeadPiece = firstClick;
                           // If both user clicks are valid.
                        if ((isRedFirstClickValid || isBlueFirstClickValid) && secondClick.isValidSquare() && secondClick.isEmptySquare()) {
                            // If user moves player once
                            if (Math.abs(secondClickColumn - firstClickColumn) == 1 && Math.abs(secondClickRow - firstClickRow) == 1) {
                                if (isRedTurn && (secondClickRow - firstClickRow) == 1) { // Red's turn
                                    isRedTurn = !isRedTurn;
                                    firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                    secondClick.setPlayer(PLAYER_RED, secondClickRow, secondClickColumn);
                                } else if (!isRedTurn && (secondClickRow - firstClickRow) == -1) {  // Blue's turn
                                    isRedTurn = !isRedTurn;
                                    firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                    secondClick.setPlayer(PLAYER_BLUE, secondClickRow, secondClickColumn);
                                }
                                // If eating another player
                            } else if (Math.abs(secondClickColumn - firstClickColumn) == 2 && Math.abs(secondClickRow - firstClickRow) == 2) {
                                if (secondClickRow - firstClickRow == 2) {                      //Red turn
//                                    if (secondClickColumn - firstClickColumn == 2) {
                                        findPiece = new Square((firstClickRow + 1), (firstClickColumn + 1));//eating right piece
//                                        if ((findPiece.PlayerColor((firstClickRow + 1), (firstClickColumn + 1))).isBluePlayer())
//                                        {
                                            isRedTurn = !isRedTurn;
                                            firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                            secondClick.setPlayer(PLAYER_RED, secondClickRow, secondClickColumn);
                                            blueDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow + 1), (firstClickColumn + 1));
                                            bluePiecesLeft--;
                                            System.out.println(blueDeadPiece.isEmptySquare());
//                                        } else {
//                                            firstClick.setPlayer(PLAYER_RED, firstClickRow, firstClickColumn);
//                                            secondClick.setPlayer(PLAYER_NONE, secondClickRow, secondClickColumn);
//                                        }
                                    }
                                    else if (secondClickColumn - firstClickColumn == -2) {
                                        findPiece = new Square((firstClickRow + 1), (firstClickColumn - 1));
//                                        if ((findPiece.PlayerColor((firstClickRow + 1), (firstClickColumn - 1))).isBluePlayer()) {   //eating left piece
                                            isRedTurn = !isRedTurn;
                                            firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                            secondClick.setPlayer(PLAYER_BLUE, secondClickRow, secondClickColumn);
                                            blueDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow + 1), (firstClickColumn - 1));
                                            bluePiecesLeft--;
//                                        }
//                                        else {
//                                            firstClick.setPlayer(PLAYER_RED,firstClickRow,firstClickColumn);
//                                            secondClick.setPlayer(PLAYER_NONE,secondClickRow,secondClickColumn);
//                                        }
                                    }
//                                }
                                else{                           //Blue turn
                                    if (secondClickColumn - firstClickColumn == 2) {  //eating right piece
                                        findPiece = new Square((firstClickRow - 1), (firstClickColumn + 1));
//                                        if ((findPiece.PlayerColor((firstClickRow - 1), (firstClickColumn + 1))).isRedPlayer()) {
                                            isRedTurn = !isRedTurn;
                                            firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                            secondClick.setPlayer(PLAYER_BLUE, secondClickRow, secondClickColumn);
                                            redDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow - 1), (firstClickColumn + 1));
                                            redPiecesLeft--;
//                                        }
//                                        else{
//                                            firstClick.setPlayer(PLAYER_BLUE,firstClickRow,firstClickColumn);
//                                            secondClick.setPlayer(PLAYER_NONE,secondClickRow,secondClickColumn);
//                                        }
                                    }




                                    //before commit
                                    else if (secondClickRow - firstClickRow == - 2) { //eating left piece
                                        findPiece = new Square((firstClickRow - 1), (firstClickColumn - 1));
//                                        if ((findPiece.PlayerColor((firstClickRow - 1), (firstClickColumn - 1))).isRedPlayer()) {
                                            isRedTurn = !isRedTurn;
                                            firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                            secondClick.setPlayer(PLAYER_BLUE, secondClickRow, secondClickColumn);
                                            redDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow - 1), (firstClickColumn - 1));
                                            redPiecesLeft--;

//                                        }
//                                        else {
//                                            firstClick.setPlayer(PLAYER_BLUE,firstClickRow,firstClickColumn);
//                                            secondClick.setPlayer(PLAYER_NONE,secondClickRow,secondClickColumn);
//                                        }
                                    }
                                }

                            }
                        }
                        if(isBlueFirstClickValid && (!secondClick.isValidSquare() || secondClick.isEmptySquare() || secondClick.isRedPlayer()))
                            firstClick.setPlayer(PLAYER_BLUE,firstClickRow,firstClickColumn);
                        else if(isRedFirstClickValid && (!secondClick.isValidSquare() || secondClick.isEmptySquare() || secondClick.isBluePlayer()))
                            firstClick.setPlayer(PLAYER_RED,firstClickRow,firstClickColumn);

                        firstClick = null;
                        secondClick = null;
                        blueDeadPiece = null;
                        redDeadPiece = null;

                    }

                    if(redPiecesLeft < 1) System.out.println("BLUE TEAM WINNNSSS");
                    else if (bluePiecesLeft < 1) System.out.println("RED TEAM WINNNSSS");
                    repaint();

                });

                this.add(square);
                currentRow.add(square);
                black = !black;
                setPlayer = !setPlayer;
            }
            this.boardData.add(currentRow);
            black = !black;
            setPlayer = !setPlayer;
        }

    }
}