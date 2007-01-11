package persistency.year;

import org.joda.time.LocalTime;
import org.joda.time.ReadableDateTime;

import persistency.XmlUtils;

public class ActivityInfo {
  public boolean includeLunch;
  
  private final int id;
  private ReadableDateTime startTime;
  private ReadableDateTime endTime;

  private final transient XmlUtils xmlUtils;

  ActivityInfo(final int id) {
    this.id = id;
    xmlUtils = XmlUtils.getInstance();
  }
    
  public int getId() {
    return id;
  }

  public ReadableDateTime getEndTime() {
    return endTime;
  }

  public ReadableDateTime getStartTime() {
    return startTime;
  }

  public void setActivityStartTime(final ReadableDateTime date, 
                                   final LocalTime startTime) {
    this.startTime = startTime.toDateTime(date);
  }

  public void setActivityEndTime(final ReadableDateTime date, 
                                 final LocalTime endTime) {
    this.endTime = endTime.toDateTime(date);
  }
  
  public void setActivityStartTime(final ReadableDateTime date,
                                   final String startTime) {
    this.startTime = xmlUtils.stringToTime(startTime, date);
  }
  
  public void setActivityEndTime(final ReadableDateTime date,
                                 final String endTime) {
    this.endTime = xmlUtils.stringToTime(endTime, date);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder();
    objRep.append("id: " + id + "\n");
    objRep.append("startTime: " + startTime.toString("kk:mm") + "\n");
    objRep.append("endTime: " + endTime.toString("kk:mm") + "\n");
    objRep.append("includeLunch: " + includeLunch);
    
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
    final ActivityInfo other = (ActivityInfo) obj;
    if (id != other.id)
      return false;
    return true;
  }
}