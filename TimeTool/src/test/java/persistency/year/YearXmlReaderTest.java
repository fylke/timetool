package persistency.year;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class YearXmlReaderTest {
	private static final YearFactory YF = new YearFactory();
	private static final String FILE_NAME = "test_year.xml";

	private static File absPath;

	@Before
	public void setUp() throws Exception {
		absPath = new File(System.getProperty("user.home"), FILE_NAME);
	}

	@After
	public void tearDown() throws Exception {
		absPath.delete();
	}

	@Test
	public final void testPopulateSimple() throws Exception {
		final int year = 1;
		final int nrOfMonths = 1;
		final int nrOfDaysEachMonth = 1;
		final int nrOfActsEachDay = 1;
		final YearConfig yc = new YearConfig(year, nrOfMonths,
																				 nrOfDaysEachMonth,
																				 nrOfActsEachDay);

		final PrintWriter pw = new PrintWriter(new FileOutputStream(absPath));
		pw.write(YF.getXmlYearWithConfig(yc));
		pw.close();

		final Year yearKey = YF.getYearWithConfig(yc);
		final YearXmlReader testYearXmlReader = new YearXmlReader();

		final Year testYear = new Year();
		testYearXmlReader.populate(testYear, absPath);
		assertEquals("Read proj set doesn't match the expected one!", yearKey, testYear);
	}

	@Test
	public final void testPopulateHard() throws Exception {
		final int year = 1;
		final int nrOfMonths = 12;
		final int nrOfDaysEachMonth = 28;
		final int nrOfActsEachDay = 3;
		final YearConfig yc = new YearConfig(year, nrOfMonths,
																				 nrOfDaysEachMonth,
																				 nrOfActsEachDay);

		final PrintWriter pw = new PrintWriter(new FileOutputStream(absPath));
		pw.write(YF.getXmlYearWithConfig(yc));
		pw.close();

		final Year yearKey = YF.getYearWithConfig(yc);
		final YearXmlReader testYearXmlReader = new YearXmlReader();

		final Year testYear = new Year();
		testYearXmlReader.populate(testYear, absPath);
		assertEquals("Read proj set doesn't match the expected one!", yearKey, testYear);
	}
}