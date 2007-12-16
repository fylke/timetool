package persistency.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static persistency.settings.SettingsFactory.EMPLOYED_AT;
import static persistency.settings.SettingsFactory.LUNCH_BREAK;
import static persistency.settings.SettingsFactory.OVERTIME_TYPE;
import static persistency.settings.SettingsFactory.PROJ_SET_ID;
import static persistency.settings.SettingsFactory.USER_FIRST_NAME;
import static persistency.settings.SettingsFactory.USER_LAST_NAME;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistency.projects.Company;
import persistency.projects.ProjSetConfig;
import persistency.projects.ProjectSet;
import persistency.projects.ProjectsFactory;
import persistency.settings.UserSettings.OvertimeType;

public class UserSettingsTest {
	private static final SettingsFactory SF = new SettingsFactory();
	private static UserSettings us;

	@Before
	public void setUp() throws Exception {
		us = SF.getSettings();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetXml() {
		assertEquals("Gotten settings didn't match the expected ones!", SF.getXmlSettings(), us.getXml());
	}

	@Test
	public final void testGetAndSetNamespace() {
		final String testNs = "testNameSpace";
		us.setNamespace(testNs);

		assertEquals("Gotten namespace didn't match the one set!", testNs, us.getNamespace());
	}

	@Test
	public final void testGetAndSetFirstName() {
		final String testName = "testName";
		us.setFirstName(testName);

		assertEquals("Gotten name didn't match the one set!", testName, us.getFirstName());
	}

	@Test
	public final void testGetAndSetLastName() {
		final String testName = "testName";
		us.setLastName(testName);

		assertEquals("Gotten name didn't match the one set!", testName, us.getLastName());
	}

	@Test
	public final void testGetAndSetEmployerId() {
		final int testEmpId = 9;
		final String testEmpStringId = "9";

		us.setEmployerId(testEmpId);
		assertEquals("Gotten employer id didn't match the one set!", testEmpId, us.getEmployerId());

		us.setEmployerId(testEmpStringId);
		assertEquals("Gotten employer id didn't match the one set!", testEmpId, us.getEmployerId());
	}

	@Test
	public final void testGetEmployedAt() {
		final ProjSetConfig config = new ProjSetConfig(1, 1, 0, 0, 0);
		final ProjectsFactory pf = new ProjectsFactory();
		final Company testComp = pf.getCompany(1, config);
		final int testCompId = testComp.getId();

		final ProjectSet ps = new ProjectSet();
		ps.addCompany(testComp);
		us.setProjectSet(ps);
		us.setEmployerId(testCompId);

		assertEquals("Gotten employer didn't match the one set!", testComp, us.getEmployedAt());
	}

	@Test
	public final void testGetAndSetLunchBreak() {
		final int testLunchBreak = 9;
		final String testLunchBreakString = "9";

		us.setLunchBreak(testLunchBreak);
		assertEquals("Gotten lunch break didn't match the one set!", testLunchBreak, us.getLunchBreak());

		us.setLunchBreak(testLunchBreakString);
		assertEquals("Gotten lunch break didn't match the one set!", testLunchBreak, us.getLunchBreak());
	}

	@Test
	public final void testGetAndSetProjectSet() throws Exception {
		final ProjSetConfig config = new ProjSetConfig(1, 1, 0, 0, 0);
		final ProjectsFactory pf = new ProjectsFactory();
		final ProjectSet testProjSet = pf.getProjSetWithConfig(config);

		us.setProjectSet(testProjSet);

		assertEquals("Gotten project set didn't match the one set!", testProjSet, us.getProjectSet());
	}

	public final void testGetAndSetTreatOvertimeAs() {
		final OvertimeType testOvertimeType = OvertimeType.COMP;
		final String testOvertimeTypeString = "comp";

		us.setTreatOvertimeAs(testOvertimeType);
		assertEquals("Gotten overtime type didn't match the one set!", testOvertimeType, us.getTreatOvertimeAs());

		us.setTreatOvertimeAs(testOvertimeTypeString);
		assertEquals("Gotten overtime type didn't match the one set!", testOvertimeType, us.getTreatOvertimeAs());
	}

	@Test
	public final void testGetAndSetProjectSetId() {
		final int testProjSetId = 9;
		final String testProjSetIdString = "9";

		us.setProjectSetId(testProjSetId);
		assertEquals("Gotten employer id didn't match the one set!", testProjSetId, us.getProjectSetId());

		us.setProjectSetId(testProjSetIdString);
		assertEquals("Gotten employer id didn't match the one set!", testProjSetId, us.getProjectSetId());
	}

	@Test
	public void testToString() {
		final String expected = "userFirstName: " + USER_FIRST_NAME + "\n" +
														"userLastName: " + USER_LAST_NAME + "\n" +
														"employedAt: " + EMPLOYED_AT + "\n" +
														"projectSetId: " + PROJ_SET_ID + "\n" +
														"lunchBreak: " + LUNCH_BREAK + "\n" +
														"treatOvertimeAs: " + OVERTIME_TYPE + "\n" +
														"ProjectSet:\n" +
														"not specified";
		assertEquals(expected, us.toString());
	}

	@Test
	public final void testEquals() {
		final UserSettings equalSettings = SF.getSettings();

		assertEquals(us, equalSettings);

		final UserSettings notEqualSettings = SF.getSettings();
		notEqualSettings.setFirstName("otherName");
		assertFalse(us.equals(notEqualSettings));
	}
}