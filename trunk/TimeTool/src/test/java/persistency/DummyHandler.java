package persistency;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class DummyHandler extends DefaultHandler {
  private final transient XMLReader reader;
  private transient DefaultHandler handlerToTest;
  
  public DummyHandler(final XMLReader testReader) 
      throws SAXException {
    super();
    reader = testReader;
  }
  
  public void setHandlerToTest(final DefaultHandler handlerToTest) {
    this.handlerToTest = handlerToTest;
  }
  
  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attributes) 
      throws SAXException {
    reader.setContentHandler(handlerToTest);
  }
}
