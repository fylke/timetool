package persistency.projects;

import java.io.OutputStream;
import java.io.PrintWriter;

import persistency.XmlUtils;

public class ProjectSetWriter {
	private static final String ns = "";
	
  private XmlUtils xmlUtils = new XmlUtils();
  
  public void writeProjectSet(final ProjectSet projectSet, 
                              final OutputStream projectsStream) 
  {
    String indent = xmlUtils.indent(0);
    StringBuilder pb = xmlUtils.getHeader(ns + "projectSet");
    indent = xmlUtils.incIndent(indent);

    for (Company comp : projectSet.getCompanies()) {
      pb.append(indent + "<" + ns + "company id=\"" + comp.getId() + "\">\n");
      indent = xmlUtils.incIndent(indent);
      pb.append(indent + "<" + ns + "compName>" + comp.getName() + "</" + ns + "compName>\n");
      if (comp.getShortName() != null) {
        pb.append(indent + "<" + ns + "compShortName>" + comp.getShortName() + 
                           "</" + ns + "compShortName>\n");
      }
      pb.append(indent + "<" + ns + "employeeId>" + comp.getEmployeeId() + 
                         "</" + ns + "employeeId>\n");
      for (Project project : comp.getProjects()) {
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
  
  private void writeProject(final Project proj, final StringBuilder pb, 
                            final String indentLvl) {
    String indent = indentLvl;
    
    pb.append(indent + "<" + ns + "project id=\"" + proj.getId() + "\">\n");
    indent = xmlUtils.incIndent(indent);
    
    pb.append(indent + "<" + ns + "projName>" + proj.getName() + "</" + ns + "projName>\n");
    if (proj.getShortName() != null) {
      pb.append(indent + "<" + ns + "projShortName>" + proj.getShortName() + 
                         "</" + ns + "projShortName>\n");
    }
    pb.append(indent + "<" + ns + "code>" + proj.getCode() + "</code>\n");
    
    if (proj.getActivities() != null) {
      for (Activity act : proj.getActivities()) {
        pb.append(indent + "<" + ns + "activity id=\"" + act.getId() + "\">\n");
        indent = xmlUtils.incIndent(indent);
        pb.append(indent + "<" + ns + "actName>" + act.getName() + "</" + ns + "actName>\n");
        pb.append(indent + "<" + ns + "actShortName>" + act.getShortName() + 
                           "</" + ns + "actShortName>\n");
        pb.append(indent + "<" + ns + "actReportCode>" + act.getReportCode() + 
                           "</" + ns + "actReportCode>\n");
        indent = xmlUtils.decIndent(indent);
        pb.append(indent + "</" + ns + "activity>\n");
      }
    }
    
    if (proj.getSubProjects() != null) {
      for (Project subProject : proj.getSubProjects()) {
        writeProject(subProject, pb, indent);
      }
    }
    indent = xmlUtils.decIndent(indent);
    pb.append(indent + "</" + ns + "project>\n");
  }
}