package persistency.projects;

import java.io.CharArrayWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ActivityHandler extends DefaultHandler {
  CharArrayWriter text;
  final XMLReader reader;
  final ContentHandler parentHandler;
  final Activity currentActivity;
  final String ns;

  public ActivityHandler(final Attributes attributes, final XMLReader reader,
                         final ContentHandler parentHandler,
                         final Activity currentActivity, final String ns)
      throws SAXException {
    this.currentActivity = currentActivity;
    this.parentHandler = parentHandler;
    this.reader = reader;
    this.ns = ns;

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
    if ((ns + "actName").equals(qName)) {
      currentActivity.setName(getText());
    } else if ((ns + "actShortName").equals(qName)) {
      currentActivity.setShortName(getText());
    } else if ((ns + "actReportCode").equals(qName)) {
      currentActivity.setReportCode(getText());
    } else if ((ns + "activity").equals(qName)) {
      reader.setContentHandler(parentHandler);
    }
  }

  @Override
  public void characters(final char[] ch, final int start, final int length) {
    text.write(ch, start, length);
  }

  String getText() {
    return text.toString().trim();
  }
}