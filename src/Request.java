import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Alireza on 5/30/2017.
 */
public class Request {


    public static void send_request_to_client(PrintWriter out  , String path)
    {
        try {
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            BufferedReader bf= new BufferedReader(new FileReader(file));
            String sent_content = "" ;
            String line = "";
            while((line = bf.readLine() )!= null)
            {
                if(!sent_content.isEmpty())
                    sent_content += ",";
                sent_content += line;
            }
            bf.close();
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
        String path = Request.class.getResource("").getPath() + "//" + TeamName + "//reqs_sent.txt";
        String Data =  OppTeam + ",";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        Data += time + ",pending\n";
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File file = new File (path);
            if(!file.exists())
                file.createNewFile();
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(Data);
            bw.flush();

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

        try
        {
            path = Request.class.getResource("").getPath() + "//" + OppTeam + "//reqs_received.txt";
            Data = TeamName + "," + time + ",pending\n";
            File file = new File (path);
            if(!file.exists())
                file.createNewFile();
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(Data);

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
