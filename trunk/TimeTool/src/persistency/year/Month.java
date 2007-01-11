package persistency.year;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Month {
  private final short id;
  private final int enclosingYear;
  private Map<Integer, WorkDay> workDays;
  
  public Month(final short id, final int enclosingYear) {
    super();
    this.id = id;
    this.enclosingYear = enclosingYear;
  }

  /**
   * @return the id
   */
  public short getId() {
    return id;
  }

  /**
   * @return the enclosingYear
   */
  public int getEnclosingYear() {
    return enclosingYear;
  }

  /**
   * @return the indicated work day
   */
  public WorkDay getWorkDay(final short date) {
    assert(date > 0 && date <= 31);
    return workDays.get(date);
  }
  
  /**
   * @return the indicated work days
   */
  public Collection<WorkDay> getWorkDays(final Collection<Short> workDayIds) {
    ArrayList<WorkDay> workDays = new ArrayList<WorkDay>();
    for (Short workDayId : workDayIds) {
      workDays.add(workDays.get(workDayId));
    }
    
    return workDays;
  }

  public Collection<WorkDay> getAllWorkDays() {
    return workDays.values();
  }
  
  public void addWorkDay(WorkDay workDay) {
    if (workDays == null) {
      workDays = new HashMap<Integer, WorkDay>();
    }
    workDays.put(workDay.getDate().getDayOfMonth(), workDay);
  }
  
  public void addWorkDays(Collection<WorkDay> workDays) {
    if (this.workDays == null) {
      this.workDays = new HashMap<Integer, WorkDay>();
    }
    for (WorkDay workDay : workDays) {
      this.workDays.put(workDay.getDate().getDayOfMonth(), workDay);
    }
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder();
    objRep.append("id: " + id + "\n");
    objRep.append("enclosingYear: " + enclosingYear + "\n");
    
    for (WorkDay workDay : workDays.values()) {
      objRep.append("WorkDays:\n");
      objRep.append(workDay.toString() + "\n");
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
    result = PRIME * result + enclosingYear;
    result = PRIME * result + id;
    result = PRIME * result + ((workDays == null) ? 0 : workDays.hashCode());
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
    final Month other = (Month) obj;
    if (enclosingYear != other.enclosingYear)
      return false;
    if (id != other.id)
      return false;
    if (workDays == null) {
      if (other.workDays != null)
        return false;
    } else if (!workDays.equals(other.workDays))
      return false;
    return true;
  }
}