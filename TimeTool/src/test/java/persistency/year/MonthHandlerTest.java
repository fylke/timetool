package persistency.year;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import persistency.MockHandler;

public class MonthHandlerTest {
  private static YearFactory yf;
  private static Reader monthInput;
  private static XMLReader reader;
  private static MockHandler testHandler;
  private static Month month;
  private static MonthHandler monthHandler;
  // We won't bother with SearchControl in these tests
  private static SearchControl sc = null;

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
    final int monthId = 1;
    final int yearId = 1;
    
    testHandler = new MockHandler(reader);
    month = new Month(monthId, yearId);
    
    reader.setContentHandler(testHandler); 
    monthHandler = new MonthHandler(reader, testHandler, month, sc);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    monthInput.close();
  }

  @Test
  public final void testMonthHandlerSimple() throws Exception {
    final int year = 1;
    final int nrOfMonths = 1;
    final int nrOfDaysEachMonth = 1;
    final int nrOfActsEachDay = 1; 
    final YearConfig yc = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                         nrOfActsEachDay, sc);
    
    final short testMonthIdKey = 1;
    final Month testMonthKey = yf.getMonth(testMonthIdKey, yc);
    
    final StringBuilder sb = new StringBuilder();
    final String testMonthString = yf.getXmlMonth(nrOfMonths, yc, sb);
    monthInput = new StringReader(testMonthString); 
    
    testHandler.setHandlerToTest(monthHandler);
    reader.parse(new InputSource(monthInput));
    
    assertEquals("Generated test object and key not equal!", 
                 testMonthKey, month);  
  }
  
  @Test
  public final void testMonthHandlerHarder() throws Exception {
  	final int year = 1;
    final int nrOfMonths = 1;
    final int nrOfDaysEachMonth = 6;
    final int nrOfActsEachDay = 6; 
    final YearConfig yc = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay, sc);
    
    final short testMonthIdKey = 1;
    final Month testMonthKey = yf.getMonth(testMonthIdKey, yc);
    
    final StringBuilder sb = new StringBuilder();
    final String testMonthString = yf.getXmlMonth(nrOfMonths, yc, sb);
    monthInput = new StringReader(testMonthString); 
    
    testHandler.setHandlerToTest(monthHandler);
    reader.parse(new InputSource(monthInput));
    
    assertEquals("Generated test object and key not equal!", 
                 testMonthKey, month);  
  }
}