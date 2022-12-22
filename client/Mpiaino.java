import java.awt.event.*;
import javax.swing.*;

public class Mpiaino implements MouseListener {

    private JButton upload;
    
    public Mpiaino(JButton b){
        this.setUpload(b);
    }
    @Override
    public void mouseClicked(MouseEvent e){
        if (e.getSource() instanceof JButton) {
            String val=getUpload().getText();
            if (val=="UPLOAD") {
                try {
                    Client.Chooser();
                } catch (Exception ex) {
                    
                }
                
            }else if (val=="DOWNLOAD") {
                new Download();
            }else{
                try {
                    Client.getFile(getUpload().getText());
                } catch (Exception ex) {
                    // TODO: handle exception
                }
                
            }
            
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
    
        
    }
    public JButton getUpload() {
        return upload;
    }
    public void setUpload(JButton upload) {
        this.upload = upload;
    }
    
}
