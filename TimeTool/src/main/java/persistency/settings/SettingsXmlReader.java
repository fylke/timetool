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

import persistency.FileParserFactory;
import persistency.PersistencyException;
import persistency.PersistencyUtils;

public class SettingsXmlReader implements SettingsFileReader {
	/* (non-Javadoc)
	 * @see persistency.settings.SettingsReader#populate(persistency.settings.Settings)
	 */
  public void populate(final Settings settings, final String filename) throws FileNotFoundException, PersistencyException {
    PersistencyUtils ph = new PersistencyUtils();

    File storDir = ph.getStorageDir();
    storDir.mkdir();
    File storageFile = new File(storDir, filename);

    final Reader fr = new FileReader(storageFile);

    XMLReader settingsParser;
		try {
			settingsParser = FileParserFactory.getParser("settings");

			// Sending in the object we want to populate.
      final SettingsParser sp = ((SettingsParser) settingsParser.getContentHandler());
      sp.setTargetObject(settings);

			settingsParser.parse(new InputSource(fr));
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
      } catch (final IOException e) {
        // We don't care if closing fails...
      }
    }
  }
}