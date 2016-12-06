package smartMirror.dataHandlers.commons;

import java.time.Instant;

/**
 * Class Timestamp provides a Unix timestamp
 */
public class Timestamp
{
    private long timestamp;

    /**
     * Timestamp constructor sets timestamp to the current unix time
     */
    public Timestamp()
    {
        this.timestamp = Instant.now().getEpochSecond();
    }

    /**
     * Getter getTimestamp
     *
     * @return timestamp
     */
    public long getTimestamp()
    {
        return this.timestamp;
    }

}
