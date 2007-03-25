package persistency.projects;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import persistency.ItemAlreadyDefinedException;

public class ProjectSetParser extends DefaultHandler {
  private static transient final String ns = "";
  private transient XMLReader reader;
  private transient ProjectSet projectSet;
  
  public ProjectSetParser(final XMLReader reader) {
    this.reader = reader;
  }
  
  public void setTargetObject(final ProjectSet projectSet) {
    this.projectSet = projectSet;
  }

  public ProjectSet parse(final InputSource is) throws SAXException, IOException, 
      ParserConfigurationException {
    reader.setContentHandler(this);
    reader.parse(is);
    
    return projectSet;
  }

  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attributes) 
      throws SAXException {
    if ((ns + "company").equals(qName)) {
      final Company company = new Company();
      assert(projectSet != null);

      try {
        projectSet.addCompany(company);
      } catch (ItemAlreadyDefinedException e) {
        // Silently omit this company
      }
           
      final ContentHandler companyHandler = 
        new CompanyHandler(attributes, reader, this, company, ns);
      reader.setContentHandler(companyHandler);
    }
  }
}