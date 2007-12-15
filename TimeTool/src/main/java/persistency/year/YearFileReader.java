package persistency.year;

import java.io.File;
import java.io.FileNotFoundException;

import persistency.PersistencyException;

public interface YearFileReader {
	void populate(final Year year, final File absPath) throws FileNotFoundException, PersistencyException;
}
