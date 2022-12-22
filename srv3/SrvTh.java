import java.io.*;
import java.net.*;
import java.util.*;

public class SrvTh extends Thread{
    private Socket socket;
    private static DataOutputStream dos= null;
    private static DataInputStream dis = null;

    public void setSocket(Socket s){
        this.socket=s;
    }
    public Socket getSocket(){
        return this.socket;
    }
    public SrvTh(Socket s){
        this.setSocket(s);
    }
    public void run(){
        try {
            dis=new DataInputStream(this.getSocket().getInputStream());
            dos=new DataOutputStream(this.getSocket().getOutputStream());
            String name =dis.readUTF();
            String ext=getExtension(name);
            if (ext==".maka") {
                sendFile(makaAnarana(name));
            }
            System.out.println(name);
            receiveFile(name);
            new SrvTh(this.getSocket()).start();
        } catch (Exception e) {
            // TODO: handle exception
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
        // dis.close();
        // dos.close();
        fileOutputStream.close();
    }
    private static void sendFile(String path) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        
        dos.writeUTF(file.getName());
        // send file size
        dos.writeLong(file.length());
        
        // break file into chunks
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dos.write(buffer,0,bytes);
            dos.flush();
        }
        fileInputStream.close();
    }
    public static String getExtension(String s){
        String ext="";
        ext=s.replaceAll("^.*\\.(.*)$", "$1");
        return "."+ext;
    }
    public static String makaAnarana(String s){
        String[] split=s.split(".");
        String name=split[0]+"."+split[1]+"."+split[2];
        return name;
    }
}
