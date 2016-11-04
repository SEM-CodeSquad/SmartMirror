package widgets;

/**
 * Created by Geoffrey on 2016/11/4.
 */
public class RSSMessage {

    String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FeedMessage [title=" + title + "]";
    }

}

