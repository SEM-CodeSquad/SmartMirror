package widgets;

import java.util.LinkedList;

/**
 * Created by Geoffrey on 2016/11/4.
 */

public class RSSFeed {


    /*
     * Stores an RSS feed
     */

    final String title;
    final LinkedList<RSSMessage> entries = new LinkedList<>();

    public RSSFeed(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public LinkedList getList() {
        return entries;
    }

    @Override
    public String toString() {
        return "Feed [title= " + title + " ]";
    }

}
