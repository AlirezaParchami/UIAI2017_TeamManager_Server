import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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



    public static void main(String[] args) throws IOException {

        System.out.println("The capitalization server is running.");
        int clientNumber = 0;

        String path = Login.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;
        private String TeamName;

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
                    if (input == null || input.equals("."))
                    {
                        break;
                    }
                    System.out.println(input);
                    if(input.equals("login") && !LoggedInTeams.contains(TeamName))
                    {
                        System.out.println("here");
                        String[] l = in.next().split(",");
                        System.out.println(l[0] + " " + l[1]);
                        System.out.println("____");
                        TeamName = Login.Execute(l[0], l[1], UserPasses, LoggedInTeams, out);
                        System.out.println("things sent!!!");
                    }
                    else if(Objects.equals(input, "login") && LoggedInTeams.contains(TeamName))
                    {
                        out.println("login no");
                        out.println("you are already login");
                    }
                    else if(LoggedInTeams.contains(TeamName))
                    {
                        switch (input)
                        {
                            case "upload":
                            {
                                String language = in.next();

                                UploadCode upload = new UploadCode(language , TeamName, socket  );
                                out.println("ok " + upload.time);
                                //upload.language_detect(language)
                            }
                                break;
                            case "select":

                                break;
                            case "req_send":

                                break;
                            case "req_recieve":

                                break;
                            case "teams":

                                break;
                            case "game":

                                break;
                            case "":

                                break;
                        }
                    }
                    else
                    {
                        out.println("error"); //client not logged in. but i have sent a request
                    }

//                    String path = getClass().getResource("").getPath();
//                    File dir = new File(path);
//                    if(!dir.isDirectory())
//                        dir.delete();
//                    if(!dir.exists())
//                        dir.mkdir();


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