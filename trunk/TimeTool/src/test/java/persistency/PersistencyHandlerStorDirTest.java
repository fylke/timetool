package persistency;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.prefs.Preferences;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
  	final File expected = new File(System.getProperty("user.home") + File.separator + ".timetool");
  	final File result = ph.getStorageDir();
  	
    assertEquals("The default dir gotten back was not the expected one!", expected, result);
  }
  
  @Test
  public final void testSetAndGetStorageDir() throws Exception {
    final File testDir = new File(System.getProperty("user.home") + File.separator + "test");
    ph.setStorageDir(testDir);
    
    final File result = ph.getStorageDir();
    assertEquals("The dir set and dir gotten back were not equal!", testDir, result);
  }
}