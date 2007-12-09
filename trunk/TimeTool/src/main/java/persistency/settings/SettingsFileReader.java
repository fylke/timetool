package persistency.settings;

import java.io.FileNotFoundException;

import persistency.PersistencyException;

public interface SettingsFileReader {
	void populate(final Settings settings, final String filename) throws FileNotFoundException, PersistencyException;
}