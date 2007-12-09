package persistency;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import persistency.year.SearchControl;
import persistency.year.Year;
import persistency.year.YearConfig;
import persistency.year.YearFactory;

public final class PersistencyHandlerTest {
  private static YearFactory yf;
  private static PersistencyUtils ph;
  private static ByteArrayOutputStream baos;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    yf = new YearFactory();
    ph = new PersistencyUtils();
  }

	@Before
	public void setUp() throws Exception {
		baos = new ByteArrayOutputStream();
	}

	@After
	public void tearDown() throws Exception {
		baos.close();
	}

  @Test
  public final void testReadYearWithSearchControlSimple() throws Exception {
    int year = 1;
    short nrOfMonths = 1;
    short nrOfDaysEachMonth = 10;
    int nrOfActsEachDay = 0;
    SearchControl searchControl = new SearchControl();
    final int month = 1;
    final int day = 5;
    final int hours = 0;
    final int minutes = 0;
    final int seconds = 0;
    final int millis = 0;
    DateTime dateToFind = new DateTime(year, month, day, hours,
                                       minutes, seconds, millis);
    Set<ReadableDateTime> dates = new TreeSet<ReadableDateTime>();
    dates.add(dateToFind);
    searchControl.setDates(dates);

    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth,
                                           nrOfActsEachDay, searchControl);

    final Year yearKey = yf.getYearWithConfig(yearConfig);
    final String testYearString = yf.getXmlYearWithConfig(yearConfig);
    final ByteArrayInputStream testYearStream =
      new ByteArrayInputStream(testYearString.getBytes("UTF-8"));

    final Year testYear = ph.readYear(testYearStream, searchControl);

    assertEquals("Generated test object and key not equal!", testYear, yearKey);
  }



  @Test
  public final void testReadYearSimple() throws Exception {
    int year = 1;
    short nrOfMonths = 1;
    short nrOfDaysEachMonth = 1;
    int nrOfActsEachDay = 1;
    SearchControl searchControl = null; // We won't bother with SearchControl
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth,
                                           nrOfActsEachDay, searchControl);

    final Year yearKey = yf.getYearWithConfig(yearConfig);
    final String testYearString = yf.getXmlYearWithConfig(yearConfig);
    final ByteArrayInputStream testYearStream =
      new ByteArrayInputStream(testYearString.getBytes("UTF-8"));

    final Year testYear = ph.readYear(testYearStream, searchControl);

    assertEquals("Generated test object and key not equal!", testYear, yearKey);
  }

  @Test
  public final void testReadYearHarder() throws Exception {
    int year = 1;
    short nrOfMonths = 6;
    short nrOfDaysEachMonth = 6;
    int nrOfActsEachDay = 6;
    SearchControl searchControl = null; // We won't bother with SearchControl
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth,
                                           nrOfActsEachDay, searchControl);

    final Year yearKey = yf.getYearWithConfig(yearConfig);
    final String testYearString = yf.getXmlYearWithConfig(yearConfig);
    final ByteArrayInputStream testYearStream =
      new ByteArrayInputStream(testYearString.getBytes("UTF-8"));

    final Year testYear = ph.readYear(testYearStream, searchControl);

    assertEquals("Generated test object and key not equal!", testYear, yearKey);
  }
}