package persistency.projects;

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

public class ProjectSet implements Persistable {
	private static final String FILE_NAME = "projectSet.xml";

	private int id;
  private Map<Integer, Company> companies;
  private String ns = "";

  public ProjectSet() {
    companies = new TreeMap<Integer, Company>();
  }

  /**
   * Gets the company specified by the supplied ID.
   * @param id
   * @return the requested company, null if not found
   */
  public Company getCompany(final int id) {
    return companies.get(id);
  }

  @Override
	public void populate() throws PersistencyException, FileNotFoundException {
		PersistencyUtils ph = new PersistencyUtils();
		final File absPath = new File(ph.getStorageDir(), FILE_NAME);

		final ProjectSetFileReader sr = new ProjectSetXmlReader();
		sr.populate(this, absPath);
	}

	@Override
	public void store() throws PersistencyException {
		PersistencyUtils ph = new PersistencyUtils();
		if (!ph.getStorageDir().exists()) ph.getStorageDir().mkdirs();

		final File absPath = new File(ph.getStorageDir(), FILE_NAME);
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(absPath));
		} catch (FileNotFoundException e) {
			throw new PersistencyException("Could not create file " + absPath
																		 + e.getMessage(), e);
		}

		pw.write(getXml());
		pw.close();
	}

	public Company getCompanyByName(final String name) {
    for (Company comp : companies.values()) {
      if (comp.getName() == name) {
        return comp;
      }
    }
    return null;
  }

	public void setNamespace(final String ns) {
		this.ns = ns;
	}

  /**
   * Gets all companies.
   * @return all stored companies, null if none stored
   */
  public Collection<Company> getCompanies() {
    return companies.values();
  }

  public void addCompany(final Company company) {
    addCompanyWithId(company, company.getId());
  }

  public void addCompanyWithId(final Company company, final int id) {
    companies.put(id, company);
  }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder();
    objRep.append("id: " + hashCode() + "\n");

    for (Company company : companies.values()) {
      objRep.append("Companies:\n");
      objRep.append(company.toString());
    }

    return objRep.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((companies == null) ? 0 : companies.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final ProjectSet other = (ProjectSet) obj;
    if (companies == null) {
      if (other.companies != null)
        return false;
    } else if (!companies.equals(other.companies))
      return false;
    return true;
  }

  String getXml() {
  	final XmlUtils xmlUtils = new XmlUtils();
    String indent = xmlUtils.indent(0);
    StringBuilder pb = xmlUtils.getHeader(ns + "projectSet", "id=\"" + getId() + "\"");
    indent = xmlUtils.incIndent(indent);

    for (Company comp : getCompanies()) {
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
        getXmlProject(project, pb, indent);
      }

      indent = xmlUtils.decIndent(indent);
      pb.append(indent + "</" + ns + "company>\n");
    }

    pb.append("</" + ns + "projectSet>");

    return pb.toString();
  }

  void getXmlProject(final Project proj, final StringBuilder pb,
                     final String indentLvl) {
    String indent = indentLvl;
    final XmlUtils xmlUtils = new XmlUtils();

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
        getXmlProject(subProject, pb, indent);
      }
    }
    indent = xmlUtils.decIndent(indent);
    pb.append(indent + "</" + ns + "project>\n");
  }
}