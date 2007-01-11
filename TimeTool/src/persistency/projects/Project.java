package persistency.projects;

import java.util.ArrayList;
import java.util.List;

import persistency.year.Activity;

public class Project {
  private int id;
  private String name;
  private String shortName;
  private String code;
  private List<Activity> activities;
  private List<Project> subProjects;
  
  public int getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public String getCode() {
    return code;
  }

  /**
   * 
   * @return a list of the activities in this project, null if there are none
   */
  public List<Activity> getActivities() {
    return activities;
  }
  
  /**
   * 
   * @return a list of the subprojects to this project, null if there are none
   */
  public List<Project> getSubProjects() {
    return subProjects;
  }
  
  public void setId(final int id) {
    this.id = id;
  }
  
  public void setName(final String name) {
    this.name = name;
  }

  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public void addActivity(final Activity activity) {
    if (activities == null) {
      activities = new ArrayList<Activity>();
    }
    activities.add(activity);
  }
  
  public void addSubProject(final Project subProject) {
    if (subProjects == null) {
      subProjects = new ArrayList<Project>();
    }
    subProjects.add(subProject);
  }
}