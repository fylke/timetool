package persistency.year;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.joda.time.DurationFieldType;

import persistency.XmlUtils;

public class YearWriter {
  private transient XmlUtils xmlUtils = XmlUtils.getInstance();
  private static transient final String ns = "";
  
  public void writeYear(final Year year, final OutputStream yearStream) 
  {
    String indent = xmlUtils.indent(0);
    final StringBuilder sb = 
      xmlUtils.getHeader(ns + "year", "id=\"" + year.getId() + "\"");
    indent = xmlUtils.incIndent(indent);

    for (Month month : year.getAllMonths()) {
      sb.append(indent + "<" + ns + "month id=\"" + month.getId() + "\">\n");
      indent = xmlUtils.incIndent(indent);

      for (WorkDay workDay : month.getAllWorkDays()) {
        sb.append(indent + "<" + ns + "workDay date=\"" + 
                  workDay.getDate().toString("d") + "\">\n");
        indent = xmlUtils.incIndent(indent);
      
        sb.append(indent + "<" + ns + "duration start=\"" + 
                  workDay.getStartTime().toString("kk:mm") + "\" end=\"" + 
                  workDay.getEndTime().toString("kk:mm") + "\"/>\n");
        
        sb.append(indent + "<" + ns + "overtime treatAs=\"" + 
                           workDay.getTreatOvertimeAs() + "\"/>\n");
        
        if (workDay.isReported) {
          sb.append(indent + "<" + ns + "isReported/>\n");
        }
        if (workDay.journalWritten) {
          sb.append(indent + "<" + ns + "journalWritten/>\n");
        }
        
        for (ActivityInfo actInfo : workDay.getAllActivities()) {
          sb.append(indent + "<" + ns + "activity id=\"" + actInfo.getId() + "\">\n");
          
          indent = xmlUtils.incIndent(indent);
          sb.append(indent + "<" + ns + "duration start=\"" + 
                    actInfo.getStartTime().toString("kk:mm") + "\" end=\"" + 
                    actInfo.getEndTime().toString("kk:mm") + "\"/>\n");
          if (actInfo.includeLunch) {
            sb.append(indent + "<" + ns + "includeLunch duration=\"" + 
                      actInfo.getLunchLenght().get(DurationFieldType.minutes()) + 
                      "\"/>\n");
          }
          
          indent = xmlUtils.decIndent(indent);
          sb.append(indent + "</" + ns + "activity>\n");
        }
        
        indent = xmlUtils.decIndent(indent);
        sb.append(indent + "</" + ns + "workDay>\n");
      }
      
      indent = xmlUtils.decIndent(indent);
      sb.append(indent + "</" + ns + "month>\n");
    }
    
    sb.append("</" + ns + "year>");
    PrintWriter writer = new PrintWriter(yearStream);
    writer.print(sb);
    writer.flush();
  }
}