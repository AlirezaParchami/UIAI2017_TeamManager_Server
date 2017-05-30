import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by MSN on 5/30/2017.
 */
public class Code {
    public static void send(Socket socket, PrintWriter out, String TeamName, String CodeName, String path) {
        try {
            File codes_directory = new File(path + "//" + TeamName + "//codes");
            if (!codes_directory.exists()) {
                codes_directory.mkdir();
            }
            File[] CodesFilesList = codes_directory.listFiles();
            for (File file : CodesFilesList) {
                if (file.getName().equals(CodeName)) {
                    out.println("ok");
                    FileOperation.send(file,socket);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("no");
        out.flush();


    }
}
