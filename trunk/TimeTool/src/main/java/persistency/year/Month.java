package persistency.year;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Month {
  private final int id;
  private final int enclosingYear;
  private final Map<Integer, WorkDay> workDays;

  public Month(final int id, final int enclosingYear) {
    super();
    this.id = id;
    this.enclosingYear = enclosingYear;
    workDays = new TreeMap<Integer, WorkDay>();
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @return the enclosing year
   */
  public int getEnclosingYear() {
    return enclosingYear;
  }

  /**
   * @return the indicated work day
   */
  public WorkDay getWorkDay(final int date) {
    assert((date > 0) && (date <= 31));
    return workDays.get(date);
  }

  /**
   * @return the indicated work days
   */
  public Collection<WorkDay> getWorkDays(final Collection<Integer> workDayIds) {
    final Collection<WorkDay> workDays = new ArrayList<WorkDay>();
    for (final Integer workDayId : workDayIds) {
      workDays.add(this.workDays.get(workDayId));
    }

    return workDays;
  }

  public Collection<WorkDay> getAllWorkDays() {
    return workDays.values();
  }

  public void addWorkDay(final WorkDay workDay) {
    workDays.put(workDay.getDate().getDayOfMonth(), workDay);
  }

  public void addWorkDays(final Collection<WorkDay> workDays) {
    for (final WorkDay workDay : workDays) {
      this.workDays.put(workDay.getDate().getDayOfMonth(), workDay);
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final StringBuilder objRep = new StringBuilder();
    objRep.append("id: " + id + "\n");
    objRep.append("enclosingYear: " + enclosingYear + "\n");

    for (final WorkDay workDay : workDays.values()) {
      objRep.append("WorkDays:\n");
      objRep.append(workDay.toString());
    }

    return objRep.toString().trim();
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
  public boolean equals(final Object obj) {
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