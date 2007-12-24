package persistency.projects;

import static org.junit.Assert.assertEquals;
import static persistency.PersistencyTestUtils.readFile;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import persistency.PersistencyUtils;

public class CompanyStoreTest {
	private static final PersistencyUtils PU = new PersistencyUtils();
	private static final CompanyFactory PF = new CompanyFactory();

	private File absPath;

	@After
	public void tearDown() throws Exception {
		absPath.delete();
	}

	@Test
	public final void testStoreSingleProjSingleAct() throws Exception {
		final int compId = 1;
		final int nrOfProjsPerComp = 1;
		final int nrOfActsPerComp = 1;
		final int projDepth = 0;
		final CompanyConfig compConfig = new CompanyConfig(compId,
																											 nrOfProjsPerComp,
																											 nrOfActsPerComp,
																											 projDepth);

		final String compKey = PF.getXmlCompany(compConfig);
		final Company testComp = PF.getCompany(compConfig);
		absPath = new File(PU.getStorageDir(), testComp.getFilename());

		testComp.store();

		assertEquals(compKey, readFile(absPath));
	}

	@Test
	public final void testStoreMultiProjMultiAct() throws Exception {
		final int compId = 1;
		final int nrOfProjsPerComp = 5;
		final int nrOfActsPerComp = 5;
		final int projDepth = 0;
		final CompanyConfig compConfig = new CompanyConfig(compId,
																											 nrOfProjsPerComp,
																											 nrOfActsPerComp,
																											 projDepth);

		final String compKey = PF.getXmlCompany(compConfig);
		final Company testComp = PF.getCompany(compConfig);
		absPath = new File(PU.getStorageDir(), testComp.getFilename());

		testComp.store();

		assertEquals(compKey, readFile(absPath));
	}

	@Test
	public final void testStoreWithDeepNestingProj() throws Exception {
		final int compId = 1;
		final int nrOfProjsPerComp = 5;
		final int nrOfActsPerComp = 5;
		final int projDepth = 5;
		final CompanyConfig compConfig = new CompanyConfig(compId,
																											 nrOfProjsPerComp,
																											 nrOfActsPerComp,
																											 projDepth);

		final String compKey = PF.getXmlCompany(compConfig);
		final Company testComp = PF.getCompany(compConfig);
		absPath = new File(PU.getStorageDir(), testComp.getFilename());

		testComp.store();

		assertEquals(compKey, readFile(absPath));
	}
}