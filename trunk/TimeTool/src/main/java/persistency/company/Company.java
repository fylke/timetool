package persistency.company;

import gui.MyComboBoxDisplayable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import persistency.Persistable;
import persistency.PersistencyException;
import persistency.PersistencyUtils;
import persistency.XmlUtils;

public class Company implements Persistable, MyComboBoxDisplayable {
	private int id;
	private String name;
	private String shortName;
	private String employeeId;
	private final Map<Integer, Project> projects = new TreeMap<Integer, Project>();
	private final Map<Integer, Activity> activities = new TreeMap<Integer, Activity>();
	private String ns = "";

	@Override
	public void populate() throws PersistencyException, FileNotFoundException {
		final PersistencyUtils ph = new PersistencyUtils();
		final File absPath = new File(ph.getStorageDir(), getFilename());

		final CompanyFileReader sr = new CompanyXmlReader();
		sr.populate(this, absPath);
	}

	@Override
	public void store() throws PersistencyException {
		final PersistencyUtils ph = new PersistencyUtils();
		if (!ph.getStorageDir().exists()) ph.getStorageDir().mkdirs();

		final File absPath = new File(ph.getStorageDir(), getFilename());
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(absPath));
		} catch (final FileNotFoundException e) {
			throw new PersistencyException("Could not create file " + absPath
																		 + e.getMessage(), e);
		}

		pw.write(toXml());
		pw.close();
	}

	@Override
	public String getFilename() {
		return name + ".xml";
	}

	@Override
	public String getLongDispString() {
		return name;
	}

	@Override
	public String getShortDispString() {
		return shortName;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public Project getProject(final int id) {
		return projects.get(id);
	}

	public Project getProjectByName(final String name) {
		for (final Project proj : projects.values()) {
			if (name.equals(proj.getName())) {
				return proj;
			}
		}
		return null;
	}

	public Collection<Project> getProjects() {
		return projects.values();
	}

	public Collection<Activity> getActivities() {
		return activities.values();
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	public void setEmployeeId(final String employeeId) {
		this.employeeId = employeeId;
	}

	public void addProject(final Project project) {
		addProjectWithId(project, project.getId());
	}

	public void addProjectWithId(final Project project, final int id) {
		projects.put(id, project);
	}

	public void addActivity(final Activity act) {
		addActivityWithId(act, act.getId());
	}

	public void addActivityWithId(final Activity act, final int id) {
		activities.put(id, act);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder objRep = new StringBuilder();
		objRep.append("id: " + id + "\n");
		objRep.append("name: " + name + "\n");
		objRep.append("shortName: " + shortName + "\n");
		objRep.append("employeeId: " + employeeId + "\n");

		for (final Project project : projects.values()) {
			objRep.append("Projects:\n");
			objRep.append(project.toString() + "\n");
		}

		for (final Activity act : activities.values()) {
			objRep.append("Activities:\n");
			objRep.append(act.toString() + "\n");
		}

		return objRep.toString().trim();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activities == null) ? 0 : activities.hashCode());
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((projects == null) ? 0 : projects.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Company other = (Company) obj;
		if (activities == null) {
			if (other.activities != null) return false;
		} else if (!activities.equals(other.activities)) return false;
		if (employeeId == null) {
			if (other.employeeId != null) return false;
		} else if (!employeeId.equals(other.employeeId)) return false;
		if (id != other.id) return false;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (projects == null) {
			if (other.projects != null) return false;
		} else if (!projects.equals(other.projects)) return false;
		if (shortName == null) {
			if (other.shortName != null) return false;
		} else if (!shortName.equals(other.shortName)) return false;
		return true;
	}

	String toXml() {
		final XmlUtils xmlUtils = new XmlUtils();
		String indent = xmlUtils.indent(0);
		final StringBuilder sb = xmlUtils.getHeader(ns + "company", "id=\"" + id + "\"");
		indent = xmlUtils.incIndent(indent);

		sb.append(indent + "<" + ns + "compName>" + name + "</" + ns + "compName>\n");
		if (shortName != null) {
			sb.append(indent + "<" + ns + "compShortName>" + shortName +
												 "</" + ns + "compShortName>\n");
		}
		sb.append(indent + "<" + ns + "employeeId>" + employeeId +
											 "</" + ns + "employeeId>\n");
		for (final Project proj : projects.values()) {
			getXmlProject(proj, sb, indent);
		}

		for (Activity act : activities.values()) {
			sb.append(indent + "<" + ns + "activity id=\"" + act.getId() + "\">\n");
			indent = xmlUtils.incIndent(indent);
			sb.append(indent + "<" + ns + "actName>" + act.getName() + "</" + ns + "actName>\n");
			sb.append(indent + "<" + ns + "actShortName>" + act.getShortName() +
												 "</" + ns + "actShortName>\n");
			sb.append(indent + "<" + ns + "actReportCode>" + act.getReportCode() +
												 "</" + ns + "actReportCode>\n");
			indent = xmlUtils.decIndent(indent);
			sb.append(indent + "</" + ns + "activity>\n");
		}
		indent = xmlUtils.decIndent(indent);

		sb.append(indent + "</" + ns + "company>\n");
		return sb.toString();
	}

	void getXmlProject(final Project proj, final StringBuilder sb,
										 final String indentLvl) {
		String indent = indentLvl;
		final XmlUtils xmlUtils = new XmlUtils();

		sb.append(indent + "<" + ns + "project id=\"" + proj.getId() + "\">\n");
		indent = xmlUtils.incIndent(indent);

		sb.append(indent + "<" + ns + "projName>" + proj.getName() + "</" + ns + "projName>\n");
		if (proj.getShortName() != null) {
			sb.append(indent + "<" + ns + "projShortName>" + proj.getShortName() +
												 "</" + ns + "projShortName>\n");
		}
		sb.append(indent + "<" + ns + "code>" + proj.getCode() + "</code>\n");

		if (proj.getSubProjects() != null) {
			for (final Project subProject : proj.getSubProjects()) {
				getXmlProject(subProject, sb, indent);
			}
		}
		indent = xmlUtils.decIndent(indent);

		sb.append(indent + "</" + ns + "project>\n");
	}
}