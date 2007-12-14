package persistency.year;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class YearWriterTest {
  private static YearFactory yf;
  private static ByteArrayOutputStream baos;
  private static YearWriter yw;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		yf = new YearFactory();
		yw = new YearWriter();
	}

	@Before
	public void setUp() throws Exception {
		baos = new ByteArrayOutputStream();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		baos.close();
	}

	@Test
	public final void testWriteYearEasy() {
    final int year = 1;
    final int nrOfMonths = 1;
    final int nrOfDaysEachMonth = 1;
    final int nrOfActsEachDay = 1;
    final SearchControl sc = null;
    final YearConfig yc = new YearConfig(year, nrOfMonths,
                                         nrOfDaysEachMonth,
                                         nrOfActsEachDay,
                                         sc);
    final Year yearInput = yf.getYearWithConfig(yc);
    final String yearKey = yf.getXmlYearWithConfig(yc);

    yw.writeYear(yearInput, baos);

    assertEquals("Generated test string and key string not equal!",
                 yearKey, baos.toString());
	}

	@Test
	public final void testWriteYearHard() {
    final int year = 1;
    final int nrOfMonths = 8;
    final int nrOfDaysEachMonth = 28;
    final int nrOfActsEachDay = 3;
    final SearchControl sc = null;
    final YearConfig yc = new YearConfig(year, nrOfMonths,
                                         nrOfDaysEachMonth,
                                         nrOfActsEachDay,
                                         sc);
    final Year yearInput = yf.getYearWithConfig(yc);
    final String yearKey = yf.getXmlYearWithConfig(yc);

    yw.writeYear(yearInput, baos);

    assertEquals("Generated test string and key string not equal!",
                 yearKey, baos.toString());
	}
}