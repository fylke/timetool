package persistency.projects;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ProjectsParser extends DefaultHandler implements ContentHandler {
  private XMLReader reader;
  private Projects projects;
  
  public ProjectsParser(final XMLReader reader) {
    this.reader = reader;
  }
  
  public void setTargetObject(final Projects projects) {
    this.projects = projects;
  }

  public Projects parse(final InputSource is) throws SAXException, IOException, 
      ParserConfigurationException {
    reader.setContentHandler(this);
    reader.parse(is);
    
    return projects;
  }

  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attributes) 
      throws SAXException {
    if (qName.equals("company")) {
      final Company company = new Company();
      company.setId(Integer.parseInt(attributes.getValue("id")));
      assert(projects != null);

      projects.addCompany(company);
           
      final ContentHandler companyHandler = 
        new CompanyHandler(attributes, reader, this, company);
      reader.setContentHandler(companyHandler);
    }
  }
}