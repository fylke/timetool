package persistency.settings;

import java.io.CharArrayWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import logic.Settings;
import logic.Settings.OvertimeType;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class SettingsParser extends DefaultHandler implements ContentHandler {
  private CharArrayWriter text;
  private XMLReader reader;
  private Settings settings;
  
  public SettingsParser(final XMLReader reader) {
    this.reader = reader;
    text = new CharArrayWriter();
  }
  
  public void setTargetObject(final Settings settings) {
    this.settings = settings;
  }

  public Settings parse(final InputSource is) throws SAXException, IOException, 
      ParserConfigurationException {
    reader.setContentHandler(this);
    reader.parse(is);
    
    return settings;
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
    if ("userName".equals(qName)) {
      settings.setUserName(getText());
    } else if ("projects".equals(qName)) {
      settings.setEmployedAt(Integer.parseInt(getText()));
    } else if ("employedAt".equals(qName)) {
      settings.setEmployedAt(Integer.parseInt(getText()));
    } else if ("lunchBreak".equals(qName)) {
      settings.setLunchBreak(Integer.parseInt(getText()));
    } else if ("overtime".equals(qName)) {
      settings.setTreatOvertimeAs(OvertimeType.transOvertimeType(getText()));
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