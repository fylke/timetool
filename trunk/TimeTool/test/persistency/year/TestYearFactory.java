package persistency.year;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadablePeriod;

import persistency.XmlUtils;


public class TestYearFactory {
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
    
    for (short i = 1; i <= yearConfig.nrOfDaysEachMonth; i++) {
      myMonth.addWorkDay(getWorkDay(yearConfig, month, i));
    }
    
    return myMonth;
  }
  
  public WorkDay getWorkDay(final YearConfig yearConfig, final short month,
                            final short dateInMonth) {  
    WorkDay workDay = new WorkDay(yearConfig.year, month, dateInMonth);
    
    workDay.setStartTime(new LocalTime(9, 0));
    workDay.setEndTime(new LocalTime(17, 0));
    ReadablePeriod actLength = 
      new Period(workDay.getDuration().getMillis() / 
                 yearConfig.nrOfActsEachDay);
    LocalTime actStartTime = new LocalTime(workDay.getStartTime().getMillis());
    
    workDay.isReported = true;
    workDay.journalWritten = true;
    
    ActivityInfo actInfo;
    for (int i = 0; i < yearConfig.nrOfActsEachDay; i++) {
      actInfo = new ActivityInfo(i);
      actInfo.setActivityStartTime(workDay.getDate(), actStartTime);
      actInfo.setActivityEndTime(workDay.getDate(), 
                                 actStartTime.plus(actLength));
      workDay.addActivity(actInfo);
    }
    
    return workDay;
  }
  
  public String getXmlYearWithConfig(final YearConfig yearConfig) {
    final StringBuilder sb = 
      xmlUtils.getHeader("year", "id=\"" + yearConfig.year + "\"");
    
    for (short i = 1; i <= yearConfig.nrOfMonths ; i++) {
      getXmlMonth(i, yearConfig, sb);
    }
    
    return sb.toString() + "</year>"; 
  }
  
  public String getXmlMonth(final short month, final YearConfig yearConfig,
                            final StringBuilder sb) {
    String indent = xmlUtils.indent(1);

    sb.append(indent + "<month id=\"" + month + "\">\n");
    indent = xmlUtils.incIndent(indent);
    
    for (short i = 1; i <= yearConfig.nrOfDaysEachMonth; i++) {
      getXmlWorkDay(i, month, yearConfig, sb);
    }
    
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</month>\n");
    
    return sb.toString();
  }
  
  public void getXmlWorkDay(final short dateInMonth, final short month,
                            final YearConfig yearConfig,
                            final StringBuilder sb) {  
    String indent = xmlUtils.indent(2);
    WorkDay workDay = new WorkDay(yearConfig.year, month, dateInMonth);
    
    workDay.setStartTime(new LocalTime(9, 0));
    workDay.setEndTime(new LocalTime(17, 0));
    ReadableDuration actLength = 
      new Duration(workDay.getDuration().getMillis() / 
                   yearConfig.nrOfActsEachDay);

    sb.append(indent + "<workDay date=\"" + dateInMonth + "\">\n");
    indent = xmlUtils.incIndent(indent);
    
    String workStartTime = workDay.getStartTime().toString("kk:mm");
    String workEndTime = workDay.getEndTime().toString("kk:mm");
    
    sb.append(indent + "<duration start=\"" + workStartTime + 
                                "\" end=\"" + workEndTime + "\"/>\n");
    sb.append(indent + "<isReported/>\n");
    sb.append(indent + "<journalWritten/>\n");
    
    DateTime actStartTime = workDay.getStartTime().toDateTime();
    DateTime actEndTime;
    
    for (int i = 0; i < yearConfig.nrOfActsEachDay; i++) {
      sb.append(indent + "<activity id=\"" + i + "\">\n");
      indent = xmlUtils.incIndent(indent);
      
      actEndTime = actStartTime.plus(actLength);
      
      sb.append(indent + "<duration start=\"" + actStartTime.toString("kk:mm") + 
                                  "\" end=\"" + actEndTime.toString("kk:mm") + 
                                  "\"/>\n");
      indent = xmlUtils.decIndent(indent);
      sb.append(indent + "</activity>\n");
    }
    
    indent = xmlUtils.decIndent(indent);
    sb.append(indent + "</workDay>\n");
  }
}