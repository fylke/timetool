package persistency.settings;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;


import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class SettingsParser extends DefaultHandler implements ContentHandler {
  private static transient final String ns = "";
  private XMLReader reader;
  private Settings settings;
  
  public SettingsParser(final XMLReader reader) {
    this.reader = reader;
  }
  
  public void setTargetObject(final Settings settings) {
    this.settings = settings;
  }

  public Settings parse(final InputSource is) 
      throws SAXException, IOException, ParserConfigurationException {
    reader.setContentHandler(this);
    reader.parse(is);
    
    return settings;
  }

  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attrs) 
      throws SAXException {
    if ((ns + "userName").equals(qName)) {
      settings.setUserFirstName(attrs.getValue("first"));
      settings.setUserLastName(attrs.getValue("last"));
    } else if ((ns + "employedAt").equals(qName)) {
      settings.setEmployedAt(attrs.getValue("id")); 
    } else if ((ns + "projectSet").equals(qName)) {
      settings.setProjectSetId(attrs.getValue("id"));
    } else if ((ns + "lunchBreak").equals(qName)) {
      settings.setLunchBreak(attrs.getValue("duration"));
    } else if ((ns + "overtime").equals(qName)) {
      settings.setTreatOvertimeAs(attrs.getValue("treatAs"));
    }
  }
}