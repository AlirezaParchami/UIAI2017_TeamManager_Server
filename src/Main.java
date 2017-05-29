import com.sun.xml.internal.ws.server.ServerRtException;
import objects.Team;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by MSN on 5/28/2017.
 */
public class Main {
    public static class userpass
    {
        private String user,pass;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public userpass(String user, String pass) {
            this.user = user;
            this.pass = pass;
        }
    }

    private static ArrayList<String> LoggedInTeams = new ArrayList<>(); // TODO: 5/29/2017 when socket DC, remove team name from ArrayList
    private static ArrayList<userpass> UserPasses = new ArrayList<>();
    private static ArrayList<Capitalizer> threads = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;

        String path = Main.class.getClassLoader().getResource("").getPath();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path+"//userpasses.txt"));
            String user, pass;
            while ((user=br.readLine())!=null)
            {
                pass = br.readLine();

                UserPasses.add(new userpass(user,pass));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ServerSocket listener = new ServerSocket(9898);
        try {
            while (true) {
                threads.add(new Capitalizer(listener.accept(), clientNumber++));
                threads.get(threads.size()-1).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;
        private String TeamName;
        public String get_TeamName()
        {
            return TeamName;
        }

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */

        public void run() {
            try {
                String path = Login.class.getProtectionDomain().getCodeSource().getLocation().getPath();

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                Scanner in = new Scanner(
                        new InputStreamReader(socket.getInputStream()));

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {

                    String input = in.next();
                    System.out.println("INPUT: " + input);

                    if (input == null || input.equals("."))
                    {
                        break;
                    }
                    if(input.equals("login") && !LoggedInTeams.contains(TeamName))
                    {
                        String[] l = in.next().split(",");
                        if(l.length!=2)
                        {
                            out.println("login no");
                            out.println("wrong message!!!");
                            System.out.println("length of l is not 2");
                            continue;
                        }
                        TeamName = Login.Execute(l[0], l[1], UserPasses, LoggedInTeams, out);
                        if(TeamName!="")
                            System.out.println(TeamName+" logged in");
                        else
                            System.out.println("login failed");
                    }
                    else if(Objects.equals(input, "login") && LoggedInTeams.contains(TeamName))
                    {
                        out.println("login no");
                        out.println("you are already login");
                        System.out.println(TeamName+" already login");
                    }
                    else if(LoggedInTeams.contains(TeamName))
                    {
                        switch (input)
                        {
                            case "upload":
                                String language = in.next();
                                System.out.println("input2: " + language);
                                UploadCode upload = new UploadCode(language , TeamName, socket  );
                                out.println("ok " + upload.time);
                                out.flush();
                                //upload.language_detect(language)
                                break;

                            case "select":
                                String name = in.next();
                                if(Select.execute(name, TeamName, path))
                                    out.println("select ok");
                                else
                                    out.println("select no");
                                break;

                            case "req_send":
                                String oppTeam = in.next();


                                break;
                            case "req_recieve":

                                break;
                            case "teams":
                                TeamsList.send(out,path);
                                break;
                            case "games":
                                GamesList.send(out,TeamName, path);
                                break;
                            case "codes":
                                CodesList.send(out, TeamName, path);
                                break;
                            case "game":
                                String GameName = in.next();
                                Game.send(out, TeamName,GameName,path);
                                break;
                            case "code":
                                String CodeName = in.next();
                                Code.send(out, TeamName, CodeName, path);
                                break;
                            case "default":
                                DefaultCode.send(out, TeamName);

                        }
                    }
                    else
                    {
                        out.println("error"); //client not logged in. but i have sent a request
                    }
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }

    }

}