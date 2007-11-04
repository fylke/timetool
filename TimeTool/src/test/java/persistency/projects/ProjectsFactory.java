package persistency.projects;

import persistency.ItemAlreadyDefinedException;
import persistency.XmlUtils;

public class ProjectsFactory {
  private static transient final String compName = "testCompany";
  private static transient final String compShortName = "testComp";
  private static transient final String empId = "testId";
  private static transient final String projName = "testProject";
  private static transient final String projShortName = "testProj";
  private static transient final String projCode = "testCode";
  private static transient final String actName = "activityName";
  private static transient final String actShortName = "actName";
  private static transient final String actRepCode = "actRepCode";
  private static transient final String ns = "";
  
  private static ProjectsFactory factoryInstance;
  private transient XmlUtils xmlUtils;
  
  private ProjectsFactory() {
    super();
    xmlUtils = XmlUtils.getInstance();
  }
  
  public static ProjectsFactory getInstance() {
    if (factoryInstance == null) {
      factoryInstance = new ProjectsFactory();
    }
    return factoryInstance;
  }
  
  public ProjectSet getProjSetWithConfig(final ProjSetConfig projSetConfig) 
      throws Exception {
    ProjectSet projSet = new ProjectSet();
    
    for (int i = 1; i <= projSetConfig.nrOfComps; i++) {
      projSet.addCompany(getCompany(i, projSetConfig));
    }
    
    return projSet;
  }
  
  public Company getCompany(final int id, final ProjSetConfig projSetConfig) 
      throws Exception {
    final Company comp = new Company();
    comp.setName(compName + id);
    comp.setShortName(compShortName + id);
    comp.setEmployeeId(empId + id);
    
    for (int i = 1; i <= projSetConfig.nrOfProjsPerComp; i++) {
      comp.addProject(getProject(i + (id * 10), projSetConfig.projDepth,
                      projSetConfig));
    }
    
    return comp;
  }
  
  public Project getProject(final int id, final int depth, 
                            final ProjSetConfig projSetConfig) 
      throws ItemAlreadyDefinedException {
    Project child = new Project();
    child.setName(projName + id);
    child.setShortName(projShortName + id);
    child.setCode(projCode + id);

    for (int i = 0; i < projSetConfig.nrOfActsPerProj; i++) {
      child.addActivity(getActivity(i + (id * 10)));
    }

    if (depth > 0) {
      child.addSubProject(getProject(id * 10, depth - 1, projSetConfig));
    }
    
    return child;
  }
  
  public Activity getActivity(final int id) {
    Activity act = new Activity();
    act.setName(actName + id);
    act.setShortName(actShortName + id);
    act.setReportCode(actRepCode + id);
    
    return act;
  }
  
  public String getXmlProjSetWithConfig(final ProjSetConfig projSetConfig) {
    final StringBuilder sb = xmlUtils.getHeader(ns + "projectSet", "id=\"" + 
                                                projSetConfig.projSetId + "\"");

    for (int i = 1; i <= projSetConfig.nrOfComps; i++) {
      sb.append(getXmlCompany(i, projSetConfig));
    }

    return sb.toString() + "</" + ns + "projectSet>";
  }
  
  public String getXmlCompany(final int id, final ProjSetConfig projSetConfig) {
    String indent = xmlUtils.indent(1);
    StringBuilder sb = new StringBuilder();
    
    final Company comp = new Company();
    comp.setName(compName + id);
    comp.setShortName(compShortName + id);
    comp.setEmployeeId(empId + id);

    sb.append(indent + "<" + ns + "company id=\"" + comp.getId() + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<" + ns + "compName>" + compName + id + 
                       "</" + ns + "compName>\n");
    sb.append(indent + "<" + ns + "compShortName>" + compShortName + id + 
                       "</" + ns + "compShortName>\n");
    sb.append(indent + "<" + ns + "employeeId>" + empId + id + 
                       "</" + ns + "employeeId>\n");

    for (int i = 1; i <= projSetConfig.nrOfProjsPerComp; i++) {
      getXmlProject(i + (id * 10), projSetConfig.projDepth, projSetConfig, sb);
    }
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</" + ns + "company>\n");

    return sb.toString();
  }

  public String getXmlProject(final int id, final int depth, 
                              final ProjSetConfig projSetConfig, 
                              final StringBuilder sb) {
    String indent = xmlUtils.indent(Integer.toString(id).length());
    
    final Project proj = new Project();
    proj.setName(projName + id);
    proj.setShortName(projShortName + id);
    proj.setCode(projCode + id);

    sb.append(indent + "<" + ns + "project id=\"" + proj.getId() + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<" + ns + "projName>" + projName + id + 
                       "</" + ns + "projName>\n");
    sb.append(indent + "<" + ns + "projShortName>" + projShortName + id + 
                       "</" + ns + "projShortName>\n");
    sb.append(indent + "<" + ns + "code>" + projCode + id + 
                       "</" + ns + "code>\n");

    for (int i = 0; i < projSetConfig.nrOfActsPerProj; i++) {
      getXmlActivity(i + (id * 10), sb);
    }
    
    if (depth > 0) {
      getXmlProject(id * 10, depth - 1, projSetConfig, sb);
    }

    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</" + ns + "project>\n");
    
    return sb.toString();
  }
  
  public String getXmlActivity(final int id, final StringBuilder sb) {
    String indent = xmlUtils.indent(Integer.toString(id).length());
    
    final Activity act = new Activity();
    act.setName(actName + id);
    act.setShortName(actShortName + id);
    act.setReportCode(actRepCode + id);
    
    sb.append(indent + "<" + ns + "activity id=\"" + act.getId() + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<" + ns + "actName>" + actName + id + 
                       "</" + ns + "actName>\n");
    sb.append(indent + "<" + ns + "actShortName>" + actShortName + id + 
                       "</" + ns + "actShortName>\n");
    sb.append(indent + "<" + ns + "actReportCode>" + actRepCode + id + 
                       "</" + ns + "actReportCode>\n");
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</" + ns + "activity>\n");
    
    return sb.toString();
  }
}