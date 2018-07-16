package leonard.ilioncorp.co.perfildesarrolladorandroid.model.dao;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import leonard.ilioncorp.co.perfildesarrolladorandroid.utils.constans.Routes;

public class Synchronized {


    public void Read(String parameter, Handler bridge)  {
        try {
            String ruta = Routes.DOMAIN.getUrl() + Routes.SYNCHRONIZED.getUrl();
            URL address = new URL(ruta);
            HttpURLConnection cnn = (HttpURLConnection) address.openConnection();
            cnn.setRequestMethod("POST");
            cnn.setDoInput(true);
            cnn.setDoOutput(true);
            PrintStream out = new PrintStream(cnn.getOutputStream());
            out.print(parameter);
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(cnn.getInputStream()));
            String line = reader.readLine();
            String content = "";
            while (line != null) {
                content += line;
                line = reader.readLine();
            }
            reader.close();
            Message message = new Message();
            message.obj = content;
            bridge.sendMessage(message);
        }catch (IOException e){
            Message message = new Message();
            message.obj = e.getMessage();
            bridge.sendMessage(message);
        }
    }
    public void Read(String parameter)  {
        try {
            String ruta = Routes.DOMAIN.getUrl() + Routes.SYNCHRONIZED.getUrl();
            URL address = new URL(ruta);
            HttpURLConnection cnn = (HttpURLConnection) address.openConnection();
            cnn.setRequestMethod("POST");
            cnn.setDoInput(true);
            cnn.setDoOutput(true);
            PrintStream out = new PrintStream(cnn.getOutputStream());
            out.print(parameter);
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(cnn.getInputStream()));
            String line = reader.readLine();
            String content = "";
            while (line != null) {
                content += line;
                line = reader.readLine();
            }
            reader.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void synchronizedTables(String jsonPerson, String jsonCar, String jsonHistory, Handler bridge) {
        String parameter = "jsonPerson="+jsonPerson+"&jsonCar="+jsonCar+"&jsonHistory="+jsonHistory;
        Read(parameter,bridge);
    }
    public void synchronizedTables(String jsonPerson, String jsonCar, String jsonHistory) {
        try {
            String parameter = "jsonPerson=" + jsonPerson + "&jsonCar=" + jsonCar + "&jsonHistory=" + jsonHistory;
            Read(parameter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
