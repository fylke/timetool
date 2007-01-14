package logic;

import persistency.projects.Company;
import persistency.projects.ProjectSet;

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
  
  private String userName;
  private int employedAt;
  private ProjectSet projectSet;
  private int lunchBreak;
  private OvertimeType treatOvertimeAs;
  
  public Settings() {
    projectSet = new ProjectSet();
    lunchBreak = 40;
    treatOvertimeAs = OvertimeType.FLEX;
  }
    
  public String getUserName() {
    return userName;
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
   * @param lunchBreak the new default lunch break length
   */
  public void setLunchBreak(int lunchBreak) {
    this.lunchBreak = lunchBreak;
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

  public void setUserName(final String userName) {
    this.userName = userName;
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
    result = PRIME * result + ((treatOvertimeAs == null) ? 0 : treatOvertimeAs.hashCode());
    result = PRIME * result + ((userName == null) ? 0 : userName.hashCode());
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
    if (treatOvertimeAs == null) {
      if (other.treatOvertimeAs != null)
        return false;
    } else if (!treatOvertimeAs.equals(other.treatOvertimeAs))
      return false;
    if (userName == null) {
      if (other.userName != null)
        return false;
    } else if (!userName.equals(other.userName))
      return false;
    return true;
  }
}