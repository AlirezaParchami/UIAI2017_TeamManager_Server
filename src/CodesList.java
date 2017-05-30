import java.io.File;
import java.io.PrintWriter;

/**
 * Created by MSN on 5/29/2017.
 */
public class CodesList {
    public static void send(PrintWriter out, String TeamName, String path)
    {
        String Codes = "";
        File codes_directory = new File(path+"//"+TeamName+"//codes");
        if(!codes_directory.exists())
        {
            codes_directory.mkdir();
        }
        // get all the files from a directory
        File[] CodeFilesList = codes_directory.listFiles();
        for (File file : CodeFilesList) {
            if (file.isFile()) {
                if(!Codes.isEmpty())
                    Codes+=",";
                Codes+=file.getName();
            }
        }
        out.println(Codes); // send code list splited by ,
        out.flush();
    }
}
