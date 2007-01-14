package persistency.projects;

import persistency.XmlUtils;

public class TestProjectsFactory {
  public static transient final String compName = "testCompany";
  public static transient final String compShortName = "testComp";
  public static transient final String empId = "testId";
  public static transient final String projName = "testProject";
  public static transient final String projShortName = "testProj";
  public static transient final String projCode = "testCode";

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
      company.addProject(getProject(i + (id * 10), projSetConfig.projDepth));
    }
    
    return company;
  }
  
  public Project getProject(final int id, final int nestingDepth) {
    Project child = new Project();
    child.setId(id);
    child.setName(projName + id);
    child.setShortName(projShortName + id);
    child.setCode(projCode + id);

    if (nestingDepth > 0) {
      child.addSubProject(getProject(id * 10, nestingDepth - 1));
    }
    
    return child;
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
      getXmlProject(i + (id * 10), projSetConfig.projDepth, sb);
    }
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</company>\n");

    return sb.toString();
  }

  public String  getXmlProject(final int id, final int depth, 
                               final StringBuilder sb) {
    String indent = xmlUtils.indent(Integer.toString(id).length());

    sb.append(indent + "<project id=\"" + id + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<name>" + projName + id + "</name>\n");
    sb.append(indent + "<shortName>" + projShortName + id + "</shortName>\n");
    sb.append(indent + "<code>" + projCode + id + "</code>\n");

    if (depth > 0) {
      getXmlProject(id * 10, depth - 1, sb);
    }

    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</project>\n");
    
    return sb.toString();
  }
}