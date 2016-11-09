package weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nimish on 04/11/2016.
 */

public class JSONWeatherParser {

    // This method takes the whole JSON formatted data returned by the web server when called the fetchWeather() method
    // from the WeatherFetcher class in the constructor. Then returns 3 instances of the object Weather in an array.
    // With the first element holding values for the present day, the next element holding the values for the next day
    // and so on.
    public Weather[] getWeather(String data) throws JSONException {
        Weather[] wArray = new Weather[3];
        JSONObject jObject = new JSONObject(data);
        JSONArray jArray = jObject.getJSONArray("list");

        JSONObject jArrayObj1 = jArray.getJSONObject(0);
        JSONObject jTempObj1 = jArrayObj1.getJSONObject("main");
        Weather weather1 = new Weather();
        weather1.setTemp(jTempObj1.getDouble("temp"));
        weather1.setMaxTemp(jTempObj1.getDouble("temp_max"));
        weather1.setMinTemp(jTempObj1.getDouble("temp_min"));
        weather1.setDesc(jArrayObj1.getJSONArray("weather").getJSONObject(0).getString("description"));
        weather1.setIcon(jArrayObj1.getJSONArray("weather").getJSONObject(0).getString("icon"));
        wArray[0] = weather1;

        JSONObject jArrayObj2 = jArray.getJSONObject(1);
        JSONObject jTempObj2 = jArrayObj2.getJSONObject("main");
        Weather weather2 = new Weather();
        weather2.setTemp(jTempObj2.getDouble("temp"));
        weather2.setMaxTemp(jTempObj2.getDouble("temp_max"));
        weather2.setMinTemp(jTempObj2.getDouble("temp_min"));
        weather2.setDesc(jArrayObj2.getJSONArray("weather").getJSONObject(0).getString("description"));
        weather2.setIcon(jArrayObj2.getJSONArray("weather").getJSONObject(0).getString("icon"));
        wArray[1] = weather2;

        JSONObject jArrayObj3 = jArray.getJSONObject(2);
        JSONObject jTempObj3 = jArrayObj3.getJSONObject("main");
        Weather weather3 = new Weather();
        weather3.setTemp(jTempObj3.getDouble("temp"));
        weather3.setMaxTemp(jTempObj3.getDouble("temp_max"));
        weather3.setMinTemp(jTempObj3.getDouble("temp_min"));
        weather3.setDesc(jArrayObj3.getJSONArray("weather").getJSONObject(0).getString("description"));
        weather3.setIcon(jArrayObj3.getJSONArray("weather").getJSONObject(0).getString("icon"));
        wArray[2] = weather3;

        return wArray;
    }
}