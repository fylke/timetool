package persistency.settings;

import java.io.File;
import java.io.FileNotFoundException;

import persistency.PersistencyException;

public interface SettingsFileReader {
	void populate(final Settings settings, final File absPath) throws FileNotFoundException, PersistencyException;
}