import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Alireza on 5/30/2017.
 */
public class GamesSchedule {

    public static void run() {
        try {
            String path = GamesSchedule.class.getResource("").getPath();
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = "";
            String Team1, Team2;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Team1 = parts[0];
                Team2 = parts[1];
                String Team1_Code_Path = path + "/" + Team1 + "//codes" + "//" + Team_Default_code(path + "//" + Team1 ) ;
                String Team2_Code_Path = path + "/" + Team2 + "//codes" + "//" + Team_Default_code(path + "//" + Team2 ) ;

                String[] spl  = Team_Default_code(path + "//" + Team1 ).split("_");
                String Team1_coding_language = spl[0];
                spl = Team_Default_code(path + "//" + Team2 ).split("_");
                String Team2_coding_language = spl[0] ;

                String Team1_log_location = path + Team1 + "//games";
                String Team2_log_location = path + Team2 + "//games";


                //call Parham Function with arguments Team1,eam1_Code_Path,Team1_coding_language ,Team1_log_location , Team2....
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String Team_Default_code(String path)
    {
        try {
            File file = new File(path + "//default.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String default_code = br.readLine();
            return default_code;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Can't open default file";
        }

    }

}
