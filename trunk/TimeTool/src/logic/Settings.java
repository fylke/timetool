package logic;

import persistency.projects.Company;
import persistency.projects.ProjectSet;
import static java.lang.Integer.parseInt;

public class Settings {
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
  
  private String userFirstName;
  private String userLastName;
  private int employedAt;
  private int projectSetId;
  private ProjectSet projectSet;
  private int lunchBreak;
  private OvertimeType treatOvertimeAs;
  
  public Settings() {
    projectSet = new ProjectSet();
    lunchBreak = 40;
    treatOvertimeAs = OvertimeType.FLEX;
  }
    
  /**
   * @return the userFirstName
   */
  public String getUserFirstName() {
    return userFirstName;
  }

  /**
   * @return the userLastName
   */
  public String getUserLastName() {
    return userLastName;
  }

  /**
   * @param userFirstName the userFirstName to set
   */
  public void setUserFirstName(final String userFirstName) {
    this.userFirstName = userFirstName;
  }

  /**
   * @param userLastName the userLastName to set
   */
  public void setUserLastName(final String userLastName) {
    this.userLastName = userLastName;
  }

  /**
   * @return the ID of the company the user is employed at
   */
  public int getEmployerId() {
    return employedAt;
  }
  
  /**
   * @return the company the user is employed at
   */
  public Company getEmployedAt() {
    return projectSet.getCompany(employedAt);
  }

  /**
   * @return the default lunch break length
   */
  public int getLunchBreak() {
    return lunchBreak;
  }

  /**
   * @return the projects
   */
  public ProjectSet getProjectSet() {
    return projectSet;
  }

  /**
   * @return the default way of treating overtime
   */
  public OvertimeType getTreatOvertimeAs() {
    return treatOvertimeAs;
  }

  /**
   * @return the default way of treating overtime
   */
  public void setTreatOvertimeAs(String treatOvertimeAs) {
    this.treatOvertimeAs = OvertimeType.transOvertimeType(treatOvertimeAs);
  }

  /**
   * @return the projectSetId
   */
  public int getProjectSetId() {
    return projectSetId;
  }

  /**
   * @param projectSetId the projectSetId to set
   */
  public void setProjectSetId(final int projectSetId) {
    this.projectSetId = projectSetId;
  }
  
  /**
   * @param projectSetId the projectSetId to set
   */
  public void setProjectSetId(final String projectSetId) {
    this.projectSetId = parseInt(projectSetId);
  }

  /**
   * @param lunchBreak the new default lunch break length
   */
  public void setLunchBreak(final int lunchBreak) {
    this.lunchBreak = lunchBreak;
  }
  
  /**
   * @param lunchBreak the new default lunch break length
   */
  public void setLunchBreak(final String lunchBreak) {
    this.lunchBreak = parseInt(lunchBreak);
  }

  /**
   * @param projectSet the projects to set
   */
  public void setProjectSet(ProjectSet projectSet) {
    this.projectSet = projectSet;
  }

  /**
   * @param treatOvertimeAs the new default way of treating overtime
   */
  public void setTreatOvertimeAs(OvertimeType treatOvertimeAs) {
    this.treatOvertimeAs = treatOvertimeAs;
  }

  /**
   * @param employedAt the company id of this user's employer 
   */
  public void setEmployedAt(final int employedAt) {
    this.employedAt = employedAt;
  }
  
  /**
   * @param employedAt the company id of this user's employer 
   */
  public void setEmployedAt(final String employedAt) {
    this.employedAt = parseInt(employedAt);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
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
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Settings other = (Settings) obj;
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