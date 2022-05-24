import com.sun.media.jfxmedia.events.PlayerStateEvent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class GameBoard extends JPanel {

    public static final int BOARD_LENGTH = 8;

    private ArrayList<ArrayList<Square>> boardData;
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

    protected void highLightPiece(Graphics g) {
        g.setColor(Color.GREEN);
    }

    Square firstClick = null;
    Square secondClick = null;
    // Square thirdClick = null;
    Square redDeadPiece = null;
    Square blueDeadPiece = null;
    Square isSelected = null;
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
                            if (firstClick.isValidSquare() && firstClick.isBluePlayer() && !firstClick.isEmptySquare()) {
                                isSelected.setPlayer(PLAYER_BLUE_SELECTED, firstClickRow, firstClickColumn);
                            }
                            else if(firstClick.isEmptySquare() || !firstClick.isValidSquare()) {
//                                isSelected.setPlayer(WRONG_PLAYER_SELECTED, firstClickRow, firstClickColumn);
//                                isSelected.setPlayer(PLAYER_NONE,firstClickRow,firstClickColumn);
                                firstClick = null;
                            }
                        }
                    }
                    else if (firstClick != null) {
                        secondClick = finalSquare;
                        secondClickRow = finalRow;
                        secondClickColumn = finalColumn;
                    }
                    while (firstClick != null && secondClick != null && bluePiecesLeft > 0 && redPiecesLeft > 0) {
                        boolean isRedFirstClickValid = isRedTurn && (firstClick.isRedPlayer() || firstClick.isRedSelectedPlayer()) ;
                        boolean isBlueFirstClickValid = !isRedTurn && (firstClick.isBluePlayer() || firstClick.isBlueSelectedPlayer());
                        if ((isRedFirstClickValid || isBlueFirstClickValid) && secondClick.isValidSquare() && secondClick.isEmptySquare()) {
                            if (Math.abs(secondClickColumn - firstClickColumn) == 1 && Math.abs(secondClickRow - firstClickRow) == 1) {
                                if (isRedTurn && (secondClickRow - firstClickRow) == 1) {
                                    firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                    secondClick.setPlayer(PLAYER_RED, secondClickRow, secondClickColumn);
                                    isRedTurn = !isRedTurn;
                                } else if (!isRedTurn && (secondClickRow - firstClickRow) == -1) {
                                    firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                    secondClick.setPlayer(PLAYER_BLUE, secondClickRow, secondClickColumn);
                                    isRedTurn = !isRedTurn;
                                }
                            } else if (Math.abs(secondClickColumn - firstClickColumn) == 2 && Math.abs(secondClickRow - firstClickRow) == 2) {
                                if (secondClickRow - firstClickRow == 2) {
                                    if (isRedTurn) {
                                        if (secondClickColumn - firstClickColumn == 2) {  //eating right piece
                                            firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                            secondClick.setPlayer(PLAYER_RED, secondClickRow, secondClickColumn);
                                            blueDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow + 1), (firstClickColumn + 1));
                                            bluePiecesLeft--;
                                            isRedTurn = !isRedTurn;
                                        } else if (isRedTurn && secondClickRow - firstClickRow == -2) { //eating left piece
                                            firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                            secondClick.setPlayer(PLAYER_RED, secondClickRow, secondClickColumn);
                                            blueDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow + 1), (firstClickColumn - 1));
                                            bluePiecesLeft--;
                                            isRedTurn = !isRedTurn;
                                        }
                                    } else if(!isRedTurn){
                                        if (secondClickColumn - firstClickColumn == 2) { //eating right piece
                                            firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                            secondClick.setPlayer(PLAYER_BLUE, secondClickRow, secondClickColumn);
                                            redDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow + 1), (firstClickColumn + 1));
                                            redPiecesLeft--;
                                            isRedTurn = !isRedTurn;
                                        } else if (isRedTurn && secondClickRow - firstClickRow == - 2) { //eating left piece
                                            firstClick.setPlayer(PLAYER_NONE, firstClickRow, firstClickColumn);
                                            secondClick.setPlayer(PLAYER_BLUE, secondClickRow, secondClickColumn);
                                            redDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow + 1), (firstClickColumn - 1));
                                            redPiecesLeft--;
                                            isRedTurn = !isRedTurn;
                                        }
                                    }
                                }

                            }
                        }
                        if(isBlueFirstClickValid && (!secondClick.isValidSquare() || secondClick.isEmptySquare() || secondClick.isRedPlayer()))
                            firstClick.setPlayer(PLAYER_BLUE,firstClickRow,firstClickColumn);
                        else if(isRedFirstClickValid && (!secondClick.isValidSquare() || secondClick.isEmptySquare() || secondClick.isBluePlayer()))
                            firstClick.setPlayer(PLAYER_RED,firstClickRow,firstClickColumn);

//                        else if((!firstClick.isValidSquare() || firstClick.isEmptySquare()) || (!secondClick.isValidSquare() || secondClick.isEmptySquare()))
//                            isRedTurn = !isRedTurn;

                        firstClick = null;
                        secondClick = null;
                        blueDeadPiece = null;
                        redDeadPiece = null;
                        break;
                    }

                    if(redPiecesLeft < 1) System.out.println("BLUE TEAM WINNNSSS");
                    else if (bluePiecesLeft < 1) System.out.println("RED TEAM WINNNSSS");

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