import java.io.*;
import java.net.*;
import java.util.*;

public class Srv1 {
    private static DataOutputStream dos = null;
    private static DataInputStream dis = null;
    public static void main(String[] args) throws Exception{
        ServerSocket ss=new ServerSocket(10001);
        while(true){
            Socket s=ss.accept();

            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
            String name =dis.readUTF();

            receiveFile(name);
            dis.close();
            dos.close();
        }
        
        
    }
    private static void receiveFile(String fileName) throws Exception{
        int bytes = 0;

        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
        long size = dis.readLong();     // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dis.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
    }
}