/**
 * Created by MSN on 5/29/2017.
 */

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UploadCode {
    private final static int FILE_SIZE = 1000000;

    public static String LOCATE_TO_SAVE = "";
    public String FILE_TO_SAVE = "";
    public String time = "";

    void file_name(String language) {
        if (language.equals("CPP"))
            FILE_TO_SAVE += "CPP_";
        else if (language.equals("JAVA"))
            FILE_TO_SAVE += "JAVA_";
        else if (language.equals("PYTHON"))
            FILE_TO_SAVE += "PYTHON_";

        FILE_TO_SAVE += time + ".zip";
    }

    void save_dir(String team_name) {

        String path = getClass().getResource("").getPath();
        LOCATE_TO_SAVE += path;
        LOCATE_TO_SAVE += "//" + team_name + "//code";
    }

    UploadCode(String language, String Team_name, Socket socket) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        time = dtf.format(now);
        // get date and time

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            byte[] mybytearray = new byte[FILE_SIZE];
            InputStream is = socket.getInputStream();
            save_dir(Team_name);    // write LOCATE_TO_SAVE
            File file = new File(LOCATE_TO_SAVE + FILE_TO_SAVE);
            if (!file.exists())
                file.createNewFile();
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            int current = bytesRead;
            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos!=null)
                    fos.close();
                if(bos != null)
                    bos.close();
            }
            catch (IOException e )
            {
                e.printStackTrace();
            }
            }
    }


}
