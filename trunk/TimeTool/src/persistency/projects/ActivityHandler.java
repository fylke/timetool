package persistency.projects;

import org.joda.time.ReadableDateTime;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import persistency.year.ActivityInfo;


public class ActivityHandler extends DefaultHandler implements ContentHandler {
  private transient final XMLReader reader;
  private transient final ContentHandler parentHandler;
  private transient final ReadableDateTime currentDate;
  private transient final ActivityInfo currentActInfo;

  public ActivityHandler(final Attributes attributes, final XMLReader reader, 
                         final ContentHandler parentHandler, 
                         final ReadableDateTime currentDate,
                         final ActivityInfo currentActInfo)  
      throws SAXException {
    this.currentActInfo = currentActInfo;
    this.currentDate = currentDate;
    this.parentHandler = parentHandler;
    this.reader = reader;
  }
  
  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attributes) 
      throws SAXException {
    if ("duration".equals(qName)) {
      currentActInfo.setActivityStartTime(currentDate, 
                                          attributes.getValue("start"));
      currentActInfo.setActivityStartTime(currentDate, 
                                          attributes.getValue("end"));
    }
  }

  @Override
  public void endElement(final String uri, final String localName, 
                         final String qName)
      throws SAXException {
    if ("lunchBreak".equals(qName)) {
      currentActInfo.includeLunch = true;
    } else if ("activity".equals(qName)) {
      reader.setContentHandler(parentHandler);
    }
  }
}