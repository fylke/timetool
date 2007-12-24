package persistency.projects;

import java.io.CharArrayWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class CompanyParser extends DefaultHandler {
	private static final String NS = "";

	private final CharArrayWriter text;
	private final XMLReader reader;
	private Company comp;

	public CompanyParser(final XMLReader reader) {
		this.reader = reader;
		text = new CharArrayWriter();
	}

	public void setTargetObject(final Company comp) {
		this.comp = comp;
	}

	public Company parse(final InputSource is) throws SAXException, IOException,
			ParserConfigurationException {
		reader.setContentHandler(this);
		reader.parse(is);

		return comp;
	}

	@Override
	public void startElement(final String uri, final String localName,
													 final String qName, final Attributes attrs)
			throws SAXException {
		text.reset();

		if ((NS + "company").equals(qName)) {
			comp.setId(Integer.parseInt(attrs.getValue("id")));
		}	else if ((NS + "project").equals(qName)) {
			final Project proj= new Project();
			comp.addProjectWithId(proj, Integer.parseInt(attrs.getValue("id")));

			final ContentHandler projectHandler =	new ProjectHandler(attrs, reader, this, proj, NS);

			reader.setContentHandler(projectHandler);
		} else if ((NS + "activity").equals(qName)) {
			final Activity act = new Activity();
			comp.addActivityWithId(act, Integer.parseInt(attrs.getValue("id")));

			final ContentHandler actHandler =	new ActivityHandler(attrs, reader, this, act, NS);

			reader.setContentHandler(actHandler);
		}
	}

	@Override
	public void endElement(final String uri, final String localName,
												 final String qName)
			throws SAXException {
		if ((NS + "compName").equals(qName)) {
			comp.setName(getText());
		} else if ((NS + "compShortName").equals(qName)) {
			comp.setShortName(getText());
		} else if ((NS + "employeeId").equals(qName)) {
			comp.setEmployeeId(getText());
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