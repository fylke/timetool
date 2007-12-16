package persistency.year;

import static org.junit.Assert.assertEquals;
import static persistency.PersistencyTestUtils.readFile;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import persistency.PersistencyUtils;

public class YearStoreTest {
	private static final YearFactory YF = new YearFactory();
	private static final PersistencyUtils PU = new PersistencyUtils();

	private File absPath;

	@After
	public void tearDown() throws Exception {
		absPath.delete();
	}

	@Test
	public final void testStoreEasy() throws Exception {
		final int year = 1;
		final int nrOfMonths = 1;
		final int nrOfDaysEachMonth = 1;
		final int nrOfActsEachDay = 1;
		final YearConfig yc = new YearConfig(year, nrOfMonths,
																				 nrOfDaysEachMonth,
																				 nrOfActsEachDay);
		final Year yearInput = YF.getYearWithConfig(yc);
		final String yearKey = YF.getXmlYearWithConfig(yc);

		absPath = new File(PU.getStorageDir(), yearInput.getFilename());

		yearInput.store();

		assertEquals("Generated test string and key string not equal!",
								 yearKey, readFile(absPath));
	}

	@Test
	public final void testStoreHard() throws Exception {
		final int year = 1;
		final int nrOfMonths = 8;
		final int nrOfDaysEachMonth = 28;
		final int nrOfActsEachDay = 3;
		final YearConfig yc = new YearConfig(year, nrOfMonths,
																				 nrOfDaysEachMonth,
																				 nrOfActsEachDay);
		final Year yearInput = YF.getYearWithConfig(yc);
		final String yearKey = YF.getXmlYearWithConfig(yc);
		absPath = new File(PU.getStorageDir(), yearInput.getFilename());

		yearInput.store();

		assertEquals("Generated test string and key string not equal!",
								 yearKey, readFile(absPath));
	}
}