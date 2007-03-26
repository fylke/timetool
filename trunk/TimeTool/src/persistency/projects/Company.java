package persistency.projects;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import persistency.ItemAlreadyDefinedException;

public class Company {
  private String name;
  private String shortName;
  private String employeeId;
  private Map<Integer, Project> projects;
  
  public Company() {
    projects = new TreeMap<Integer, Project>();
  }
  
  public int getId() {
    return hashCode();
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

  public Collection<Project> getProjects() {
    return projects.values();
  }
 
  public void setName(final String name) {
    if (name == null || name == "") {
      throw new IllegalArgumentException("Name cannot be empty!");
    } else {
      this.name = name;
    }
  }

  public void setShortName(final String shortName) {
    if (shortName == null) {
      this.shortName = "";
    } else {
      this.shortName = shortName;
    }
  }

  public void setEmployeeId(final String employeeId) {
    if (employeeId == null || employeeId == "") {
      throw new IllegalArgumentException("Employee id cannot be empty!");
    } else {
      this.employeeId = employeeId;
    }
  }
  
  public void addProject(final Project project) 
      throws ItemAlreadyDefinedException {
    if (projects.containsKey(project.hashCode())) {
      throw new ItemAlreadyDefinedException("Project " + project.getName() + 
                                            " is already defined!");
    }
    projects.put(project.getId(), project);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder();
    objRep.append("id: " + hashCode() + "\n");
    objRep.append("name: " + name + "\n");
    objRep.append("shortName: " + shortName + "\n");
    objRep.append("employeeId: " + employeeId + "\n");
        
    for (Project project : projects.values()) {
      objRep.append("Projects:\n");
      objRep.append(project.toString());
    }
    
    return objRep.toString().trim();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((employeeId == null) ? 0 : employeeId.hashCode());
    result = PRIME * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Company other = (Company) obj;
    if (employeeId == null) {
      if (other.employeeId != null)
        return false;
    } else if (!employeeId.equals(other.employeeId))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}