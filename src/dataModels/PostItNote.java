package dataModels;

public class PostItNote
{
    private String postItId;
    private String bodyText;
    private String senderId;
    private boolean important;
    private int timestamp;

    public PostItNote(String postItId, String bodyText, String senderId, boolean important, int timestamp)
    {
        this.postItId = postItId;
        this.bodyText = bodyText;
        this.senderId = senderId;
        this.important = important;
        this.timestamp = timestamp;
    }

    public String getPostItId()
    {
        return postItId;
    }

    public String getBodyText()
    {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getSenderId()
    {
        return senderId;
    }

    public boolean isImportant()
    {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public int getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
