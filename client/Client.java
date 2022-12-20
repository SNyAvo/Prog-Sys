import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.*;

public class Client {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private String anarana;

    public String getAnarana() {
        return anarana;
    }

    public void setAnarana(String anarana) {
        this.anarana = anarana;
    }
    public static void Chooser()throws Exception{
        JFileChooser dialogue=new JFileChooser(".");
        File file;
        String path="";
        if (dialogue.showSaveDialog(null)== JFileChooser.APPROVE_OPTION) {
            file=dialogue.getSelectedFile();
            // sendFile(file.getAbsolutePath());
            // System.out.println(file.getAbsolutePath());
            path=file.getAbsolutePath();
            sendFile(path);
        }
    }

    public static void main(String[] args) throws Exception{
        
            Socket socket = new Socket("localhost",5000);
            InputStream is=socket.getInputStream();
            OutputStream os=socket.getOutputStream();
            dataInputStream = new DataInputStream(is);
            dataOutputStream = new DataOutputStream(os);

            new Liste();

        
    }

    private static void sendFile(String path) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        // send filename
        dataOutputStream.writeUTF(file.getName());
        // send file size
        dataOutputStream.writeLong(file.length());
        // zarazaraina
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }
    private static void receiveFile(String fileName) throws Exception{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
        long size = dataInputStream.readLong();     // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
    }
}
