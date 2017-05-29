import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by MSN on 5/30/2017.
 */
public class Game {
    public static void send(Socket socket, PrintWriter out, String TeamName, String GameName, String path)
    {
        try {
            File games_directory = new File(path+"//"+TeamName+"//games");
            if(!games_directory.exists())
            {
                games_directory.mkdir();
            }
            File[] GamesFilesList = games_directory.listFiles();
            for (File file : GamesFilesList) {
                if(file.getName().equals(GameName))
                {
                    FileOperation.send(file, socket);
                    return;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        out.println("not exist!!!");
    }
}
