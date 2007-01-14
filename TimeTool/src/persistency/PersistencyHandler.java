package persistency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;

import org.joda.time.DurationFieldType;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import persistency.projects.Activity;
import persistency.projects.Company;
import persistency.projects.Project;
import persistency.projects.ProjectSet;
import persistency.projects.ProjectSetParser;
import persistency.year.ActivityInfo;
import persistency.year.Month;
import persistency.year.WorkDay;
import persistency.year.Year;
import persistency.year.YearParser;

public class PersistencyHandler {
  private transient XmlUtils xmlUtils = XmlUtils.getInstance();

  public Year readYear(final InputStream yearStream) 
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
  
  public void writeYear(final Year year, final OutputStream yearStream) 
  {
    String indent = xmlUtils.indent(0);
    final StringBuilder sb = 
      xmlUtils.getHeader("year", "id=\"" + year.getId() + "\"");
    indent = xmlUtils.incIndent(indent);

    for (Month month : year.getAllMonths()) {
      sb.append(indent + "<month id=\"" + month.getId() + "\">\n");
      indent = xmlUtils.incIndent(indent);

      for (WorkDay workDay : month.getAllWorkDays()) {
        sb.append(indent + "<workDay date=\"" + 
                  workDay.getDate().toString("d") + "\">\n");
        indent = xmlUtils.incIndent(indent);
      
        sb.append(indent + "<duration start=\"" + 
                  workDay.getStartTime().toString("kk:mm") + "\" end=\"" + 
                  workDay.getEndTime().toString("kk:mm") + "\"/>\n");
        
        sb.append(indent + "<overtime treatAs=\"" + 
                           workDay.getTreatOvertimeAs() + "\"/>\n");
        
        if (workDay.isReported) {
          sb.append(indent + "<isReported/>\n");
        }
        if (workDay.journalWritten) {
          sb.append(indent + "<journalWritten/>\n");
        }
        
        for (ActivityInfo actInfo : workDay.getAllActivities()) {
          sb.append(indent + "<activity id=\"" + actInfo.getId() + "\">\n");
          
          indent = xmlUtils.incIndent(indent);
          sb.append(indent + "<duration start=\"" + 
                    actInfo.getStartTime().toString("kk:mm") + "\" end=\"" + 
                    actInfo.getEndTime().toString("kk:mm") + "\"/>\n");
          if (actInfo.includeLunch) {
            sb.append(indent + "<includeLunch duration=\"" + 
                      actInfo.getLunchLenght().get(DurationFieldType.minutes()) + 
                      "\"/>\n");
          }
          
          indent = xmlUtils.decIndent(indent);
          sb.append(indent + "</activity>\n");
        }
        
        indent = xmlUtils.decIndent(indent);
        sb.append(indent + "</workDay>\n");
      }
      
      indent = xmlUtils.decIndent(indent);
      sb.append(indent + "</month>\n");
    }
    
    sb.append("</year>");
    PrintWriter writer = new PrintWriter(yearStream);
    writer.print(sb);
    writer.flush();
  }
  
  public void writeProjectSet(final ProjectSet projectSet, 
                              final OutputStream projectsStream) 
  {
    String indent = xmlUtils.indent(0);
    StringBuilder pb = xmlUtils.getHeader("projectSet", 
                                          "id=\"" + projectSet.getId() + "\"");
    indent = xmlUtils.incIndent(indent);

    for (Company company : projectSet.getCompanies()) {
      pb.append(indent + "<company id=\"" + company.getId() + "\">\n");
      indent = xmlUtils.incIndent(indent);
      pb.append(indent + "<name>" + company.getName() + "</name>\n");
      if (company.getShortName() != null) {
        pb.append(indent + "<shortName>" + company.getShortName() + 
                           "</shortName>\n");
      }
      pb.append(indent + "<employeeId>" + company.getEmployeeId() + 
                         "</employeeId>\n");
      for (Project project : company.getProjects()) {
        writeProject(project, pb, indent);
      }
      
      indent = xmlUtils.decIndent(indent);
      pb.append(indent + "</company>\n");
    }
    
    pb.append("</projectSet>");
    PrintWriter writer = new PrintWriter(projectsStream);
    writer.print(pb);
    writer.flush();
  }
  
  private void writeProject(final Project project, final StringBuilder pb, 
                            final String indentation) {
    String indent = indentation;
    
    pb.append(indent + "<project id=\"" + project.getId() + "\">\n");
    indent = xmlUtils.incIndent(indent);
    
    pb.append(indent + "<name>" + project.getName() + "</name>\n");
    if (project.getShortName() != null) {
      pb.append(indent + "<shortName>" + project.getShortName() + 
                         "</shortName>\n");
    }
    pb.append(indent + "<code>" + project.getCode() + "</code>\n");
    
    if (project.getActivities() != null) {
      for (Activity activity : project.getActivities()) {
        pb.append(indent + "<activity id=\"" + activity.getId() + "\">\n");
        indent = xmlUtils.incIndent(indent);
        pb.append(indent + "<name>" + activity.getName() + "</name>\n");
        pb.append(indent + "<shortName>" + activity.getShortName() + 
                           "</shortName>\n");
        indent = xmlUtils.decIndent(indent);
        pb.append(indent + "</activity>\n");
      }
    }
    
    if (project.getSubProjects() != null) {
      for (Project subProject : project.getSubProjects()) {
        writeProject(subProject, pb, indent);
      }
    }
    indent = xmlUtils.decIndent(indent);
    pb.append(indent + "</project>\n");
  }
}