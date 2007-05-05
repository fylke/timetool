package persistency;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.TreeSet;


import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistency.projects.ProjSetConfig;
import persistency.projects.ProjectSet;
import persistency.projects.TestProjectsFactory;
import persistency.settings.Settings;
import persistency.settings.TestSettingsFactory;
import persistency.year.SearchControl;
import persistency.year.TestYearFactory;
import persistency.year.Year;
import persistency.year.YearConfig;

public final class PersistencyHandlerTest {
  private transient TestProjectsFactory tpf;
  private transient TestYearFactory tyf;
  private transient TestSettingsFactory tsf;
  private transient PersistencyHandler ph;
  private transient ByteArrayInputStream bais;
  private transient ByteArrayOutputStream baos;

  @Before
  public void setUp() throws Exception {
    tpf = TestProjectsFactory.getInstance();
    tyf = TestYearFactory.getInstance();
    tsf = TestSettingsFactory.getInstance();
    ph = PersistencyHandler.getInstance();
    baos = new ByteArrayOutputStream();
  }

  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public final void testReadYearWithSearchControlSimple() throws Exception {
    int year = 1;
    short nrOfMonths = 1;
    short nrOfDaysEachMonth = 10;
    int nrOfActsEachDay = 0;
    SearchControl searchControl = new SearchControl();
    final int month = 1;
    final int day = 5;
    final int hours = 0;
    final int minutes = 0;
    final int seconds = 0;
    final int millis = 0;
    DateTime dateToFind = new DateTime(year, month, day, hours, 
                                       minutes, seconds, millis);
    Set<ReadableDateTime> dates = new TreeSet<ReadableDateTime>();
    dates.add(dateToFind);
    searchControl.setDates(dates);
    
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay, searchControl);
        
    final Year yearKey = tyf.getYearWithConfig(yearConfig);
    final String testYearString = tyf.getXmlYearWithConfig(yearConfig);
    final ByteArrayInputStream testYearStream = 
      new ByteArrayInputStream(testYearString.getBytes("UTF-8"));
    
    final Year testYear = ph.readYear(testYearStream, searchControl);
    
    assertEquals("Generated test object and key not equal!", testYear, yearKey);
  }

  @Test
  public final void testReadSettings() throws Exception {
    final String settingsString = tsf.getXmlSettings();
    final Settings userSettingsKey = tsf.getUserSettings();
    
    bais = new ByteArrayInputStream(settingsString.getBytes("UTF-8"));
    final Settings testUserSettings = ph.readUserSettings(bais);
  
    assertEquals("Generated test stream and output key not equal!", 
                 testUserSettings, userSettingsKey);
  }
  
  @Test
  public final void testWriteSettings() throws Exception {
    final Settings userSettingsInput = tsf.getUserSettings();
    final String settingsKey = tsf.getXmlSettings();

    ph.writeUserSettings(userSettingsInput, baos);
    
    assertEquals("Generated test string and key string not equal!", 
                 settingsKey, baos.toString());
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
    SearchControl searchControl = null; // We won't bother with SearchControl
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay, searchControl);
    
    final Year yearKey = tyf.getYearWithConfig(yearConfig);
    final String testYearString = tyf.getXmlYearWithConfig(yearConfig);
    final ByteArrayInputStream testYearStream = 
      new ByteArrayInputStream(testYearString.getBytes("UTF-8"));
    
    final Year testYear = ph.readYear(testYearStream, searchControl);
    
    assertEquals("Generated test object and key not equal!", testYear, yearKey);
  }
  
  @Test
  public final void testReadYearHarder() throws Exception {
    int year = 1;
    short nrOfMonths = 6;
    short nrOfDaysEachMonth = 6;
    int nrOfActsEachDay = 6;
    SearchControl searchControl = null; // We won't bother with SearchControl
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay, searchControl);
    
    final Year yearKey = tyf.getYearWithConfig(yearConfig);
    final String testYearString = tyf.getXmlYearWithConfig(yearConfig);
    final ByteArrayInputStream testYearStream = 
      new ByteArrayInputStream(testYearString.getBytes("UTF-8"));
    
    final Year testYear = ph.readYear(testYearStream, searchControl);
    
    assertEquals("Generated test object and key not equal!", testYear, yearKey);
  }
  
  @Test
  public final void testWriteYearSimple() throws Exception {
    int year = 1;
    short nrOfMonths = 1;
    short nrOfDaysEachMonth = 1;
    int nrOfActsEachDay = 1; 
    SearchControl searchControl = null; // Makes no sense when writing
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay, searchControl);
    
    final Year yearToWrite = tyf.getYearWithConfig(yearConfig);
    final String yearOutputKey = tyf.getXmlYearWithConfig(yearConfig);
    final ByteArrayOutputStream yearStream = new ByteArrayOutputStream();    
    
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
    SearchControl searchControl = null; // Makes no sense when writing
    YearConfig yearConfig = new YearConfig(year, nrOfMonths, nrOfDaysEachMonth, 
                                           nrOfActsEachDay, searchControl);
    
    final Year yearToWrite = tyf.getYearWithConfig(yearConfig);
    final String yearOutputKey = tyf.getXmlYearWithConfig(yearConfig);
    final ByteArrayOutputStream yearStream = new ByteArrayOutputStream();    
    
    ph.writeYear(yearToWrite, yearStream);
    
    assertEquals("Generated test stream and output key not equal!", 
                 yearOutputKey, yearStream.toString());
  }
  
  @Test
  public final void testSetAndGetStorageDir() throws Exception {
    final String home = System.getProperty("user.home");
    ph.setStorageDir(home);
    
    final String result = ph.getStorageDir();
    
    assertEquals("The dir set and dir gotten back were not equal!", 
                 home, result);
  }
}