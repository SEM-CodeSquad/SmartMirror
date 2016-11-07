package weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nimish on 04/11/2016.
 */

public class JSONWeatherParser {

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
        wArray[0] = weather1;

        JSONObject jArrayObj2 = jArray.getJSONObject(1);
        JSONObject jTempObj2 = jArrayObj2.getJSONObject("main");
        Weather weather2 = new Weather();
        weather2.setTemp(jTempObj2.getDouble("temp"));
        weather2.setMaxTemp(jTempObj2.getDouble("temp_max"));
        weather2.setMinTemp(jTempObj2.getDouble("temp_min"));
        wArray[1] = weather2;

        JSONObject jArrayObj3 = jArray.getJSONObject(2);
        JSONObject jTempObj3 = jArrayObj3.getJSONObject("main");
        Weather weather3 = new Weather();
        weather3.setTemp(jTempObj3.getDouble("temp"));
        weather3.setMaxTemp(jTempObj3.getDouble("temp_max"));
        weather3.setMinTemp(jTempObj3.getDouble("temp_min"));
        wArray[2] = weather3;

        return wArray;
    }


    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

}