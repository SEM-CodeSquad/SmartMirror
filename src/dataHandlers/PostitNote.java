package dataHandlers;

public class PostitNote
{
    private String title;
    private String bodyText;
    private String senderId;
    private boolean important;
    private int timestamp;

    public PostitNote(String title, String bodyText, String senderId, boolean important, int timestamp)
    {
        this.title = title;
        this.bodyText = bodyText;
        this.senderId = senderId;
        this.important = important;
        this.timestamp = timestamp;
    }

    public String getTitle()
    {
        return title;
    }

    public String getBodyText()
    {
        return bodyText;
    }

    public String getSenderId()
    {
        return senderId;
    }

    public boolean isImportant()
    {
        return important;
    }

    public int getTimestamp()
    {
        return timestamp;
    }
}
