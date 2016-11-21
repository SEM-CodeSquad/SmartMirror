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

public class RSSStAXParser {
    final String ITEM = "item";
    final String TITLE = "title";
    String news;
    URL url;


    /**
     * constructor
     *
     * @param source
     */
    public RSSStAXParser(String source) {

        RSSStAXParser NewsParser = null;

        if (source.equalsIgnoreCase("ABC")) {
            this.readUrl("http://feeds.abcnews.com/abcnews/internationalheadlines");
        } else if (source.equalsIgnoreCase("Google")) {
            this.readUrl("https://news.google.com/?output=rss");
        } else if (source.equalsIgnoreCase("CNN")) {
            this.readUrl("http://rss.cnn.com/rss/edition.rss");
        } else if (source.equalsIgnoreCase("DN")) {
            this.readUrl("http://www.dn.se/nyheter/m/rss/");
        } else if (source.equalsIgnoreCase("SVT")) {
            this.readUrl("http://www.svt.se/nyheter/rss.xml");
        } else if (source.equalsIgnoreCase("Expressen")) {
            this.readUrl("http://expressen.se/rss/nyheter");
        } else {
            this.readUrl("http://feeds.abcnews.com/abcnews/internationalheadlines");
        }
        RSSFeed feed = NewsParser.RSSParser();
        this.news = feed.toString();
        for (Object message : feed.getList()) {
            this.news += "   ★" + message.toString();
        }

    }

    /**
     * Try to read the URL
     *
     * @param feed User choice of news feed e.g. ABC, Google News etc.
     */
    private void readUrl(String feed) {
        try {
            this.url = new URL(feed);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter to get news
     *
     * @return parsered news
     */
    public String getNews() {
        return this.news;
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






