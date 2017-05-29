import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by MSN on 5/29/2017.
 */
public class DefaultCode
{
    public static void send(PrintWriter out, String TeamName)
    {
        String path = Login.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String s = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path + "//" + TeamName + "//default.txt"));
            s = br.readLine();// send default code name
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println(s);
    }
}