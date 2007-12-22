package persistency.year;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.ReadableDateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityInfoTest {
	static final int id = 1;
	static ActivityInfo actInfo;
	static ReadableDateTime today;
	static LocalTime now;
	static String nowString;
	static LocalTime later;
	static String laterString;
	static boolean includeLunch;
	static int lunchLength;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		actInfo = new ActivityInfo(id);
		today = new DateTime(System.currentTimeMillis());
		now = new LocalTime(System.currentTimeMillis());
		nowString = now.toString("hh:mm");
		later = new LocalTime(now.plusHours(5));
		laterString = later.toString("hh:mm");
		includeLunch = true;
		lunchLength = 40;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public final void testGetId() {
		final int expectedId = 1;
		assertEquals(expectedId, actInfo.getId());
	}

	@Test
	public final void testSetAndGetEndTime() {
		actInfo.setActivityEndTime(today, later);
		assertEquals(later.toString("hhmmss"), actInfo.getEndTime().toString("hhmmss"));
		actInfo.setActivityEndTime(today, laterString);
		assertEquals(later.toString("hhmm"), actInfo.getEndTime().toString("hhmm"));
	}

	@Test
	public final void testSetAndGetStartTime() {
		actInfo.setActivityStartTime(today, now);
		assertEquals(now.toString("hhmmss"), actInfo.getStartTime().toString("hhmmss"));
		actInfo.setActivityStartTime(today, nowString);
		assertEquals(now.toString("hhmm"), actInfo.getStartTime().toString("hhmm"));
	}

	@Test
	public final void testToString() {
		actInfo.includeLunch = includeLunch;
		actInfo.setActivityStartTime(today, now);
		actInfo.setActivityEndTime(today, later);
		actInfo.setLunchLength(lunchLength);

		StringBuilder expected = new StringBuilder();
		expected.append("id: " + id + "\n");
		expected.append("startTime: " + now.toString("kk:mm") + "\n");
		expected.append("endTime: " + later.toString("kk:mm") + "\n");
		expected.append("includeLunch: " + includeLunch + "\n");
		expected.append("lunchLength: " + lunchLength + "\n");

		assertEquals(expected.toString(), actInfo.toString());
	}

	@Test
	public final void testSetAndGetLunchLenght() {
		actInfo.setLunchLength(lunchLength);
		assertEquals(lunchLength, actInfo.getLunchLength());
	}

	@Test
	public final void testEquals() {
		ActivityInfo actInfoSame = new ActivityInfo(id);
		assertEquals(actInfo, actInfoSame);

		ActivityInfo actInfoOther = new ActivityInfo(2);
		assertFalse(actInfo.equals(actInfoOther));
	}
}