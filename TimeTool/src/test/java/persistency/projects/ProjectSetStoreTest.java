package persistency.projects;

import static org.junit.Assert.assertEquals;
import static persistency.PersistencyTestUtils.readFile;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import persistency.PersistencyUtils;

public class ProjectSetStoreTest {
	private static final PersistencyUtils PU = new PersistencyUtils();
	private static final ProjectsFactory PF = new ProjectsFactory();

	private File absPath;

	@After
	public void tearDown() throws Exception {
		absPath.delete();
	}

	@Test
	public final void testStoreSingleCompany() throws Exception {
		final int projSetId = 1;
		final int nrOfComps = 1;
		final int nrOfProjsPerComp = 1;
		final int nrOfActsPerProj = 1;
		final int projDepth = 0;
		final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
																													nrOfProjsPerComp,
																													nrOfActsPerProj,
																													projDepth);

		final String projSetKey = PF.getXmlProjSetWithConfig(projSetConfig);
		final ProjectSet testProjSet = PF.getProjSetWithConfig(projSetConfig);
		absPath = new File(PU.getStorageDir(), testProjSet.getFilename());

		testProjSet.store();

		assertEquals("Generated test string and key string not equal!",
								 projSetKey, readFile(absPath));
	}

	@Test
	public final void testStoreMultipleCompanies() throws Exception {
		final int projSetId = 1;
		final int nrOfComps = 3;
		final int nrOfProjsPerComp = 1;
		final int nrOfActsPerProj = 1;
		final int projDepth = 0;
		final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
																													nrOfProjsPerComp,
																													nrOfActsPerProj,
																													projDepth);

		final String projSetKey = PF.getXmlProjSetWithConfig(projSetConfig);
		final ProjectSet testProjSet = PF.getProjSetWithConfig(projSetConfig);
		absPath = new File(PU.getStorageDir(), testProjSet.getFilename());

		testProjSet.store();

		assertEquals("Generated test string and key string not equal!",
								 projSetKey, readFile(absPath));
	}

	@Test
	public final void testStoreWithDeepNesting() throws Exception {
		final int projSetId = 1;
		final int nrOfComps = 7;
		final int nrOfProjsPerComp = 5;
		final int nrOfActsPerProj = 4;
		final int projDepth = 3;
		final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
																													nrOfProjsPerComp,
																													nrOfActsPerProj,
																													projDepth);

		final String projSetKey = PF.getXmlProjSetWithConfig(projSetConfig);
		final ProjectSet testProjSet = PF.getProjSetWithConfig(projSetConfig);
		absPath = new File(PU.getStorageDir(), testProjSet.getFilename());

		testProjSet.store();

		assertEquals("Generated test string and key string not equal!",
								 projSetKey, readFile(absPath));
	}
}