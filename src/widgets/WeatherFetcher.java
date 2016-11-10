package widgets;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nimish on 04/11/2016.
 */

/*
 * The WeatherFetcher class fetches the weather of a city that is inputted in the parameters of the method fetchWeather()
 * It returns a String containing weather information of that city in JSON format.
 */
public class WeatherFetcher {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";        /*Base URL to fetch weather of city */
    private static String IMG_URL = "http://openweathermap.org/img/w/";                           /* Base URL to fetch weather icon */

    /*
     * The fetchWeather method takes the city name as a constructor and returns the JSON formatted text
     * returned by the web server. The fetchWeather uses the HTTP GET request method to fetch the weather.
    */
    public String fetchWeather(String cityName) {

            HttpURLConnection connection = null ;
            InputStream IS = null;

            try {
                connection = (HttpURLConnection) ( new URL(BASE_URL + cityName + "&cnt=3&units=metric&APPID=47d83bc50f7e59413e487108ded5c729")).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();


                StringBuffer buffer = new StringBuffer();
                IS = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(IS));
                String line;
                while ( (line = br.readLine()) != null )
                    buffer.append(line + "\r\n");

                IS.close();
                connection.disconnect();
                return buffer.toString();
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            finally {
                try { IS.close(); } catch(Throwable t) {}
                try { connection.disconnect(); } catch(Throwable t) {}
            }

            return null;

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
    public byte[] getImage(String code) {
        HttpURLConnection connection = null ;
        InputStream IS = null;
        try {
            connection = (HttpURLConnection) ( new URL(IMG_URL + code + ".png")).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            /* Response got */
            IS = connection.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream ByteArray = new ByteArrayOutputStream();

            while (IS.read(buffer) != -1)
                ByteArray.write(buffer);

            return ByteArray.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {IS.close(); } catch(Throwable t) {}
            try {connection.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

    }

