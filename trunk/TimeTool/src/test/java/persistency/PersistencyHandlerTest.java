package persistency;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import persistency.projects.ProjSetConfig;
import persistency.projects.ProjectSet;
import persistency.projects.ProjectsFactory;
import persistency.settings.Settings;
import persistency.settings.SettingsFactory;
import persistency.year.SearchControl;
import persistency.year.Year;
import persistency.year.YearConfig;
import persistency.year.YearFactory;

public final class PersistencyHandlerTest {
  private ProjectsFactory pf;
  private YearFactory yf;
  private SettingsFactory sf;
  private PersistencyHandler ph;
  private ByteArrayInputStream bais;
  private ByteArrayOutputStream baos;

  @BeforeClass
  public void setUpBeforeClass() throws Exception {
    pf = new ProjectsFactory();
    yf = new YearFactory();
    sf = new SettingsFactory();
    ph = PersistencyHandler.getInstance();
    baos = new ByteArrayOutputStream();
  }

  @After
  public void tearDownAfterClass() throws Exception {
    baos.close();
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
        
    final Year yearKey = yf.getYearWithConfig(yearConfig);
    final String testYearString = yf.getXmlYearWithConfig(yearConfig);
    final ByteArrayInputStream testYearStream = 
      new ByteArrayInputStream(testYearString.getBytes("UTF-8"));
    
    final Year testYear = ph.readYear(testYearStream, searchControl);
    
    assertEquals("Generated test object and key not equal!", testYear, yearKey);
  }

  @Test
  public final void testReadSettings() throws Exception {
    final String settingsString = sf.getXmlSettings();
    final Settings userSettingsKey = sf.getUserSettings();
    
    bais = new ByteArrayInputStream(settingsString.getBytes("UTF-8"));
    final Settings testUserSettings = ph.readUserSettings(bais);
  
    assertEquals("Generated test stream and output key not equal!", 
                 testUserSettings, userSettingsKey);
  }
  
  @Test
  public final void testWriteSettings() throws Exception {
    final Settings userSettingsInput = sf.getUserSettings();
    final String settingsKey = sf.getXmlSettings();

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
    final String projSetString = pf.getXmlProjSetWithConfig(projSetConfig);
    final ProjectSet projSetKey = pf.getProjSetWithConfig(projSetConfig);

    bais = new ByteArrayInputStream(projSetString.getBytes("UTF-8"));
    final ProjectSet testProjSet = ph.readProjectSet(bais);

    assertEquals("Generated test stream and output key not equal!", 
                 testProjSet, projSetKey);
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
    final String projSetString = pf.getXmlProjSetWithConfig(projSetConfig);
    final ProjectSet projSetKey = pf.getProjSetWithConfig(projSetConfig);
    
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
    final ProjectSet projSetInput = pf.getProjSetWithConfig(projSetConfig);
    final String projSetKey = pf.getXmlProjSetWithConfig(projSetConfig);

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
    final ProjectSet projSetInput = pf.getProjSetWithConfig(projSetConfig);
    final String projSetKey = pf.getXmlProjSetWithConfig(projSetConfig);

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
    final ProjectSet projSetInput = pf.getProjSetWithConfig(projSetConfig);
    final String projSetKey = pf.getXmlProjSetWithConfig(projSetConfig);

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
    
    final Year yearKey = yf.getYearWithConfig(yearConfig);
    final String testYearString = yf.getXmlYearWithConfig(yearConfig);
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
    
    final Year yearKey = yf.getYearWithConfig(yearConfig);
    final String testYearString = yf.getXmlYearWithConfig(yearConfig);
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
    
    final Year yearToWrite = yf.getYearWithConfig(yearConfig);
    final String yearOutputKey = yf.getXmlYearWithConfig(yearConfig);
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
    
    final Year yearToWrite = yf.getYearWithConfig(yearConfig);
    final String yearOutputKey = yf.getXmlYearWithConfig(yearConfig);
    final ByteArrayOutputStream yearStream = new ByteArrayOutputStream();    
    
    ph.writeYear(yearToWrite, yearStream);
    
    assertEquals("Generated test stream and output key not equal!", 
                 yearOutputKey, yearStream.toString());
  }
  
  @Test
  public final void testSetAndGetStorageDir() throws Exception {
    final String testDir = System.getProperty("user.home") + File.separator + "test";
    ph.setStorageDir(testDir);
    
    final String result = ph.getStorageDir();
    
    assertEquals("The dir set and dir gotten back were not equal!", 
                 testDir, result);
  }
}