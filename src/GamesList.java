import java.io.File;
import java.io.PrintWriter;

/**
 * Created by MSN on 5/29/2017.
 */
public class GamesList {
    public static void send(PrintWriter out, String TeamName, String path)
    {
        String Games = "";
        File games_directory = new File(path+"//"+TeamName+"//games");
        if(!games_directory.exists())
        {
            games_directory.mkdir();
        }
        File[] GameFilesList = games_directory.listFiles();
        for (File file : GameFilesList) {
            if (file.isFile()) {
                if(!Games.isEmpty())
                    Games+=",";
                Games+=file.getName();
            }
        }
        out.println(Games); // send games list splited by ,

    }
}
