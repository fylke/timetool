package persistency.projects;

import persistency.XmlUtils;

public class ProjectsFactory {
  private static final String COMP_NAME = "testCompany";
  private static final String COMP_SHORT_NAME = "testComp";
  private static final String EMP_ID = "testId";
  private static final String PROJ_NAME = "testProject";
  private static final String PROJ_SHORT_NAME = "testProj";
  private static final String PROJ_CODE = "testCode";
  private static final String ACT_NAME = "activityName";
  private static final String ACT_SHORT_NAME = "actName";
  private static final String ACT_REP_CODE = "actRepCode";
  private static final String NS = "";

  private final XmlUtils xmlUtils = new XmlUtils();

  public ProjectSet getProjSetWithConfig(final ProjSetConfig projSetConfig)
      throws Exception {
    final ProjectSet projSet = new ProjectSet();
    projSet.setId(projSetConfig.projSetId);

    for (int i = 1; i <= projSetConfig.nrOfComps; i++) {
      projSet.addCompany(getCompany(i, projSetConfig));
    }

    return projSet;
  }

  public Company getCompany(final int id, final ProjSetConfig projSetConfig) {
    final Company comp = new Company();
    comp.setName(COMP_NAME + id);
    comp.setShortName(COMP_SHORT_NAME + id);
    comp.setEmployeeId(EMP_ID + id);

    for (int i = 1; i <= projSetConfig.nrOfProjsPerComp; i++) {
      comp.addProject(getProject(i + (id * 10), projSetConfig.projDepth,
                      projSetConfig));
    }

    return comp;
  }

  public Project getProject(final int id, final int depth,
                            final ProjSetConfig projSetConfig) {
    final Project child = new Project();
    child.setName(PROJ_NAME + id);
    child.setShortName(PROJ_SHORT_NAME + id);
    child.setCode(PROJ_CODE + id);

    for (int i = 0; i < projSetConfig.nrOfActsPerProj; i++) {
      child.addActivity(getActivity(i + (id * 10)));
    }

    if (depth > 0) {
      child.addSubProject(getProject(id * 10, depth - 1, projSetConfig));
    }

    return child;
  }

  public Activity getActivity(final int id) {
    final Activity act = new Activity();
    act.setName(ACT_NAME + id);
    act.setShortName(ACT_SHORT_NAME + id);
    act.setReportCode(ACT_REP_CODE + id);

    return act;
  }

  public String getXmlProjSetWithConfig(final ProjSetConfig projSetConfig) {
    final StringBuilder sb = xmlUtils.getHeader(NS + "projectSet", "id=\"" +
                                                projSetConfig.projSetId + "\"");

    for (int i = 1; i <= projSetConfig.nrOfComps; i++) {
      sb.append(getXmlCompany(i, projSetConfig));
    }

    return sb.toString() + "</" + NS + "projectSet>";
  }

  public String getXmlCompany(final int id, final ProjSetConfig projSetConfig) {
    String indent = xmlUtils.indent(1);
    final StringBuilder sb = new StringBuilder();

    final Company comp = new Company();
    comp.setName(COMP_NAME + id);
    comp.setShortName(COMP_SHORT_NAME + id);
    comp.setEmployeeId(EMP_ID + id);

    sb.append(indent + "<" + NS + "company id=\"" + comp.getId() + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<" + NS + "compName>" + COMP_NAME + id +
                       "</" + NS + "compName>\n");
    sb.append(indent + "<" + NS + "compShortName>" + COMP_SHORT_NAME + id +
                       "</" + NS + "compShortName>\n");
    sb.append(indent + "<" + NS + "employeeId>" + EMP_ID + id +
                       "</" + NS + "employeeId>\n");

    for (int i = 1; i <= projSetConfig.nrOfProjsPerComp; i++) {
      getXmlProject(i + (id * 10), projSetConfig.projDepth, projSetConfig, sb);
    }
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</" + NS + "company>\n");

    return sb.toString();
  }

  public String getXmlProject(final int id, final int depth,
                              final ProjSetConfig projSetConfig,
                              final StringBuilder sb) {
    String indent = xmlUtils.indent(Integer.toString(id).length());

    final Project proj = new Project();
    proj.setName(PROJ_NAME + id);
    proj.setShortName(PROJ_SHORT_NAME + id);
    proj.setCode(PROJ_CODE + id);

    sb.append(indent + "<" + NS + "project id=\"" + proj.getId() + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<" + NS + "projName>" + PROJ_NAME + id +
                       "</" + NS + "projName>\n");
    sb.append(indent + "<" + NS + "projShortName>" + PROJ_SHORT_NAME + id +
                       "</" + NS + "projShortName>\n");
    sb.append(indent + "<" + NS + "code>" + PROJ_CODE + id +
                       "</" + NS + "code>\n");

    for (int i = 0; i < projSetConfig.nrOfActsPerProj; i++) {
      getXmlActivity(i + (id * 10), sb);
    }

    if (depth > 0) {
      getXmlProject(id * 10, depth - 1, projSetConfig, sb);
    }

    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</" + NS + "project>\n");

    return sb.toString();
  }

  public String getXmlActivity(final int id, final StringBuilder sb) {
    String indent = xmlUtils.indent(Integer.toString(id).length());

    final Activity act = new Activity();
    act.setName(ACT_NAME + id);
    act.setShortName(ACT_SHORT_NAME + id);
    act.setReportCode(ACT_REP_CODE + id);

    sb.append(indent + "<" + NS + "activity id=\"" + act.getId() + "\">\n");
    indent = xmlUtils.incIndent(indent);
    sb.append(indent + "<" + NS + "actName>" + ACT_NAME + id +
                       "</" + NS + "actName>\n");
    sb.append(indent + "<" + NS + "actShortName>" + ACT_SHORT_NAME + id +
                       "</" + NS + "actShortName>\n");
    sb.append(indent + "<" + NS + "actReportCode>" + ACT_REP_CODE + id +
                       "</" + NS + "actReportCode>\n");
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</" + NS + "activity>\n");

    return sb.toString();
  }
}