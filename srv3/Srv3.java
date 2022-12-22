import java.io.*;
import java.net.*;
import java.util.*;

public class Srv3 extends Thread{
    private static DataOutputStream dos = null;
    private static DataInputStream dis = null;
    public void run(){
        try {
            ServerSocket ss=new ServerSocket(10003);
            while(true){
                Socket s=ss.accept();
                System.out.println("connecte");

                new SrvTh(s).start();
            }
        } catch (Exception e) {
            
        }
    }
    public static void main(String[] args) throws Exception{
        
        new Srv3().start();
        
    }

}
