import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by MSN on 5/28/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
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

                // Send a welcome message to the client.
                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter a line with only a period to quit\n");

                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {
                    String input = in.next();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    switch (input)
                    {
                        case "login":

                            break;
                        case "upload":

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


//                    String path = getClass().getResource("").getPath();
//                    //String path2 = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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