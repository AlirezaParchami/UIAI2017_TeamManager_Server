import java.io.File;
import java.io.PrintWriter;

/**
 * Created by MSN on 5/30/2017.
 */
public class Code {
    public static void send(PrintWriter out, String TeamName, String CodeName, String path) {
        try {
            File codes_directory = new File(path + "//" + TeamName + "//codes");
            if (!codes_directory.exists()) {
                codes_directory.mkdir();
            }
            File[] CodesFilesList = codes_directory.listFiles();
            for (File file : CodesFilesList) {
                if (file.getName().equals(CodeName)) {
                    // TODO: 5/30/2017 send file!!!
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("not exist!!!");
    }
}
