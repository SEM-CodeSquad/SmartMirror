package dataHandlers;

/**
 * Class Timestamp provides a Unix timestamp
 *
 * Created by Emanuel on 04/11/2016.
 */
public class Timestamp {
    long timestamp;

    /**
     * Timestamp constructor sets timestamp to the current unix time
     *
      */
    public Timestamp(){
        this.timestamp = System.currentTimeMillis()/ 1000;
    }

    /**
     * Getter getTime
     *
     * @return timestamp
     */
    public long getTime(){
        return this.timestamp;
    }

}
