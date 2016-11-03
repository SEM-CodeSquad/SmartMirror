package dataHandlers;

public class PairedClient
{
    private String clientId;
    private boolean paired;

    public PairedClient(String userId, boolean paired)
    {
        this.clientId = userId;
        this.paired = paired;
    }

    public String getClientId()
    {
        return clientId;
    }

    public boolean isPaired()
    {
        return paired;
    }

    public void setPaired(boolean paired)
    {
        this.paired = paired;
    }
}
