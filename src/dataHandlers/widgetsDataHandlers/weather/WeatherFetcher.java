package dataHandlers.widgetsDataHandlers.weather;

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
    
    /*
     * The fetchWeather method takes the city name as a constructor and returns the JSON formatted text
     * returned by the web server. The fetchWeather uses the HTTP GET request method to fetch the weather.
    */
    public void fetchWeather(String query, String cityName) {

            HttpURLConnection connection = null ;
            InputStream IS = null;

        try {
            String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast" + query + "?q=";
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

            isForecast = !query.equals("");

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
    /*
     * The below getImage() method returns a ByteArray code of the icon requested with the variable "code"
     * coming from the returned weather call requested by fetchWeather().
     * 'ImageView' could be used to display this icon on the Android end.
    */

    /* <ImageView
    android:id="@+id/condIcon"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_alignParentLeft="true"
    android:layout_below="@id/cityText" />*/
    public Image getImage(String code) {
        File f;
        try {
            f = new File("resources/" + code + ".png");
        } catch (Exception e) {
            f = new File("resources/na.png");
        }
        return new Image(f.toURI().toString());
    }

    public String getWeatherData() {
        return weatherData;
    }

    public boolean isForecast() {
        return this.isForecast;
    }
}

