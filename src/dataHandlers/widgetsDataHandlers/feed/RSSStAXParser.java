package dataHandlers.widgetsDataHandlers.feed;

import dataModels.widgetsModels.feedModels.RSSMarqueeMessage;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.*;

public class RSSStAXParser extends Observable {
    private RSSMarqueeMessage marquee;
    private URL url;


    public RSSStAXParser() {
        this.marquee = new RSSMarqueeMessage();
    }

    /**
     *
     *
     * @param source s
     */
    public void setUrl(String source) {
        if (source.equalsIgnoreCase("ABC")) {
            this.readUrl("http://feeds.abcnews.com/abcnews/internationalheadlines");
        } else if (source.equalsIgnoreCase("GOOGLE")) {
            this.readUrl("https://news.google.com/?output=rss");
        } else if (source.equalsIgnoreCase("CNN")) {
            this.readUrl("http://rss.cnn.com/rss/edition.rss");
        } else if (source.equalsIgnoreCase("DN")) {
            this.readUrl("http://www.dn.se/nyheter/m/rss/");
        } else if (source.equalsIgnoreCase("SVT")) {
            this.readUrl("http://www.svt.se/nyheter/rss.xml");
        } else if (source.equalsIgnoreCase("EXPRESSEN")) {
            this.readUrl("http://expressen.se/rss/nyheter");
        } else {
            this.readUrl("http://feeds.abcnews.com/abcnews/internationalheadlines");
        }

        this.rssParser();
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
     * Parse the XML file from given URL and store the Source of the RSS Feed as well as the news title into the linked list
     *
     *
     */
    @SuppressWarnings("unchecked")
    private void rssParser() {
        String news = null;
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


                        case "item":

                            if (isFeedHeader) {
                                isFeedHeader = false;
                                rssFeed = new RSSFeed(Title);
                            }
                            event = eventReader.nextEvent();
                            StartElement startElement1 = event.asStartElement();
                            String qName1 = startElement1.getName().getLocalPart();
                            if (qName1.equalsIgnoreCase("title")) {
                                Title = getCharacterData(event, eventReader);
                            }

                            break;


                        case "title":

                            Title = getCharacterData(event, eventReader);
                            break;

                    }
                } else if (event.isEndElement()) {

                    if (event.asEndElement().getName().getLocalPart().equals("item")) {
                        RSSMessage rssMsg = new RSSMessage();
                        rssMsg.setTitle(Title);
                        assert rssFeed != null;
                        rssFeed.getList().add(rssMsg);
                        eventReader.nextEvent();
                    }

                }
            }
            eventReader.close();
            RSS.close();

        } catch (XMLStreamException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (rssFeed != null) {
            news = rssFeed.toString();
            for (Object message : rssFeed.getList()) {
                news += "   ★" + message.toString();
            }
        } else {
            news += "   ★ N/A";
        }

        this.marquee.setMsg(news);
        setChanged();
        notifyObservers(this.marquee);
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
     * @param event e
     * @param eventReader e
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






