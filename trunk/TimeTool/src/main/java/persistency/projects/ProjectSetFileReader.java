package persistency.projects;

import java.io.File;
import java.io.FileNotFoundException;

import persistency.PersistencyException;

public interface ProjectSetFileReader {
	void populate(final ProjectSet projSet, final File absPath) throws FileNotFoundException, PersistencyException;
}
