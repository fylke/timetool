package persistency.settings;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import persistency.PersistencyException;
import persistency.PersistencyUtils;
import persistency.XmlUtils;
import persistency.projects.Company;
import persistency.projects.ProjectSet;

public class UserSettings implements Settings {
	private static final String FILE_NAME = "settings.xml";

  public enum OvertimeType {
    FLEX("flex"),
    COMP("comp"),
    PAID("paid");

    private final String stringRepr;

    OvertimeType(String stringRepr) {
      this.stringRepr = stringRepr;
    }

    public static OvertimeType transOvertimeType(String overtimeType) {
      if (OvertimeType.FLEX.toString().equalsIgnoreCase(overtimeType)) {
        return OvertimeType.FLEX;
      } else if (OvertimeType.COMP.toString().equalsIgnoreCase(overtimeType)) {
        return OvertimeType.COMP;
      } else if (OvertimeType.PAID.toString().equalsIgnoreCase(overtimeType)) {
        return OvertimeType.PAID;
      } else {
        throw new IllegalArgumentException("Argument \"" + overtimeType +
                                           "\" is not an overtime type!");
      }
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
      return stringRepr;
    }
  }

  private String ns = "";

  private String userFirstName;
  private String userLastName;
  private int employedAt;
  private int projectSetId;
  private ProjectSet projectSet;
  private int lunchBreak;
  private OvertimeType treatOvertimeAs;

  public UserSettings() {
    lunchBreak = 40;
    treatOvertimeAs = OvertimeType.FLEX;
  }

	@Override
	public void populate() throws PersistencyException, FileNotFoundException {
		PersistencyUtils ph = new PersistencyUtils();
		final File absPath = new File(ph.getStorageDir() + File.separator + FILE_NAME);

		final SettingsFileReader sr = new SettingsXmlReader();
		sr.populate(this, absPath);
	}

