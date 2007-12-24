/*
 * Settings.java
 *
 * Created on October 31, 2007, 9:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package persistency.settings;

import java.util.Map;

import persistency.Persistable;
import persistency.projects.Company;


public interface Settings extends Persistable {
		Company getEmployer();

		int getEmployerId();

		String getFirstName();

		String getLastName();

		int getLunchBreak();

		Map<Integer, Company> getWorkplaces();

		Company getWorkplaceWithId(final int workplaceId);

		UserSettings.OvertimeType getTreatOvertimeAs();

		void setEmployerId(final int employedAt);

		void setEmployerId(final String employedAt);

		void setFirstName(final String userFirstName);

		void setLastName(final String userLastName);

		void setLunchBreak(final String lunchBreak);

		void setLunchBreak(final int lunchBreak);

		void setWorkplaces(final Map<Integer, Company> workplaces);

		void addWorkplace(final Company workplace);

		void addWorkplaceWithId(final Company workplace, final int id);

		void setTreatOvertimeAs(UserSettings.OvertimeType treatOvertimeAs);

		void setTreatOvertimeAs(String treatOvertimeAs);
}