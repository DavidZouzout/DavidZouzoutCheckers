
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class GameBoard extends JPanel {


    private final static ArrayList<ArrayList<Square>> boardData = new ArrayList<>();
    public static final int BOARD_LENGTH = 8;
    public static final int PLAYER_NONE = 0;
    public static final int PLAYER_BLUE = 1;
    public static final int PLAYER_RED = 2;
    public static final int PLAYER_RED_SELECTED = 4;
    public static final int PLAYER_BLUE_SELECTED = 5;
    public static final int WRONG_PLAYER_SELECTED = 6;
    public static final int PLAYER_RED_KING = 7;
    public static final int PLAYER_BLUE_KING = 9;
    public static final int PLAYER_RED_KING_SELECTED = 10;
    public static final int PLAYER_BLUE_KING_SELECTED = 11;
    int redPiecesLeft = 12;
    int bluePiecesLeft = 12;
    int firstClickRow;
    int firstClickColumn;
    int secondClickRow;
    int secondClickColumn;
    Square firstClick = null;
    Square secondClick = null;
    Square eatenPLayer = null;
    Square isSelected = null;
    Square becomeKing = null;
    boolean isRedTurn = false;
    public GameBoard() {
        this.setBackground(Color.BLUE);
        GridLayout gridLayout = new GridLayout(BOARD_LENGTH, BOARD_LENGTH);
        this.setLayout(gridLayout);

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
                    square = new Square(Color.WHITE, player,false);
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
                            if (firstClick.isValidSquare() && (firstClick.isRedPlayer() || firstClick.isRedKing()) && !firstClick.isEmptySquare()) {
                                if (firstClick.isRedKing()) isSelected.setPlayer((PLAYER_RED_KING_SELECTED));
                                else isSelected.setPlayer(PLAYER_RED_SELECTED);
                            }
                            else if(firstClick.isEmptySquare() || !firstClick.isValidSquare()){
                             new Thread(()->{
                                        isSelected.setPlayer(WRONG_PLAYER_SELECTED);
                                        try {
                                            Thread.sleep(200);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                       }
                                        isSelected.setPlayer(PLAYER_NONE);
                                        firstClick = null;
                                        repaint();
                                    }).start();
                            }
                        }
                        else {
                            if ((firstClick.isBluePlayer() || firstClick.isBlueKing()) && firstClick.isValidSquare() && !firstClick.isEmptySquare()) {
                                if(firstClick.isBlueKing()) isSelected.setPlayer((PLAYER_BLUE_KING_SELECTED));
                                else isSelected.setPlayer(PLAYER_BLUE_SELECTED);
                            }
                            else if(firstClick.isEmptySquare() || !firstClick.isValidSquare()) {
                                new Thread(()->{
                                    isSelected.setPlayer(WRONG_PLAYER_SELECTED);
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    isSelected.setPlayer(PLAYER_NONE);
                                    firstClick = null;
                                    repaint();
                                }).start();
                            }
                        }
                    }
                    else{
                        secondClick = finalSquare;
                        secondClickRow = finalRow;
                        secondClickColumn = finalColumn;
                        /* If user presses on twice on the same color/player. */
                         if ((firstClick.isBlueSelectedKing() || firstClick.isRedSelectedKing()) && (secondClick.isBlueSelectedKing()
                                 || secondClick.isBluePlayer() || secondClick.isRedSelectedKing() || secondClick.isRedPlayer())) {
                            if(firstClick.isBlueSelectedKing()) firstClick.setPlayer(PLAYER_BLUE_KING);
                            else if (firstClick.isRedSelectedKing()) firstClick.setPlayer(PLAYER_RED_KING);
                             /* If user presses twice on the same color/player/king. */
                         } else if((firstClick.isRedSelectedPlayer() && (secondClick.isRedPlayer() || secondClick.isRedSelectedPlayer())) || (firstClick.isBlueSelectedPlayer()
                                 && (secondClick.isBluePlayer() || secondClick.isBlueSelectedPlayer()))){
                             if (firstClick.isBlueSelectedPlayer()) firstClick.setPlayer(PLAYER_BLUE);
                             else if (firstClick.isRedSelectedPlayer()) firstClick.setPlayer(PLAYER_RED);
                         }
                    }
                                /* If both user clicks are not empty. */
                    if (firstClick != null && secondClick != null && bluePiecesLeft > 0 && redPiecesLeft > 0) {
                        boolean isRedFirstClickValid = isRedTurn && (firstClick.isRedPlayer() || firstClick.isRedSelectedPlayer() || firstClick.isRedSelectedPlayer() || firstClick.isRedSelectedKing());
                        boolean isBlueFirstClickValid = !isRedTurn && (firstClick.isBluePlayer() || firstClick.isBlueSelectedPlayer() || firstClick.isRedSelectedPlayer() || firstClick.isBlueSelectedKing());
                                      /* If both user clicks are valid. */
                        if ((isRedFirstClickValid || isBlueFirstClickValid) && secondClick.isValidSquare() && secondClick.isEmptySquare()) {
                                     /* If user moves player once */
                            if (Math.abs(secondClickColumn - firstClickColumn) == 1 && Math.abs(secondClickRow - firstClickRow) == 1) {
                                if (isRedTurn) {       /* Red's turn */
                                    if (firstClick.isRedSelectedKing()) {
                                        isRedTurn = !isRedTurn;
                                        firstClick.setPlayer(PLAYER_NONE);
                                        secondClick.setPlayer(PLAYER_RED_KING);
                                    } else if ((secondClickRow - firstClickRow) == 1) {
                                        isRedTurn = !isRedTurn;
                                        firstClick.setPlayer(PLAYER_NONE);
                                        secondClick.setPlayer(PLAYER_RED);
                                    } else if ((secondClickRow - firstClickRow) == -1) firstClick.setPlayer(PLAYER_RED);
                                } else {              /* Blue's turn */
                                    if (firstClick.isBlueSelectedKing()) {
                                        isRedTurn = !isRedTurn;
                                        firstClick.setPlayer(PLAYER_NONE);
                                        secondClick.setPlayer(PLAYER_BLUE_KING);
                                    } else if ((secondClickRow - firstClickRow) == -1) {
                                        isRedTurn = !isRedTurn;
                                        firstClick.setPlayer(PLAYER_NONE);
                                        secondClick.setPlayer(PLAYER_BLUE);
                                    } else if ((secondClickRow - firstClickRow) == 1) firstClick.setPlayer(PLAYER_BLUE);
                                }
                                /* If eating another player */
                            } else if (Math.abs(secondClickColumn - firstClickColumn) == 2 && Math.abs(secondClickRow - firstClickRow) == 2) {
                                if (secondClickRow - firstClickRow == 2) {          /* Red turn */
                                    if (secondClickColumn - firstClickColumn == 2) {            /* RED Eating right piece */
                                        eatenPLayer = boardData.get((firstClickRow + 1)).get((firstClickColumn + 1));
                                        if (firstClick.isBlueSelectedKing() || firstClick.isRedSelectedKing()) {
                                            if (firstClick.isRedSelectedKing()) {
                                                if (eatenPLayer.isBluePlayer() || eatenPLayer.isBlueKing()) {
                                                    firstClick.setPlayer(PLAYER_NONE);
                                                    secondClick.setPlayer(PLAYER_RED_KING);
                                                    eatenPLayer.setPlayer(PLAYER_NONE);
                                                    isRedTurn = !isRedTurn;
                                                    bluePiecesLeft--;
                                                } else firstClick.setPlayer(PLAYER_RED_KING);
                                            } else if (firstClick.isBlueSelectedKing()) {
                                                if (eatenPLayer.isRedPlayer() || eatenPLayer.isRedKing()) {
                                                    firstClick.setPlayer(PLAYER_NONE);
                                                    secondClick.setPlayer(PLAYER_BLUE_KING);
                                                    eatenPLayer.setPlayer(PLAYER_NONE);
                                                    isRedTurn = !isRedTurn;
                                                    redPiecesLeft--;
                                                } else firstClick.setPlayer(PLAYER_BLUE_KING);
                                            }
                                        } else {
                                            if (eatenPLayer.isBluePlayer() || eatenPLayer.isBlueKing()) {
                                                secondClick.setPlayer(PLAYER_RED);
                                                firstClick.setPlayer(PLAYER_NONE);
                                                eatenPLayer.setPlayer(PLAYER_NONE);
                                                isRedTurn = !isRedTurn;
                                                bluePiecesLeft--;
                                            } else firstClick.setPlayer(PLAYER_RED);
                                        }
                                    } else if (secondClickColumn - firstClickColumn == -2) {    /* RED piece or King Eating left piece */
                                        eatenPLayer = boardData.get((firstClickRow + 1)).get((firstClickColumn - 1));
                                        if (firstClick.isBlueSelectedKing() || firstClick.isRedSelectedKing()) {
                                            if (firstClick.isRedSelectedKing()) {
                                                if (eatenPLayer.isBluePlayer() || eatenPLayer.isBlueKing()) {
                                                    firstClick.setPlayer(PLAYER_NONE);
                                                    secondClick.setPlayer(PLAYER_RED_KING);
                                                    eatenPLayer.setPlayer(PLAYER_NONE);
                                                    isRedTurn = !isRedTurn;
                                                    bluePiecesLeft--;
                                                } else firstClick.setPlayer(PLAYER_RED_KING);
                                            } else if (firstClick.isBlueSelectedKing()) {
                                                if (eatenPLayer.isRedPlayer() || eatenPLayer.isRedKing()) {
                                                    firstClick.setPlayer(PLAYER_NONE);
                                                    secondClick.setPlayer(PLAYER_BLUE_KING);
                                                    eatenPLayer.setPlayer(PLAYER_NONE);
                                                    isRedTurn = !isRedTurn;
                                                    redPiecesLeft--;
                                                } else firstClick.setPlayer(PLAYER_BLUE_KING);
                                            }
                                        } else {
                                            if (eatenPLayer.isBluePlayer() || eatenPLayer.isBlueKing()) {
                                                secondClick.setPlayer(PLAYER_RED);
                                                firstClick.setPlayer(PLAYER_NONE);
                                                eatenPLayer.setPlayer(PLAYER_NONE);
                                                isRedTurn = !isRedTurn;
                                                bluePiecesLeft--;
                                            } else firstClick.setPlayer(PLAYER_RED);
                                        }
                                    }
                                } else {                           /* Blue turn */
                                    if (secondClickColumn - firstClickColumn == 2) {  /* eating right piece */
                                        eatenPLayer = boardData.get((firstClickRow - 1)).get((firstClickColumn + 1));
                                        if (firstClick.isBlueSelectedKing() || firstClick.isRedSelectedKing()) {
                                            if (firstClick.isRedSelectedKing()) {
                                                if (eatenPLayer.isBluePlayer() || eatenPLayer.isBlueKing()) {
                                                    firstClick.setPlayer(PLAYER_NONE);
                                                    secondClick.setPlayer(PLAYER_RED_KING);
                                                    eatenPLayer.setPlayer(PLAYER_NONE);
                                                    isRedTurn = !isRedTurn;
                                                    bluePiecesLeft--;
                                                } else firstClick.setPlayer(PLAYER_RED_KING);
                                            } else if (firstClick.isBlueSelectedKing()) {
                                                if (eatenPLayer.isRedPlayer() || eatenPLayer.isRedKing()) {
                                                    firstClick.setPlayer(PLAYER_NONE);
                                                    secondClick.setPlayer(PLAYER_BLUE_KING);
                                                    eatenPLayer.setPlayer(PLAYER_NONE);
                                                    isRedTurn = !isRedTurn;
                                                    redPiecesLeft--;
                                                } else firstClick.setPlayer(PLAYER_BLUE_KING);
                                            }
                                        } else {
                                            if (eatenPLayer.isRedPlayer() || eatenPLayer.isRedKing()) {
                                                secondClick.setPlayer(PLAYER_BLUE);
                                                firstClick.setPlayer(PLAYER_NONE);
                                                eatenPLayer.setPlayer(PLAYER_NONE);
                                                isRedTurn = !isRedTurn;
                                                redPiecesLeft--;
                                            } else firstClick.setPlayer(PLAYER_BLUE);
                                        }
                                    } else if (secondClickRow - firstClickRow == -2) { /* eating left piece */
                                        eatenPLayer = boardData.get((firstClickRow - 1)).get((firstClickColumn - 1));
                                        if (firstClick.isBlueSelectedKing() || firstClick.isRedSelectedKing()) {
                                            if (firstClick.isRedSelectedKing()) {
                                                if (eatenPLayer.isBluePlayer() || eatenPLayer.isBlueKing()) {
                                                    firstClick.setPlayer(PLAYER_NONE);
                                                    secondClick.setPlayer(PLAYER_RED_KING);
                                                    eatenPLayer.setPlayer(PLAYER_NONE);
                                                    isRedTurn = !isRedTurn;
                                                    bluePiecesLeft--;
                                                } else firstClick.setPlayer(PLAYER_RED_KING);
                                            } else if (firstClick.isBlueSelectedKing()) {
                                                if (eatenPLayer.isRedPlayer() || eatenPLayer.isRedKing()) {
                                                    firstClick.setPlayer(PLAYER_NONE);
                                                    secondClick.setPlayer(PLAYER_BLUE_KING);
                                                    eatenPLayer.setPlayer(PLAYER_NONE);
                                                    isRedTurn = !isRedTurn;
                                                    redPiecesLeft--;
                                                } else firstClick.setPlayer(PLAYER_BLUE_KING);
                                            }
                                        } else {
                                            if (eatenPLayer.isRedPlayer() || eatenPLayer.isRedKing()) {
                                                secondClick.setPlayer(PLAYER_BLUE);
                                                firstClick.setPlayer(PLAYER_NONE);
                                                eatenPLayer.setPlayer(PLAYER_NONE);
                                                isRedTurn = !isRedTurn;
                                                redPiecesLeft--;
                                            } else firstClick.setPlayer(PLAYER_BLUE);
                                        }
                                    }
                                }
                           }
                        }
                        if (isBlueFirstClickValid && (!secondClick.isValidSquare() || firstClickRow == secondClickRow || firstClickColumn == secondClickColumn ||
                                Math.abs(secondClickColumn - firstClickColumn) > 2 || Math.abs(secondClickRow - firstClickRow) > 2)) {
                            if (firstClick.isBlueSelectedKing()) firstClick.setPlayer(PLAYER_BLUE_KING);
                            else firstClick.setPlayer(PLAYER_BLUE);
                        }
                        else if (isRedFirstClickValid && (!secondClick.isValidSquare() || firstClickRow == secondClickRow || firstClickColumn == secondClickColumn ||
                                Math.abs(secondClickColumn - firstClickColumn) > 2 || Math.abs(secondClickRow - firstClickRow) > 2)) {
                            if (firstClick.isRedSelectedKing()) firstClick.setPlayer(PLAYER_RED_KING);
                            else firstClick.setPlayer(PLAYER_RED);
                        }
                        /* Blue piece becoming king */
                        if (boardData.get(0).get(1).isBluePlayer()) {
                            becomeKing = boardData.get(0).get(1);
                            becomeKing.setPlayer(PLAYER_BLUE_KING);
                            becomeKing.setKing(true);
                        }
                        if (boardData.get(0).get(3).isBluePlayer()) {
                            becomeKing = boardData.get(0).get(3);
                            becomeKing.setPlayer(PLAYER_BLUE_KING);
                            becomeKing.setKing(true);
                        }
                        if (boardData.get(0).get(5).isBluePlayer()) {
                            becomeKing = boardData.get(0).get(5);
                            becomeKing.setPlayer(PLAYER_BLUE_KING);
                            becomeKing.setKing(true);
                        }
                        if (boardData.get(0).get(7).isBluePlayer()) {
                            becomeKing = boardData.get(0).get(7);
                            becomeKing.setPlayer(PLAYER_BLUE_KING);
                            becomeKing.setKing(true);
                        }
                        /* Red piece becoming king */
                        if (boardData.get(7).get(0).isRedPlayer()) {
                            becomeKing = boardData.get(7).get(0);
                            becomeKing.setPlayer(PLAYER_RED_KING);
                            becomeKing.setKing(true);
                        }
                        if (boardData.get(7).get(2).isRedPlayer()) {
                            becomeKing = boardData.get(7).get(2);
                            becomeKing.setPlayer(PLAYER_RED_KING);
                            becomeKing.setKing(true);
                        }
                        if (boardData.get(7).get(4).isRedPlayer()) {
                            becomeKing = boardData.get(7).get(4);
                            becomeKing.setPlayer(PLAYER_RED_KING);
                            becomeKing.setKing(true);
                        }
                        if (boardData.get(7).get(6).isRedPlayer()) {
                            becomeKing = boardData.get(7).get(6);
                            becomeKing.setPlayer(PLAYER_RED_KING);
                            becomeKing.setKing(true);
                        }
                        firstClick = null;
                        secondClick = null;
                    }

                    if(redPiecesLeft < 1) {
                        System.out.println("🥳🥳 BLUE TEAM WIN'S 🥳🥳");
                        System.exit(0);
                    }
                    else if (bluePiecesLeft < 1) {
                        System.out.println("🥳🥳 RED TEAM WIN'S 🥳🥳");
                        System.exit(0);
                    }
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