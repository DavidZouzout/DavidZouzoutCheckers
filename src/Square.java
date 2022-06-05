import javax.swing.*;
import java.awt.*;

public class Square extends JButton {
    private Color color;
    private int player;
    private boolean isKing;
    private int x;
    private int y;



    public Square (Color color, int player, int x, int y, boolean isKing) {
        this.color = color;
        this.player = player;
        this.isKing = isKing;
        this.x = x;
        this.y = y;
    }

//    public Square(int x, int y) {
//        this.x = x;
//        this.y = y;
//        this.color = this.color;
//    }

    public Square setPlayer(int player) {
        this.player = player;
        return this;
    }
//    public player getPieceByIndex(int i, int j){
//        return
//    }
    public int findPlayerAt(int x,int y) {
        if (this.player == GameBoard.PLAYER_RED) return GameBoard.PLAYER_RED;
        else if (this.player == GameBoard.PLAYER_BLUE) return GameBoard.PLAYER_BLUE;
        else return GameBoard.PLAYER_NONE;
    }
    public Square setPlayerByIndex(int row,int column){
        this.x = row;
        this.y = column;
        return new Square(Color.black,GameBoard.PLAYER_NONE,row,column,false);
    }
    public Square getPlayer(int x, int y) {
        if(this.player == GameBoard.PLAYER_BLUE) return this;
        else if(this.player == GameBoard.PLAYER_RED) return this;
        else if(this.player == GameBoard.PLAYER_BLUE) return this;
        else return null;

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
            case GameBoard.WRONG_PLAYER_SELECTED_RED_TURN:
                g.setColor(Color.YELLOW);
                g.drawOval(4,4, this.getWidth() - 8, this.getHeight() -8);
                g.setColor(Color.BLUE);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                break;
            case GameBoard.WRONG_PLAYER_SELECTED_BLUE_TURN:
                g.setColor(Color.YELLOW);
                g.drawOval(4,4, this.getWidth() - 8, this.getHeight() -8);
                g.setColor(Color.RED);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                break;
            case GameBoard.WRONG_PLAYER_SELECTED:
                g.setColor(Color.red);
                g.fillRect(0,0,this.getWidth(),this.getHeight());
                break;

        }
        if (this.isKing) {
            g.setColor(Color.GREEN);
            g.fillOval(20, 20, this.getWidth() - 40, this.getHeight() - 40);
        }
    }
    public boolean isBluePlayer () {
        return this.player == GameBoard.PLAYER_BLUE;
    }
    public boolean isRedPlayer () {
        return this.player == GameBoard.PLAYER_RED;
    }
    public boolean isRedSelectedPlayer(){
        return this.player == GameBoard.PLAYER_RED_SELECTED;
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
