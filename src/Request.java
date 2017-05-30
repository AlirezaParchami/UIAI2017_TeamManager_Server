import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Alireza on 5/30/2017.
 */
public class Request {


    public static void send_request_to_client(Socket socket , String path)
    {
        try {
            BufferedReader bf= new BufferedReader(new FileReader(path));
            String sent_content = "" ;
            String line = "";
            while((line = bf.readLine() )!= null)
            {
                if(!sent_content.isEmpty())
                    sent_content += ",";
                sent_content += line;

            }
            bf.close();

            PrintWriter out= new PrintWriter(socket.getOutputStream(),true);
            out.println(sent_content);
            out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void send_request_to_file(String TeamName , String OppTeam)
    {
        String Data = OppTeam + ",";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        Data += dtf.format(now);
        Data += ",pending";
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            String path = Request.class.getResource("").getPath();
            path += "//" + TeamName + "//req_sent.txt";
            File file = new File (path);
            if(!file.exists())
                file.createNewFile();
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(Data);

            System.out.println("Done");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if(bw!=null)
                    bw.close();
                if(fw!=null)
                    fw.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }



    }
//    public static void

}
