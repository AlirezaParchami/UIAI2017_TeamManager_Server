import com.sun.xml.internal.bind.v2.TODO;

import javax.security.auth.login.LoginException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by MSN on 5/29/2017.
 */
public class Login {

    //Login.Execute(l[0],l[1], userpasses, LogedInTeams, out);
    public static String Execute(String user, String pass, ArrayList<Main.userpass> UserPasses, ArrayList<String> LogedInTeams, PrintWriter out)
    {
        if(LogedInTeams.contains(user))
        {
            out.println("login no you are already log in");
            return "";
        }
        for (Main.userpass userpass :
                UserPasses) {
            if (userpass.user == user)
            {
                if(userpass.pass == pass)
                {
                    // logged in
                    return userpass.user;
                }
                else
                {
                    out.println("login no you are already log in");
                    return "";
                }
            }
        }
    }
}