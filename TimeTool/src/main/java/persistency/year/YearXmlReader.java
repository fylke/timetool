/**
 *
 */
package persistency.year;

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

public class YearXmlReader implements YearFileReader {

	/* (non-Javadoc)
	 * @see persistency.year.YearFileReader#populate(persistency.projects.ProjectSet, java.io.File)
	 */
	@Override
	public void populate(Year year, File absPath)
			throws FileNotFoundException, PersistencyException {
		final Reader fr = new FileReader(absPath);
		XMLReader settingsParser;
		try {
			settingsParser = FileParserFactory.getParser("year");

			// Sending in the object we want to populate.
			final YearParser sp = ((YearParser) settingsParser.getContentHandler());
			sp.setTargetObject(year);

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
			} catch (final IOException e) { /* We don't care if closing fails... */ }
		}
	}
}