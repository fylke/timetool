package persistency.year;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Year {
  private int id;
  private Map<Short, Month> months;
  
  public Year() {
    super();
    /* All Maps stored in XML files are made TreeMaps as they automatically
     * sort themselves, if not, you cannot count on the being in the same order 
     * when comparing for testing purposes as they are only viewed as 
     * collections then. */
    months = new TreeMap<Short, Month>();
  }
  
  public void setId(final int id) {
    this.id = id;
  }
  
  public int getId() {
    return id;
  }
  
  /**
   * @return the indicated month
   */
  public Month getMonth(final short monthId) {
    return months.get(monthId);
  }
  
  /**
   * @return the indicated months
   */
  public Collection<Month> getMonths(final Collection<Integer> monthIds) {
    Collection<Month> months = new ArrayList<Month>();
    for (Integer monthId : monthIds) {
      months.add(this.months.get(monthId));
    }
    
    return months;
  }
  
  public Collection<Month> getAllMonths() {
    return months.values();
  }

  public void addMonth(final Month month) {
    months.put(month.getId(), month);
  }
  
  public void addMonth(final Collection<Month> months) {
    for (Month month : months) {
      this.months.put(month.getId(), month);
    }
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder(); 
    objRep.append("id: " + id + "\n");

    for (Month month : months.values()) {
      objRep.append("Months:\n");
      objRep.append(month.toString() + "\n");
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
    result = PRIME * result + id;
    result = PRIME * result + ((months == null) ? 0 : months.hashCode());
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
    final Year other = (Year) obj;
    if (id != other.id)
      return false;
    if (months == null) {
      if (other.months != null)
        return false;
    } else if (!months.equals(other.months))
      return false;
    return true;
  }
}