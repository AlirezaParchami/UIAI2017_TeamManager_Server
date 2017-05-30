import java.io.*;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

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

    private static ArrayList<String> LoggedInTeams = new ArrayList<>();
    private static ArrayList<userpass> UserPasses = new ArrayList<>();
    private static ArrayList<Capitalizer> threads = new ArrayList<>();
    //private static ArrayList<String> Teams = new ArrayList<>();

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
                    if(input.equals("login"))
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
                    else if(LoggedInTeams.contains(TeamName))
                    {
                        switch (input)
                        {
                            case "upload":
                                String language = in.next();
                                System.out.println("input2: " + language);
                                System.out.println("PATH: "+ ReceiveCode.save_locate(TeamName) + ReceiveCode.file_name(language));
                                File file = new File (ReceiveCode.save_locate(TeamName) + ReceiveCode.file_name(language));
                                out.println("ok " + ReceiveCode.file_name(language));
                                out.flush();
                                int sizes = Integer.parseInt(in.next());
                                System.out.println("SIZE FILE: " + sizes);
                                ReceiveCode.execute(file,sizes,socket,out);
                                ArrayList<String> lines = new ArrayList<>();
                                try
                                {
                                    BufferedReader bf= new BufferedReader(new FileReader(path+"//teams.txt"));
                                    String tmp;
                                    while ((tmp=bf.readLine())!=null)
                                    {
                                        lines.add(tmp);
                                    }
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                boolean mustWriteToFile = true;
                                for (String line :
                                        lines) {
                                    String[] list = line.split(",");
                                    if(list[0].equals(TeamName))
                                    {
                                        if(list[1].equals("yes"))
                                        {
                                            mustWriteToFile = false;
                                            break;
                                        }
                                        else
                                        {
                                            lines.remove(line);
                                            String tmp = list[0]+",yes";
                                            lines.add(tmp);
                                        }
                                    }
                                }
                                if(mustWriteToFile)
                                {
                                    try
                                    {
                                        BufferedWriter br = new BufferedWriter(new FileWriter(path + "//teams.txt"));
                                        for(int i=0;i<lines.size();i++)
                                        {
                                            if(i!=0)
                                                br.write("\n");
                                            br.write(lines.get(i));
                                        }
                                        br.flush();
                                        br.close();
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }

                                break;

                            case "select":
                                String name = in.next();
                                if(Select.execute(name, TeamName, path))
                                    out.println("select ok");
                                else
                                    out.println("select no");
                                break;

                            case "req_send":
                                String OppTeam = in.next();
                                Request.send_request_to_file( TeamName , OppTeam);
                                break;

                            case "reqs_sent":
                                Request.send_request_to_client(out,path + "//" + TeamName + "//reqs_sent.txt");
                                break;
                            case "reqs_received":
                                reqs_sent.send(out, path ,TeamName);
                                break;
                            case "req_answer":
                                String[] l = in.next().split(",");
                                req_answer.execute(l[0],TeamName,l[1],l[2],path);
                                break;
                            case "teams":
                                TeamsList.send(out,path );
                                break;
                            case "games":
                                GamesList.send(out,TeamName, path);
                                break;
                            case "codes":
                                CodesList.send(out, TeamName, path);
                                break;
                            case "game":
                                String GameName = in.next();
                                Game.send(socket, out, TeamName,GameName,path);
                                break;
                            case "code":
                                String CodeName = in.next();
                                Code.send(socket, out, TeamName, CodeName, path);
                                break;
                            case "default":
                                DefaultCode.send(out, TeamName);
                                break;
                            default:
                                out.println("wrong command");
                        }
                    }
                    else
                    {
                        out.println("error"); //client not logged in. but i have sent a request
                    }
                }
            }
            catch (Exception e)
            {
//                log("Error handling client# " + clientNumber + ": " + e);
            }
            finally
            {
                try
                {
                    socket.close();
                }
                catch (Exception e) {
//                    log("Couldn't close a socket, what's going on?");
                }
//                log("Connection with client# " + clientNumber + " closed");
            }
            if(LoggedInTeams.contains(TeamName)) {
                LoggedInTeams.remove(TeamName);
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