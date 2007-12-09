package persistency.projects;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.After;
import org.junit.Test;

import persistency.PersistencyUtils;

public class ProjectSetStoreTest {
	private static final String FILE_NAME = "projectSet.xml";
	private static final PersistencyUtils PU = new PersistencyUtils();
	private static final File absPath = new File(PU.getStorageDir() + File.separator + FILE_NAME);
  private static final ProjectsFactory PF = new ProjectsFactory();

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
    testProjSet.store();

    assertEquals("Generated test string and key string not equal!",
                 projSetKey, readFile());
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
    testProjSet.store();

    assertEquals("Generated test string and key string not equal!",
                 projSetKey, readFile());
  }

  @Test
  public final void tesStoreWithDeepNesting() throws Exception {
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
    testProjSet.store();

    assertEquals("Generated test string and key string not equal!",
                 projSetKey, readFile());
  }

  private String readFile() throws Exception {
  	BufferedReader br = new BufferedReader(new FileReader(absPath));

  	String line;
    StringBuilder sb = new StringBuilder();
    while ((line = br.readLine()) != null) {
    	sb.append(line);
    	sb.append("\n");
    }

    // Removing the last newline before returning.
    return sb.subSequence(0, sb.length() - 1).toString();
  }
}