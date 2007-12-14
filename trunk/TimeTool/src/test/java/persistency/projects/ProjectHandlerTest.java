package persistency.projects;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import persistency.DummyHandler;

public class ProjectHandlerTest {
  private static ProjectsFactory pf;
  private static Reader projectInput;
  private static XMLReader reader;
  private static DummyHandler testHandler;
  private static Project testProject;
  private static ProjectHandler projectHandler;

  private static final int testProjId = 10;
  private static final String ns = "";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    pf = new ProjectsFactory();

    try {
      reader =
        XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
    }
    catch (final SAXException e) {
      try {
        reader = XMLReaderFactory.createXMLReader();
      } catch (final SAXException e1) {
        throw new NoClassDefFoundError("No SAX parser is available.");
      }
    }

    testProject = new Project();
    testHandler = new DummyHandler(reader);

    reader.setContentHandler(testHandler);

    final AttributesImpl dummyAttr = new AttributesImpl();
    projectHandler = new ProjectHandler(dummyAttr, reader, testHandler,
                                        testProject, ns);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    projectInput.close();
  }

  @Test
  public final void testProjectHandlerSimple() throws Exception {
    final int projSetId = 1; // These first three aren't relevant in this test.
    final int nrOfComps = 1;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 0;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
    final StringBuilder sb = new StringBuilder();

    final String simpleTestProject =
      pf.getXmlProject(testProjId, projDepth, projSetConfig, sb);
    final Project simpleProjectKey =
      pf.getProject(testProjId, projDepth, projSetConfig);

    projectInput = new StringReader(simpleTestProject);

    testHandler.setHandlerToTest(projectHandler);

    reader.parse(new InputSource(projectInput));

    assertEquals("Generated test object and key not equal!",
                 simpleProjectKey, testProject);
  }

  @Test
  public final void testProjectHandlerNested() throws Exception {
    final int projSetId = 1; // These first three aren't relevant in this test.
    final int nrOfComps = 1;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 1;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
    final StringBuilder sb = new StringBuilder();

    final String nestedTestProject =
      pf.getXmlProject(testProjId, projDepth, projSetConfig, sb);
    final Project nestedProjectKey =
      pf.getProject(testProjId, projDepth, projSetConfig);

    projectInput = new StringReader(nestedTestProject);

    testHandler.setHandlerToTest(projectHandler);

    reader.parse(new InputSource(projectInput));

    assertEquals("Generated test object and key not equal!",
                 nestedProjectKey, testProject);
  }

  @Test
  public final void testProjectHandlerDeeplyNested() throws Exception {
    final int projSetId = 1; // These first three aren't relevant in this test.
    final int nrOfComps = 1;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 3;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
    final StringBuilder sb = new StringBuilder();

    final String nestedTestProject =
      pf.getXmlProject(testProjId, projDepth, projSetConfig, sb);
    final Project nestedProjectKey =
      pf.getProject(testProjId, projDepth, projSetConfig);

    projectInput = new StringReader(nestedTestProject);

    testHandler.setHandlerToTest(projectHandler);

    reader.parse(new InputSource(projectInput));

    assertEquals("Generated test object and key not equal!",
                 nestedProjectKey, testProject);
  }
}