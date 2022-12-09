import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) throws Exception{
        
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("listening to port:5000");
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket+" connected.");
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());


            Scanner sc=new Scanner(dataInputStream);
            PrintWriter pw=new PrintWriter(dataOutputStream);

            InputStream is=clientSocket.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            String test=br.readLine();
            System.out.println(test);
            receiveFile("test.mp4");

            dataInputStream.close();
            dataOutputStream.close();
            clientSocket.close();
        
    }

    private static void receiveFile(String fileName) throws Exception{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
        long size = dataInputStream.readLong();     
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      
        }
        fileOutputStream.close();
    }
 
    public static void manoratra(String[] s)throws Exception{
        Process proc=Runtime.getRuntime().exec(s);
            Scanner out=new Scanner(proc.getInputStream());
            while(out.hasNextLine())
                System.out.println(out.nextLine());
            out.close();

            Scanner err=new Scanner(proc.getErrorStream());
            while (err.hasNextLine())                
                System.err.println(err.nextLine());
            err.close();
    }

}
