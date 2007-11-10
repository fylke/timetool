package persistency;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import persistency.projects.ProjSetConfig;
import persistency.projects.ProjectSet;
import persistency.projects.ProjectsFactory;

public class FileWriterTest {
	static ProjectsFactory pf;
	static PersistencyHandler ph;
	static FileWriter fw;
	static File storDir;
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
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
		ps = pf.getProjSetWithConfig(projSetConfig);
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

	public void testReadProjSetContents() throws Exception {
		//TODO: Write a projSet to file
		fw.readProjSetContents(ps);
	}
	
	public void testProjSetWrite() throws Exception {
		File projSetFile = new File(storDir + "projectSet.xml");
		assertTrue("No file created!", projSetFile.exists());
	}
}
