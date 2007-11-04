package persistency.year;


import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.ReadableDuration;

import persistency.XmlUtils;
import persistency.settings.UserSettings.OvertimeType;


public class TestYearFactory {
  public static transient final String startTime = "9:00";
  public static transient final int startHour = 9;
  public static transient final int startMinute = 0;
  public static transient final String endTime = "17:00";
  public static transient final int endHour = 17;
  public static transient final int endMinute = 0;
  public static transient final int lunchLength = 40;
  
  private static transient final int hours = 0;
  private static transient final int minutes = 0;
  private static transient final int seconds = 0;
  private static transient final int millis = 0;
  
  private static transient final String ns = "";
  
  private static TestYearFactory factoryInstance;
  private transient XmlUtils xmlUtils;
  
  private TestYearFactory() {
    super();
    xmlUtils = XmlUtils.getInstance();
  }
  
  public static TestYearFactory getInstance() {
    if (factoryInstance == null) {
      factoryInstance = new TestYearFactory();
    }
    return factoryInstance;
  }
  
  public Year getYearWithConfig(final YearConfig yearConfig) {
    Year myYear = new Year();
    myYear.setId(yearConfig.year);
    
    for (short i = 1; i <= yearConfig.nrOfMonths; i++) {
      myYear.addMonth(getMonth(i, yearConfig));
    }
    
    return myYear;
  }
  
  public Month getMonth(final short month, final YearConfig yearConfig) {
    Month myMonth = new Month(month, yearConfig.year);
    
    for (short day = 1; day <= yearConfig.nrOfDaysEachMonth; day++) {
      if (yearConfig.searchControl == null ||
          (yearConfig.searchControl != null &&
          yearConfig.searchControl.isHit(new DateTime(yearConfig.year, month,
                                                      day, hours, minutes,
                                                      seconds, millis)))) {
        myMonth.addWorkDay(getWorkDay(yearConfig, month, day));
      }
    }
    
    return myMonth;
  }
  
  public WorkDay getWorkDay(final YearConfig yearConfig, final short month,
                            final short dateInMonth) {  
    WorkDay workDay = new WorkDay(yearConfig.year, month, dateInMonth);
    
    workDay.setStartTime(new LocalTime(startHour, startMinute));
    workDay.setEndTime(new LocalTime(endHour, endMinute));
    
    ReadableDuration actLength = null;
    if (yearConfig.nrOfActsEachDay > 0) {
      actLength = new Duration(workDay.getDuration().getMillis() / 
                               yearConfig.nrOfActsEachDay);
    } else {
      actLength = new Duration(workDay.getStartTime(), workDay.getEndTime());
    }
    DateTime actStartTime = workDay.getStartTime().toDateTime();
    DateTime actEndTime = actStartTime.plus(actLength);
    
    workDay.setTreatOvertimeAs(OvertimeType.FLEX);
    workDay.isReported = true;
    workDay.journalWritten = true;
    
    ActivityInfo actInfo;
    for (int i = 0; i < yearConfig.nrOfActsEachDay; i++) {
      actInfo = new ActivityInfo(i);
      actInfo.setActivityStartTime(workDay.getDate(), 
                                   actStartTime.toLocalTime());
      actInfo.setActivityEndTime(workDay.getDate(), 
                                 actEndTime.toLocalTime());
      actInfo.includeLunch = true;
      actInfo.setLunchLenght(lunchLength);
      workDay.addActivity(actInfo);
      
      actStartTime = actEndTime;
      actEndTime = actStartTime.plus(actLength);
    }
    
    return workDay;
  }
  
  public String getXmlYearWithConfig(final YearConfig yearConfig) {
    final StringBuilder sb = 
      xmlUtils.getHeader(ns + "year", "id=\"" + yearConfig.year + "\"");
    
    for (short i = 1; i <= yearConfig.nrOfMonths ; i++) {
      getXmlMonth(i, yearConfig, sb);
    }
    
    return sb.toString() + "</" + ns + "year>"; 
  }
  
  public String getXmlMonth(final short month, final YearConfig yearConfig,
                            final StringBuilder sb) {
    String indent = xmlUtils.indent(1);

    sb.append(indent + "<" + ns + "month id=\"" + month + "\">\n");
    indent = xmlUtils.incIndent(indent);
    
    for (short i = 1; i <= yearConfig.nrOfDaysEachMonth; i++) {
      getXmlWorkDay(i, month, yearConfig, sb);
    }
    
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</" + ns + "month>\n");
    
    return sb.toString();
  }
  
  public void getXmlWorkDay(final short dateInMonth, final short month,
                            final YearConfig yearConfig,
                            final StringBuilder sb) {  
    String indent = xmlUtils.indent(2);
    WorkDay workDay = new WorkDay(yearConfig.year, month, dateInMonth);
    
    workDay.setStartTime(new LocalTime(startHour, startMinute));
    workDay.setEndTime(new LocalTime(endHour, endMinute));
    
    ReadableDuration actLength = null;
    if (yearConfig.nrOfActsEachDay > 0) {
      actLength = new Duration(workDay.getDuration().getMillis() / 
                               yearConfig.nrOfActsEachDay);
    } else {
      actLength = new Duration(workDay.getStartTime(), workDay.getEndTime());
    }

    sb.append(indent + "<" + ns + "workDay date=\"" + dateInMonth + "\">\n");
    indent = xmlUtils.incIndent(indent);
    
    String workStartTime = workDay.getStartTime().toString("kk:mm");
    String workEndTime = workDay.getEndTime().toString("kk:mm");
    
    sb.append(indent + "<" + ns + "duration start=\"" + workStartTime + 
                                        "\" end=\"" + workEndTime + "\"/>\n");
    sb.append(indent + "<" + ns + "overtime treatAs=\"flex\"/>\n");
    sb.append(indent + "<" + ns + "isReported/>\n");
    sb.append(indent + "<" + ns + "journalWritten/>\n");
    
    DateTime actStartTime = workDay.getStartTime().toDateTime();
    DateTime actEndTime = actStartTime.plus(actLength);
    
    for (int i = 0; i < yearConfig.nrOfActsEachDay; i++) {
      sb.append(indent + "<" + ns + "activity id=\"" + i + "\">\n");
      indent = xmlUtils.incIndent(indent);
                  
      sb.append(indent + "<" + ns + 
                "duration start=\"" + actStartTime.toString("kk:mm") + 
                      "\" end=\"" + actEndTime.toString("kk:mm") + "\"/>\n");
      sb.append(indent + "<" + ns + "includeLunch duration=\"" + 
                lunchLength + "\"/>\n");
      indent = xmlUtils.decIndent(indent);
      sb.append(indent + "</" + ns + "activity>\n");
      
      actStartTime = actEndTime;
      actEndTime = actStartTime.plus(actLength);
    }
    
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</" + ns + "workDay>\n");
  }
}