package widgets;

/**
 * Created by GeoffreyC on 2016/11/4.
 */
public class testRss {
    public static void main(String[] args) {
        RSSStAXParser parser = new RSSStAXParser("https://www.reddit.com/r/news/.rss");
        RSSFeed feed = parser.RSSParser();
        System.out.println(feed);
        for (Object message : feed.getList()) {
            System.out.println(message);
        }
    }
}
