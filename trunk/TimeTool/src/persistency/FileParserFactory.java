package persistency;

import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import persistency.projects.ProjectSetParser;
import persistency.year.YearParser;

public class FileParserFactory {
  public static XMLReader getParser(final String kind) throws SAXException, 
      ParserConfigurationException {
    XMLReader reader;
    try {
      reader = 
        XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
    } catch (final SAXException e) {
      try {
        reader = XMLReaderFactory.createXMLReader();
      } catch (final SAXException e1) {
        throw new NoClassDefFoundError("No SAX parser is available.");
      }
    }
    
    final EntityResolver resolver = new JarResolver();
    reader.setEntityResolver(resolver);

    DefaultHandler handler = null;
    
    if (kind.equalsIgnoreCase("projectSet")) {
      handler = new ProjectSetParser(reader);
    } else if (kind.equalsIgnoreCase("year")) {
      handler = new YearParser(reader);
    } else {
      throw new ParserConfigurationException("Unknown parser type requested!");
    }
    
    reader.setContentHandler(handler);
    return reader;    
  }

  private static final class JarResolver implements EntityResolver {
    public InputSource resolveEntity(final String publicId, 
                                     final String systemId) {
      if ("projectSet.dtd".equals(systemId)) {
        final InputStream is = 
          getClass().getResourceAsStream("projectSet.dtd");
        return new InputSource(is);
      } else if ("year.dtd".equals(systemId)) {
        final InputStream is = 
          getClass().getResourceAsStream("year.dtd");
        return new InputSource(is);
      }
      return null;
    }
  }
}