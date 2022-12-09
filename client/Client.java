import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.*;

public class Client {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) throws Exception{
        
            Socket socket = new Socket("localhost",5000);
            InputStream is=socket.getInputStream();
            OutputStream os=socket.getOutputStream();
            dataInputStream = new DataInputStream(is);
            dataOutputStream = new DataOutputStream(os);

            JFileChooser dialogue=new JFileChooser(".");
            File file;
            String path="";
            dialogue.showOpenDialog(null);
            file=dialogue.getSelectedFile();
            path=file.getAbsolutePath();

            
            sendFile(path,os);
            
            dataInputStream.close();
            dataInputStream.close();
        
    }

    private static void sendFile(String path,OutputStream os) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        
        PrintStream ps=new PrintStream(os,true);
        ps.print(file.getName());
        // send file size
        dataOutputStream.writeLong(file.length());
        
        // break file into chunks
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }
}
