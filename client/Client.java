import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.*;

public class Client {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private static String anarana;

    public static String getAnarana() {
        return anarana;
    }

    public static void setAnarana(String anar) {
        anarana = anar;
    }
    public static void Chooser()throws Exception{
        JFileChooser dialogue=new JFileChooser(".");
        File file;
        String path="";
        if (dialogue.showSaveDialog(null)== JFileChooser.APPROVE_OPTION) {
            file=dialogue.getSelectedFile();
            path=file.getAbsolutePath();
            // setAnarana(file.getName());
            FileListWrite(file.getName());
            sendFile(path);
        }
    }

    public static void main(String[] args) throws Exception{
            Socket socket = new Socket("localhost",5000);
            InputStream is=socket.getInputStream();
            OutputStream os=socket.getOutputStream();
            dataInputStream = new DataInputStream(is);
            dataOutputStream = new DataOutputStream(os);
            // receiveFile("FileList.txt");
            
            new Liste();
            // readFile("FileList.txt");
    }

    private static void sendFile(String path) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        //mandefa anarana
        dataOutputStream.writeUTF(file.getName());

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

   private static void FileListWrite(String name)throws Exception{
    File file=new File("FileList.txt");
    if (!file.exists()) {
        file.createNewFile();
        }
       
        FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(name);
        // bw.write(";");
        bw.newLine();
        bw.close();
    }
    public static void getFile(String name)throws Exception{
        dataOutputStream.writeUTF(name+".maka");
        // receiveFile(name);
    }
}
