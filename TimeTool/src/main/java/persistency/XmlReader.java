package persistency;

import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XmlReader {
	protected XMLReader initXmlReader() throws NoClassDefFoundError {
		XMLReader xmlReader;
		try {
			xmlReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		} catch (final SAXException e) {
			try {
				xmlReader = XMLReaderFactory.createXMLReader();
			} catch (final SAXException e1) {
				throw new NoClassDefFoundError("No SAX parser is available.");
			}
		}

		final EntityResolver resolver = new JarResolver();
		xmlReader.setEntityResolver(resolver);
		return xmlReader;
	}
}