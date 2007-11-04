package persistency.year;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import persistency.TestHandler;

public class MonthHandlerTest {
  private transient TestYearFactory tyf;
  private transient Reader monthInput;
  private transient XMLReader reader;
  private transient TestHandler testHandler;
  private transient Month testMonth;
  private transient MonthHandler monthHandler;
  // We won't bother with SearchControl in these tests
  private transient SearchControl searchControl = null;

  @Before
  public void setUp() throws Exception {
    tyf = TestYearFactory.getInstance();
    
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
    final short testMonthId = 1;
    final short testYearId = 1;
    
    final AttributesImpl attr = new AttributesImpl();
    attr.addAttribute("", "", "id", "String", Short.toString(testMonthId));
    
    testHandler = new TestHandler(reader);
    testMonth = new Month(testMonthId, testYearId);
    
    reader.setContentHandler(testHandler); 
    monthHandler = 
      new MonthHandler(attr, reader, testHandler, testMonth, searchControl);
  }

  @After
  public void tearDown() throws Exception {
    monthInput.close();
  }

  @Test
  public final void testMonthHandlerSimple() throws Exception {
    int year = 1;
    short nrOfMonths = 1;
    short nrOfDaysEachMonth = 1;
    int nrOfActsEachDay = 1; 
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay, searchControl);
    
    final short testMonthIdKey = 1;
    Month testMonthKey = tyf.getMonth(testMonthIdKey, yearConfig);
    
    StringBuilder sb = new StringBuilder();
    final String testMonthString = tyf.getXmlMonth(nrOfMonths, yearConfig, sb);
    monthInput = new StringReader(testMonthString); 
    
    testHandler.setHandlerToTest(monthHandler);
    reader.parse(new InputSource(monthInput));
    
    assertEquals("Generated test object and key not equal!", 
                 testMonthKey, testMonth);  
  }
  
  @Test
  public final void testMonthHandlerHarder() throws Exception {
    int year = 1;
    short nrOfMonths = 1;
    short nrOfDaysEachMonth = 6;
    int nrOfActsEachDay = 6; 
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay, searchControl);
    
    final short testMonthIdKey = 1;
    Month testMonthKey = tyf.getMonth(testMonthIdKey, yearConfig);
    
    StringBuilder sb = new StringBuilder();
    final String testMonthString = tyf.getXmlMonth(nrOfMonths, yearConfig, sb);
    monthInput = new StringReader(testMonthString); 
    
    testHandler.setHandlerToTest(monthHandler);
    reader.parse(new InputSource(monthInput));
    
    assertEquals("Generated test object and key not equal!", 
                 testMonthKey, testMonth);  
  }
}