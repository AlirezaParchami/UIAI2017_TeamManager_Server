import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiveCode {

    public static String file_name(String language) {
         String FILE_TO_SAVE = "";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);

        if(language.equals("CPP"))
            FILE_TO_SAVE += "CPP_";
        else if (language.equals("JAVA"))
            FILE_TO_SAVE += "JAVA_";
        else if (language.equals("PYTHON"))
            FILE_TO_SAVE += "PYTHON_";

        FILE_TO_SAVE += time + ".zip";
        return FILE_TO_SAVE;
    }

    public static String save_locate(String team_name) {
        String LOCATE_TO_SAVE = "";
        String path = Request.class.getResource("").getPath();
        LOCATE_TO_SAVE += path;
        LOCATE_TO_SAVE += "//" + team_name + "//codes//";
        return LOCATE_TO_SAVE;
    }

    public static void execute(File file, int fileSize, Socket socket, PrintWriter writer) {
        System.out.println("Receiving file");
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] byteArray = new byte[fileSize];
            socket.getInputStream().read(byteArray);
            bos.write(byteArray);
            bos.flush();
            System.out.println("File received");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
