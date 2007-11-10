package persistency.year;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.ReadableDateTime;

public class SearchControl {
  SortedSet<Integer> hits;
  
  public SearchControl() {
    super();
    hits = new TreeSet<Integer>();
  }
  
  public void setDates(final Set<ReadableDateTime> dates) {
    for (ReadableDateTime date : dates) {
      hits.add(date.getDayOfYear());
    }
  }
  
  public void setDateInterval(final ReadableDateTime start, 
                              final ReadableDateTime end) {
    assert(start.isBefore(end));
    for (int i = start.getDayOfYear(); i < end.getDayOfYear(); i++) {
      hits.add(i);
    }
  }
  
  public boolean isHit(final ReadableDateTime date) {
    return hits.contains(date.getDayOfYear());
  }
}
