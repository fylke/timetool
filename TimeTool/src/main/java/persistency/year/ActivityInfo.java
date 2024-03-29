package persistency.year;

import org.joda.time.LocalTime;
import org.joda.time.ReadableDateTime;

import persistency.XmlUtils;

public class ActivityInfo {
	public boolean includeLunch;

	private final int id;
	private ReadableDateTime startTime;
	private ReadableDateTime endTime;
	private int lunchLength;

	ActivityInfo(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public ReadableDateTime getEndTime() {
		return endTime;
	}

	public ReadableDateTime getStartTime() {
		return startTime;
	}

	public void setActivityStartTime(final ReadableDateTime date,
																	 final LocalTime startTime) {
		this.startTime = startTime.toDateTime(date);
	}

	public void setActivityEndTime(final ReadableDateTime date,
																 final LocalTime endTime) {
		this.endTime = endTime.toDateTime(date);
	}

	public void setActivityStartTime(final ReadableDateTime date,
																	 final String startTime) {
		final XmlUtils xmlUtils = new XmlUtils();
		this.startTime = xmlUtils.stringToTime(startTime, date);
	}

	public void setActivityEndTime(final ReadableDateTime date,
																 final String endTime) {
		final XmlUtils xmlUtils = new XmlUtils();
		this.endTime = xmlUtils.stringToTime(endTime, date);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder objRep = new StringBuilder();
		objRep.append("id: " + id + "\n");
		objRep.append("startTime: " + startTime.toString("kk:mm") + "\n");
		objRep.append("endTime: " + endTime.toString("kk:mm") + "\n");
		objRep.append("includeLunch: " + includeLunch + "\n");
		objRep.append("lunchLength: " + lunchLength + "\n");

		return objRep.toString();
	}

	/**
	 * @return the lunchLength
	 */
	public int getLunchLength() {
		return lunchLength;
	}

	/**
	 * @param lunchLength the lunchLenght to set
	 */
	public void setLunchLength(final int lunchLength) {
		this.lunchLength = lunchLength;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + id;
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
		final ActivityInfo other = (ActivityInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}
}