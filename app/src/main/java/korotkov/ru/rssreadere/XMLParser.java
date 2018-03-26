package korotkov.ru.rssreadere;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

/**
 * Created by Eugene on 26.03.2018.
 */

public class XMLParser extends Observable {

    private ArrayList<News> newsArrayList;
    private News currentNews;

    public XMLParser() {
        newsArrayList = new ArrayList<>();
        currentNews = new News();
    }

    public void parseXML(String xml) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        factory.setNamespaceAware(false);
        XmlPullParser xmlPullParser = factory.newPullParser();

        xmlPullParser.setInput(new StringReader(xml));
        boolean insideItem = false;
        int eventType = xmlPullParser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG) {

                if (xmlPullParser.getName().equalsIgnoreCase("item")) {

                    insideItem = true;

                } else if (xmlPullParser.getName().equalsIgnoreCase("title")) {

                    if (insideItem) {
                        String title = xmlPullParser.nextText();
                        currentNews.setTitle(title);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("enclosure")) {


                    if (insideItem) {
                        String pic;
                        if (xmlPullParser.getAttributeValue(null, "type").equals("image/jpeg")) {

                            pic = xmlPullParser.getAttributeValue(null, "url");
                            currentNews.setImage(pic);

                        }
                    }


                } else if (xmlPullParser.getName().equalsIgnoreCase("pubDate")) {
                    @SuppressWarnings("deprecation")

                    //Date to stringconvertation

                            Date pubDate = new Date(xmlPullParser.nextText());
                    Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String s = formatter.format(pubDate);
                    currentNews.setPubDate(s);
                }

            } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.getName().equalsIgnoreCase("item")) {
                insideItem = false;
                newsArrayList.add(currentNews);
                currentNews = new News();
            }
            eventType = xmlPullParser.next();
        }
        triggerObserver();
    }


    private void triggerObserver() {
        setChanged();
        notifyObservers(newsArrayList);
    }
}
