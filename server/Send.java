import java.io.*;
import java.net.*;
import java.util.*;

public class Send extends Thread{
    private Socket socket;
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private int isa;
    private Socket[] list;
    

    public void run(){
        try {
            if (this.getSocket()!=null) {
            dataInputStream = new DataInputStream(this.getSocket().getInputStream());
            dataOutputStream = new DataOutputStream(this.getSocket().getOutputStream());
            Scanner sc=new Scanner(dataInputStream);
            PrintWriter pw=new PrintWriter(dataOutputStream);

            String test=dataInputStream.readUTF();
            int n=Server.CheckServ(this.getList());
            String ext=getExtension(test);
            // System.out.println(ext);
            if (ext==".maka") {
                for (int i = 0; i < n; i++) {
                    Server.getFile((makaAnarana(test)+"."+"0"+i),this.getList());
                }
            }else{
                receiveFile(test);
                FileListWrite(test);
            }
            // System.out.println(test);
            // System.out.println("extension e "+ext);
            
            // sendFile("FileList.txt");
            
            sparatefile(test,this.getIsa());
            System.out.println("isa="+n);    
            Server.sendtoServ(this.getList(), test);
   
            new Send(this.getSocket(),n,this.getList()).start();

            }
            
        } catch (Exception e) {
            
        }
    }
    public Send(Socket s,int n,Socket[] list){
        this.setSocket(s);
        this.setIsa(n);
        this.setList(list);             
    }
    public Send(){}
    public Socket getSocket() {
        return socket;
    }
    public int getIsa() {
        return isa;
    }
    public void setIsa(int isa) {
        if (isa<=0) {
            this.isa=1;
        }else{
            this.isa = isa;
        }
        
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public Socket[] getList() {
        return list;
    }
    public void setList(Socket[] list) {
        this.list = list;
    }
    public static void receiveFile(String fileName) throws Exception{
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
    private static void sparatefile(String fileName,int isa) throws Exception{
        

        ///////  ito mizara sy mandefa serveur anazy eto en local eto
        File file=new File(fileName);
        int size=(int)(file.length()/isa);
        String filesize=Integer.toString(size);
        Vector<String> spt=new Vector<>();
        spt.add("split");
        spt.add("-b"+filesize);
        spt.add("-d");
        spt.add(fileName);
        spt.add(fileName+".");
        String[] split=spt.toArray(new String[spt.size()]);
        manoratra(split);

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
    public static String makaAnarana(String s){
        String[] split=s.split(".");
        String name=split[0]+"."+split[1];
        return name;
    }
    public static void getFile(String name)throws Exception{
        dataOutputStream.writeUTF(name+".maka");
        receiveFile(name);
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
    private static void sendFile(String path) throws Exception{
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
    private static void FileListWrite(String name)throws Exception{
        File file=new File("FileList.txt");
        if (!file.exists()) {
            file.createNewFile();
            }
           
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(name);
            bw.write(";");
            bw.newLine();
            bw.close();
    }
}
