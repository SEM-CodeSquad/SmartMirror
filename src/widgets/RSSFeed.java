package widgets;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geoffrey on 2016/11/4.
 */

public class RSSFeed {


    /*
     * Stores an RSS feed
     */


    final String title;
    final List<RSSMessage> entries = new ArrayList<RSSMessage>();


    public RSSFeed(String title) {
        this.title = title;

    }


    public String getTitle() {
        return title;
    }

    public List<RSSMessage> getMessages() {
        return entries;
    }

    @Override
    public String toString() {
        return "Feed [title=" + title + "]";
    }

}
