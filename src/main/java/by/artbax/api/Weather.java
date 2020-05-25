package by.artbax.api;

import by.artbax.model.WeatherModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    public static String getWeather(String message, WeatherModel model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=e775047cea01ee4b5ac8c41599dc9cd9");
        Scanner scan = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scan.hasNext()) {
            result += scan.nextLine();
        }
        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setMain((String) obj.get("main"));
            model.setIcon((String) obj.get("icon"));
        }

        return "City " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + " C" + "\n" +
                " Humidity: " + model.getHumidity() + " %" + "\n" +
                model.getMain() + "\n" +
                "http://openweathermap.org/img/wn/" + model.getIcon() + "@2x.png";
    }
}
