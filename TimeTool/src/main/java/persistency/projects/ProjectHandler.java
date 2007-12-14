package persistency.projects;

import java.io.CharArrayWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class ProjectHandler extends DefaultHandler {
  private final CharArrayWriter text;
  private transient final XMLReader reader;
  private transient final ContentHandler parentHandler;
  private transient final Project currProj;
  private transient final String ns;

  private transient Activity act;
  private transient Project subProj;

  public ProjectHandler(final Attributes attributes, final XMLReader reader,
                        final ContentHandler parentHandler,
                        final Project currProj, final String ns)
      throws SAXException {
    this.currProj = currProj;
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

    if ((ns + "activity").equals(qName)) {
      assert(currProj != null);
      act = new Activity();
      currProj.addActivityWithId(act, Integer.parseInt(attrs.getValue("id")));

      final ContentHandler actHandler =
        new ActivityHandler(attrs, reader, this, act, ns);

      reader.setContentHandler(actHandler);
    } else if ((ns + "project").equals(qName)) {
      assert(currProj != null);
      subProj = new Project();
      currProj.addSubProjectWithId(subProj, Integer.parseInt(attrs.getValue("id")));

      final ContentHandler subProjectHandler =
        new ProjectHandler(attrs, reader, this, subProj, ns);

      reader.setContentHandler(subProjectHandler);
    }
  }

  @Override
  public void endElement(final String uri, final String localName,
                         final String qName)
      throws SAXException {
    if ((ns + "projName").equals(qName)) {
      currProj.setName(getText());
    } else if ((ns + "projShortName").equals(qName)) {
      currProj.setShortName(getText());
    } else if ((ns + "code").equals(qName)) {
      currProj.setCode(getText());
    } else if ((ns + "project").equals(qName)) {
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