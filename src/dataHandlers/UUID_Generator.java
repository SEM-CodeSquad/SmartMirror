package dataHandlers;

import java.util.UUID;

public class UUID_Generator {

    private String uuid;

    public UUID_Generator()
    {
        UUID id = UUID.randomUUID();
        this.uuid = "SmartMirror_" + id;
    }

    public String getUUID()
    {
        return this.uuid;
    }
}
