package persistency.year;

import java.io.CharArrayWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class MonthHandler extends DefaultHandler {
  private CharArrayWriter text;
  private final transient XMLReader reader;
  private final transient ContentHandler parentHandler;
  private final transient Month currentMonth;

  public MonthHandler(final Attributes attributes, final XMLReader reader, 
                        final ContentHandler parentHandler, 
                        final Month currentMonth)  
      throws SAXException {
    this.currentMonth = currentMonth;
    this.parentHandler = parentHandler;
    this.reader = reader;
    
    text = new CharArrayWriter();
  }

  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attributes) 
      throws SAXException {
    text.reset();
 
    if ("workDay".equals(qName)) {
      assert(currentMonth != null);
      final int year = currentMonth.getEnclosingYear();
      final short month = currentMonth.getId();
      final short dayInMonth = Short.parseShort(attributes.getValue("date"));
      final WorkDay workDay = new WorkDay(year, month, dayInMonth);
      
      currentMonth.addWorkDay(workDay);
            
      final ContentHandler workDayHandler = 
        new WorkDayHandler(attributes, reader, this, workDay);

      reader.setContentHandler(workDayHandler);
    }
  }

  @Override
  public void endElement(final String uri, final String localName, 
                         final String qName)
      throws SAXException {
    if ("month".equals(qName)) {
      reader.setContentHandler(parentHandler);
    }
  }
}