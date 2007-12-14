package persistency.projects;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ProjectSetParser extends DefaultHandler {
  private static transient final String ns = "";
  private transient final XMLReader reader;
  private transient ProjectSet projSet;
  private transient Company comp;

  public ProjectSetParser(final XMLReader reader) {
    this.reader = reader;
  }

  public void setTargetObject(final ProjectSet projSet) {
    this.projSet = projSet;
  }

  public ProjectSet parse(final InputSource is) throws SAXException, IOException,
      ParserConfigurationException {
    reader.setContentHandler(this);
    reader.parse(is);

    return projSet;
  }

  @Override
  public void startElement(final String uri, final String localName,
                           final String qName, final Attributes attrs)
      throws SAXException {
    if ((ns + "company").equals(qName)) {
      assert(projSet != null);
      comp = new Company();
      projSet.addCompanyWithId(comp, Integer.parseInt(attrs.getValue("id")));

      final ContentHandler compHandler =
        new CompanyHandler(attrs, reader, this, comp, ns);
      reader.setContentHandler(compHandler);
    }
  }
}