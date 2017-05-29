import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by MSN on 5/30/2017.
 */
public class FileOperation {
    public static void send(File f, Socket socket) {
        BufferedInputStream bis = null;
        try {
            System.out.println("Sending: " + f.getPath());
            bis = new BufferedInputStream(new FileInputStream(f));
            byte[] byteArray = new byte[256];
            int currentByte = 0;
            while ((currentByte = bis.read(byteArray)) > 0)
                socket.getOutputStream().write(byteArray, 0, currentByte);
            socket.getOutputStream().flush();
            System.out.println("Sent");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null)
                    bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
