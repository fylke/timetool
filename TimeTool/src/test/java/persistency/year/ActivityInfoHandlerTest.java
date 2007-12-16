package persistency.year;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;

import org.joda.time.LocalTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import persistency.DummyHandler;

public class ActivityInfoHandlerTest {
	private static YearFactory yf;
	private static XMLReader reader;
	private static DummyHandler testHandler;
	private static WorkDay workDay;
	private static ActivityInfo actInfo;
	private static ActivityInfoHandler actInfoHandler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		yf = new YearFactory();

		try {
			reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		}
		catch (final SAXException e) {
			try {
				reader = XMLReaderFactory.createXMLReader();
			} catch (final SAXException e1) {
				throw new NoClassDefFoundError("No SAX parser is available.");
			}
		}

		/* We can't really test the enclosing year ID as it is set
		 * before entering the handler we're testing. */
		final int testActInfoId = 1;
		testHandler = new DummyHandler(reader);
		actInfo = new ActivityInfo(testActInfoId);

		final int testYearId = 1;
		final int testMonthId = 1;
		final int testWorkDayId = 1;

		testHandler = new DummyHandler(reader);
		workDay = new WorkDay(testYearId, testMonthId, testWorkDayId);
		workDay.setStartTime(new LocalTime(9, 0));
		workDay.setEndTime(new LocalTime(17, 0));

		reader.setContentHandler(testHandler);
		AttributesImpl attrs = new AttributesImpl();
		actInfoHandler = new ActivityInfoHandler(attrs, reader, testHandler, workDay.getDate(), actInfo);
	}

	@Test
	public final void testActivityInfoHandler() throws Exception {
		final int year = 1;
		final int nrOfMonths = 1;
		final int nrOfDaysEachMonth = 1;
		final int nrOfActsEachDay = 1;

		final YearConfig yc = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth,
																				 nrOfActsEachDay);

		final String indent = "    ";
		final int id = 1;
		final ActivityInfo actInfoKey = yf.getActivityInfo(workDay, 1, yc);

		String xmlActInfo = yf.getXmlActivityInfo(indent, workDay, id, yc);
		final Reader testReader = new StringReader(xmlActInfo);

		testHandler.setHandlerToTest(actInfoHandler);
		reader.parse(new InputSource(testReader));

		assertEquals("Generated test object and key not equal!", actInfoKey, actInfo);
	}
}