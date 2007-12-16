package persistency.year;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MonthTest {
	private static Month testMonth;
	private static final int YEAR = 1;
	private static final int MONTH = 1;
	private static final int WORK_DAY = 1;
	private static final WorkDay workDay = new WorkDay(YEAR, MONTH, WORK_DAY);

	@Before
	public void setUp() throws Exception {
		testMonth = new Month(MONTH, YEAR);
		testMonth.addWorkDay(workDay);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashCode() {
		final Month month2 = new Month(2, YEAR);
		assertEquals(testMonth.hashCode(), testMonth.hashCode());
		assertNotSame(testMonth.hashCode(), month2.hashCode());
	}

	@Test
	public void testGetId() {
		assertEquals(MONTH, testMonth.getId());
	}

	@Test
	public void testGetEnclosingYear() {
		assertEquals(YEAR, testMonth.getEnclosingYear());
	}

	@Test
	public void testGetWorkDay() {
		assertEquals(workDay, testMonth.getWorkDay(WORK_DAY));
	}

	@Test
	public void testGetWorkDays() {
		final WorkDay workDay2 = new WorkDay(YEAR, MONTH, 2);

		final Collection<WorkDay> workDays = new ArrayList<WorkDay>();
		workDays.add(workDay);
		workDays.add(workDay2);

		Collection<Integer> workDayIds = new ArrayList<Integer>();
		workDayIds.add(workDay.getDate().getDayOfMonth());
		workDayIds.add(workDay2.getDate().getDayOfMonth());

		testMonth.addWorkDay(workDay2);
		assertEquals(workDays, testMonth.getWorkDays(workDayIds));
	}

	@Test
	public void testGetAllWorkDays() {
		final ArrayList<WorkDay> expectedWorkDays = new ArrayList<WorkDay>();
		expectedWorkDays.add(workDay);

		final Collection<WorkDay> allWorkDays = testMonth.getAllWorkDays();
		final WorkDay[] allWorkDaysArray = allWorkDays.toArray(new WorkDay[]{});
		for (int i = 0; i < expectedWorkDays.size(); i++) {
			assertEquals(expectedWorkDays.get(i), allWorkDaysArray[i]);
		}
	}

	@Test
	public void testAddWorkDay() {
		final WorkDay workDay2 = new WorkDay(YEAR, MONTH, 2);

		final Collection<WorkDay> workDays = new ArrayList<WorkDay>();
		workDays.add(workDay);
		workDays.add(workDay2);

		final Month expectedMonth = new Month(YEAR, MONTH);
		expectedMonth.addWorkDays(workDays);

		testMonth.addWorkDay(workDay2);

		assertEquals(expectedMonth, testMonth);
	}

	@Test
	public void testAddWorkDays() {
		final WorkDay workDay2 = new WorkDay(YEAR, MONTH, 2);
		final WorkDay workDay3 = new WorkDay(YEAR, MONTH, 3);

		final Collection<WorkDay> expectedWorkDays = new ArrayList<WorkDay>();
		expectedWorkDays.add(workDay);
		expectedWorkDays.add(workDay2);
		expectedWorkDays.add(workDay3);

		final Month expectedMonth = new Month(YEAR, MONTH);
		expectedMonth.addWorkDays(expectedWorkDays);

		final Collection<WorkDay> workDays = new ArrayList<WorkDay>();
		workDays.add(workDay2);
		workDays.add(workDay3);

		testMonth.addWorkDays(expectedWorkDays);

		assertEquals(expectedMonth, testMonth);
	}

	@Test
	public void testToString() {
		final String expected = "id: " + MONTH + "\n" +
														"year: " + YEAR + "\n" +
														"WorkDays:" + "\n" +
														"date: " + testMonth.getWorkDay(WORK_DAY).getDate().toString("d/M") + "\n" +
														"startTime: not specified" + "\n" +
														"endTime: not specified" + "\n" +
														"treatOvertimeAs: not specified" + "\n" +
														"isReported: " + testMonth.getWorkDay(WORK_DAY).isReported + "\n" +
														"journalWritten: " + testMonth.getWorkDay(WORK_DAY).journalWritten;
		assertEquals(expected, testMonth.toString());
	}

	@Test
	public void testEqualsObject() {
		final Month equalMonth = new Month(YEAR, MONTH);
		equalMonth.addWorkDay(new WorkDay(YEAR, MONTH, WORK_DAY));

		assertEquals(equalMonth, testMonth);

		final Month notEqualMonth = new Month(YEAR, MONTH);

		assertFalse(equalMonth.equals(notEqualMonth));
	}
}