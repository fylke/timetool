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
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import persistency.MockHandler;

public class MonthHandlerTest {
  private YearFactory yf;
  private Reader monthInput;
  private XMLReader reader;
  private MockHandler testHandler;
  private Month testMonth;
  private MonthHandler monthHandler;
  // We won't bother with SearchControl in these tests
  private SearchControl searchControl = null;

  @BeforeClass
  public void setUpBeforeClass() throws Exception {
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
    final short testMonthId = 1;
    final short testYearId = 1;
    
    final AttributesImpl attr = new AttributesImpl();
    attr.addAttribute("", "", "id", "String", Short.toString(testMonthId));
    
    testHandler = new MockHandler(reader);
    testMonth = new Month(testMonthId, testYearId);
    
    reader.setContentHandler(testHandler); 
    monthHandler = 
      new MonthHandler(attr, reader, testHandler, testMonth, searchControl);
  }

  @AfterClass
  public void tearDownAfterClass() throws Exception {
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
    Month testMonthKey = yf.getMonth(testMonthIdKey, yearConfig);
    
    StringBuilder sb = new StringBuilder();
    final String testMonthString = yf.getXmlMonth(nrOfMonths, yearConfig, sb);
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
    Month testMonthKey = yf.getMonth(testMonthIdKey, yearConfig);
    
    StringBuilder sb = new StringBuilder();
    final String testMonthString = yf.getXmlMonth(nrOfMonths, yearConfig, sb);
    monthInput = new StringReader(testMonthString); 
    
    testHandler.setHandlerToTest(monthHandler);
    reader.parse(new InputSource(monthInput));
    
    assertEquals("Generated test object and key not equal!", 
                 testMonthKey, testMonth);  
  }
}