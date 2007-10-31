package persistency.year;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class Week {
  private List<WorkDay> workDays;
  private LocalDate startDate;
  
  public Week(LocalDate startDate) {
    super();
    this.startDate = startDate;
  }

  /**
   * 
   * @return the number of this week
   */
  public int getWeekNumber() {
    return startDate.getWeekOfWeekyear();
  }

  /**
   * @return the workDays
   */
  public List<WorkDay> getWorkDays() {
    return workDays;
  }

  /**
   * @param workDays the workDays to set
   */
  public void addWorkDay(final WorkDay workDay) {
    if (workDays == null) {
      workDays = new ArrayList<WorkDay>();
    }
    this.workDays.add(workDay);
  }
}