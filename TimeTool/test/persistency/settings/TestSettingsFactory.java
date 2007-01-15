package persistency.settings;

import logic.Settings;
import logic.Settings.OvertimeType;
import persistency.XmlUtils;

public class TestSettingsFactory {
  public static transient final String userFirstName = "testUserFirstName";
  public static transient final String userLastName = "testUserLastName";
  public static transient final int employedAt = 1;
  public static transient final int projectSetId = 1;
  public static transient final int lunchBreak = 40;
  public static transient final OvertimeType treatOvertimeAs = 
    OvertimeType.FLEX;
  
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
  
  public Settings getSettings() {
    Settings settings = new Settings();
    
    settings.setUserFirstName(userFirstName);
    settings.setUserLastName(userLastName);
    settings.setEmployedAt(employedAt);
    settings.setProjectSetId(projectSetId);
    settings.setLunchBreak(lunchBreak);
    settings.setTreatOvertimeAs(treatOvertimeAs);
    
    return settings;
  }
  
  public String getXmlSettings() {
    final StringBuilder sb = xmlUtils.getHeader("settings");
    String indent = xmlUtils.indent(1);
    
    sb.append(indent + "<userName first=\"" + userFirstName + 
                               "\" last=\"" + userLastName + "\"/>\n");
    sb.append(indent + "<employedAt id=\"" + employedAt + "\"/>\n");
    sb.append(indent + "<projectSet id=\"" + projectSetId + "\"/>\n");
    sb.append(indent + "<lunchBreak duration=\"" + lunchBreak  + "\"/>\n");
    sb.append(indent + "<overtime treatAs=\"" + treatOvertimeAs  + "\"/>\n");
    indent = xmlUtils.decIndent(indent);
    
    sb.append(indent + "</settings>");
    return sb.toString();
  }
}