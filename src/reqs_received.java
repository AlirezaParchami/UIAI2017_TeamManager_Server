import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by MSN on 5/30/2017.
 */
public class reqs_received {
    public static void send(PrintWriter out, String path)
    {
        String reqs_received = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "//reqs_received.txt"));
            String line="";
            while((line=br.readLine())!=null)
            {
                if(!reqs_received.isEmpty())
                    reqs_received+=",";
                reqs_received+=line;
            }
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        out.println(reqs_received);
        out.flush();
    }
}