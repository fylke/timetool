package persistency.year;

import java.util.List;

import org.joda.time.ReadableDateTime;

public class Week {
  public final List<WorkDay> workDays;
  public final ReadableDateTime startDate;
  public final int weekNumber;

  public Week(final ReadableDateTime startDate, final List<WorkDay> workDays) {
    super();
    this.startDate = startDate;
    this.workDays = workDays;
    weekNumber = startDate.getWeekOfWeekyear();
  }
}