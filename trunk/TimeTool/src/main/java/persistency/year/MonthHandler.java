package persistency.year;

import java.io.CharArrayWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class MonthHandler extends DefaultHandler {
  private final CharArrayWriter text;
  private final XMLReader reader;
  private final ContentHandler parentHandler;
  private final Month currentMonth;
  private final SearchControl wanted;

  public MonthHandler(final XMLReader reader,
                      final ContentHandler parentHandler,
                      final Month currentMonth,
                      final SearchControl wanted)
      throws SAXException {
    this.wanted = wanted;
    this.currentMonth = currentMonth;
    this.parentHandler = parentHandler;
    this.reader = reader;

    text = new CharArrayWriter();
  }

  @Override
  public void startElement(final String uri, final String localName,
                           final String qName, final Attributes attrs)
      throws SAXException {
    text.reset();

    if ("workDay".equals(qName)) {
      assert(currentMonth != null);
      final int year = currentMonth.getEnclosingYear();
      final int month = currentMonth.getId();
      final int dayInMonth = Integer.parseInt(attrs.getValue("date"));
      final WorkDay workDay = new WorkDay(year, month, dayInMonth);

      if ((wanted == null) || wanted.isHit(workDay.getDate())) {
        currentMonth.addWorkDay(workDay);

        final ContentHandler workDayHandler =
          new WorkDayHandler(reader, this, workDay);

        reader.setContentHandler(workDayHandler);
      }
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