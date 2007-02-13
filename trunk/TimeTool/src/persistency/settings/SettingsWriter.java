package persistency.settings;

import java.io.OutputStream;
import java.io.PrintWriter;

import persistency.XmlUtils;


public class SettingsWriter {
  private transient XmlUtils xmlUtils = XmlUtils.getInstance();
  private static transient final String ns = "";
  
  public void writeSettings(final Settings settings, 
                            final OutputStream settingsStream) 
  {
    String indent = xmlUtils.indent(0);
    StringBuilder sb = xmlUtils.getHeader("settings");
    indent = xmlUtils.incIndent(indent);

    sb.append(indent + "<" + ns + "userName first=\"" + settings.getUserFirstName() + 
                               "\" last=\"" + settings.getUserLastName() +
                               "\"/>\n");
    sb.append(indent + "<" + ns + "employedAt id=\"" + settings.getEmployerId() + 
                       "\"/>\n");
    sb.append(indent + "<" + ns + "projectSet id=\"" + settings.getProjectSetId() + 
                       "\"/>\n");
    sb.append(indent + "<" + ns + "lunchBreak duration=\"" + settings.getLunchBreak() + 
                       "\"/>\n");
    sb.append(indent + "<" + ns + "overtime treatAs=\"" + settings.getTreatOvertimeAs() + 
                       "\"/>\n");
    
    indent = xmlUtils.decIndent(indent);   
    sb.append("</" + ns + "settings>");
    PrintWriter writer = new PrintWriter(settingsStream);
    writer.print(sb);
    writer.flush();
  }
}