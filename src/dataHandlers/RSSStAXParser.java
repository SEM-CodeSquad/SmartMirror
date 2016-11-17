package dataHandlers;

/**
 * Created by Geoffrey on 2016/11/3.
 */


import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.*;

/*
 *
 *
 */
public class RSSStAXParser {
    static final String ITEM = "item";
    static final String TITLE = "title";

    URL url;


    /**
     * constructor
     *
     * @param feedUrl
     */
    public RSSStAXParser(String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Take a site name(string) as input and return a String of news
     *
     * @param source source name e.g. CNN, ABC
     * @return News as a packed String
     */

    public static String NewsToString(String source) {
        String News;
        RSSStAXParser NewsParser = null;

        if (source.equalsIgnoreCase("ABC")) {
            NewsParser = new RSSStAXParser("http://feeds.abcnews.com/abcnews/internationalheadlines");
        } else if (source.equalsIgnoreCase("Google")) {
            NewsParser = new RSSStAXParser("https://news.google.com/?output=rss");
        } else if (source.equalsIgnoreCase("CNN")) {
            NewsParser = new RSSStAXParser("http://rss.cnn.com/rss/edition.rss");
        } else if (source.equalsIgnoreCase("DN")) {
            NewsParser = new RSSStAXParser("http://www.dn.se/nyheter/m/rss/");
        } else if (source.equalsIgnoreCase("SVT")) {
            NewsParser = new RSSStAXParser("http://www.svt.se/nyheter/rss.xml");
        } else if (source.equalsIgnoreCase("Expressen")) {
            NewsParser = new RSSStAXParser("http://expressen.se/rss/nyheter");
        }
        RSSFeed feed = NewsParser.RSSParser();
        News = feed.toString();
        for (Object message : feed.getList()) {
            News += "   ★" + message.toString();
        }

        return News;
    }

    /**
     * Parse the XML file from given URL and store the Source of the RSS Feed as well as the news title into the linked list
     *
     *
     * @return rssFeed
     */

    public RSSFeed RSSParser() {

        RSSFeed rssFeed = null;

        try {
            boolean isFeedHeader = true;
            String Title = "";

            XMLInputFactory factory = XMLInputFactory.newInstance();

            InputStream RSS = read();
            XMLEventReader eventReader = factory.createXMLEventReader(RSS);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName()
                            .getLocalPart();

                    switch (localPart) {


                        case ITEM:

                            if (isFeedHeader) {
                                isFeedHeader = false;
                                rssFeed = new RSSFeed(Title);
                            }
                            event = eventReader.nextEvent();
                            StartElement startElement1 = event.asStartElement();
                            String qName1 = startElement1.getName().getLocalPart();
                            if(qName1.equalsIgnoreCase("title")){
                                Title = getCharacterData(event, eventReader);
                            }

                            break;

                        case TITLE:

                            Title = getCharacterData(event, eventReader);
                            break;

                    }
                }


                else if (event.isEndElement()) {

                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        RSSMessage rssMsg = new RSSMessage();
                        rssMsg.setTitle(Title);
                        rssFeed.getList().add(rssMsg);
                        event = eventReader.nextEvent();
                        continue;

                    }

                }
            }


        } catch (XMLStreamException e) {

        }
        return rssFeed;
    }

    /**
     * connect to the url and read the RSS feed
     *
     * @return an InputStream for reading from that connection (url.openStream())
     */

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param event
     * @param eventReader
     * @return result
     * @throws XMLStreamException
     */
    private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

}






