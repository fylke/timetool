package persistency.projects;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProjectSetWriterTest {
  private static ProjectsFactory pf;
  private static ByteArrayOutputStream baos;
  private static ProjectSetWriter psw;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
    pf = new ProjectsFactory();
    baos = new ByteArrayOutputStream();
    psw = new ProjectSetWriter();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		baos.close();
	}

	@Test
	public final void testWriteProjectSet() throws Exception {
    final int projSetId = 1;
    final int nrOfComps = 1;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 0;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
    final ProjectSet projSetInput = pf.getProjSetWithConfig(projSetConfig);
    final String projSetKey = pf.getXmlProjSetWithConfig(projSetConfig);

    psw.writeProjectSet(projSetInput, baos);

    assertEquals("Generated test string and key string not equal!", 
                 projSetKey, baos.toString());
	}
}