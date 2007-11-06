package persistency.projects;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import persistency.MockHandler;

public class ProjectHandlerTest {
  private transient ProjectsFactory tpf;
  private transient Reader projectInput;
  private transient XMLReader reader;
  private transient MockHandler testHandler;
  private transient Project testProject;
  private transient ProjectHandler projectHandler;
  private transient final int testProjId = 10;
  private transient final String ns = "";

  @Before
  public void setUp() throws Exception {
    tpf = ProjectsFactory.getInstance();
    
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
    testHandler = new MockHandler(reader);
    
    reader.setContentHandler(testHandler);
    
    final AttributesImpl dummyAttr = new AttributesImpl();
    projectHandler = new ProjectHandler(dummyAttr, reader, testHandler, 
                                        testProject, ns);
  }

  @After
  public void tearDown() throws Exception {
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
    StringBuilder sb = new StringBuilder();
        
    final String simpleTestProject = 
      tpf.getXmlProject(testProjId, projDepth, projSetConfig, sb);
    final Project simpleProjectKey = 
      tpf.getProject(testProjId, projDepth, projSetConfig);
    
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
    StringBuilder sb = new StringBuilder();

    final String nestedTestProject = 
      tpf.getXmlProject(testProjId, projDepth, projSetConfig, sb);
    final Project nestedProjectKey = 
      tpf.getProject(testProjId, projDepth, projSetConfig);
    
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
    StringBuilder sb = new StringBuilder();
        
    final String nestedTestProject = 
      tpf.getXmlProject(testProjId, projDepth, projSetConfig, sb);
    final Project nestedProjectKey = 
      tpf.getProject(testProjId, projDepth, projSetConfig);
    
    projectInput = new StringReader(nestedTestProject); 
    
    testHandler.setHandlerToTest(projectHandler);
    
    reader.parse(new InputSource(projectInput));

    assertEquals("Generated test object and key not equal!", 
                 nestedProjectKey, testProject); 
  }
}