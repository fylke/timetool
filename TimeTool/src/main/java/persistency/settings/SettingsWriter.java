package persistency.settings;

import java.io.OutputStream;
import java.io.PrintWriter;

import persistency.XmlUtils;


public class SettingsWriter {
  private static final String ns = "";
  
  public void writeUserSettings(final Settings userSettings, 
                            final OutputStream settingsStream) 
  {
  	final XmlUtils xmlUtils = new XmlUtils();
    String indent = xmlUtils.indent(0);
    StringBuilder sb = xmlUtils.getHeader("settings");
    indent = xmlUtils.incIndent(indent);

    sb.append(indent + "<" + ns + "userName first=\"" + userSettings.getFirstName() + 
                               "\" last=\"" + userSettings.getLastName() +
                               "\"/>\n");
    sb.append(indent + "<" + ns + "employedAt id=\"" + userSettings.getEmployerId() + 
                       "\"/>\n");
    sb.append(indent + "<" + ns + "projectSet id=\"" + userSettings.getProjectSetId() + 
                       "\"/>\n");
    sb.append(indent + "<" + ns + "lunchBreak duration=\"" + userSettings.getLunchBreak() + 
                       "\"/>\n");
    sb.append(indent + "<" + ns + "overtime treatAs=\"" + userSettings.getTreatOvertimeAs() + 
                       "\"/>\n");
    
    indent = xmlUtils.decIndent(indent);   
    sb.append("</" + ns + "settings>");
    PrintWriter writer = new PrintWriter(settingsStream);
    writer.print(sb);
    writer.flush();
  }
}