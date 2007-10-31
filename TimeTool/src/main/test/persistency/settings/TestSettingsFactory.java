package persistency.settings;

import persistency.XmlUtils;

public class TestSettingsFactory {
  private static transient final String userFirstName = "testUserFirstName";
  private static transient final String userLastName = "testUserLastName";
  private static transient final int employedAt = 1;
  private static transient final int projectSetId = 1;
  private static transient final int lunchBreak = 40;
  private static transient final String overtime = "flex";
  private static transient final String ns = "";
  
  private static TestSettingsFactory factoryInstance;
  private transient XmlUtils xmlUtils;
  
  private TestSettingsFactory() {
    super();
    xmlUtils = XmlUtils.getInstance();
  }
  
  public static TestSettingsFactory getInstance() {
    if (factoryInstance == null) {
      factoryInstance = new TestSettingsFactory();
    }
    return factoryInstance;
  }
  
  public Settings getUserSettings() {
    Settings userSettings = UserSettings.getInstance();
    
    userSettings.setFirstName(userFirstName);
    userSettings.setLastName(userLastName);
    userSettings.setEmployedAt(employedAt);
    userSettings.setProjectSetId(projectSetId);
    userSettings.setLunchBreak(lunchBreak);
    userSettings.setTreatOvertimeAs(overtime);
    
    return userSettings;
  }
  
  public String getXmlSettings() {
    final StringBuilder sb = xmlUtils.getHeader(ns + "settings");
    String indent = xmlUtils.indent(1);
    
    sb.append(indent + "<" + ns + "userName first=\"" + userFirstName + 
                                        "\" last=\"" + userLastName + "\"/>\n");
    sb.append(indent + "<" + ns + "employedAt id=\"" + employedAt + "\"/>\n");
    sb.append(indent + "<" + ns + "projectSet id=\"" + projectSetId + "\"/>\n");
    sb.append(indent + "<" + ns + "lunchBreak duration=\"" + lunchBreak  + 
              "\"/>\n");
    sb.append(indent + "<" + ns + "overtime treatAs=\"" + overtime  + "\"/>\n");
    
    indent = xmlUtils.decIndent(indent);
    
    sb.append(indent + "</" + ns + "settings>");
    return sb.toString();
  }
}