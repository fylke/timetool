package persistency.company;

import gui.MyComboBoxDisplayable;


public class Activity implements MyComboBoxDisplayable {
  private String name;
  private String shortName;
  private String reportCode;

  public String getLongDispString() {
    return name;
  }

  public String getShortDispString() {
    return shortName;
  }

  /**
   * @return the activity's unique ID
   */
  public int getId() {
    return hashCode();
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
    final StringBuilder objRep = new StringBuilder();
    objRep.append("id: " + hashCode() + "\n");
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
    result = PRIME * result + ((name == null) ? 0 : name.hashCode());
    result = PRIME * result + ((reportCode == null) ? 0 : reportCode.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Activity other = (Activity) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (reportCode == null) {
      if (other.reportCode != null)
        return false;
    } else if (!reportCode.equals(other.reportCode))
      return false;
    return true;
  }
}