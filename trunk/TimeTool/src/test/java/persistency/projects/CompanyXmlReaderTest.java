package persistency.projects;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Test;

public class CompanyXmlReaderTest {
	private static final String FILE_NAME = "test_company.xml";
	private static final CompanyFactory CF = new CompanyFactory();
	private static final File ABS_PATH = new File(System.getProperty("user.home"), FILE_NAME);

	@After
	public void tearDown() throws Exception {
		ABS_PATH.delete();
	}

	@Test
	public final void testPopulateCompanySingleProjSingleAct() throws Exception {
		final int compId = 1;
		final int nrOfProjsPerComp = 1;
		final int nrOfActsPerComp = 1;
		final int projDepth = 0;
		final CompanyConfig compConfig = new CompanyConfig(compId,
																											 nrOfProjsPerComp,
																											 nrOfActsPerComp,
																											 projDepth);

		final PrintWriter pw = new PrintWriter(new FileOutputStream(ABS_PATH));
		pw.write(CF.getXmlCompany(compConfig));
		pw.close();

		final Company compKey = CF.getCompany(compConfig);
		final CompanyXmlReader testCompXmlReader = new CompanyXmlReader();

		final Company testComp = new Company();
		testCompXmlReader.populate(testComp, ABS_PATH);
		assertEquals(compKey, testComp);
	}

	@Test
	public final void testPopulateCompanyMultiProjMultiAct() throws Exception {
		final int compId = 1;
		final int nrOfProjsPerComp = 5;
		final int nrOfActsPerComp = 5;
		final int projDepth = 0;
		final CompanyConfig compConfig = new CompanyConfig(compId,
																											 nrOfProjsPerComp,
																											 nrOfActsPerComp,
																											 projDepth);

		final PrintWriter pw = new PrintWriter(new FileOutputStream(ABS_PATH));
		pw.write(CF.getXmlCompany(compConfig));
		pw.close();

		final Company compKey = CF.getCompany(compConfig);
		final CompanyXmlReader testCompXmlReader = new CompanyXmlReader();

		final Company testComp = new Company();
		testCompXmlReader.populate(testComp, ABS_PATH);
		assertEquals(compKey, testComp);
	}

	@Test
	public final void testPopulateCompanyMultiProjDeepNesting() throws Exception {
		final int compId = 1;
		final int nrOfProjsPerComp = 5;
		final int nrOfActsPerComp = 1;
		final int projDepth = 5;
		final CompanyConfig compConfig = new CompanyConfig(compId,
																											 nrOfProjsPerComp,
																											 nrOfActsPerComp,
																											 projDepth);

		final PrintWriter pw = new PrintWriter(new FileOutputStream(ABS_PATH));
		pw.write(CF.getXmlCompany(compConfig));
		pw.close();

		final Company compKey = CF.getCompany(compConfig);
		final CompanyXmlReader testCompXmlReader = new CompanyXmlReader();

		final Company testComp = new Company();
		testCompXmlReader.populate(testComp, ABS_PATH);
		assertEquals(compKey, testComp);
	}
}