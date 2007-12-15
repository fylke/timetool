package persistency;

import java.io.File;
import java.util.prefs.Preferences;

public final class PersistencyUtils {
	private final Preferences userPrefs = Preferences.userRoot().node("/timetool");

	/**
	 * Gets a reference to the directory where TimeTool files are stored.
	 *
	 * @return the reference to the directory to store files in
	 */
	public File getStorageDir() {
		return new File(userPrefs.get("storage_dir", System.getProperty("user.home") +
																									File.separator + ".timetool"));
	}

	/**
	 * Sets the directory where TimeTool files should be stored.
	 *
	 * @param dir the path to the directory
	 */
	public void setStorageDir(final File dir) {
		userPrefs.put("storage_dir", dir.getAbsolutePath());
	}
}