package persistency.projects;

import persistency.XmlUtils;

public class TestProjectsFactory {
  public static transient final String compName = "testCompany";
  public static transient final String compShortName = "testComp";
  public static transient final String empId = "testId";
  public static transient final String projName = "testProject";
  public static transient final String projShortName = "testProj";
  public static transient final String projCode = "testCode";
  public static transient final String actName = "activityName";
  public static transient final String actShortName = "actName";
  public static transient final String actRepCode = "actRepCode";
  
  
  private static TestProjectsFactory factoryInstance;
  private transient XmlUtils xmlUtils;
  
  private TestProjectsFactory() {
    super();
    xmlUtils = XmlUtils.getInstance();
  }
  
  public static TestProjectsFactory getInstance() {
    if (factoryInstance == null) {
      factoryInstance = new TestProjectsFactory();
    }
    return factoryInstance;
  }
  
  public ProjectSet getProjSetWithConfig(final ProjSetConfig projSetConfig) {
    ProjectSet projectSet = new ProjectSet();
    projectSet.setId(projSetConfig.projSetId);
    
    for (int i = 1; i <= projSetConfig.nrOfComps; i++) { 
       projectSet.addCompany(getCompany(i, projSetConfig));
    }
    
    return projectSet;
  }
  
  public Company getCompany(final int id, final ProjSetConfig projSetConfig) {
    Company company = new Company();
    company.setId(id);
    company.setName(compName + id);
    company.setShortName(compShortName + id);
    company.setEmployeeId(empId + id);
    
    for (int i = 1; i <= projSetConfig.nrOfProjsPerComp; i++) {
      company.addProject(getProject(i + (id * 10), projSetConfig.projDepth,
                                    projSetConfig));
    }
    
    return company;
  }
  
  public Project getProject(final int id, final int depth, 
                            final ProjSetConfig projSetConfig) {
    Project child = new Project();
    child.setId(id);
    child.setName(projName + id);
    child.setShortName(projShortName + id);
    child.setCode(projCode + id);

    for (int i = 0; i < projSetConfig.nrOfActsPerProj; i++) {
      child.addActivity(getActivity(i + (id *10)));
    }

    if (depth > 0) {
      child.addSubProject(getProject(id * 10, depth - 1, projSetConfig));
    }
    
    return child;
  }
  
  public Activity getActivity(final int id) {
    Activity act = new Activity(id);
    act.setName(actName + id);
    act.setShortName(actShortName + id);
    act.setReportCode(actRepCode + id);
    
    return act;
  }
  
  public String getXmlProjSetWithConfig(final ProjSetConfig projSetConfig) {
    final StringBuilder sb = xmlUtils.getHeader("projectSet", "id=\"" + 
                                                projSetConfig.projSetId + "\"");

    for (int i = 1; i <= projSetConfig.nrOfComps; i++) {
      sb.append(getXmlCompany(i, projSetConfig));
    }

    return sb.toString() + "</projectSet>";
  }
  
  public String getXmlCompany(final int id, final ProjSetConfig projSetConfig) {
    String indent = xmlUtils.indent(1);
    StringBuilder sb = new StringBuilder();
    sb.append(indent + "<company id=\"" + id + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<name>" + compName + id + "</name>\n");
    sb.append(indent + "<shortName>" + compShortName + id + "</shortName>\n");
    sb.append(indent + "<employeeId>" + empId + id + "</employeeId>\n");

    for (int i = 1; i <= projSetConfig.nrOfProjsPerComp; i++) {
      getXmlProject(i + (id * 10), projSetConfig.projDepth, projSetConfig, sb);
    }
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</company>\n");

    return sb.toString();
  }

  public String getXmlProject(final int id, final int depth, 
                              final ProjSetConfig projSetConfig, 
                              final StringBuilder sb) {
    String indent = xmlUtils.indent(Integer.toString(id).length());

    sb.append(indent + "<project id=\"" + id + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<name>" + projName + id + "</name>\n");
    sb.append(indent + "<shortName>" + projShortName + id + "</shortName>\n");
    sb.append(indent + "<code>" + projCode + id + "</code>\n");

    for (int i = 0; i < projSetConfig.nrOfActsPerProj; i++) {
      getXmlActivity(i + (id * 10), sb);
    }
    
    if (depth > 0) {
      getXmlProject(id * 10, depth - 1, projSetConfig, sb);
    }

    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</project>\n");
    
    return sb.toString();
  }
  
  public String getXmlActivity(final int id, final StringBuilder sb) {
    String indent = xmlUtils.indent(Integer.toString(id).length());
    
    sb.append(indent + "<activity id=\"" + id + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<name>" + actName + id + "</name>\n");
    sb.append(indent + "<shortName>" + actShortName + id + "</shortName>\n");
    sb.append(indent + "<reportCode>" + actRepCode + id + "</reportCode>\n");
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</activity>\n");
    
    return sb.toString();
  }
}