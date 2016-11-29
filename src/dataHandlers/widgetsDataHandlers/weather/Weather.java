package dataHandlers.widgetsDataHandlers.weather;

import com.google.zxing.common.detector.MathUtils;

import java.util.Observable;

/* The weather object here stores the information related to the weather of the city that we call.
 * It will store the current, maximum and minimum temperature along with the description and icon of the weather.
 * Public getters and setters can be used to view any of the above mentioned variables.
*/
public class Weather extends Observable {
    private String currentTemp;
    private String minTemp;
    private String maxTemp;
    private String desc;
    private String icon;
    private String dayName;

    public double getTemp() {
        return Math.round(Double.parseDouble(currentTemp));
        }

    public void setTemp(String temp) {
            this.currentTemp = temp;
        }

    public double getMinTemp() {
        return Math.round(Double.parseDouble(minTemp));
        }

    public void setMinTemp(String minTemp) {
            this.minTemp = minTemp;
        }

    public double getMaxTemp() {
        return Math.round(Double.parseDouble(maxTemp));
        }

    public void setMaxTemp(String maxTemp) {
            this.maxTemp = maxTemp;
        }

    public String getDesc() {
            return desc;
        }

    public void setDesc(String desc) {
        System.out.println(desc);
            this.desc = desc;
        }

    public String getIcon() {
            return this.icon;
        }

    public void setIcon(String icon) {
            this.icon = icon;
    }

    public String getDayName() {
        return this.dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
        }
    }




