import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by MSN on 5/30/2017.
 */
public class req_answer { // TeamName is Name of receiver
    public static void execute(String SenderTeamName, String ReceiverTeamName, String date, String status, String path)
    {
        boolean exists = false;
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "//" + ReceiverTeamName +"//reqs_received.txt"));
            String line="";
            while((line=br.readLine())!=null)
            {
                lines.add(line);
            }
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

//        for (String line :
//                lines) {
//            String[] list = line.split(",");
//            if(SenderTeamName.equals(list[0]) && date.equals(list[1]))
//            {
//                exists = true;
//                String tmp = list[0]+","+list[1]+","+status;
//                lines.add(tmp);
//                lines.remove(line);
//                break;
//            }
//        }
        for(int i=0;i<lines.size();i++)
        {
            String[] list = lines.get(i).split(",");
            if(SenderTeamName.equals(list[0]) && date.equals(list[1]))
            {
                exists = true;
                String tmp = list[0]+","+list[1]+","+status;
                lines.set(i,tmp);
                break;
            }
        }
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + "//" + ReceiverTeamName + "//reqs_received.txt"));
            for(int i=0;i<lines.size();i++)
            {
                if(i!=0)
                    bw.write("\n");
                bw.write(lines.get(i));
            }
            bw.flush();
            bw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        lines.clear();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "//" + SenderTeamName +"//reqs_sent.txt"));
            String line="";
            while((line=br.readLine())!=null)
            {
                lines.add(line);
            }
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

//        for (String line :
//                lines) {
//            String[] list = line.split(",");
//            if(ReceiverTeamName.equals(list[0]))
//            {
//                String tmp = list[0]+","+list[1]+","+status;
//                lines.add(tmp);
//                lines.remove(line);
//                break;
//            }
//        }

        for(int i=0;i<lines.size();i++)
        {
            String[] list = lines.get(i).split(",");
            if(ReceiverTeamName.equals(list[0]) && date.equals(list[1]))
            {
                exists = true;
                String tmp = list[0]+","+list[1]+","+status;
                lines.set(i,tmp);
                break;
            }
        }


        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + "//" + SenderTeamName + "//reqs_sent.txt"));
            for(int i=0;i<lines.size();i++)
            {
                if(i!=0)
                    bw.write("\n");
                bw.write(lines.get(i));
            }
            bw.flush();
            bw.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(exists && status.equals("accept"))
        {
            System.out.println("running");
            RunGame.execute(SenderTeamName, ReceiverTeamName,path);
        }
    }
}