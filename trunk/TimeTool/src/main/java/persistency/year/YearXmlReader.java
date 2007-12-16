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

import persistency.PersistencyException;
import persistency.XmlReader;

public class YearXmlReader extends XmlReader implements YearFileReader {
	/* (non-Javadoc)
	 * @see persistency.year.YearFileReader#populate(persistency.projects.ProjectSet, java.io.File)
	 */
	@Override
	public void populate(Year year, File absPath)
			throws FileNotFoundException, PersistencyException {
		final XMLReader xmlReader = initXmlReader();

		final YearParser yp = new YearParser(xmlReader);
		yp.setTargetObject(year);

		final Reader fr = new FileReader(absPath);
		try {
			yp.parse(new InputSource(fr));
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