package persistency.projects;


public class Activity {
  private final int id;
  private String name;
  private String shortName;
  private String reportCode;
  
  public Activity(final int id) {
    super();
    this.id = id;
  }

  /**
   * @return the activity's unique ID
   */
  public int getId() {
    return id;
  }
  
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the shortName
   */
  public String getShortName() {
    return shortName;
  }
  
  /**
   * @return the reportCode
   */
  public String getReportCode() {
    return reportCode;
  }

  /**
   * @param reportCode the reportCode to set
   */
  public void setReportCode(final String reportCode) {
    this.reportCode = reportCode;
  }

  /**
   * @param the name of the activity to set
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * @param shortName the shortName to set
   */
  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder(); 
    objRep.append("actId: " + id + "\n");
    objRep.append("name: " + name + "\n");
    objRep.append("shortName: " + shortName + "\n");
    objRep.append("reportCode: " + reportCode);
    
    return objRep.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + id;
    result = PRIME * result + ((name == null) ? 0 : name.hashCode());
    result = PRIME * result + ((shortName == null) ? 0 : shortName.hashCode());
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
    final Activity other = (Activity) obj;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (shortName == null) {
      if (other.shortName != null)
        return false;
    } else if (!shortName.equals(other.shortName))
      return false;
    return true;
  }
}