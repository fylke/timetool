package persistency.projects;

import java.io.OutputStream;
import java.io.PrintWriter;

import persistency.XmlUtils;

public class ProjectSetWriter {
  private transient XmlUtils xmlUtils = XmlUtils.getInstance();
  private static transient final String ns = "";
  
  public void writeProjectSet(final ProjectSet projectSet, 
                              final OutputStream projectsStream) 
  {
    String indent = xmlUtils.indent(0);
    StringBuilder pb = xmlUtils.getHeader(ns + "projectSet");
    indent = xmlUtils.incIndent(indent);

    for (Company company : projectSet.getCompanies()) {
      pb.append(indent + "<" + ns + "company>\n");
      indent = xmlUtils.incIndent(indent);
      pb.append(indent + "<" + ns + "compName>" + company.getName() + "</" + ns + "compName>\n");
      if (company.getShortName() != null) {
        pb.append(indent + "<" + ns + "compShortName>" + company.getShortName() + 
                           "</" + ns + "compShortName>\n");
      }
      pb.append(indent + "<" + ns + "employeeId>" + company.getEmployeeId() + 
                         "</" + ns + "employeeId>\n");
      for (Project project : company.getProjects()) {
        writeProject(project, pb, indent);
      }
      
      indent = xmlUtils.decIndent(indent);
      pb.append(indent + "</" + ns + "company>\n");
    }
    
    pb.append("</" + ns + "projectSet>");
    PrintWriter writer = new PrintWriter(projectsStream);
    writer.print(pb);
    writer.flush();
  }
  
  private void writeProject(final Project project, final StringBuilder pb, 
                            final String indentation) {
    String indent = indentation;
    
    pb.append(indent + "<" + ns + "project>\n");
    indent = xmlUtils.incIndent(indent);
    
    pb.append(indent + "<" + ns + "projName>" + project.getName() + "</" + ns + "projName>\n");
    if (project.getShortName() != null) {
      pb.append(indent + "<" + ns + "projShortName>" + project.getShortName() + 
                         "</" + ns + "projShortName>\n");
    }
    pb.append(indent + "<" + ns + "code>" + project.getCode() + "</code>\n");
    
    if (project.getActivities() != null) {
      for (Activity activity : project.getActivities()) {
        pb.append(indent + "<" + ns + "activity>\n");
        indent = xmlUtils.incIndent(indent);
        pb.append(indent + "<" + ns + "actName>" + activity.getName() + "</" + ns + "actName>\n");
        pb.append(indent + "<" + ns + "actShortName>" + activity.getShortName() + 
                           "</" + ns + "actShortName>\n");
        pb.append(indent + "<" + ns + "actReportCode>" + activity.getReportCode() + 
                           "</" + ns + "actReportCode>\n");
        indent = xmlUtils.decIndent(indent);
        pb.append(indent + "</" + ns + "activity>\n");
      }
    }
    
    if (project.getSubProjects() != null) {
      for (Project subProject : project.getSubProjects()) {
        writeProject(subProject, pb, indent);
      }
    }
    indent = xmlUtils.decIndent(indent);
    pb.append(indent + "</" + ns + "project>\n");
  }
}