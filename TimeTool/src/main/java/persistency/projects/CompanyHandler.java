package persistency.projects;

import java.io.CharArrayWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import persistency.ItemAlreadyDefinedException;


public class CompanyHandler extends DefaultHandler {
  private final transient CharArrayWriter text;
  private final transient XMLReader reader;
  private final transient ContentHandler parentHandler;
  private final transient Company currComp;
  private final transient String ns;
  
  private transient Project proj;

  public CompanyHandler(final Attributes attrs, final XMLReader reader, 
                        final ContentHandler parentHandler, 
                        final Company currComp, final String ns)  
      throws SAXException {
    this.currComp = currComp;
    this.parentHandler = parentHandler;
    this.reader = reader;
    this.ns = ns;
    
    text = new CharArrayWriter();
  }

  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attrs) 
      throws SAXException {
    text.reset();
 
    if ((ns + "project").equals(qName)) {
      assert(currComp != null);
      proj = new Project();
      currComp.addProjectWithId(proj, Integer.parseInt(attrs.getValue("id")));    
      
      final ContentHandler projectHandler = 
        new ProjectHandler(attrs, reader, this, proj, ns);

      reader.setContentHandler(projectHandler);     
    }
  }

  @Override
  public void endElement(final String uri, final String localName, 
                         final String qName)
      throws SAXException {
    if ((ns + "compName").equals(qName)) {
      currComp.setName(getText());
    } else if ((ns + "compShortName").equals(qName)) {
      currComp.setShortName(getText());
    } else if ((ns + "employeeId").equals(qName)) {
      currComp.setEmployeeId(getText());
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