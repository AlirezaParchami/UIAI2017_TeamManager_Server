import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by MSN on 5/29/2017.
 */
public class TeamsList {
    public static void send(PrintWriter out, String path)
    {
        String teams="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "//teams.txt"));
            String line="";
            while((line=br.readLine())!=null)
            {
                if(!teams.isEmpty())
                    teams+=",";
                teams+=line;
            }
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        out.println(teams);
    }
}
