package persistency.year;


public class YearConfig {
  final int year;
  final int nrOfMonths;
  final int nrOfDaysEachMonth;
  final int nrOfActsEachDay;
  final SearchControl searchControl;

  public YearConfig(final int year, final int nrOfMonths,
                    final int nrOfDaysEachMonth,
                    final int nrOfActsEachDay,
                    final SearchControl searchControl) {
    super();
    this.year = year;
    this.nrOfMonths = nrOfMonths;
    this.nrOfDaysEachMonth = nrOfDaysEachMonth;
    this.nrOfActsEachDay = nrOfActsEachDay;
    this.searchControl = searchControl;
  }
}
