package persistency.settings;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import persistency.PersistencyException;
import persistency.PersistencyUtils;
import persistency.XmlUtils;
import persistency.projects.Company;

public class UserSettings implements Settings {
	private static final String FILE_NAME = "settings.xml";

	public enum OvertimeType {
		FLEX("flex"),
		COMP("comp"),
		PAID("paid");

		final String stringRepr;

		OvertimeType(final String stringRepr) {
			this.stringRepr = stringRepr;
		}

		public static OvertimeType transOvertimeType(final String overtimeType) {
			if (OvertimeType.FLEX.toString().equalsIgnoreCase(overtimeType)) {
				return OvertimeType.FLEX;
			} else if (OvertimeType.COMP.toString().equalsIgnoreCase(overtimeType)) {
				return OvertimeType.COMP;
			} else if (OvertimeType.PAID.toString().equalsIgnoreCase(overtimeType)) {
				return OvertimeType.PAID;
			} else {
				throw new IllegalArgumentException("Argument \"" + overtimeType +
																					 "\" is not an overtime type!");
			}
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return stringRepr;
		}
	}

	private String ns = "";

	private int employerId;
	private String userFirstName;
	private String userLastName;
	private int lunchBreak = 40;
	private OvertimeType treatOvertimeAs = OvertimeType.FLEX;
	private Map<Integer, Company> workplaces = new HashMap<Integer, Company>();

	@Override
	public void populate() throws PersistencyException, FileNotFoundException {
		final PersistencyUtils ph = new PersistencyUtils();
		final File absPath = new File(ph.getStorageDir(), getFilename());

		final SettingsFileReader sr = new SettingsXmlReader();
		sr.populate(this, absPath);
	}

	@Override
	public synchronized void store() throws PersistencyException {
		final PersistencyUtils ph = new PersistencyUtils();
		final File absPath = new File(ph.getStorageDir(), getFilename());
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(absPath));
		} catch (final FileNotFoundException e) {
			try {
				pw = new PrintWriter(new FileOutputStream(absPath));
			} catch (final FileNotFoundException e1) {
				throw new PersistencyException("Could not create file " + absPath
																				+ e.getMessage(), e);
			}
		}

		pw.write(getXml());
		pw.close();
	}

	@Override
	public String getFilename() {
		return FILE_NAME;
	}

	String getXml() {
		final XmlUtils xmlUtils = new XmlUtils();
		String indent = xmlUtils.indent(0);
		final StringBuilder sb = xmlUtils.getHeader("settings");
		indent = xmlUtils.incIndent(indent);

		sb.append(indent + "<" + ns + "userName first=\"" + getFirstName() +
																				"\" last=\"" + getLastName() + "\"/>\n");
		sb.append(indent + "<" + ns + "employedAt id=\"" + getEmployerId() + "\"/>\n");
		sb.append(indent + "<" + ns + "lunchBreak duration=\"" + getLunchBreak() + "\"/>\n");
		sb.append(indent + "<" + ns + "overtime treatAs=\"" + getTreatOvertimeAs() + "\"/>\n");

		indent = xmlUtils.decIndent(indent);
		sb.append(indent + "</" + ns + "settings>");

		return sb.toString();
	}

	public void setNamespace(final String ns) {
		this.ns = ns;
	}

	public String getNamespace() {
		return ns;
	}

	public Company getEmployer() {
		return workplaces.get(employerId);
	}

	public int getEmployerId() {
		return employerId;
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getUserFirstName()
	 */
	public String getFirstName() {
		return userFirstName;
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getUserLastName()
	 */
	public String getLastName() {
		return userLastName;
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setUserFirstName(java.lang.String)
	 */
	public void setFirstName(final String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setUserLastName(java.lang.String)
	 */
	public void setLastName(final String userLastName) {
		this.userLastName = userLastName;
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getLunchBreak()
	 */
	public int getLunchBreak() {
		return lunchBreak;
	}

	public Map<Integer, Company> getWorkplaces() {
		return workplaces;
	}

	public Company getWorkplaceWithId(final int workplaceId) {
		return workplaces.get(workplaceId);
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getTreatOvertimeAs()
	 */
	public OvertimeType getTreatOvertimeAs() {
		return treatOvertimeAs;
	}

	public void setEmployerId(final int empId) {
		this.employerId = empId;
	}

	public void setEmployerId(final String empIdAsString) {
		this.employerId = Integer.parseInt(empIdAsString);
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setLunchBreak(int)
	 */
	public void setLunchBreak(final int lunchBreak) {
		this.lunchBreak = lunchBreak;
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setLunchBreak(java.lang.String)
	 */
	public void setLunchBreak(final String lunchBreak) {
		this.lunchBreak = parseInt(lunchBreak);
	}

	public void setWorkplaces(final Map<Integer, Company> workplaces) {
		this.workplaces = workplaces;
	}

	public void addWorkplace(final Company workplace) {
		addWorkplaceWithId(workplace, workplace.getId());
	}

	public void addWorkplaceWithId(final Company workplace, final int id) {
		workplaces.put(id, workplace);
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setTreatOvertimeAs(persistency.settings.UserSettings.OvertimeType)
	 */
	public void setTreatOvertimeAs(final OvertimeType treatOvertimeAs) {
		this.treatOvertimeAs = treatOvertimeAs;
	}

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setTreatOvertimeAs(java.lang.String)
	 */
	public void setTreatOvertimeAs(final String treatOvertimeAs) {
		this.treatOvertimeAs = OvertimeType.transOvertimeType(treatOvertimeAs);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder objRep = new StringBuilder();
		objRep.append("employerId: " + employerId + "\n");
		objRep.append("userFirstName: " + (userFirstName != null ?  userFirstName : "not specified") + "\n");
		objRep.append("userLastName: " + (userLastName != null ?  userLastName : "not specified") + "\n");
		objRep.append("lunchBreak: " + lunchBreak + "\n");
		objRep.append("treatOvertimeAs: " + (treatOvertimeAs != null ?  treatOvertimeAs : "not specified") + "\n");
		objRep.append("Workplaces:\n");
		objRep.append(workplaces.toString() + "\n");

		return objRep.toString().trim();
	}
}