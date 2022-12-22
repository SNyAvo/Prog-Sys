import java.io.*;
import java.net.*;
import java.util.*;

public class Srv4 extends Thread{
    private static DataOutputStream dos = null;
    private static DataInputStream dis = null;
    public void run(){
        try {
            ServerSocket ss=new ServerSocket(10004);
            while(true){
                Socket s=ss.accept();
                System.out.println("connecte");

                new SrvTh(s).start();
            }
        } catch (Exception e) {
            
        }
    }
    public static void main(String[] args) throws Exception{
        
        new Srv4().start();
        
    }

}
