package persistency;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistency.projects.Company;
import persistency.projects.Project;
import persistency.projects.Projects;
import persistency.projects.TestProjectsFactory;
import persistency.year.TestYearFactory;
import persistency.year.Year;
import persistency.year.YearConfig;

public class PersistencyHandlerTest {
  private transient TestProjectsFactory tpf;
  private transient TestYearFactory tyf;

  @Before
  public void setUp() throws Exception {
    tpf = TestProjectsFactory.getInstance();
    tyf = TestYearFactory.getInstance();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public final void testReadProjectsSingleCompany() throws Exception {
    readProjectsWithNCompanies(1);
  }
  
  @Test
  public final void testReadProjectsMultipleCompanies() throws Exception {
    readProjectsWithNCompanies(4);
  }
  
  @Test
  public final void testWriteProjectsSingleCompany() throws Exception {
    final Projects projects = tpf.getProjectsWithNCompanies(1);
    final String projectsOutput = tpf.getXmlProjectsWithNCompanies(1);
    final ByteArrayOutputStream projectsStream = new ByteArrayOutputStream();
    
    final PersistencyHandler ph = new PersistencyHandler();
    ph.writeProjects(projects, projectsStream);
    
    assertEquals(projectsOutput, projectsStream.toString());
  }
  
  @Test
  public final void testWriteProjectsMultipleCompanies() throws Exception {
    final Projects projects = tpf.getProjectsWithNCompanies(3);
    final String projectsOutput = tpf.getXmlProjectsWithNCompanies(3);
    final ByteArrayOutputStream projectsStream = new ByteArrayOutputStream();
    
    final PersistencyHandler ph = new PersistencyHandler();
    ph.writeProjects(projects, projectsStream);
    
    assertEquals(projectsOutput, projectsStream.toString());
  }

  @Test
  public final void testWriteProjectsWithDeepNesting() throws Exception {
    final Projects projects = tpf.getCompaniesWithNestedProjects(2, 3);
    final String projectsOutput = tpf.getXmlCompaniesWithNestedProjects(2, 3);
    final ByteArrayOutputStream projectsStream = new ByteArrayOutputStream();
    
    final PersistencyHandler ph = new PersistencyHandler();
    ph.writeProjects(projects, projectsStream);
    
    assertEquals(projectsOutput, projectsStream.toString());
  }
  
  @Test
  public final void testReadYearSimple() throws Exception {
    int year = 1;
    short nrOfMonths = 1;
    short nrOfDaysEachMonth = 1;
    int nrOfActsEachDay = 1; 
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay);
    
    final Year yearKey = tyf.getYearWithConfig(yearConfig);
    final String testYearString = tyf.getXmlYearWithConfig(yearConfig);
    final ByteArrayInputStream testYearStream = 
      new ByteArrayInputStream(testYearString.getBytes("UTF-8"));
    
    final PersistencyHandler ph = new PersistencyHandler();
    final Year testYear = ph.readYear(testYearStream);
    
    assertEquals("Generated test object and key not equal!", testYear, yearKey);
  }
  
  @Test
  public final void testReadYearHarder() throws Exception {
    int year = 1;
    short nrOfMonths = 6;
    short nrOfDaysEachMonth = 6;
    int nrOfActsEachDay = 6; 
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay);
    
    final Year yearKey = tyf.getYearWithConfig(yearConfig);
    final String testYearString = tyf.getXmlYearWithConfig(yearConfig);
    final ByteArrayInputStream testYearStream = 
      new ByteArrayInputStream(testYearString.getBytes("UTF-8"));
    
    final PersistencyHandler ph = new PersistencyHandler();
    final Year testYear = ph.readYear(testYearStream);
    
    assertEquals("Generated test object and key not equal!", testYear, yearKey);
  }
  
  @Test
  public final void testWriteYearSimple() throws Exception {
    int year = 1;
    short nrOfMonths = 1;
    short nrOfDaysEachMonth = 1;
    int nrOfActsEachDay = 1; 
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay);
    
    final Year yearToWrite = tyf.getYearWithConfig(yearConfig);
    final String yearOutputKey = tyf.getXmlYearWithConfig(yearConfig);
    final ByteArrayOutputStream yearStream = new ByteArrayOutputStream();    
    
    final PersistencyHandler ph = new PersistencyHandler();
    ph.writeYear(yearToWrite, yearStream);
    
    assertEquals("Generated test stream and output key not equal!", 
                 yearOutputKey, yearStream.toString());
  }
  
  private final void readProjectsWithNCompanies(final int n) throws Exception {
    final String projectsString = tpf.getXmlProjectsWithNCompanies(n);
    final ByteArrayInputStream projectsStream = 
      new ByteArrayInputStream(projectsString.getBytes("UTF-8"));
    
    final PersistencyHandler ph = new PersistencyHandler();
    final Projects projects = ph.readProjects(projectsStream);
    
    for (int i = 1; i <= projects.getCompanies().size(); i++) {    
      final Company company = projects.getCompanies().get(i - 1);
       
      assertEquals("Company name mismatch!", 
                   TestProjectsFactory.compName + i, 
                   company.getName());

      assertEquals("Company employee ID mismatch!", 
                   TestProjectsFactory.empId + i, 
                   company.getEmployeeId());
  
      for (int j = 0; j < company.getProjects().size(); j++) {
        final Project project = company.getProjects().get(j);
        
        assertEquals("Project name mismatch!", 
                     TestProjectsFactory.projName + i + j, 
                     project.getName());
    
        assertEquals("Project short name mismatch!", 
                     TestProjectsFactory.projShortName + i + j,
                     project.getShortName());

        assertEquals("Project code mismatch!", 
                     TestProjectsFactory.projCode + i + j,
                     project.getCode());
      }
    }
  }
}