import java.io.*;
import java.net.*;
import java.util.*;

public class Srv1 extends Thread{
    private static DataOutputStream dos = null;
    private static DataInputStream dis = null;
    public void run(){
        try {
            ServerSocket ss=new ServerSocket(10001);
            while(true){
                Socket s=ss.accept();
                System.out.println("connecte");

                new SrvTh(s).start();
            }
        } catch (Exception e) {
            
        }
    }
    public static void main(String[] args) throws Exception{
        
        new Srv1().start();
        
    }

}
