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

import persistency.projects.ProjectSet;
import persistency.projects.ProjectSetParser;
import persistency.projects.ProjectSetWriter;
import persistency.settings.Settings;
import persistency.settings.UserSettings;
import persistency.settings.SettingsParser;
import persistency.settings.SettingsWriter;
import persistency.year.SearchControl;
import persistency.year.Year;
import persistency.year.YearParser;
import persistency.year.YearWriter;

public final class PersistencyHandler {
  private final Preferences userPrefs = Preferences.userRoot().node("/timetool");
  private static PersistencyHandler instance;
  
  private PersistencyHandler() {
    super();
  }
  
  public static PersistencyHandler getInstance() {
    if (PersistencyHandler.instance == null) {
      instance = new PersistencyHandler();
    }
    return instance;
  }
  
  public Settings readUserSettings(final InputStream settingsStream) 
      throws PersistencyException {
    Reader br = null;
    Settings userSettings = UserSettings.getInstance();
    try {
      final XMLReader settingsParser = FileParserFactory.getParser("settings");
      br = new BufferedReader(new InputStreamReader(settingsStream));

      // Sending in the object we want to contain the result of the parsing.
      final SettingsParser sp = 
        ((SettingsParser) settingsParser.getContentHandler());
      sp.setTargetObject(userSettings);

      settingsParser.parse(new InputSource(br));
    } catch (final SAXException e) {
      throw new PersistencyException("There was a parser problem when " +
                                     "reading the settings file. " + 
                                     e.getMessage(), e);
    } catch (final IOException e) {
      throw new PersistencyException("There was an I/O problem when " +
                                     "reading the settings file. " + 
                                     e.getMessage(), e);
    } catch (final ParserConfigurationException e) {
      throw new PersistencyException("There was a problem initializing " +
                                     "a parser while reading the settings " +
                                     "file. " + e.getMessage(), e);
    } finally {
      try {
        br.close();
      } catch (final IOException e) {
        // We don't care if closing fails...
      }
    }
    return userSettings;
  }
  
  public Year readYear(final InputStream yearStream, final SearchControl wanted) 
      throws PersistencyException {
    Reader br = null;
    Year year = new Year();
    try {
      final XMLReader yearParser = FileParserFactory.getParser("year");
      br = new BufferedReader(new InputStreamReader(yearStream));
      
      // Sending in the object we want to contain the result of the parsing.
      final YearParser pp = 
        ((YearParser) yearParser.getContentHandler());
      pp.setTargetObject(year);
      pp.setSearchControl(wanted);
      
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
  
  public ProjectSet readProjectSet(final InputStream projStr) 
      throws PersistencyException {
    Reader br = null;
    ProjectSet projSet = new ProjectSet();
    try {
      final XMLReader projSetParser = 
        FileParserFactory.getParser("projectSet");
      
      br = new BufferedReader(new InputStreamReader(projStr));
      
      // Sending in the object we want to contain the result of the parsing.
      final ProjectSetParser pp = 
        ((ProjectSetParser) projSetParser.getContentHandler());
      pp.setTargetObject(projSet);
      
      projSetParser.parse(new InputSource(br));
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
        br.close();
      } catch (final IOException e) {
        // We don't care if closing fails...
      }
    }
    return projSet;
  }
  
  public synchronized void writeUserSettings(final Settings userSettings, 
                                             final OutputStream settingsStream) 
  {
    SettingsWriter sw = new SettingsWriter();
    sw.writeUserSettings(userSettings, settingsStream);
  }
  
  public synchronized void writeYear(final Year year, 
                                     final OutputStream yearStream) 
  {
    YearWriter yw = new YearWriter();
    yw.writeYear(year, yearStream);
  }
  
  public synchronized void writeProjectSet(final ProjectSet projectSet, 
                                           final OutputStream projectsStream) 
  {
    ProjectSetWriter psw = new ProjectSetWriter();
    psw.writeProjectSet(projectSet, projectsStream);
  }
  
  /**
   * Gets the absolute path of the directory where files are stored.
   *  
   * @return the absolute path of the directory to store files in
   */
  public String getStorageDir() {
    return userPrefs.get("storage_dir", System.getProperty("user.home") + 
                         								File.separator + ".timetool");
  }
  
  /**
   * Sets the path to where TimeTool files should be stored.
   * 
   * @param absPath the absolute path to the directory
   */
  public void setStorageDir(final String absPath) {
    userPrefs.put("storage_dir", absPath);
  }
}