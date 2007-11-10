package persistency;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;
import java.util.prefs.Preferences;

import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;
import org.junit.After;
import org.junit.Before;
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

public final class PersistencyHandlerStorDirTest {
  private static PersistencyHandler ph;
  private static Preferences userPrefs;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    ph = PersistencyHandler.getInstance();
    userPrefs = Preferences.userRoot().node("/timetool");
  }

	@Before
	public void setUp() throws Exception {
		userPrefs.clear();
	}
	
	@After
	public void tearDown() throws Exception {
		userPrefs.clear();
	}
  
  @Test
  public final void testGetDefaultStorageDir() throws Exception {
  	final String expected = System.getProperty("user.home") + File.separator + ".timetool";
  	final String result = ph.getStorageDir();
  	
    assertEquals("The default dir gotten back was not the expected one!", expected, result);
  }
  
  @Test
  public final void testSetAndGetStorageDir() throws Exception {
    final String testDir = System.getProperty("user.home") + File.separator + "test";
    ph.setStorageDir(testDir);
    
    final String result = ph.getStorageDir();
    assertEquals("The dir set and dir gotten back were not equal!", testDir, result);
  }
}