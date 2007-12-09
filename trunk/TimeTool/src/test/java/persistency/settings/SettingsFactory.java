package persistency.settings;

import persistency.XmlUtils;

public class SettingsFactory {
  private static final String USER_FIRST_NAME = "testUserFirstName";
  private static final String USER_LAST_NAME = "testUserLastName";
  private static final int EMPLOYED_AT = 1;
  private static final int PROJ_SET_ID = 1;
  private static final int LUNCH_BREAK = 40;
  private static final String OVERTIME_TYPE = "flex";
  private static final String NS = "";

  public UserSettings getSettings() {
    final UserSettings userSettings = new UserSettings();

    userSettings.setFirstName(USER_FIRST_NAME);
    userSettings.setLastName(USER_LAST_NAME);
    userSettings.setEmployerId(EMPLOYED_AT);
    userSettings.setProjectSetId(PROJ_SET_ID);
    userSettings.setLunchBreak(LUNCH_BREAK);
    userSettings.setTreatOvertimeAs(OVERTIME_TYPE);

    return userSettings;
  }

  public String getXmlSettings() {
  	final XmlUtils xmlUtils = new XmlUtils();

    final StringBuilder sb = xmlUtils.getHeader(NS + "settings");
    String indent = xmlUtils.indent(1);

    sb.append(indent + "<" + NS + "userName first=\"" + USER_FIRST_NAME +
                                        "\" last=\"" + USER_LAST_NAME + "\"/>\n");
    sb.append(indent + "<" + NS + "employedAt id=\"" + EMPLOYED_AT + "\"/>\n");
    sb.append(indent + "<" + NS + "projectSet id=\"" + PROJ_SET_ID + "\"/>\n");
    sb.append(indent + "<" + NS + "lunchBreak duration=\"" + LUNCH_BREAK  + "\"/>\n");
    sb.append(indent + "<" + NS + "overtime treatAs=\"" + OVERTIME_TYPE  + "\"/>\n");

    indent = xmlUtils.decIndent(indent);

    sb.append(indent + "</" + NS + "settings>");
    return sb.toString();
  }
}