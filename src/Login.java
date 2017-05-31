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
        for (Main.userpass userpass :
                UserPasses) {
            if (Objects.equals(userpass.getUser(), user))
            {
                if(Objects.equals(userpass.getPass(), pass))
                {
                    // logged in
//                    if(LoggedInTeams.contains(user))
//                    {
//                        out.println("login no");
//                        out.println("you are already login");
//                        return "";
//                    }
                    LoggedInTeams.add(user);
                    out.println("login ok");
                    String path = Login.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                    String members="";
                    BufferedReader br;
                    try
                    {
                        br = new BufferedReader(new FileReader(path+"//"+user+"//info.txt"));

                        String sCurrentLine;

                        members = br.readLine();
                        while ((sCurrentLine = br.readLine()) != null) {
                            members += "," + sCurrentLine;
                        }
                        br.close();
                    }
                    catch (IOException e)
                    {

                        e.printStackTrace();
                    }
                    out.println(members); //send members list splited by ,
                    return userpass.getUser();
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
        out.flush();
        return "";
    }
}