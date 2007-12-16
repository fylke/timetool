package persistency.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import persistency.PersistencyException;
import persistency.XmlReader;

public class SettingsXmlReader extends XmlReader implements SettingsFileReader {
	/* (non-Javadoc)
	 * @see persistency.settings.SettingsReader#populate(persistency.settings.Settings)
	 */
	public void populate(final Settings settings, final File absPath)
			throws FileNotFoundException, PersistencyException {
		final XMLReader xmlReader = initXmlReader();

		final SettingsParser sp = new SettingsParser(xmlReader);
		sp.setTargetObject(settings);

		final Reader fr = new FileReader(absPath);
		try {
			sp.parse(new InputSource(fr));
		} catch (final SAXException e) {
			throw new PersistencyException("There was a parser problem when " +
																		 "reading the settings file. " +
																		 e.getMessage(), e);
		} catch (final ParserConfigurationException e) {
			throw new PersistencyException("There was a problem initializing " +
																		 "a parser while reading the settings " +
																		 "file. " + e.getMessage(), e);
		} catch (final IOException e) {
			throw new PersistencyException("There was an IO problem problem " +
																		 "while reading the settings " +
																		 "file. " + e.getMessage(), e);
		} finally {
			try {
				fr.close();
			} catch (final IOException e) { /* We don't care if closing fails... */ }
		}
	}
}