package weather;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nimish on 04/11/2016.
 */

// This class initiates a weather fetcher.
public class WeatherFetcher {

    // The base URL is the starting point for any call to get the weather of a place or the icon.
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    // The fetchWeather method takes the city name as a constructor and returns the JSON formatted text
    // returned by the web server.
    public String fetchWeather(String cityName) {

            HttpURLConnection connection = null ;
            InputStream IS = null;

            try {
                connection = (HttpURLConnection) ( new URL(BASE_URL + cityName + "&cnt=3&units=metric&APPID=47d83bc50f7e59413e487108ded5c729")).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();

                // Reading the response got.
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
    // The below getImage() method returns a ByteArray code of the icon requested with the variable "code"
    // coming from the returned weather call requested by fetchWeather().
    // 'ImageView' could be used to display this icon on the Android end.
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

            // Let's read the response
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

