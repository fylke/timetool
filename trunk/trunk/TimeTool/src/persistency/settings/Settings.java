package persistency.settings;

import persistency.projects.Company;
import persistency.projects.ProjectSet;
import persistency.settings.UserSettings.OvertimeType;

public interface Settings {

	public abstract void setInstance(final Settings settings);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getUserFirstName()
	 */
	public abstract String getFirstName();

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getUserLastName()
	 */
	public abstract String getLastName();

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setUserFirstName(java.lang.String)
	 */
	public abstract void setFirstName(final String userFirstName);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setUserLastName(java.lang.String)
	 */
	public abstract void setLastName(final String userLastName);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getEmployerId()
	 */
	public abstract int getEmployerId();

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getEmployedAt()
	 */
	public abstract Company getEmployedAt();

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getLunchBreak()
	 */
	public abstract int getLunchBreak();

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getProjectSet()
	 */
	public abstract ProjectSet getProjectSet();

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setTreatOvertimeAs(java.lang.String)
	 */
	public abstract void setTreatOvertimeAs(String treatOvertimeAs);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setProjectSetId(int)
	 */
	public abstract void setProjectSetId(final int projectSetId);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setProjectSetId(java.lang.String)
	 */
	public abstract void setProjectSetId(final String projectSetId);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setLunchBreak(int)
	 */
	public abstract void setLunchBreak(final int lunchBreak);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setLunchBreak(java.lang.String)
	 */
	public abstract void setLunchBreak(final String lunchBreak);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setProjectSet(persistency.projects.ProjectSet)
	 */
	public abstract void setProjectSet(ProjectSet projectSet);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setTreatOvertimeAs(persistency.settings.UserSettings.OvertimeType)
	 */
	public abstract void setTreatOvertimeAs(OvertimeType treatOvertimeAs);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#getTreatOvertimeAs()
	 */
	public abstract OvertimeType getTreatOvertimeAs();
	
	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setEmployedAt(int)
	 */
	public abstract void setEmployedAt(final int employedAt);

	/* (non-Javadoc)
	 * @see persistency.settings.Settings#setEmployedAt(java.lang.String)
	 */
	public abstract void setEmployedAt(final String employedAt);

}