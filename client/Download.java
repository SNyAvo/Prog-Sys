import java.awt.*;
import java.awt.Color;

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.Vector;

public class Download extends JFrame{
    Vector<String> list=new Vector<>();
    public Download(){
        this.setSize(700, 350);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        readFile("FileList.txt");
        JPanel jp=new JPanel();
        String[]li=list.toArray(new String[list.size()]);
        JButton[] jb=new JButton[li.length];
        JLabel[]jl=new JLabel[li.length];
        for (int i = 0; i < jb.length; i++) {
            jb[i]=new JButton(li[i]);
            jb[i].addMouseListener(new Mpiaino(jb[i]));
            jp.add(jb[i]);
            // jl[i]=new JLabel(li[i]);
            // jp.add(jl[i]);
        }
        this.add(jp);
    }
    public void readFile(String name){

        try {
            File file=new File(name);
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            
            Object[] tableLines=br.lines().toArray();
            for(int i = 0; i <= tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                list.add(line);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        
   }
}
