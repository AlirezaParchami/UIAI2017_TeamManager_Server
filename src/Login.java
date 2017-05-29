import com.sun.xml.internal.bind.v2.TODO;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static javax.script.ScriptEngine.FILENAME;

/**
 * Created by MSN on 5/29/2017.
 */
public class Login {

    //Login.Execute(l[0],l[1], userpasses, LogedInTeams, out);
    public static String Execute(String user, String pass, ArrayList<Main.userpass> UserPasses, ArrayList<String> LoggedInTeams, PrintWriter out)
    {
        System.out.println(user+" " +pass);
        for (Main.userpass userpass :
                UserPasses) {
            if (Objects.equals(userpass.getUser(), user))
            {
                if(Objects.equals(userpass.getPass(), pass))
                {
                    System.out.println("logged in");
                    // logged in
                    LoggedInTeams.add(user);
                    out.println("login ok");
                    String path = Login.class.getProtectionDomain().getCodeSource().getLocation().getPath();

                    BufferedReader br;
                    try
                    {
                        br = new BufferedReader(new FileReader(path+"//"+user+"//info.txt"));

                        String sCurrentLine;

                        String members = br.readLine();
                        while ((sCurrentLine = br.readLine()) != null) {
                            members += "," + sCurrentLine;
                        }
                        out.println(members); //send members list splited by ,
                        br.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    String Codes = "";
                    File codes_directory = new File(path+"//"+user+"//codes");
                    if(!codes_directory.exists())
                    {
                        codes_directory.mkdir();
                    }
                    // get all the files from a directory
                    File[] CodeFilesList = codes_directory.listFiles();
                    for (File file : CodeFilesList) {
                        if (file.isFile()) {
                            if(!Codes.isEmpty())
                                Codes+=",";
                            Codes+=file.getName();
                        }
                    }
                    out.println(Codes); // send code list splited by ,

                    try
                    {
                        br = new BufferedReader(new FileReader(path + "//" + user + "//default.txt"));
                        out.println(br.readLine());// send default code name
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    String Games = "";
                    File games_directory = new File(path+"//"+user+"//games");
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

                    return userpass.getUser(); // send code list splited by ,

                }
                else
                {
                    out.println("login no");
                    out.println("wrong password");
                    return "";
                }
            }
        }
        out.println("login no");
        out.println("wrong username");
        return "";
    }
}