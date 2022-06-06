import javax.swing.*;
import java.awt.*;

public class Square extends JButton {
    private Color color;
    private int player;
    private boolean isKing;
    private int x;
    private int y;

    public Square (Color color, int player, boolean isKing) {
        this.color = color;
        this.player = player;
        this.isKing = isKing;
    }
    public Square setPlayer(int player) {
        this.player = player;
        return this;
    }
    public Square setKing(boolean king) {
        this.isKing = isKing;
        return this;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.color);
        g.fillRect(0, 0,
                this.getWidth(), this.getHeight());
        switch (this.player) {
            case GameBoard.PLAYER_NONE:
                //do nothing
                break;
            case GameBoard.PLAYER_RED:
                g.setColor(Color.RED);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                break;
            case GameBoard.PLAYER_BLUE:
                g.setColor(Color.BLUE);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                break;
            case GameBoard.PLAYER_BLUE_SELECTED:
                g.setColor(Color.green);
                g.drawOval(4,4, this.getWidth() - 8, this.getHeight() -8);
                g.setColor(Color.BLUE);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                break;
            case GameBoard.PLAYER_RED_SELECTED:
                g.setColor(Color.green);
                g.drawOval(4,4, this.getWidth() - 8, this.getHeight() -8);
                g.setColor(Color.RED);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                break;
            case GameBoard.PLAYER_BLUE_KING:
                g.setColor(Color.BLUE);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                g.setColor(Color.GREEN);
                g.fillOval(20, 20, this.getWidth() - 40, this.getHeight() - 40);
                break;
            case GameBoard.PLAYER_RED_KING:
                g.setColor(Color.RED);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                g.setColor(Color.GREEN);
                g.fillOval(20, 20, this.getWidth() - 40, this.getHeight() - 40);
                break;
            case GameBoard.PLAYER_BLUE_KING_SELECTED:
                g.setColor(Color.green);
                g.drawOval(4,4, this.getWidth() - 8, this.getHeight() -8);
                g.setColor(Color.BLUE);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10); /* Makes king green dot */
                g.setColor(Color.GREEN);
                g.fillOval(20, 20, this.getWidth() - 40, this.getHeight() - 40);
                break;
            case GameBoard.PLAYER_RED_KING_SELECTED:
                g.setColor(Color.green);
                g.drawOval(4,4, this.getWidth() - 8, this.getHeight() -8);
                g.setColor(Color.RED);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10); /* Makes king green dot */
                g.setColor(Color.GREEN);
                g.fillOval(20, 20, this.getWidth() - 40, this.getHeight() - 40);
                break;
            case GameBoard.WRONG_PLAYER_SELECTED:
                g.setColor(Color.red);
                g.fillRect(0,0,this.getWidth(),this.getHeight());
                break;
        }
    }
    public boolean isBluePlayer () {
        return this.player == GameBoard.PLAYER_BLUE;
    }
    public boolean isRedPlayer () {
        return this.player == GameBoard.PLAYER_RED;
    }
    public boolean isRedKing () {
        return this.player == GameBoard.PLAYER_RED_KING;
    }
    public boolean isBlueKing () {
        return this.player == GameBoard.PLAYER_BLUE_KING;
    }
    public boolean isRedSelectedPlayer(){
        return this.player == GameBoard.PLAYER_RED_SELECTED;
    }
    public boolean isRedSelectedKing(){
        return this.player == GameBoard.PLAYER_RED_KING_SELECTED;
    }
    public boolean isBlueSelectedKing(){
        return this.player == GameBoard.PLAYER_BLUE_KING_SELECTED;
    }
    public boolean isBlueSelectedPlayer(){
        return this.player == GameBoard.PLAYER_BLUE_SELECTED;
    }
    public boolean isEmptySquare(){
        return this.player == GameBoard.PLAYER_NONE;
    }
    public boolean isValidSquare(){
        return this.color == Color.BLACK;
    }
    public boolean isKing() {
        return isKing;
    }



}
