package persistency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import persistency.projects.ProjectSet;
import persistency.projects.ProjectSetParser;
import persistency.projects.ProjectSetWriter;
import persistency.settings.Settings;
import persistency.settings.SettingsParser;
import persistency.settings.SettingsWriter;
import persistency.year.SearchControl;
import persistency.year.Year;
import persistency.year.YearParser;
import persistency.year.YearWriter;

public class PersistencyHandler {
  public Settings readSettings(final InputStream settingsStream) 
  throws PersistencyException {
    Reader br = null;
    Settings settings = new Settings();
    try {
      final XMLReader settingsParser = FileParserFactory.getParser("settings");
      br = new BufferedReader(new InputStreamReader(settingsStream));

      // Sending in the object we want to contain the result of the parsing.
      final SettingsParser sp = 
        ((SettingsParser) settingsParser.getContentHandler());
      sp.setTargetObject(settings);

      settingsParser.parse(new InputSource(br));
    } catch (final SAXException e) {
      throw new PersistencyException("There was a parser problem when " +
                                     "reading the settings file.", e);
    } catch (final IOException e) {
      throw new PersistencyException("There was an I/O problem when " +
                                     "reading the settings file.", e);
    } catch (final ParserConfigurationException e) {
      throw new PersistencyException("There was a problem initializing " +
                                     "a parser while reading the settings " +
                                     "file.", e);
    } finally {
      try {
        br.close();
      } catch (final IOException e) {
        // We don't care if closing fails...
      }
    }
    return settings;
  }
  
  public Year readYear(final InputStream yearStream, SearchControl wanted) 
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
                                     "reading the year file.", e);
    } catch (final IOException e) {
      throw new PersistencyException("There was an I/O problem when " +
                                     "reading the year file.", e);
    } catch (final ParserConfigurationException e) {
      throw new PersistencyException("There was a problem initializing " +
                                     "a parser while reading the year " +
                                     "file.", e);
    } finally {
      try {
        br.close();
      } catch (final IOException e) {
        // We don't care if closing fails...
      }
    }
    return year;
  }
  
  public ProjectSet readProjectSet(final InputStream projectsStream) 
      throws PersistencyException {
    Reader br = null;
    ProjectSet projectSet = new ProjectSet();
    try {
      final XMLReader projectsParser = 
        FileParserFactory.getParser("projectSet");
      br = new BufferedReader(new InputStreamReader(projectsStream));
      
      // Sending in the object we want to contain the result of the parsing.
      final ProjectSetParser pp = 
        ((ProjectSetParser) projectsParser.getContentHandler());
      pp.setTargetObject(projectSet);
      
      projectsParser.parse(new InputSource(br));
    } catch (final SAXException e) {
      throw new PersistencyException("There was a parser problem when " +
                                     "reading the projects file.", e);
    } catch (final IOException e) {
      throw new PersistencyException("There was an I/O problem when " +
                                     "reading the projects file.", e);
    } catch (final ParserConfigurationException e) {
      throw new PersistencyException("There was a problem initializing " +
                                     "a parser while reading the projects " +
                                     "file.", e);
    } finally {
      try {
        br.close();
      } catch (final IOException e) {
        // We don't care if closing fails...
      }
    }
    return projectSet;
  }
  
  public void writeSettings(final Settings settings, 
                            final OutputStream settingsStream) 
  {
    SettingsWriter sw = new SettingsWriter();
    sw.writeSettings(settings, settingsStream);
  }
  
  public void writeYear(final Year year, final OutputStream yearStream) 
  {
    YearWriter yw = new YearWriter();
    yw.writeYear(year, yearStream);
  }
  
  public void writeProjectSet(final ProjectSet projectSet, 
                              final OutputStream projectsStream) 
  {
    ProjectSetWriter psw = new ProjectSetWriter();
    psw.writeProjectSet(projectSet, projectsStream);
  }
}