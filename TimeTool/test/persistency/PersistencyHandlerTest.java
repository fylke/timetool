package persistency;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistency.projects.ProjSetConfig;
import persistency.projects.ProjectSet;
import persistency.projects.TestProjectsFactory;
import persistency.year.TestYearFactory;
import persistency.year.Year;
import persistency.year.YearConfig;

public class PersistencyHandlerTest {
  private transient TestProjectsFactory tpf;
  private transient TestYearFactory tyf;
  private transient PersistencyHandler ph;
  private transient ByteArrayInputStream bais;
  private transient ByteArrayOutputStream baos;

  @Before
  public void setUp() throws Exception {
    tpf = TestProjectsFactory.getInstance();
    tyf = TestYearFactory.getInstance();
    ph = new PersistencyHandler();
    baos = new ByteArrayOutputStream();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public final void testReadProjSetSingleCompany() throws Exception {
    final int projSetId = 1;
    final int nrOfComps = 1;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 0;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
    final String projSetString = tpf.getXmlProjSetWithConfig(projSetConfig);
    final ProjectSet projSetKey = tpf.getProjSetWithConfig(projSetConfig);
    
    bais = new ByteArrayInputStream(projSetString.getBytes("UTF-8"));
    final ProjectSet testProjectSet = ph.readProjectSet(bais);
  
    assertEquals("Generated test stream and output key not equal!", 
                 testProjectSet, projSetKey);
  }
  
  @Test
  public final void testReadProjectSetMultipleCompanies() throws Exception {
    final int projSetId = 1;
    final int nrOfComps = 4;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 0;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
    final String projSetString = tpf.getXmlProjSetWithConfig(projSetConfig);
    final ProjectSet projSetKey = tpf.getProjSetWithConfig(projSetConfig);
    
    bais = new ByteArrayInputStream(projSetString.getBytes("UTF-8"));
    final ProjectSet testProjectSet = ph.readProjectSet(bais);
  
    assertEquals("Generated test stream and output key not equal!", 
                 testProjectSet, projSetKey);
  }
  
  @Test
  public final void testWriteProjectSetSingleCompany() throws Exception {
    final int projSetId = 1;
    final int nrOfComps = 1;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 0;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
    final ProjectSet projSetInput = tpf.getProjSetWithConfig(projSetConfig);
    final String projSetKey = tpf.getXmlProjSetWithConfig(projSetConfig);

    ph.writeProjectSet(projSetInput, baos);

    
    assertEquals("Generated test string and key string not equal!", 
                 projSetKey, baos.toString());
  }
  
  @Test
  public final void testWriteProjectSetMultipleCompanies() throws Exception {
    final int projSetId = 1;
    final int nrOfComps = 3;
    final int nrOfProjsPerComp = 1;
    final int nrOfActsPerProj = 1;
    final int projDepth = 0;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
    final ProjectSet projSetInput = tpf.getProjSetWithConfig(projSetConfig);
    final String projSetKey = tpf.getXmlProjSetWithConfig(projSetConfig);

    ph.writeProjectSet(projSetInput, baos);
    
    assertEquals("Generated test string and key string not equal!", 
                 projSetKey, baos.toString());
  }
  
  @Test
  public final void testWriteProjectSetWithDeepNesting() throws Exception {
    final int projSetId = 1;
    final int nrOfComps = 7;
    final int nrOfProjsPerComp = 5;
    final int nrOfActsPerProj = 4;
    final int projDepth = 3;
    final ProjSetConfig projSetConfig = new ProjSetConfig(projSetId, nrOfComps,
                                                          nrOfProjsPerComp,
                                                          nrOfActsPerProj,
                                                          projDepth);
    final ProjectSet projSetInput = tpf.getProjSetWithConfig(projSetConfig);
    final String projSetKey = tpf.getXmlProjSetWithConfig(projSetConfig);

    ph.writeProjectSet(projSetInput, baos);
    assertEquals("Generated test string and key string not equal!", 
                 projSetKey, baos.toString());
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
  
  @Test
  public final void testWriteYearHarder() throws Exception {
    int year = 1;
    short nrOfMonths = 6;
    short nrOfDaysEachMonth = 6;
    int nrOfActsEachDay = 6; 
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
}