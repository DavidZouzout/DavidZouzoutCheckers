import javax.swing.*;

public class Main extends JFrame {

    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH;

    public static void main(String[] args) {
        new Main();
    }

    public Main () {
        Panel panel = new Panel();
        this.add(panel);
        this.setSize(50,50);
        this.setVisible(true);
        GameBoard gameBoard = new GameBoard(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.add(gameBoard);
        this.setResizable(false);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);





    /*
    event click listener - player color/row/column/isKing
    rules of the game - function that gets both positions and return true/false (
        first or second position can't be null.
        second can't b a player
        difference can't b more than 2 in rows or columns
        red - second click row can't be lower than first click row
        blue -  the opposite
    )
    nullify first pos and move player to second pos
    if player is eaten  then  nullify that players spot with square(player_null, color: black, false)

    if it's supposed to be a king then you sen to the square ...... true


        */
    }

}
