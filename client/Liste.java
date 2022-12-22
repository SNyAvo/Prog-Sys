import java.awt.*;
import javax.swing.*;

public class Liste extends JFrame{
    public Liste(){
        this.setSize(700, 350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
         
        JButton b1=new JButton("UPLOAD");
        JButton b2=new JButton("DOWNLOAD");
        b1.addMouseListener(new Mpiaino(b1));
        
        Box bo1=Box.createHorizontalBox();
        bo1.add(b1);
        bo1.add(b2);
        Box box=Box.createVerticalBox();
        box.add(bo1);
        // JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        // p1.setSize(700, 100);
        // p1.add(box);
        // p1.setBackground(Color.GRAY);
        p2.setBackground(Color.WHITE);
        // this.setLayout(new GridLayout(2,0));
        // this.add(box);
        p2.add(box);
        // this.add(p1);
        this.add(p2);
    }
}
