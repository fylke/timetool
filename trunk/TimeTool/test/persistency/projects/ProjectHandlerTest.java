package persistency.projects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import persistency.projects.Project;
import persistency.projects.ProjectHandler;

public class ProjectHandlerTest {
  private transient TestProjectsFactory tpf;
  private transient Reader projectInput;
  private transient XMLReader reader;
  private transient TestHandler testHandler;
  private transient Project testProject;
  private transient ProjectHandler projectHandler;  

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
    
    /* We can't really test the enclosing project ID as it is set 
     * before entering the handler we're testing. */
    final int testProjId = 1;
    
    final AttributesImpl attr = new AttributesImpl();
    attr.addAttribute("", "", "id", "String", Integer.toString(testProjId));
    
    testProject = new Project();
    testHandler = new TestHandler(reader);
    
    reader.setContentHandler(testHandler);
    
    projectHandler = new ProjectHandler(attr, reader, testHandler, testProject);
  }

  @After
  public void tearDown() throws Exception {
    projectInput.close();
  }

  @Test
  public final void testProjectHandlerSimple() throws Exception {
    StringBuilder sb = new StringBuilder();
    final String singleProject = tpf.getXmlProject(10, 0, sb);
    projectInput = new StringReader(singleProject); 
    
    testHandler.setHandlerToTest(projectHandler);
    
    reader.parse(new InputSource(projectInput));

    assertEquals("Project name mismatch!", 
        TestProjectsFactory.projName + 10,
        testProject.getName());
    
    assertEquals("Project short name mismatch!", 
                 TestProjectsFactory.projShortName + 10,
                 testProject.getShortName());
    
    assertEquals("Project code mismatch!", 
                 TestProjectsFactory.projCode + 10, 
                 testProject.getCode());
    
    assertNull("Shouldn't be any subprojects in this project!",
               testProject.getSubProjects());
  }
  
  @Test
  public final void testProjectHandlerNested() throws Exception {
    StringBuilder sb = new StringBuilder();
    final String nestedProject = tpf.getXmlProject(10, 1, sb);
   
    projectInput = new StringReader(nestedProject);

    testHandler.setHandlerToTest(projectHandler);
    
    reader.parse(new InputSource(projectInput));
    
    assertEquals("Project short name mismatch!", 
                 TestProjectsFactory.projShortName + 10,
                 testProject.getShortName());
    
    assertEquals("Project code mismatch!", 
                 TestProjectsFactory.projCode + 10, 
                 testProject.getCode());
    
    final Project subProject = testProject.getSubProjects().get(0);

    assertEquals("SubProject name mismatch!", 
                 TestProjectsFactory.projName + 100, 
                 subProject.getName());

    assertEquals("SubProject short name mismatch!", 
                 TestProjectsFactory.projShortName + 100, 
                 subProject.getShortName());

    assertEquals("SubProject code mismatch!", 
                 TestProjectsFactory.projCode + 100, 
                 subProject.getCode());
    
    assertNull("Shouldn't be any subprojects in this project!",
               subProject.getSubProjects());
  }
  
  @Test
  public final void testProjectHandlerDeeplyNested() throws Exception {
    StringBuilder sb = new StringBuilder();
    final String nestedProject = tpf.getXmlProject(10, 2, sb);
   
    projectInput = new StringReader(nestedProject);

    testHandler.setHandlerToTest(projectHandler);
    
    reader.parse(new InputSource(projectInput));
    
    assertEquals("Project short name mismatch!", 
                 TestProjectsFactory.projShortName + 10,
                 testProject.getShortName());
    
    assertEquals("Project code mismatch!", 
                 TestProjectsFactory.projCode + 10, 
                 testProject.getCode());
    
    final Project subProject = testProject.getSubProjects().get(0);

    assertEquals("SubProject name mismatch!", 
                 TestProjectsFactory.projName + 100, 
                 subProject.getName());

    assertEquals("SubProject short name mismatch!", 
                 TestProjectsFactory.projShortName + 100, 
                 subProject.getShortName());

    assertEquals("SubProject code mismatch!", 
                 TestProjectsFactory.projCode + 100, 
                 subProject.getCode());
    
    final Project subSubProject = subProject.getSubProjects().get(0);
    
    assertEquals("SubSubProject name mismatch!", 
                 TestProjectsFactory.projName + 1000, 
                 subSubProject.getName());

    assertEquals("SubSubProject short name mismatch!", 
                 TestProjectsFactory.projShortName + 1000, 
                 subSubProject.getShortName());

    assertEquals("SubSubProject code mismatch!", 
                 TestProjectsFactory.projCode + 1000, 
                 subSubProject.getCode());
    
    assertNull("Shouldn't be any subprojects in this project!",
               subSubProject.getSubProjects());
  }
}