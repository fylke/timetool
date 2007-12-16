package persistency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PersistencyTestUtils {
	public static String readFile(final File absPath) throws Exception {
		final BufferedReader br = new BufferedReader(new FileReader(absPath));

		String line;
		final StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		// Removing the last newline before returning.
		return sb.subSequence(0, sb.length() - 1).toString();
	}
}
