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

import persistency.TestHandler;

public class ProjectHandlerTest {
  private transient TestProjectsFactory tpf;
  private transient Reader projectInput;
  private transient XMLReader reader;
  private transient TestHandler testHandler;
  private transient Project testProject;
  private transient ProjectHandler projectHandler;
  private final transient int testProjId = 10;

  @Before
  public void setUp() throws Exception {
    tpf = TestProjectsFactory.getInstance();
    
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
    testProject.setId(testProjId);
    testHandler = new TestHandler(reader);
    
    reader.setContentHandler(testHandler);
    
    final AttributesImpl dummyAttr = new AttributesImpl();
    projectHandler = new ProjectHandler(dummyAttr, reader, testHandler, 
                                        testProject);
  }

  @After
  public void tearDown() throws Exception {
    projectInput.close();
  }

  @Test
  public final void testProjectHandlerSimple() throws Exception {
    final int projNestingDepth = 0;
    StringBuilder sb = new StringBuilder();
        
    final String simpleTestProject = 
      tpf.getXmlProject(testProjId, projNestingDepth, sb);
    final Project simpleProjectKey = 
      tpf.getProject(testProjId, projNestingDepth);
    
    projectInput = new StringReader(simpleTestProject); 
    
    testHandler.setHandlerToTest(projectHandler);
    
    reader.parse(new InputSource(projectInput));

    assertEquals("Generated test object and key not equal!", 
                 simpleProjectKey, testProject); 
  }
  
  @Test
  public final void testProjectHandlerNested() throws Exception {
    final int projNestingDepth = 1;
    StringBuilder sb = new StringBuilder();
        
    final String nestedTestProject = 
      tpf.getXmlProject(testProjId, projNestingDepth, sb);
    final Project nestedProjectKey = 
      tpf.getProject(testProjId, projNestingDepth);
   
    projectInput = new StringReader(nestedTestProject);

    testHandler.setHandlerToTest(projectHandler);
    
    reader.parse(new InputSource(projectInput));
    
    assertEquals("Generated test object and key not equal!", 
                 nestedProjectKey, testProject); 
  }
  
  @Test
  public final void testProjectHandlerDeeplyNested() throws Exception {
    final int projNestingDepth = 3;
    StringBuilder sb = new StringBuilder();
        
    final String nestedTestProject = 
      tpf.getXmlProject(testProjId, projNestingDepth, sb);
    final Project nestedProjectKey = 
      tpf.getProject(testProjId, projNestingDepth);
   
    projectInput = new StringReader(nestedTestProject);

    testHandler.setHandlerToTest(projectHandler);
    
    reader.parse(new InputSource(projectInput));
    
    assertEquals("Generated test object and key not equal!", 
                 nestedProjectKey, testProject); 
  }
}