import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    public Panel(){
        this.setBackground(Color.white);
        this.setBounds(0,0,200,400);
        System.out.println("hello");
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

    }
}
