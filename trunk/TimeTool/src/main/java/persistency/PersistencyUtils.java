package persistency;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.prefs.Preferences;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import persistency.year.SearchControl;
import persistency.year.Year;
import persistency.year.YearParser;
import persistency.year.YearWriter;

public final class PersistencyUtils {
  private final Preferences userPrefs = Preferences.userRoot().node("/timetool");

  public Year readYear(final InputStream yearStream, final SearchControl wanted)
      throws PersistencyException {
    Reader br = null;
    final Year year = new Year();
    try {
      final XMLReader yearParser = FileParserFactory.getParser("year");
      br = new BufferedReader(new InputStreamReader(yearStream));

      // Sending in the object we want to contain the result of the parsing.
      final YearParser pp =
        ((YearParser) yearParser.getContentHandler());
      pp.setTargetObject(year);
      pp.wanted = wanted;

      yearParser.parse(new InputSource(br));
    } catch (final SAXException e) {
      throw new PersistencyException("There was a parser problem when " +
                                     "reading the year file. " +
                                     e.getMessage(), e);
    } catch (final IOException e) {
      throw new PersistencyException("There was an I/O problem when " +
                                     "reading the year file. " +
                                     e.getMessage(), e);
    } catch (final ParserConfigurationException e) {
      throw new PersistencyException("There was a problem initializing " +
                                     "a parser while reading the year " +
                                     "file. " + e.getMessage(), e);
    } finally {
      try {
        br.close();
      } catch (final IOException e) {
        // We don't care if closing fails...
      }
    }
    return year;
  }

  public synchronized void writeYear(final Year year,
                                     final OutputStream yearStream)
  {
    YearWriter yw = new YearWriter();
    yw.writeYear(year, yearStream);
  }

  /**
   * Gets a reference to the directory where TimeTool files are stored.
   *
   * @return the reference to the directory to store files in
   */
  public File getStorageDir() {
    return new File(userPrefs.get("storage_dir", System.getProperty("user.home") +
                         												 File.separator + ".timetool"));
  }

  /**
   * Sets the directory where TimeTool files should be stored.
   *
   * @param dir the path to the directory
   */
  public void setStorageDir(final File dir) {
    userPrefs.put("storage_dir", dir.getAbsolutePath());
  }
}