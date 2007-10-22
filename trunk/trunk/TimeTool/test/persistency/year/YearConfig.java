package persistency.year;


public class YearConfig {
  final transient int year;
  final transient short nrOfMonths;
  final transient short nrOfDaysEachMonth;
  final transient int nrOfActsEachDay;
  final transient SearchControl searchControl;
  
  public YearConfig(final int year, final short nrOfMonths, 
                    final short nrOfDaysEachMonth, 
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
