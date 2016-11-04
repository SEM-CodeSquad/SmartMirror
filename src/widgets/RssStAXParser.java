package widgets;

/**
 * Created by Geoffrey on 2016/11/3.
 */

import java.io.FileInputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;




public class RssStAXParser {
    public static void main(String[] args) {

        RSSParser();

    }
    public static void RSSParser() {
            boolean bTitle = false;

            try {
                URL url = new URL("http://www.reddit.com/r/news/.rss");
                URLConnection connection = url.openConnection();

                XMLInputFactory factory = XMLInputFactory.newInstance();
                XMLEventReader eventReader =
                        factory.createXMLEventReader(url);

                while (eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();
                    switch (event.getEventType()) {
                        case XMLStreamConstants.START_ELEMENT:
                            StartElement startElement = event.asStartElement();
                            String qName = startElement.getName().getLocalPart();
                            if (qName.equalsIgnoreCase("title")) {
                                bTitle = true;
                            }
                            break;
                        case XMLStreamConstants.CHARACTERS:
                            Characters characters = event.asCharacters();
                            if (bTitle) {
                                System.out.println(characters.getData());
                                bTitle = false;
                            }
                            break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    public static Document getXML(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new URL(url).openStream());
    }


}



