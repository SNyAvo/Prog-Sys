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
            receiveFile(test);

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
    private static void sparatefile(String fileName) throws Exception{
        File file=new File(fileName);
        int size=(int)(file.length()/2);
        String filesize=Integer.toString(size);
        Vector<String> s=new Vector<>();
        s.add("zip");
        s.add(fileName+".zip");
        s.add(fileName);
        String[] zip=s.toArray(new String[s.size()]);
        Vector<String> del=new Vector<>();
        del.add("rm");
        del.add(fileName);
        String[] delete=del.toArray(new String[del.size()]);
        Vector<String> spt=new Vector<>();
        spt.add("split");
        spt.add("-b");
        spt.add(filesize);
        spt.add(fileName+".zip");
        spt.add(fileName+".part");
        String[] split=spt.toArray(new String[spt.size()]);
        Vector<String> sk1=new Vector<>();
        sk1.add("mv");
        sk1.add(fileName+".partaa");
        sk1.add("../stock1");
        String[] stock1=sk1.toArray(new String[sk1.size()]);
        
        Vector<String> sk2=new Vector<>();
        sk2.add("mv");
        sk2.add(fileName+".partab");
        sk2.add("../stock2");
        String[] stock2=sk2.toArray(new String[sk2.size()]);
        Vector<String> del1=new Vector<>();
        del1.add("rm");
        del1.add(fileName+".zip");
        String[] delete2=del1.toArray(new String[del1.size()]);

        manoratra(zip);
        manoratra(delete);
        manoratra(split);
        manoratra(stock1);
        Thread.sleep(100);
        manoratra(stock2);
        manoratra(delete2);

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
        cat.add("../stock1/"+filename+"part*");
        cat.add("../stock2/"+filename+"part*");
        cat.add(">");
        cat.add(filename+".zip");
        String[] maka=cat.toArray(new String[cat.size()]);
        Vector<String> mamafafa=new Vector<>();
        mamafafa.add("rm");
        mamafafa.add("../stock1/"+filename+"part*");
        mamafafa.add("../stock2/"+filename+"part*");
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
}
