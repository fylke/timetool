package persistency.settings;

import persistency.XmlUtils;

public class SettingsFactory {
	static final String USER_FIRST_NAME = "testUserFirstName";
	static final String USER_LAST_NAME = "testUserLastName";
	static final int EMPLOYED_AT = 1;
	static final int LUNCH_BREAK = 40;
	static final String OVERTIME_TYPE = "flex";
	static final String NS = "";

	public UserSettings getSettings() {
		final UserSettings userSettings = new UserSettings();

		userSettings.setFirstName(USER_FIRST_NAME);
		userSettings.setLastName(USER_LAST_NAME);
		userSettings.setEmployerId(EMPLOYED_AT);
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
		sb.append(indent + "<" + NS + "lunchBreak duration=\"" + LUNCH_BREAK  + "\"/>\n");
		sb.append(indent + "<" + NS + "overtime treatAs=\"" + OVERTIME_TYPE  + "\"/>\n");

		indent = xmlUtils.decIndent(indent);

		sb.append(indent + "</" + NS + "settings>");
		return sb.toString();
	}
}