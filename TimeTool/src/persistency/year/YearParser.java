package persistency.year;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;



public class YearParser extends DefaultHandler implements ContentHandler {
  private XMLReader reader;
  private Year year;
  private SearchControl wanted;
  
  public YearParser(final XMLReader reader) {
    this.reader = reader;
  }
  
  public void setTargetObject(final Year year) {
    this.year = year;
  }
  
  public void setSearchControl(final SearchControl wanted) {
    this.wanted = wanted;
  }

  public Year parse(final InputSource is) throws SAXException, IOException, 
      ParserConfigurationException {
    reader.setContentHandler(this);
    reader.parse(is);
    
    return year;
  }

  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attributes) 
      throws SAXException {
    if ("year".equals(qName)) {
      year.setId(Integer.parseInt(attributes.getValue("id")));
    }
    else if ("month".equals(qName)) {
      assert(year != null);
      final Month month = new Month(Short.parseShort(attributes.getValue("id")), 
                                    year.getId());
      year.addMonth(month);
           
      final ContentHandler monthHandler = 
        new MonthHandler(attributes, reader, this, month, wanted);
      reader.setContentHandler(monthHandler);
    }
  }
}