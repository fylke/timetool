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
  private Settings userSettings;
  
  public SettingsParser(final XMLReader reader) {
    this.reader = reader;
  }
  
  public void setTargetObject(final Settings userSettings) {
    this.userSettings = userSettings;
  }

  public Settings parse(final InputSource is) 
      throws SAXException, IOException, ParserConfigurationException {
    reader.setContentHandler(this);
    reader.parse(is);
    
    return userSettings;
  }

  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attrs) 
      throws SAXException {
    if ((ns + "userName").equals(qName)) {
      userSettings.setFirstName(attrs.getValue("first"));
      userSettings.setLastName(attrs.getValue("last"));
    } else if ((ns + "employedAt").equals(qName)) {
      userSettings.setEmployerId(attrs.getValue("id")); 
    } else if ((ns + "projectSet").equals(qName)) {
      userSettings.setProjectSetId(attrs.getValue("id"));
    } else if ((ns + "lunchBreak").equals(qName)) {
      userSettings.setLunchBreak(attrs.getValue("duration"));
    } else if ((ns + "overtime").equals(qName)) {
      userSettings.setTreatOvertimeAs(attrs.getValue("treatAs"));
    }
  }
}