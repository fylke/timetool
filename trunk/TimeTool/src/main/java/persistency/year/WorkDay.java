package persistency.year;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalTime;
import org.joda.time.PeriodType;
import org.joda.time.ReadableDateTime;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadablePeriod;

import persistency.XmlUtils;
import persistency.settings.UserSettings.OvertimeType;

public class WorkDay {
	public boolean isReported;
	public boolean journalWritten;

	private static int nextActId = 0;

	private final Map<Integer, ActivityInfo> activities;
	private final ReadableDateTime date;
	private ReadableDateTime startTime;
	private ReadableDateTime endTime;
	private OvertimeType treatOvertimeAs;

	public WorkDay(final int year, final int month, final int dateInMonth) {
		this(new DateTime(year, month, dateInMonth, 0, 0, 0, 0));
	}

	public WorkDay(final ReadableDateTime date) {
		super();
		this.date = date;
		activities = new TreeMap<Integer, ActivityInfo>();
	}

	/**
	 * @return the date
	 */
	public ReadableDateTime getDate() {
		return date;
	}

	/**
	 * @return the endTime
	 */
	public ReadableDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @return the startTime
	 */
	public ReadableDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @return the length of the work day
	 */
	public ReadableDuration getDuration() {
		return new Duration(startTime, endTime);
	}

	/**
	 * @param endTime the endTime to set on the form hh:mm
	 */
	public void setEndTime(final String endTime) {
		final XmlUtils xmlUtils = new XmlUtils();
		this.endTime = xmlUtils.stringToTime(endTime, date);
	}

	/**
	 * @param startTime the startTime to set on the form hh:mm
	 */
	public void setStartTime(final String startTime) {
		final XmlUtils xmlUtils = new XmlUtils();
		this.startTime = xmlUtils.stringToTime(startTime, date);
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(final LocalTime endTime) {
		this.endTime = endTime.toDateTime(date);
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(final LocalTime startTime) {
		this.startTime = startTime.toDateTime(date);
	}

	/**
	 * @return the treatOvertimeAs
	 */
	public OvertimeType getTreatOvertimeAs() {
		return treatOvertimeAs;
	}

	/**
	 * @param treatOvertimeAs the treatOvertimeAs to set
	 */
	public void setTreatOvertimeAs(final OvertimeType treatOvertimeAs) {
		this.treatOvertimeAs = treatOvertimeAs;
	}

	/**
	 * @param treatOvertimeAs the treatOvertimeAs to set
	 */
	public void setTreatOvertimeAs(final String treatOvertimeAs) {
		this.treatOvertimeAs = OvertimeType.transOvertimeType(treatOvertimeAs);
	}

	/**
	 * Adds an activity to this work day, assumes that the activity is already
	 * properly defined.
	 * @param actId
	 */
	public void addActivity(final ActivityInfo actInfo) {
		activities.put(actInfo.getId(), actInfo);
	}

	public ActivityInfo getActivity(final int actId) {
		return activities.get(actId);
	}

	public Collection<ActivityInfo> getAllActivities() {
		return activities.values();
	}

	public LocalTime getDayBalanceAsLocalTime() {
		final ReadablePeriod dayBalance = getDayBalance();

		return new LocalTime(dayBalance.get(DurationFieldType.hours()),
												 dayBalance.get(DurationFieldType.minutes()));
	}

	public int getNewActId() {
		final int max = Collections.max(activities.keySet());
		while (activities.keySet().contains(WorkDay.nextActId)) {
			WorkDay.nextActId++;
		}

		return WorkDay.nextActId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder objRep = new StringBuilder();
		objRep.append("date: " + date.toString("d/M") + "\n");
		objRep.append("startTime: " + (startTime != null ?  startTime.toString("kk:mm") : "not specified")  + "\n");
		objRep.append("endTime: " + (endTime != null ?  endTime.toString("kk:mm") : "not specified") + "\n");
		objRep.append("treatOvertimeAs: " + (treatOvertimeAs != null ?  treatOvertimeAs : "not specified") + "\n");
		objRep.append("isReported: " + isReported + "\n");
		objRep.append("journalWritten: " + journalWritten + "\n");

		for (final ActivityInfo act : activities.values()) {
			objRep.append("Activities:\n");
			objRep.append(act.toString() + "\n");
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
		result = PRIME * result + ((date == null) ? 0 : date.hashCode());
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
		final WorkDay other = (WorkDay) obj;
		if (activities == null) {
			if (other.activities != null)
				return false;
		} else if (!activities.equals(other.activities))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (isReported != other.isReported)
			return false;
		if (journalWritten != other.journalWritten)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (treatOvertimeAs == null) {
			if (other.treatOvertimeAs != null)
				return false;
		} else if (!treatOvertimeAs.equals(other.treatOvertimeAs))
			return false;
		return true;
	}

	ReadablePeriod getDayBalance() {
		final Duration dayBalance = new Duration(Duration.ZERO);
		for (final ActivityInfo actInfo : activities.values()) {
			dayBalance.plus(new Duration(actInfo.getStartTime(),
																	 actInfo.getEndTime()));
			if (actInfo.includeLunch) {
				dayBalance.plus(actInfo.getLunchLenght());
			}
		}

		return dayBalance.toPeriod(PeriodType.minutes());
	}
}