package persistency.projects;

import java.io.CharArrayWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ActivityHandler extends DefaultHandler {
  private CharArrayWriter text;
  private transient final XMLReader reader;
  private transient final ContentHandler parentHandler;
  private transient final Activity currentActivity;

  public ActivityHandler(final Attributes attributes, final XMLReader reader, 
                         final ContentHandler parentHandler, 
                         final Activity currentActivity)  
      throws SAXException {
    this.currentActivity = currentActivity;
    this.parentHandler = parentHandler;
    this.reader = reader;
    
    text = new CharArrayWriter();
  }
  
  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attributes) 
      throws SAXException {
    text.reset();
  }

  @Override
  public void endElement(final String uri, final String localName, 
                         final String qName)
      throws SAXException {
    if ("name".equals(qName)) {
      currentActivity.setName(getText());
    } else if ("shortName".equals(qName)) {
      currentActivity.setShortName(getText());
    } else if ("activity".equals(qName)) {
      reader.setContentHandler(parentHandler);
    } 
  }

  @Override
  public void characters(final char[] ch, final int start, final int length) {
    text.write(ch, start, length);
  }
  
  private String getText() {
    return text.toString().trim();
  }
}
