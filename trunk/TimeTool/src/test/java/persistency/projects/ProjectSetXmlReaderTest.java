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

	private static File absPath;

	@Before
	public void setUp() throws Exception {
		absPath = new File(System.getProperty("user.home") + File.separator + FILE_NAME);
	}

	@After
	public void tearDown() throws Exception {
		absPath.delete();
	}

	@Test
	public final void testPopulate() throws Exception {
    final int projSetId = 1;
    final int nrOfComps = 1;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 0;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);

    final PrintWriter pw = new PrintWriter(new FileOutputStream(absPath));
		pw.write(PF.getXmlProjSetWithConfig(projSetConfig));
		pw.close();

    final ProjectSet projSetKey = PF.getProjSetWithConfig(projSetConfig);
    final ProjectSetXmlReader testProjSetXmlReader = new ProjectSetXmlReader();

    ProjectSet testProjSet = new ProjectSet();
    testProjSetXmlReader.populate(testProjSet, absPath);
    assertEquals("Read proj set doesn't match the expected one!", projSetKey, testProjSet);
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

    final PrintWriter pw = new PrintWriter(new FileOutputStream(absPath));
		pw.write(PF.getXmlProjSetWithConfig(projSetConfig));
		pw.close();

    final ProjectSet projSetKey = PF.getProjSetWithConfig(projSetConfig);
    final ProjectSetXmlReader testProjSetXmlReader = new ProjectSetXmlReader();

    ProjectSet testProjSet = new ProjectSet();
    testProjSetXmlReader.populate(testProjSet, absPath);
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

    final PrintWriter pw = new PrintWriter(new FileOutputStream(absPath));
		pw.write(PF.getXmlProjSetWithConfig(projSetConfig));
		pw.close();

    final ProjectSet projSetKey = PF.getProjSetWithConfig(projSetConfig);
    final ProjectSetXmlReader testProjSetXmlReader = new ProjectSetXmlReader();

    ProjectSet testProjSet = new ProjectSet();
    testProjSetXmlReader.populate(testProjSet, absPath);
    assertEquals("Read proj set doesn't match the expected one!", projSetKey, testProjSet);
	}
}
