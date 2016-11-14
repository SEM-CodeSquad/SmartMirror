package dataModels;

/**
 * Class Timestamp provides a Unix timestamp
 */
public class Timestamp {
    private long timestamp;

    /**
     * Timestamp constructor sets timestamp to the current unix time
     *
      */
    public Timestamp() {
        this.timestamp = System.currentTimeMillis()/ 1000;
    }

    /**
     * Getter getTimestamp
     *
     * @return timestamp
     */
    public long getTimestamp() {
        return this.timestamp;
    }

}
