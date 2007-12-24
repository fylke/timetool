package persistency.company;

import java.io.File;
import java.io.FileNotFoundException;

import persistency.PersistencyException;

public interface CompanyFileReader {
	void populate(final Company comp, final File absPath) throws FileNotFoundException, PersistencyException;
}
