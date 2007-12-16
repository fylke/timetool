package persistency.projects;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectSetXmlReaderTest {
	private static final String FILE_NAME = "test_projectSet.xml";
	private static final ProjectsFactory PF = new ProjectsFactory();
	private static final File ABS_PATH = new File(System.getProperty("user.home"), FILE_NAME);

	@After
	public void tearDown() throws Exception {
		ABS_PATH.delete();
	}

	@Test
	public final void testPopulateSingleCompany() throws Exception {
		final int projSetId = 1;
		final int nrOfComps = 1;
		final int nrOfProjsPerComp = 1;
		final int nrOfActsPerProj = 1;
		final int projDepth = 0;
		final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
																													nrOfProjsPerComp,
																													nrOfActsPerProj,
																													projDepth);

		final PrintWriter pw = new PrintWriter(new FileOutputStream(ABS_PATH));
		pw.write(PF.getXmlProjSetWithConfig(projSetConfig));
		pw.close();

		final ProjectSet projSetKey = PF.getProjSetWithConfig(projSetConfig);
		final ProjectSetXmlReader testProjSetXmlReader = new ProjectSetXmlReader();

		final ProjectSet testProjSet = new ProjectSet();
		testProjSetXmlReader.populate(testProjSet, ABS_PATH);
		assertEquals("Read proj set doesn't match the expected one!", projSetKey, testProjSet);
	}

	@Test
	public final void testPopulateMultipleCompanies() throws Exception {
		final int projSetId = 1;
		final int nrOfComps = 4;
		final int nrOfProjsPerComp = 1;
		final int nrOfActsPerProj = 1;
		final int projDepth = 0;
		final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
																													nrOfProjsPerComp,
																													nrOfActsPerProj,
																													projDepth);

		final PrintWriter pw = new PrintWriter(new FileOutputStream(ABS_PATH));
		pw.write(PF.getXmlProjSetWithConfig(projSetConfig));
		pw.close();

		final ProjectSet projSetKey = PF.getProjSetWithConfig(projSetConfig);
		final ProjectSetXmlReader testProjSetXmlReader = new ProjectSetXmlReader();

		final ProjectSet testProjSet = new ProjectSet();
		testProjSetXmlReader.populate(testProjSet, ABS_PATH);
		assertEquals("Read proj set doesn't match the expected one!", projSetKey, testProjSet);
	}
}