import java.awt.*;
import java.awt.Color;

import javax.swing.*;
import javax.swing.table.*;


import java.io.*;

public class Liste extends JFrame{
    public Liste(){
        this.setSize(700, 350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
         
        JButton b1=new JButton("UPLOAD");
        JButton b2=new JButton("DOWNLOAD");
        b1.addMouseListener(new Mpiaino(b1));
        b2.addMouseListener(new Mpiaino(b2));
        Box bo1=Box.createHorizontalBox();
        bo1.add(b1);
        bo1.add(b2);
        Box box=Box.createVerticalBox();
        box.add(bo1);
        // JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        // p2.setLayout(new GridLayout(0,0));
        // JLabel jb=new JLabel("Liste des fichier");
        // JPanel test=readFile("FileList.txt");
        // test.setBackground(Color.WHITE);
        // JScrollBar js=new JScrollBar();
               
        // p1.setSize(700, 100);
        // p1.add(box);
        // p1.setBackground(Color.GRAY);
        p2.setBackground(Color.WHITE);
        // this.setLayout(new GridLayout(2,0));
        // this.add(box);
        p2.add(box);
        // p2.add(jb);
        // p2.add(test);
        // this.add(p1);
        this.add(p2);
    }
    public JPanel readFile(String name){
        JTable jt=new JTable();
        JPanel jp=new JPanel();
        JLabel [] jb;
        JLabel titre=new JLabel("Listes des fichier: ");
        jp.add(titre);
        try {
            File file=new File(name);
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            
            DefaultTableModel model = (DefaultTableModel)jt.getModel();
            Object[] tableLines=br.lines().toArray();
            jb=new JLabel[tableLines.length];
            for(int i = 0; i <= tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                jb[i]=new JLabel(line);
                jp.add(jb[i]);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return jp;
   }
}
