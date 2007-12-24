package persistency.projects;

import persistency.XmlUtils;

public class CompanyFactory {
	private static final int COMP_ID = 1;
	private static final String COMP_NAME = "testCompany";
	private static final String COMP_SHORT_NAME = "testComp";
	private static final String EMP_ID = "testEmpId";
	private static final String PROJ_NAME = "testProject";
	private static final String PROJ_SHORT_NAME = "testProj";
	private static final String PROJ_CODE = "testCode";
	private static final String ACT_NAME = "activityName";
	private static final String ACT_SHORT_NAME = "actName";
	private static final String ACT_REP_CODE = "actRepCode";
	private static final String NS = "";

	private final XmlUtils xmlUtils = new XmlUtils();

	public Company getCompany(final CompanyConfig compConfig) {
		final Company comp = new Company();
		comp.setId(COMP_ID);
		comp.setName(COMP_NAME);
		comp.setShortName(COMP_SHORT_NAME);
		comp.setEmployeeId(EMP_ID);

		for (int i = 1; i <= compConfig.nrOfProjsPerComp; i++) {
			comp.addProject(getProject(i + (compConfig.compId * 10), compConfig.projDepth,
											compConfig));
		}

		for (int i = 0; i < compConfig.nrOfActsPerComp; i++) {
			comp.addActivity(getActivity(i + (compConfig.compId * 10)));
		}

		return comp;
	}

	public Project getProject(final int id, final int depth,
														final CompanyConfig projSetConfig) {
		final Project child = new Project();
		child.setName(PROJ_NAME + id);
		child.setShortName(PROJ_SHORT_NAME + id);
		child.setCode(PROJ_CODE + id);

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

	public String getXmlCompany(final CompanyConfig compConfig) {
		final StringBuilder sb = xmlUtils.getHeader(NS + "company", "id=\"" +
																								compConfig.compId + "\"");
		String indent = xmlUtils.indent(1);

		sb.append(indent + "<" + NS + "compName>" + COMP_NAME +
											 "</" + NS + "compName>\n");
		sb.append(indent + "<" + NS + "compShortName>" + COMP_SHORT_NAME +
											 "</" + NS + "compShortName>\n");
		sb.append(indent + "<" + NS + "employeeId>" + EMP_ID +
											 "</" + NS + "employeeId>\n");

		for (int i = 1; i <= compConfig.nrOfProjsPerComp; i++) {
			getXmlProject(i + (compConfig.compId * 10), compConfig.projDepth, compConfig, sb);
		}

		for (int i = 0; i < compConfig.nrOfActsPerComp; i++) {
			getXmlActivity(i + (compConfig.compId * 10), sb);
		}

		indent = xmlUtils.decIndent(indent);
		sb.append(indent + "</" + NS + "company>");

		return sb.toString();
	}

	public String getXmlProject(final int id, final int depth,
															final CompanyConfig projSetConfig,
															final StringBuilder sb) {
		String indent = xmlUtils.indent(Integer.toString(id).length() - 1);

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

		if (depth > 0) {
			getXmlProject(id * 10, depth - 1, projSetConfig, sb);
		}

		indent = xmlUtils.decIndent(indent);
		sb.append(indent + "</" + NS + "project>\n");

		return sb.toString();
	}

	public String getXmlActivity(final int id, final StringBuilder sb) {
		String indent = xmlUtils.indent(1);

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