package persistency.year;


import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.ReadableDuration;

import persistency.XmlUtils;
import persistency.settings.UserSettings.OvertimeType;


public class YearFactory {
	public static final String START_TIME = "9:00";
	public static final int START_HOUR = 9;
	public static final int START_MINUTE = 0;
	public static final String END_TIME = "17:00";
	public static final int END_HOUR = 17;
	public static final int END_MINUTE = 0;
	public static final int LUNCH_LENGTH = 40;

	private static final String ns = "";
	private final XmlUtils xmlUtils = new XmlUtils();

	public Year getYearWithConfig(final YearConfig yc) {
		final Year myYear = new Year();
		myYear.setId(yc.year);

		for (int i = 1; i <= yc.nrOfMonths; i++) {
			myYear.addMonth(getMonth(i, yc));
		}

		return myYear;
	}

	public Month getMonth(final int month, final YearConfig yc) {
		final Month myMonth = new Month(month, yc.year);

		for (int day = 1; day <= yc.nrOfDaysEachMonth; day++) {
				myMonth.addWorkDay(getWorkDay(yc, month, day));
		}

		return myMonth;
	}

	public WorkDay getWorkDay(final YearConfig yc, final int month,
														final int dateInMonth) {
		final WorkDay workDay = new WorkDay(yc.year, month, dateInMonth);

		workDay.setStartTime(new LocalTime(START_HOUR, START_MINUTE));
		workDay.setEndTime(new LocalTime(END_HOUR, END_MINUTE));

		workDay.setTreatOvertimeAs(OvertimeType.FLEX);
		workDay.isReported = true;
		workDay.journalWritten = true;

		for (int i = 0; i < yc.nrOfActsEachDay; i++) {
			workDay.addActivity(getActivityInfo(workDay, i, yc));
		}

		return workDay;
	}

	public ActivityInfo getActivityInfo(final WorkDay workDay, final int id, final YearConfig yc) {
		final ActivityInfo actInfo = new ActivityInfo(id);

		final ReadableDuration actLength =
			new Duration(workDay.getDuration().getMillis() / yc.nrOfActsEachDay);

		// To get activities that are back to back throughout the day
		final DateTime actStartTime = workDay.getStartTime().toDateTime().plus(actLength.getMillis() * (id + 1));
		final DateTime actEndTime = actStartTime.plus(actLength);

		actInfo.setActivityStartTime(workDay.getDate(),
																 actStartTime.toLocalTime());
		actInfo.setActivityEndTime(workDay.getDate(),
															 actEndTime.toLocalTime());
		actInfo.includeLunch = true;
		actInfo.setLunchLenght(LUNCH_LENGTH);
		workDay.addActivity(actInfo);

		return actInfo;
	}

	public String getXmlYearWithConfig(final YearConfig yc) {
		final StringBuilder sb =
			xmlUtils.getHeader(ns + "year", "id=\"" + yc.year + "\"");

		for (short i = 1; i <= yc.nrOfMonths ; i++) {
			getXmlMonth(i, yc, sb);
		}

		return sb.toString() + "</" + ns + "year>";
	}

	public String getXmlMonth(final int month, final YearConfig yc,
														final StringBuilder sb) {
		String indent = xmlUtils.indent(1);

		sb.append(indent + "<" + ns + "month id=\"" + month + "\">\n");
		indent = xmlUtils.incIndent(indent);

		for (short i = 1; i <= yc.nrOfDaysEachMonth; i++) {
			getXmlWorkDay(i, month, yc, sb);
		}

		indent = xmlUtils.decIndent(indent);
		sb.append(indent + "</" + ns + "month>\n");

		return sb.toString();
	}

	public void getXmlWorkDay(final int dateInMonth, final int month,
														final YearConfig yc,
														final StringBuilder sb) {
		String indent = xmlUtils.indent(2);
		final WorkDay workDay = new WorkDay(yc.year, month, dateInMonth);

		workDay.setStartTime(new LocalTime(START_HOUR, START_MINUTE));
		workDay.setEndTime(new LocalTime(END_HOUR, END_MINUTE));

		sb.append(indent + "<" + ns + "workDay date=\"" + dateInMonth + "\">\n");
		indent = xmlUtils.incIndent(indent);

		final String workStartTime = workDay.getStartTime().toString("kk:mm");
		final String workEndTime = workDay.getEndTime().toString("kk:mm");

		sb.append(indent + "<" + ns + "duration start=\"" + workStartTime +
																				"\" end=\"" + workEndTime + "\"/>\n");
		sb.append(indent + "<" + ns + "overtime treatAs=\"flex\"/>\n");
		sb.append(indent + "<" + ns + "isReported/>\n");
		sb.append(indent + "<" + ns + "journalWritten/>\n");

		for (int i = 0; i < yc.nrOfActsEachDay; i++) {
			sb.append(getXmlActivityInfo(indent, workDay, i, yc));
		}

		indent = xmlUtils.decIndent(indent);
		sb.append(indent + "</" + ns + "workDay>\n");
	}

	public String getXmlActivityInfo(String indent, final WorkDay workDay, final int id, final YearConfig yc) {
		final ActivityInfo actInfo = getActivityInfo(workDay, id, yc);

		final StringBuilder sb = new StringBuilder();
		sb.append(indent + "<" + ns + "activity id=\"" + id + "\">\n");
		indent = xmlUtils.incIndent(indent);

		sb.append(indent + "<" + ns +
							"duration start=\"" + actInfo.getStartTime().toString("kk:mm") +
										"\" end=\"" + actInfo.getEndTime().toString("kk:mm") + "\"/>\n");
		sb.append(indent + "<" + ns + "lunchBreak duration=\"" + LUNCH_LENGTH + "\"/>\n");
		indent = xmlUtils.decIndent(indent);
		sb.append(indent + "</" + ns + "activity>\n");

		return sb.toString();
	}
}