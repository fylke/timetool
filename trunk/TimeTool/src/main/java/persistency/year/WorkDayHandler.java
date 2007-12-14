package persistency.year;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;



public class WorkDayHandler extends DefaultHandler {
  private final XMLReader reader;
  private final ContentHandler parentHandler;
  private final WorkDay currentWorkDay;

  public WorkDayHandler(final XMLReader reader,
                        final ContentHandler parentHandler,
                        final WorkDay currentWorkDay)
      throws SAXException {
    this.currentWorkDay = currentWorkDay;
    this.parentHandler = parentHandler;
    this.reader = reader;
  }

  @Override
  public void startElement(final String uri, final String localName,
                           final String qName, final Attributes attributes)
      throws SAXException {
    if ("activity".equals(qName)) {
      assert(currentWorkDay != null);
      final int actId = Integer.parseInt(attributes.getValue("id"));
      final ActivityInfo actInfo = new ActivityInfo(actId);
      currentWorkDay.addActivity(actInfo);

      final ContentHandler activityHandler =
        new ActivityInfoHandler(attributes, reader, this,
                                currentWorkDay.getDate(), actInfo);

      reader.setContentHandler(activityHandler);
    } else if ("duration".equals(qName)) {
      currentWorkDay.setStartTime(attributes.getValue("start"));
      currentWorkDay.setEndTime(attributes.getValue("end"));
    } else if ("overtime".equals(qName)) {
      currentWorkDay.setTreatOvertimeAs(attributes.getValue("treatAs"));
    }
  }

  @Override
  public void endElement(final String uri, final String localName,
                         final String qName)
      throws SAXException {
    if ("isReported".equals(qName)) {
      currentWorkDay.isReported = true;
    } else if ("journalWritten".equals(qName)) {
      currentWorkDay.journalWritten = true;
    } else if ("workDay".equals(qName)) {
      reader.setContentHandler(parentHandler);
    }
  }
}