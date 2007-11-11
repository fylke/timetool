package persistency;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import persistency.projects.ProjSetConfig;
import persistency.projects.ProjectSet;
import persistency.projects.ProjectsFactory;

public class FileHandlerTest {
	static ProjectsFactory pf;
	static PersistencyHandler ph;
	static FileHandler fh;
	static File storDir;
	static ProjSetConfig pc;
	static ProjectSet ps;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pf = new ProjectsFactory();
		ph = PersistencyHandler.getInstance();
		storDir = ph.getStorageDir();
		
    final int projSetId = 1;
    final int nrOfComps = 1;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 0;
    pc = new ProjSetConfig(projSetId, nrOfComps, nrOfProjsPerComp,
                           nrOfActsPerProj, projDepth);
		ps = pf.getProjSetWithConfig(pc);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public void testReadStoredProjSet() throws Exception {
		final File projSetFile = new File(storDir + "projectSet.xml");
		final String projSet = pf.getXmlProjSetWithConfig(pc);
		
		final FileWriter fw = new FileWriter(projSetFile);
		fw.write(projSet);
		fw.close();
		
		final ProjectSet resultProjSet = fh.readStoredProjSet(projSetFile);
		
		assertEquals("The read ProjectSet is not equal to the expected one!", resultProjSet, ps);
	}
}
