package smartMirror.dataModels.widgetsModels.postItsModels;

public class PostItNote {
    private String postItId;
    private String bodyText;
    private String senderId;
    private long timestamp;

    private int postItIndex;

    public PostItNote(String postItId, String bodyText, String senderId, long timestamp)
    {
        this.postItId = postItId;
        this.bodyText = bodyText;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    public String getPostItId() {
        return postItId;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getSenderId() {
        return senderId;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getPostItIndex() {
        return postItIndex;
    }

    public void setPostItIndex(int index) {
        if (index > 12 || index < 0) throw new IllegalArgumentException("Just Integers Between 0 and 12 are Allowed");
        else this.postItIndex = index;
    }
}
