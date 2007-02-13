package persistency.projects;

import java.io.CharArrayWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class CompanyHandler extends DefaultHandler {
  private CharArrayWriter text;
  private final transient XMLReader reader;
  private final transient ContentHandler parentHandler;
  private final transient Company currentCompany;
  private final transient String ns;

  public CompanyHandler(final Attributes attributes, final XMLReader reader, 
                        final ContentHandler parentHandler, 
                        final Company currentCompany, final String ns)  
      throws SAXException {
    this.currentCompany = currentCompany;
    this.parentHandler = parentHandler;
    this.reader = reader;
    this.ns = ns;
    
    text = new CharArrayWriter();
  }

  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attributes) 
      throws SAXException {
    text.reset();
 
    if ((ns + "project").equals(qName)) {
      assert(currentCompany != null);
      final Project project = new Project();
      project.setId(Integer.parseInt(attributes.getValue("id")));
      currentCompany.addProject(project);
            
      final ContentHandler projectHandler = 
        new ProjectHandler(attributes, reader, this, project, ns);

      reader.setContentHandler(projectHandler);     
    }
  }

  @Override
  public void endElement(final String uri, final String localName, 
                         final String qName)
      throws SAXException {
    if ((ns + "compName").equals(qName)) {
      currentCompany.setName(getText());
    } else if ((ns + "compShortName").equals(qName)) {
      currentCompany.setShortName(getText());
    } else if ((ns + "employeeId").equals(qName)) {
      currentCompany.setEmployeeId(getText());
    } else if ((ns + "company").equals(qName)) {
      reader.setContentHandler(parentHandler);
    }
  }

  public String getText() {
    return text.toString().trim();
  }

  @Override
  public void characters(final char[] ch, final int start, final int length) {
    text.write(ch, start, length);
  }
}