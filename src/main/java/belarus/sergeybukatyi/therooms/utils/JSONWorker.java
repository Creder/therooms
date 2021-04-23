package belarus.sergeybukatyi.therooms.utils;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;


public class JSONWorker {
    @Value("${roomsfile}")
    private String roomFile;

    @Autowired
    private  Logger logger;

    public String parseJSON() {
        try {

            File file = new File(roomFile);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String s = reader.readLine();
            while (s !=null){
                stringBuilder.append(s);
                s =reader.readLine();
            }
            return stringBuilder.toString();
        }
        catch (IOException e){
            logger.error(e.getMessage());
            return null;
        }

    }

    public void writeJSON(String rooms) {

        try {
            File file = new File(roomFile);
            FileWriter fileWriter= new FileWriter(file);
            fileWriter.write(rooms);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

}
