package persistency.year;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalTime;
import org.joda.time.PeriodType;
import org.joda.time.ReadableDateTime;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadablePeriod;

import persistency.XmlUtils;
import persistency.settings.Settings.OvertimeType;

public class WorkDay {
  public boolean isReported;
  public boolean journalWritten;
  
  private Map<Integer, ActivityInfo> activities;
  private ReadableDateTime date;
  private ReadableDateTime startTime;
  private ReadableDateTime endTime;
  private OvertimeType treatOvertimeAs;
  
  private final transient XmlUtils xmlUtils;
  
  public WorkDay(final int year, final short month, final short dateInMonth) {
    super();
    this.date = new DateTime(year, month, dateInMonth, 0, 0, 0, 0);
    xmlUtils = XmlUtils.getInstance();
    activities = new TreeMap<Integer, ActivityInfo>();
  }

  /**
   * @return the date
   */
  public ReadableDateTime getDate() {
    return date;
  }

  /**
   * @return the endTime
   */
  public ReadableDateTime getEndTime() {
    return endTime;
  }

  /**
   * @return the startTime
   */
  public ReadableDateTime getStartTime() {
    return startTime;
  }

  /**
   * @return the length of the work day
   */
  public ReadableDuration getDuration() {
    return new Duration(startTime, endTime);
  }
  
  /**
   * @param endTime the endTime to set on the form hh:mm
   */
  public void setEndTime(String endTime) {
    this.endTime = xmlUtils.stringToTime(endTime, date); 
  }

  /**
   * @param startTime the startTime to set on the form hh:mm
   */
  public void setStartTime(String startTime) {
    this.startTime = xmlUtils.stringToTime(startTime, date); 
  }
  
  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime.toDateTime(date);
  }

  /**
   * @param startTime the startTime to set
   */
  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime.toDateTime(date);
  }

  /**
   * @return the isReported property
   */
  public boolean isReported() {
    return isReported;
  }
  
  /**
   * @return the journalWritten property
   */
  public boolean isJournalWritten() {
    return journalWritten;
  }

  /**
   * @return the treatOvertimeAs
   */
  public OvertimeType getTreatOvertimeAs() {
    return treatOvertimeAs;
  }

  /**
   * @param treatOvertimeAs the treatOvertimeAs to set
   */
  public void setTreatOvertimeAs(OvertimeType treatOvertimeAs) {
    this.treatOvertimeAs = treatOvertimeAs;
  }

  /**
   * @param treatOvertimeAs the treatOvertimeAs to set
   */
  public void setTreatOvertimeAs(String treatOvertimeAs) {
    this.treatOvertimeAs = OvertimeType.transOvertimeType(treatOvertimeAs);
  }
  
  /**
   * Adds an activity to this work day, assumes that the activity is already 
   * properly defined.
   * @param actId
   */
  public void addActivity(final ActivityInfo actInfo) {
    activities.put(actInfo.getId(), actInfo);
  }

  public ActivityInfo getActivity(final int actId) {
    return activities.get(actId);
  }
  
  public Collection<ActivityInfo> getAllActivities() {
    return activities.values();
  }
  
  public ReadablePeriod getDayBalance() {
    Duration dayBalance = new Duration(Duration.ZERO);
    for (final ActivityInfo actInfo : activities.values()) {
      dayBalance.plus(new Duration(actInfo.getStartTime(), 
                                   actInfo.getEndTime()));
      if (actInfo.includeLunch) {
        dayBalance.plus(actInfo.getLunchLenght().get(DurationFieldType.minutes()));
      }
    }
    
    return dayBalance.toPeriod(PeriodType.minutes());
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder(); 
    objRep.append("date: " + date.toString("d/M") + "\n");
    objRep.append("startTime: " + startTime.toString("kk:mm") + "\n");
    objRep.append("endTime: " + endTime.toString("kk:mm") + "\n");
    objRep.append("treatOvertimeAs: " + treatOvertimeAs + "\n");
    objRep.append("isReported: " + isReported + "\n");
    objRep.append("journalWritten: " + journalWritten + "\n");
    
    for (ActivityInfo act : activities.values()) {
      objRep.append("Activities:\n");
      objRep.append(act.toString() + "\n");
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
    result = PRIME * result + ((activities == null) ? 0 : activities.hashCode());
    result = PRIME * result + ((date == null) ? 0 : date.hashCode());
    result = PRIME * result + ((endTime == null) ? 0 : endTime.hashCode());
    result = PRIME * result + (isReported ? 1231 : 1237);
    result = PRIME * result + (journalWritten ? 1231 : 1237);
    result = PRIME * result + ((startTime == null) ? 0 : startTime.hashCode());
    result = PRIME * result + ((treatOvertimeAs == null) ? 0 : treatOvertimeAs.hashCode());
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
    final WorkDay other = (WorkDay) obj;
    if (activities == null) {
      if (other.activities != null)
        return false;
    } else if (!activities.equals(other.activities))
      return false;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (endTime == null) {
      if (other.endTime != null)
        return false;
    } else if (!endTime.equals(other.endTime))
      return false;
    if (isReported != other.isReported)
      return false;
    if (journalWritten != other.journalWritten)
      return false;
    if (startTime == null) {
      if (other.startTime != null)
        return false;
    } else if (!startTime.equals(other.startTime))
      return false;
    if (treatOvertimeAs == null) {
      if (other.treatOvertimeAs != null)
        return false;
    } else if (!treatOvertimeAs.equals(other.treatOvertimeAs))
      return false;
    return true;
  }
}