package persistency;

import java.io.FileNotFoundException;

public interface Persistable {
	void store() throws PersistencyException;
	void populate() throws PersistencyException, FileNotFoundException;
}
