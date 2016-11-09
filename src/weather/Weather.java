package weather;

/**
 * Created by Nimish on 04/11/2016.
 */


//The weather object here stores the information related to the weather of the city that we call.
//It will store the current, maximum and minimum temperature along with the description and icon of the weather.
// Public getters and setters can be used to view any of the above mentioned variables.
public class Weather {
    private double currentTemp;
    private double minTemp;
    private double maxTemp;
    private String desc;
    private String icon;

        public double getTemp(){
            return currentTemp;
        }
        public void setTemp(double temp){
            this.currentTemp = temp;
        }
        public double getMinTemp() {
            return minTemp;
        }
        public void setMinTemp(double minTemp) {
            this.minTemp = minTemp;
        }
        public double getMaxTemp() {
            return maxTemp;
        }
        public void setMaxTemp(double maxTemp) {
            this.maxTemp = maxTemp;
        }
        public String getDesc(){
            return desc;
        }
        public void setDesc(String desc){
            this.desc = desc;
        }
        public String getIcon(){
            return this.icon;
        }
        public void setIcon(String icon){
            this.icon = icon;
        }
    }




