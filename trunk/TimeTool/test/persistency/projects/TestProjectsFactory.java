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
  
  public Projects getCompaniesWithNestedProjects(final int nrOfCompanies,
                                                 final int nestingDepth) {
    Projects projects = new Projects();
    
    for (int i = 1; i <= nrOfCompanies; i++) {
      Project project = getNestedProjects(i * 10, nestingDepth);
    
      Company company = new Company();
      company.setId(i);
      company.setName(compName + i);
      company.setShortName(compShortName + i);
      company.setEmployeeId(empId + i);
      company.addProject(project);
      
      projects.addCompany(company);
    }
    
    return projects;
  }
  
  public Projects getProjectsWithNCompanies(final int n) {
    Projects projects = new Projects();
    
    for (int i = 1; i <= n; i++) {
      Project project = new Project();
      project.setId(i * 10);
      project.setName(projName + (i * 10));
      project.setShortName(projShortName + (i * 10));
      project.setCode(projCode + (i * 10));
    
      Company company = new Company();
      company.setId(i);
      company.setName(compName + i);
      company.setShortName(compShortName + i);
      company.setEmployeeId(empId + i);
      company.addProject(project);
      
      projects.addCompany(company);
    }
    
    return projects;
  }
  
  public String getXmlCompaniesWithNestedProjects(final int nrOfCompanies,
                                                  final int nestingDepth) {
    final StringBuilder sb = xmlUtils.getHeader("projects");
    
    for (int i = 1; i <= nrOfCompanies; i++) {
      sb.append(getXmlCompanyWithNestedProject(i, nestingDepth));
    }
    
    return sb.toString() + "</projects>";
  }

  public String getXmlProjectsWithNCompanies(final int n) {
    final StringBuilder sb = xmlUtils.getHeader("projects");

    for (int i = 1; i <= n; i++) {
      sb.append(getXmlCompany(i, 1));
    }

    return sb.toString() + "</projects>";
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

  private String getXmlCompany(final int id, final int nrOfProjects) {
    String indent = xmlUtils.indent(1);
    StringBuilder sb = new StringBuilder();
    sb.append(indent + "<company id=\"" + id + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<name>" + compName + id + "</name>\n");
    sb.append(indent + "<shortName>" + compShortName + id + "</shortName>\n");
    sb.append(indent + "<employeeId>" + empId + id + "</employeeId>\n");

    for (int i = 0; i < nrOfProjects; i++) {
      getXmlProject(id * 10, 0, sb);
    }
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</company>\n");

    return sb.toString();
  }

  private String getXmlCompanyWithNestedProject(final int id,
                                                final int nestingDepth) {
    String indent = xmlUtils.indent(1);
    StringBuilder sb = new StringBuilder();
    sb.append(indent + "<company id=\"" + id + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<name>" + compName + id + "</name>\n");
    sb.append(indent + "<shortName>" + compShortName + id + "</shortName>\n");
    sb.append(indent + "<employeeId>" + empId + id + "</employeeId>\n");

    getXmlProject(id * 10, nestingDepth, sb);

    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</company>\n");

    return sb.toString();
  }
  
  private Project getNestedProjects(final int id,
                                    final int depth) {  
    Project child = new Project();
    child.setId(id);
    child.setName(projName + id);
    child.setShortName(projShortName + id);
    child.setCode(projCode + id);
    if (depth > 0) {
      child.addSubProject(getNestedProjects(id * 10, depth - 1));
    }
    return child;
  }
}