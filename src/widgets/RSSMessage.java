package widgets;

/**
 * Created by Geoffrey on 2016/11/4.
 */

/*
 * Store the information within the RSS message
 * in this case, the title of the news
 */
public class RSSMessage {

    String title;

    /**
     * item title getter
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * item title setter
     *
     * @param title
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * toString
     *
     * @return string
     */

    @Override
    public String toString() {
        return "FeedMessage [title= " + title + " ]";
    }
}

