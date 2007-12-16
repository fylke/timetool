package persistency;

import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class JarResolver implements EntityResolver {
	@Override
	public InputSource resolveEntity(final String publicId,
																	 final String systemId) {
		if ("projectSet.dtd".equals(systemId)) {
			final InputStream is =
				getClass().getResourceAsStream("projectSet.dtd");
			return new InputSource(is);
		} else if ("year.dtd".equals(systemId)) {
			final InputStream is =
				getClass().getResourceAsStream("year.dtd");
			return new InputSource(is);
		} else if ("settings.dtd".equals(systemId)) {
			final InputStream is =
				getClass().getResourceAsStream("settings.dtd");
			return new InputSource(is);
		}
		return null;
	}
}
