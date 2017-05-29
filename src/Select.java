import java.awt.image.DirectColorModel;
import java.io.*;

/**
 * Created by MSN on 5/29/2017.
 */
public class Select {
    public static boolean execute(String CodeName, String TeamName, String path)
    {
        try {

            File codes_directory = new File(path+"//"+TeamName+"//codes");
            if(!codes_directory.exists())
            {
                codes_directory.mkdir();
            }
            File[] CodesFilesList = codes_directory.listFiles();
            for (File file : CodesFilesList) {
                if (file.isFile()) {
                    if(file.getName().equals(CodeName))
                    {
                        BufferedWriter br = new BufferedWriter(new FileWriter(path + "//" + TeamName + "//default.txt"));
                        br.write(CodeName);
                        return true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
