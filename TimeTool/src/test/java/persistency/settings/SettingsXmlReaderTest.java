package persistency.settings;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SettingsXmlReaderTest {
	private static final String FILE_NAME = "test_settings.xml";
	private static final SettingsFactory SF = new SettingsFactory();

	private static File absPath;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		absPath = new File(System.getProperty("user.home") + File.separator + FILE_NAME);
		final PrintWriter pw = new PrintWriter(new FileOutputStream(absPath));

		pw.write(SF.getXmlSettings());
		pw.close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		absPath.delete();
	}

	@Test
	public final void testPopulate() throws Exception {
		final SettingsXmlReader sxr = new SettingsXmlReader();
		Settings user = SF.getSettings();
		sxr.populate(user, absPath);
		assertEquals("Read settings didn't match the expected ones!", SF.getSettings(), user);
	}
}
