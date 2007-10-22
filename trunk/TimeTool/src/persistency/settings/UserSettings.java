package persistency.settings;

import persistency.projects.Company;
import persistency.projects.ProjectSet;

public class UserSettings implements Settings {
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
  
  private static Settings instance;
  
  private String userFirstName;
  private String userLastName;
  private int employedAt;
  private int projectSetId;
  private ProjectSet projectSet;
  private int lunchBreak;
  private OvertimeType treatOvertimeAs;
  
  private UserSettings() {
    lunchBreak = 40;
    treatOvertimeAs = OvertimeType.FLEX;
    projectSet = new ProjectSet();
  }
  
  public static Settings getInstance() {
    if (instance == null) {
      return new UserSettings();
    } 
    return instance;
  }
  
  public void setInstance(final Settings settings) {
    instance = settings;
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
   * @see persistency.settings.Settings#getEmployerId()
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
  public void setEmployedAt(final int employedAt) {
    this.employedAt = employedAt;
  }
  
  /* (non-Javadoc)
   * @see persistency.settings.Settings#setEmployedAt(java.lang.String)
   */
  public void setEmployedAt(final String employedAt) {
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