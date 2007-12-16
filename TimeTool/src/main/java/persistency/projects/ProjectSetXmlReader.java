package persistency.projects;

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
import persistency.XmlUtils;

public class ProjectSetXmlReader implements ProjectSetFileReader {

	@Override
	public void populate(final ProjectSet projSet, final File absPath)
			throws FileNotFoundException,	PersistencyException {
		final XmlUtils xmlUtils = new XmlUtils();
		final XMLReader xmlReader = xmlUtils.getXmlReader();

		final ProjectSetParser pp = new ProjectSetParser(xmlReader);
		pp.setTargetObject(projSet);

		final Reader fr = new FileReader(absPath);
		try {
			pp.parse(new InputSource(fr));
		} catch (final SAXException e) {
			throw new PersistencyException("There was a parser problem when " +
																		 "reading the projects file." +
																		 e.getMessage(), e);
		} catch (final IOException e) {
			throw new PersistencyException("There was an I/O problem when " +
																		 "reading the projects file. " +
																		 e.getMessage(), e);
		} catch (final ParserConfigurationException e) {
			throw new PersistencyException("There was a problem initializing " +
																		 "a parser while reading the projects " +
																		 "file. " + e.getMessage(), e);
		} finally {
			try {
				fr.close();
			} catch (final IOException e) { /* We don't care if closing fails... */ }
		}
	}
}