	@Override
	public synchronized void store() throws PersistencyException {
		PersistencyUtils ph = new PersistencyUtils();
		final String absFilename = ph.getStorageDir() + File.separator + FILE_NAME;
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(absFilename));
		} catch (FileNotFoundException e) {
			try {
				pw = new PrintWriter(new FileOutputStream(absFilename));
			} catch (FileNotFoundException e1) {
				throw new PersistencyException("Could not create file " + absFilename
																				+ e.getMessage(), e);
			}
		}

		pw.write(getXml());
		pw.close();
	}

	String getXml() {
  	final XmlUtils xmlUtils = new XmlUtils();
    String indent = xmlUtils.indent(0);
    final StringBuilder sb = xmlUtils.getHeader("settings");
    indent = xmlUtils.incIndent(indent);

    sb.append(indent + "<" + ns + "userName first=\"" + getFirstName() +
                                        "\" last=\"" + getLastName() + "\"/>\n");
    sb.append(indent + "<" + ns + "employedAt id=\"" + getEmployerId() + "\"/>\n");
    sb.append(indent + "<" + ns + "projectSet id=\"" + getProjectSetId() + "\"/>\n");
    sb.append(indent + "<" + ns + "lunchBreak duration=\"" + getLunchBreak() + "\"/>\n");
    sb.append(indent + "<" + ns + "overtime treatAs=\"" + getTreatOvertimeAs() + "\"/>\n");

    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</" + ns + "settings>");

    return sb.toString();
	}

	public void setNamespace(final String ns) {
		this.ns = ns;
	}

	public String getNamespace() {
		return ns;
	}

	/* (non-Javadoc)
   * @see persistency.settings.Settings#getUserFirstName()
   */
  public String getFirstName() {
    return userFirstName;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#getUserLastName()
   */
  public String getLastName() {
    return userLastName;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setUserFirstName(java.lang.String)
   */
  public void setFirstName(final String userFirstName) {
    this.userFirstName = userFirstName;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setUserLastName(java.lang.String)
   */
  public void setLastName(final String userLastName) {
    this.userLastName = userLastName;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#getEmployedAt()
   */
  public int getEmployerId() {
    return employedAt;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#getEmployedAt()
   */
  public Company getEmployedAt() {
    return projectSet.getCompany(employedAt);
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#getLunchBreak()
   */
  public int getLunchBreak() {
    return lunchBreak;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#getProjectSet()
   */
  public ProjectSet getProjectSet() {
    return projectSet;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#getTreatOvertimeAs()
   */
  public OvertimeType getTreatOvertimeAs() {
    return treatOvertimeAs;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setTreatOvertimeAs(java.lang.String)
   */
  public void setTreatOvertimeAs(String treatOvertimeAs) {
    this.treatOvertimeAs = OvertimeType.transOvertimeType(treatOvertimeAs);
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#getProjectSetId()
   */
  public int getProjectSetId() {
    return projectSetId;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setProjectSetId(int)
   */
  public void setProjectSetId(final int projectSetId) {
    this.projectSetId = projectSetId;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setProjectSetId(java.lang.String)
   */
  public void setProjectSetId(final String projectSetId) {
    this.projectSetId = parseInt(projectSetId);
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setLunchBreak(int)
   */
  public void setLunchBreak(final int lunchBreak) {
    this.lunchBreak = lunchBreak;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setLunchBreak(java.lang.String)
   */
  public void setLunchBreak(final String lunchBreak) {
    this.lunchBreak = parseInt(lunchBreak);
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setProjectSet(persistency.projects.ProjectSet)
   */
  public void setProjectSet(ProjectSet projectSet) {
    this.projectSet = projectSet;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setTreatOvertimeAs(persistency.settings.UserSettings.OvertimeType)
   */
  public void setTreatOvertimeAs(OvertimeType treatOvertimeAs) {
    this.treatOvertimeAs = treatOvertimeAs;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setEmployedAt(int)
   */
  public void setEmployerId(final int employedAt) {
    this.employedAt = employedAt;
  }

  /* (non-Javadoc)
   * @see persistency.settings.Settings#setEmployedAt(java.lang.String)
   */
  public void setEmployerId(final String employedAt) {
    this.employedAt = parseInt(employedAt);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  /* (non-Javadoc)
   * @see persistency.settings.Settings#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder();
    objRep.append("userFirstName: " + userFirstName + "\n");
    objRep.append("userLastName: " + userLastName + "\n");
    objRep.append("employedAt: " + employedAt + "\n");
    objRep.append("projectSetId: " + projectSetId + "\n");
    objRep.append("lunchBreak: " + lunchBreak + "\n");
    objRep.append("ProjectSet:\n" + projectSet);

    return objRep.toString().trim();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  /* (non-Javadoc)
   * @see persistency.settings.Settings#hashCode()
   */
  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + employedAt;
    result = PRIME * result + lunchBreak;
    result = PRIME * result + ((projectSet == null) ? 0 : projectSet.hashCode());
    result = PRIME * result + projectSetId;
    result = PRIME * result + ((treatOvertimeAs == null) ? 0 : treatOvertimeAs.hashCode());
    result = PRIME * result + ((userFirstName == null) ? 0 : userFirstName.hashCode());
    result = PRIME * result + ((userLastName == null) ? 0 : userLastName.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  /* (non-Javadoc)
   * @see persistency.settings.Settings#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final UserSettings other = (UserSettings) obj;
    if (employedAt != other.employedAt)
      return false;
    if (lunchBreak != other.lunchBreak)
      return false;
    if (projectSet == null) {
      if (other.projectSet != null)
        return false;
    } else if (!projectSet.equals(other.projectSet))
      return false;
    if (projectSetId != other.projectSetId)
      return false;
    if (treatOvertimeAs == null) {
      if (other.treatOvertimeAs != null)
        return false;
    } else if (!treatOvertimeAs.equals(other.treatOvertimeAs))
      return false;
    if (userFirstName == null) {
      if (other.userFirstName != null)
        return false;
    } else if (!userFirstName.equals(other.userFirstName))
      return false;
    if (userLastName == null) {
      if (other.userLastName != null)
        return false;
    } else if (!userLastName.equals(other.userLastName))
      return false;
    return true;
  }
}