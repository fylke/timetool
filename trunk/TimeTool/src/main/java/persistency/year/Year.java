package persistency.year;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import persistency.Persistable;
import persistency.PersistencyException;
import persistency.PersistencyUtils;
import persistency.XmlUtils;

public class Year implements Persistable {
	int id;
	Map<Integer, Month> months;
	private String ns = "";

	public Year() {
		super();
		/* All Maps stored in XML files are made TreeMaps as they automatically
		 * sort themselves, if not, you cannot count on the being in the same order
		 * when comparing for testing purposes as they are only viewed as
		 * collections then. */
		months = new TreeMap<Integer, Month>();
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void populate() throws PersistencyException, FileNotFoundException {
		final PersistencyUtils ph = new PersistencyUtils();
		final File absPath = new File(ph.getStorageDir(), getFilename());

		final YearFileReader sr = new YearXmlReader();
		sr.populate(this, absPath);
	}

	public void store() throws PersistencyException {
		final PersistencyUtils ph = new PersistencyUtils();
		if (!ph.getStorageDir().exists()) ph.getStorageDir().mkdirs();

		final File absPath = new File(ph.getStorageDir(), getFilename());
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(absPath));
		} catch (final FileNotFoundException e) {
			throw new PersistencyException("Could not create file " + absPath
																		 + e.getMessage(), e);
		}

		pw.write(toXml());
		pw.close();
	}

	@Override
	public String getFilename() {
		return "year" + id + ".xml";
	}

	/**
	 * @return the indicated month
	 */
	public Month getMonth(final int monthId) {
		return months.get(monthId);
	}

	/**
	 * @return the indicated months
	 */
	public Collection<Month> getMonths(final Collection<Integer> monthIds) {
		final Collection<Month> months = new ArrayList<Month>();
		for (final Integer monthId : monthIds) {
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
		for (final Month month : months) {
			this.months.put(month.getId(), month);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder objRep = new StringBuilder();
		objRep.append("id: " + id + "\n");

		for (final Month month : months.values()) {
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
	public boolean equals(final Object obj) {
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

	String toXml() {
		XmlUtils xmlUtils = new XmlUtils();
		String indent = xmlUtils.indent(0);
		final StringBuilder sb =
			xmlUtils.getHeader(ns + "year", "id=\"" + id + "\"");
		indent = xmlUtils.incIndent(indent);

		for (final Month month : getAllMonths()) {
			sb.append(indent + "<" + ns + "month id=\"" + month.getId() + "\">\n");
			indent = xmlUtils.incIndent(indent);

			for (final WorkDay workDay : month.getAllWorkDays()) {
				sb.append(indent + "<" + ns + "workDay date=\"" +
									workDay.getDate().toString("d") + "\">\n");
				indent = xmlUtils.incIndent(indent);

				sb.append(indent + "<" + ns + "duration start=\"" +
									workDay.getStartTime().toString("kk:mm") + "\" end=\"" +
									workDay.getEndTime().toString("kk:mm") + "\"/>\n");

				sb.append(indent + "<" + ns + "overtime treatAs=\"" +
													 workDay.getTreatOvertimeAs() + "\"/>\n");

				if (workDay.isReported) {
					sb.append(indent + "<" + ns + "isReported/>\n");
				}
				if (workDay.journalWritten) {
					sb.append(indent + "<" + ns + "journalWritten/>\n");
				}

				for (final ActivityInfo actInfo : workDay.getAllActivities()) {
					sb.append(indent + "<" + ns + "activity id=\"" + actInfo.getId() + "\">\n");

					indent = xmlUtils.incIndent(indent);
					sb.append(indent + "<" + ns + "duration start=\"" +
										actInfo.getStartTime().toString("kk:mm") + "\" end=\"" +
										actInfo.getEndTime().toString("kk:mm") + "\"/>\n");
					if (actInfo.includeLunch) {
						sb.append(indent + "<" + ns + "lunchBreak duration=\"" +
											actInfo.getLunchLenght() + "\"/>\n");
					}

					indent = xmlUtils.decIndent(indent);
					sb.append(indent + "</" + ns + "activity>\n");
				}

				indent = xmlUtils.decIndent(indent);
				sb.append(indent + "</" + ns + "workDay>\n");
			}

			indent = xmlUtils.decIndent(indent);
			sb.append(indent + "</" + ns + "month>\n");
		}

		sb.append("</" + ns + "year>");

		return sb.toString();
	}
}