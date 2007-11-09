package persistency.projects;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;

import persistency.FileParserFactory;

public class ActivityHandlerTest{
	private ActivityHandler handler;
	private Activity act;
	private ContentHandler dummyHandler;
	private XMLReader dummyReader;
	
	@Before
	protected void setUp() throws Exception {
		dummyHandler = new ProjectHandler(null, null, null, null, "");
		dummyReader = FileParserFactory.getParser("projectSet");
		act = new Activity();
		handler = new ActivityHandler(null, dummyReader, dummyHandler, act, "");
		
		final char[] ch = { 't', 'e', 's', 't'};
		final int start = 0;
		final int length = 4;
		
		handler.characters(ch, start, length);
	}
	
	@Test
	public void testEndElementName() throws Exception {
		handler.endElement("", "", "actName");
		assertEquals("Name should be 'test'!", "test", act.getName());
	}
	
	@Test
	public void testEndElementShortName() throws Exception {
		handler.endElement("", "", "actShortName");
		assertEquals("Short name should be 'test'!", "test", act.getShortName());
	}
	
	@Test
	public void testEndElementReportCode() throws Exception {
		handler.endElement("", "", "actReportCode");
		assertEquals("Report code should be 'test'!", "test", act.getReportCode());
	}
	
	@Test
	public void testEndElementActivity() throws Exception {
		handler.endElement("", "", "activity");
		assertEquals("The handler should be the dummyHandler!", dummyHandler, dummyReader.getContentHandler());
	}

	@Test
	public void testStartElement() throws Exception {
		handler.startElement("", "", "", null);
		assertEquals("Text should be empty since reset!", "", handler.getText());
	}

	@Test
	public void testCharacters() {
		assertEquals("Return string not the expected one!", "test", handler.getText());
	}
}