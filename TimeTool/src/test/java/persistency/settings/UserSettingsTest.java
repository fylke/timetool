package persistency.settings;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import persistency.projects.Company;
import persistency.projects.ProjSetConfig;
import persistency.projects.ProjectSet;
import persistency.projects.ProjectsFactory;
import persistency.settings.UserSettings.OvertimeType;

public class UserSettingsTest {
	private static final SettingsFactory SF = new SettingsFactory();

	@Test
	public final void testGetXml() {
		final UserSettings testSettings = SF.getSettings();

		assertEquals("Gotten settings didn't match the expected ones!", SF.getXmlSettings(), testSettings.getXml());
	}

	@Test
	public final void testGetAndSetNamespace() {
		String testNs = "testNameSpace";
		final UserSettings testSettings = SF.getSettings();
		testSettings.setNamespace(testNs);

		assertEquals("Gotten namespace didn't match the one set!", testNs, testSettings.getNamespace());
	}

	@Test
	public final void testGetAndSetFirstName() {
		String testName = "testName";
		final UserSettings testSettings = SF.getSettings();
		testSettings.setFirstName(testName);

		assertEquals("Gotten name didn't match the one set!", testName, testSettings.getFirstName());
	}

	@Test
	public final void testGetAndSetLastName() {
		String testName = "testName";
		final UserSettings testSettings = SF.getSettings();
		testSettings.setLastName(testName);

		assertEquals("Gotten name didn't match the one set!", testName, testSettings.getLastName());
	}

	@Test
	public final void testGetAndSetEmployerId() {
		int testEmpId = 9;
		String testEmpStringId = "9";
		final UserSettings testSettings = SF.getSettings();

		testSettings.setEmployerId(testEmpId);
		assertEquals("Gotten employer id didn't match the one set!", testEmpId, testSettings.getEmployerId());

		testSettings.setEmployerId(testEmpStringId);
		assertEquals("Gotten employer id didn't match the one set!", testEmpId, testSettings.getEmployerId());
	}

	@Test
	public final void testGetEmployedAt() {
		ProjSetConfig config = new ProjSetConfig(1, 1, 0, 0, 0);
		ProjectsFactory pf = new ProjectsFactory();
		Company testComp = pf.getCompany(1, config);
		int testCompId = testComp.getId();

		ProjectSet ps = new ProjectSet();
		ps.addCompany(testComp);
		final UserSettings testSettings = SF.getSettings();
		testSettings.setProjectSet(ps);
		testSettings.setEmployerId(testCompId);

		assertEquals("Gotten employer didn't match the one set!", testComp, testSettings.getEmployedAt());
	}

	@Test
	public final void testGetAndSetLunchBreak() {
		int testLunchBreak = 9;
		String testLunchBreakString = "9";
		final UserSettings testSettings = SF.getSettings();

		testSettings.setLunchBreak(testLunchBreak);
		assertEquals("Gotten lunch break didn't match the one set!", testLunchBreak, testSettings.getLunchBreak());

		testSettings.setLunchBreak(testLunchBreakString);
		assertEquals("Gotten lunch break didn't match the one set!", testLunchBreak, testSettings.getLunchBreak());
	}

	@Test
	public final void testGetAndSetProjectSet() throws Exception {
		ProjSetConfig config = new ProjSetConfig(1, 1, 0, 0, 0);
		ProjectsFactory pf = new ProjectsFactory();
		ProjectSet testProjSet = pf.getProjSetWithConfig(config);

		final UserSettings testSettings = SF.getSettings();
		testSettings.setProjectSet(testProjSet);

		assertEquals("Gotten project set didn't match the one set!", testProjSet, testSettings.getProjectSet());
	}

	public final void testGetAndSetTreatOvertimeAs() {
		OvertimeType testOvertimeType = OvertimeType.COMP;
		String testOvertimeTypeString = "comp";
		final UserSettings testSettings = SF.getSettings();

		testSettings.setTreatOvertimeAs(testOvertimeType);
		assertEquals("Gotten overtime type didn't match the one set!", testOvertimeType, testSettings.getTreatOvertimeAs());

		testSettings.setTreatOvertimeAs(testOvertimeTypeString);
		assertEquals("Gotten overtime type didn't match the one set!", testOvertimeType, testSettings.getTreatOvertimeAs());
	}

	@Test
	public final void testGetAndSetProjectSetId() {
		int testProjSetId = 9;
		String testProjSetIdString = "9";
		final UserSettings testSettings = SF.getSettings();

		testSettings.setProjectSetId(testProjSetId);
		assertEquals("Gotten employer id didn't match the one set!", testProjSetId, testSettings.getProjectSetId());

		testSettings.setProjectSetId(testProjSetIdString);
		assertEquals("Gotten employer id didn't match the one set!", testProjSetId, testSettings.getProjectSetId());
	}
}