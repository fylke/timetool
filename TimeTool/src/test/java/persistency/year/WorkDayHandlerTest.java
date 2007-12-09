package persistency.year;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import persistency.DummyHandler;

public class WorkDayHandlerTest {
  private static YearFactory yf;
  private static XMLReader reader;
  private static DummyHandler testHandler;
  private static WorkDay workDay;
  private static WorkDayHandler workDayHandler;

  private final SearchControl sc = null;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    yf = new YearFactory();

    try {
      reader =
        XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
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
    final int testYearId = 1;
    final int testMonthId = 1;
    final int testWorkDayId = 1;

    testHandler = new DummyHandler(reader);
    workDay = new WorkDay(testYearId, testMonthId, testWorkDayId);

    reader.setContentHandler(testHandler);
    workDayHandler = new WorkDayHandler(reader, testHandler, workDay);
  }

  @Test
  public final void testMonthHandler() throws Exception {
    final int year = 1;
    final int nrOfMonths = 1;
    final int nrOfDaysEachMonth = 1;
    final int nrOfActsEachDay = 1;

    final YearConfig yc = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth,
                                         nrOfActsEachDay, sc);

    final int month = 1;
    final int dateInMonth = 1;
    final WorkDay workDayKey = yf.getWorkDay(yc, month, dateInMonth);

    final StringBuilder sb = new StringBuilder();
    yf.getXmlWorkDay(dateInMonth, month, yc, sb);

    final Reader testReader = new StringReader(sb.toString());

    testHandler.setHandlerToTest(workDayHandler);
    reader.parse(new InputSource(testReader));

    assertEquals("Generated test object and key not equal!", workDayKey, workDay);
  }
}