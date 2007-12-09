/*
 * Settings.java
 *
 * Created on October 31, 2007, 9:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package persistency.settings;

import persistency.Persistable;
import persistency.projects.Company;
import persistency.projects.ProjectSet;


public interface Settings extends Persistable {
    Company getEmployedAt();

    int getEmployerId();

    String getFirstName();

    String getLastName();

    int getLunchBreak();

    ProjectSet getProjectSet();

    int getProjectSetId();

    UserSettings.OvertimeType getTreatOvertimeAs();

    void setEmployedAt(final String employedAt);

    void setEmployedAt(final int employedAt);

    void setFirstName(final String userFirstName);

    void setLastName(final String userLastName);

    void setLunchBreak(final String lunchBreak);

    void setLunchBreak(final int lunchBreak);

    void setProjectSet(ProjectSet projectSet);

    void setProjectSetId(final String projectSetId);

    void setProjectSetId(final int projectSetId);

    void setTreatOvertimeAs(UserSettings.OvertimeType treatOvertimeAs);

    void setTreatOvertimeAs(String treatOvertimeAs);   
}
