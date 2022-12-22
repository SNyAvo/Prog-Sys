import java.io.*;
import java.net.*;
import java.util.*;


public class Server extends Thread{
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) throws Exception{
        new Server().start();
    }
    public void run(){
        try{

            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("listening to port:5000");
            while(true){
            Socket clientSocket = serverSocket.accept();
            
            Socket[] s=new Socket[4];
            try {
                try {
                    s[0]=new Socket("localhost", 10001);
                } catch (Exception ex) {
                    
                }
                try {
                    s[1]=new Socket("localhost",10002);
                } catch (Exception e) {
                    
                }
                try {
                    s[2]=new Socket("localhost",10003);
                } catch (Exception e) {
                    
                }
                try {
                    s[3]=new Socket("localhost",10004);
                } catch (Exception e) {
                    
                }
                
            } catch (Exception e) {


            }
            System.out.println(CheckServ(s));
            int n=CheckServ(s);
            
            new Send(clientSocket,n,s).start();            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public static int CheckServ(Socket[] s){
        int v=0;
        for (int i = 0; i < s.length; i++) {
            if (s[i]!=null) {
                v++;
            }
        }
        return v;
    }
    public static Boolean ifConnect(Socket s){
        if (s!=null) {
            return true;
        }
        return false;
    }
    private static void sendFile(String path,Socket s) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        OutputStream os=s.getOutputStream();
        DataOutputStream dos=new DataOutputStream(os);
        //mandefa anarana
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
    private static Socket[] getListServ(Socket[] s){
        Socket[] list=new Socket[CheckServ(s)];
        int c=0;
        for (int i = 0; i < s.length; i++) {
            if (s[i]!=null) {
                list[c]=s[i];
                c++;
            }
        }
        return list;
    }
    public static void sendtoServ(Socket[] s,String fileName)throws Exception{
        Socket[] list=getListServ(s);
        for (int i = 0; i < list.length; i++) {
            sendFile(fileName+".0"+i, list[i]);
        }
        mamafa(fileName);
    }
    public static void mamafa(String s) throws Exception{
        String File="./s.*";
        Vector<String> m=new Vector<>();
        // m.add("/bin/sh");
        // m.add("-c");
        // m.add("rm");
        // m.add("./"+s+"*");
        m.add("./function.sh");
        m.add(s);
        String[] delete=m.toArray(new String[m.size()]);
        manoratra(delete);
    }

    private static void receiveFile(String fileName) throws Exception{
        int bytes = 0;
        // File file=new File(fileName);
        // if (!file.exists()) {
        //     file.createNewFile();
        // }
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
        long size = dataInputStream.readLong();     // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
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
    public static void manambatra(String filename) throws Exception{
        Vector<String> cat=new Vector<>();
        cat.add("cat");
        cat.add("../stock1/"+filename+".part*");
        cat.add("../stock2/"+filename+".part*");
        cat.add(">");
        cat.add(filename+".zip");
        String[] maka=cat.toArray(new String[cat.size()]);
        Vector<String> mamafafa=new Vector<>();
        mamafafa.add("rm");
        mamafafa.add("../stock1/"+filename+".part*");
        mamafafa.add("../stock2/"+filename+".part*");
        String[]mamafa=mamafafa.toArray(new String[mamafafa.size()]);
        Vector<String> mamerina=new Vector<>();
        mamerina.add("unzip");
        mamerina.add(filename+".zip");
        mamerina.add("&&");
        mamerina.add("rm");
        mamerina.add(filename+".zip");
        String[] getfile=mamerina.toArray(new String[mamerina.size()]);

        manoratra(maka);
        manoratra(mamafa);
        manoratra(getfile);
    }
    public static String getExtension(String s){
        String ext="";
        ext=s.replaceAll("^.*\\.(.*)$", "$1");
        return "."+ext;
    }
    private static void sendFile(String path,DataOutputStream dataOutputStream) throws Exception{
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        
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
}
