package persistency.projects;

import java.util.ArrayList;
import java.util.List;

public class Company {
  private int id;
  private String name;
  private String shortName;
  private String employeeId;
  private List<Project> projects;
  
  public int getId() {
    return id;
  }  
  
  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }
  
  public String getEmployeeId() {
    return employeeId;
  }

  public List<Project> getProjects() {
    if (projects == null) {
      projects = new ArrayList<Project>();
    }
    return projects;
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

  public void setEmployeeId(final String employeeId) {
    this.employeeId = employeeId;
  }
  
  public void addProject(final Project project) {
    if (projects == null) {
      projects = new ArrayList<Project>();
    }
    projects.add(project);
  }
}