package persistency;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.ReadableDateTime;
import org.junit.Before;
import org.junit.Test;

public class XmlUtilsTest extends TestCase {
	private transient XmlUtils xmlUtils;
	
	@Before
	protected void setUp() throws Exception {
		xmlUtils = new XmlUtils();
	}

	@Test
	public void testStringToTime() {
		final String hhmm = "04:30";
		final ReadableDateTime today = new DateTime(System.currentTimeMillis());
		final ReadableDateTime key = new LocalTime(4, 30).toDateTimeToday();

		final ReadableDateTime result = xmlUtils.stringToTime(hhmm, today);
		
		assertEquals("Hours didn't match!", key.getHourOfDay(), result.getHourOfDay());
		assertEquals("Minutes didn't match!", key.getMinuteOfHour(), result.getMinuteOfHour());
	}

	@Test
	public void testXmlify() {
		final String stringToXmlify = "&<\'>\"";

		final String expResult = "&amp;&lt;&apos;&gt;&quot;";
		final String result = xmlUtils.xmlify(stringToXmlify);
		
		assertEquals("String not translated correctly!", expResult, result);
	}

	@Test
	public void testIndent() {
		final int level = 1;

		final String expResult = "  ";
		final String result = xmlUtils.indent(level);
		
		assertEquals("Indent level not correct!", expResult, result);
	}

	@Test
	public void testIncIndent() {
		final String indent = "";

		final String expResult = "  ";
		final String result = xmlUtils.incIndent(indent);
		assertEquals("Indent level not correct!", expResult, result);
	}

	@Test
	public void testDecIndent() {
		final String indent = "  ";

		final String expResult = "";
		final String result = xmlUtils.decIndent(indent);
		assertEquals("Indent level not correct!", expResult, result);
	}

	@Test
	public void testGetHeader() {
		final String type = "test";

		final String expResult =  "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
															"<!DOCTYPE " + type + " SYSTEM \"" + type + ".dtd\">\n" +
															"<" + type + ">\n";
		final StringBuilder result = xmlUtils.getHeader(type);
		assertEquals("Header not correct!", expResult, result.toString());
	}
	
	@Test
	public void testGetHeaderWithAttrs() {
		final String type = "test";
		final String attr1 = "attr1=\"1\"";
		final String attr2 = "attr2=\"2\"";
		
		final String expResult =  "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
															"<!DOCTYPE " + type + " SYSTEM \"" + type + ".dtd\">\n" +
															"<" + type + " " + attr1 + " " + attr2 + ">\n";
		final StringBuilder result = xmlUtils.getHeader(type, attr1, attr2);
		assertEquals("Header not correct!", expResult, result.toString());
	}
}