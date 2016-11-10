package widgets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Nimish on 04/11/2016.
 */

public class JSONWeatherParser {

    /*
     * This method takes the whole JSON formatted data returned by the web server when called the fetchWeather() method
     * from the WeatherFetcher class in the constructor. Then returns 3 instances of the object Weather in an array.
     * With the first element holding values for the present day, the next element holding the values for the next day
     * and so on.
    */
    public Weather[] parseWeather(String data)  {
        JSONParser parser = new JSONParser();
        Weather [] wArray = new Weather[3];
        try {
            JSONObject jObject = (JSONObject) parser.parse(data);
            JSONArray jArray = (JSONArray) jObject.get("list");
            JSONObject jArrayObj1 = (JSONObject) jArray.get(0);
            JSONObject jTempObj1 = (JSONObject) jArrayObj1.get("main");
            Weather weather1 = new Weather();
            weather1.setTemp(jTempObj1.get("temp").toString());
            weather1.setMaxTemp(jTempObj1.get("temp_max").toString());
            weather1.setMinTemp(jTempObj1.get("temp_min").toString());
            JSONObject jTempObj2 = (JSONObject) ((JSONArray) jArrayObj1.get("weather")).get(0);
            weather1.setDesc(jTempObj2.get("description").toString());
            weather1.setIcon(jTempObj2.get("icon").toString());
            wArray[0] = weather1;

            JSONObject jArrayObj2 = (JSONObject) jArray.get(1);
            JSONObject jTempObj3 = (JSONObject) jArrayObj2.get("main");
            Weather weather2 = new Weather();
            weather2.setTemp(jTempObj3.get("temp").toString());
            weather2.setMaxTemp(jTempObj3.get("temp_max").toString());
            weather2.setMinTemp(jTempObj3.get("temp_min").toString());
            JSONObject jTempObj4 = (JSONObject) ((JSONArray) jArrayObj1.get("weather")).get(0);
            weather2.setDesc(jTempObj4.get("description").toString());
            weather2.setIcon(jTempObj4.get("icon").toString());
            wArray[1] = weather2;

            JSONObject jArrayObj3 = (JSONObject) jArray.get(2);
            JSONObject jTempObj5 = (JSONObject) jArrayObj3.get("main");
            Weather weather3 = new Weather();
            weather3.setTemp(jTempObj5.get("temp").toString());
            weather3.setMaxTemp(jTempObj5.get("temp_max").toString());
            weather3.setMinTemp(jTempObj5.get("temp_min").toString());
            JSONObject jTempObj6 = (JSONObject) ((JSONArray) jArrayObj1.get("weather")).get(0);
            weather3.setDesc(jTempObj6.get("description").toString());
            weather3.setIcon(jTempObj6.get("icon").toString());
            wArray[2] = weather3;

        } catch (ParseException e) {
            e.printStackTrace();
        }


       /* Weather[] wArray = new Weather[3];
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
        wArray[2] = weather3;*/

        return wArray;
    }
}