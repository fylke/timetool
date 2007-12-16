package persistency.year;


public class YearConfig {
	final int year;
	final int nrOfMonths;
	final int nrOfDaysEachMonth;
	final int nrOfActsEachDay;

	public YearConfig(final int year, final int nrOfMonths,
										final int nrOfDaysEachMonth,
										final int nrOfActsEachDay) {
		super();
		this.year = year;
		this.nrOfMonths = nrOfMonths;
		this.nrOfDaysEachMonth = nrOfDaysEachMonth;
		this.nrOfActsEachDay = nrOfActsEachDay;
	}
}
