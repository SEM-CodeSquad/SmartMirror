package smartMirror.dataHandlers.widgetsDataHandlers.weather;

import javafx.scene.image.Image;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;

/*
 * The WeatherFetcher class fetches the weather of a city that is inputted in the parameters of the method fetchWeather()
 * It returns a String containing weather information of that city in JSON format.
 */
public class WeatherFetcher extends Observable {
    private String weatherData;
    private boolean isForecast;

    public void fetchWeather(String query, String cityName) {

            HttpURLConnection connection = null ;
            InputStream IS = null;

        try {
            String BASE_URL = "http://api.openweathermap.org/data/2.5/" + query + "?q=";
            connection = (HttpURLConnection) (new URL(BASE_URL + cityName + "&type=accurate&units=metric&cnt=3" +
                    "&lang=en&APPID=47d83bc50f7e59413e487108ded5c729")).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();


            StringBuilder buffer = new StringBuilder();
                IS = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(IS));
                String line;
                while ( (line = br.readLine()) != null )
                    buffer.append(line).append("\r\n");

                IS.close();
                connection.disconnect();
            this.weatherData = buffer.toString();

            isForecast = query.equals("forecast/daily");
            setChanged();
            notifyObservers(this);
        } catch (Throwable t) {
                t.printStackTrace();
            }
            finally {
            try {
                assert IS != null;
                IS.close();
            } catch (Throwable ignored) {
            }
            try {
                assert connection != null;
                connection.disconnect();
            } catch (Throwable ignored) {
            }
        }
    }

    public Image getImage(String code) {
        InputStream is;

        try {
            is = getClass().getClassLoader().getResourceAsStream(code + ".png");
        } catch (Exception e) {
            is = getClass().getClassLoader().getResourceAsStream("na.png");
        }
        return new Image(is);
    }

    String getWeatherData() {
        return weatherData;
    }

    boolean isForecast() {
        return this.isForecast;
    }
}

