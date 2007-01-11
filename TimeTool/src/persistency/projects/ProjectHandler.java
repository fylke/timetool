package persistency.projects;

import java.io.CharArrayWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class ProjectHandler extends DefaultHandler implements ContentHandler {
  private CharArrayWriter text;
  private transient final XMLReader reader;
  private transient final ContentHandler parentHandler;
  private transient final Project currentProject;

  public ProjectHandler(final Attributes attributes, final XMLReader reader, 
                        final ContentHandler parentHandler, 
                        final Project currentProject)  
      throws SAXException {
    this.currentProject = currentProject;
    this.parentHandler = parentHandler;
    this.reader = reader;
    
    text = new CharArrayWriter();
  }
  
  @Override
  public void startElement(final String uri, final String localName, 
                           final String qName, final Attributes attributes) 
      throws SAXException {
    text.reset();
 
    if ("project".equals(qName)) {
      assert(currentProject != null);
      final Project subProject = new Project();
      subProject.setId(Integer.parseInt(attributes.getValue("id")));
      currentProject.addSubProject(subProject);
      
      final ContentHandler subProjectHandler = 
        new ProjectHandler(attributes, reader, this, subProject);
      
      reader.setContentHandler(subProjectHandler);
    }
  }

  @Override
  public void endElement(final String uri, final String localName, 
                         final String qName)
      throws SAXException {
    if ("name".equals(qName)) {
      currentProject.setName(getText());
    } else if ("shortName".equals(qName)) {
      currentProject.setShortName(getText());
    } else if ("code".equals(qName)) {
      currentProject.setCode(getText());
    } else if ("project".equals(qName)) {
      reader.setContentHandler(parentHandler);
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