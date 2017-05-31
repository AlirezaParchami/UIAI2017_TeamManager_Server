import org.omg.SendingContext.RunTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

/**
 * Created by MSN on 5/30/2017.
 */
public class RunGame {
    public static void execute(String Team1, String Team2, String path)
    {
        String DefaultCodeName1 = "", DefaultCodeName2="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path+"//"+Team1+"//default.txt"));
            DefaultCodeName1 = br.readLine();
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(DefaultCodeName1.equals(""))
        {
            System.out.println(Team1+" is not set default code");
            return;
        }
        File Team1Code = new File(path + "//" + Team1 +"//codes//"+DefaultCodeName1);
        File Team1Games = new File(path + "//" + Team1 +"//games");

        if(!Team1Games.exists())
        {
            Team1Games.mkdir();
        }
        if(!Team1Code.exists())
            return;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path+"//"+Team2+"//default.txt"));
            DefaultCodeName2 = br.readLine();
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(DefaultCodeName2.equals(""))
        {
            System.out.println(Team1+" is not set default code");
            return;
        }
        File Team2Code = new File(path + "//" + Team2 +"//codes//"+DefaultCodeName2);
        File Team2Games = new File(path + "//" + Team2 +"//games");
        if(!Team2Games.exists())
        {
            Team2Games.mkdir();
        }
        if(!Team2Code.exists())
            return;

        String Command="";
        Command += Team1 + " "+
                Team1Code.getAbsolutePath() + " "+
                DefaultCodeName1.split("_")[0] + " "+
                Team1Games + " " +
                Team2 + " "+
                Team2Code.getAbsolutePath() + " "+
                DefaultCodeName2.split("_")[0] + " "+
                Team2Games;
        try {
            System.out.println("Running game");
            Process p = Runtime.getRuntime().exec("cmd /c start java -jar AutoGameRunner.jar " + Command, null, new File(path+"//AutoGameRunner"));
            p.waitFor();
            System.out.println("Game done");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
