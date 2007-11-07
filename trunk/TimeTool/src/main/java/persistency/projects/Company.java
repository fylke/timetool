package persistency.projects;

import gui.MyComboBoxDisplayable;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Company implements MyComboBoxDisplayable {
  private String name;
  private String shortName;
  private String employeeId;
  private Map<Integer, Project> projects;
  
  public Company() {
    projects = new TreeMap<Integer, Project>();
  }
  
  public String getLongDispString() {
    return name;
  }
  
  public String getShortDispString() {
    return shortName;
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

  public Project getProject(final int id) {
    return projects.get(id);
  }
  
  public Project getProjectByName(final String name) {
    for (Project proj : projects.values()) {
      if (name.equals(proj.getName())) {
        return proj;
      }
    }
    return null;
  }
  
  public Collection<Project> getProjects() {
    return projects.values();
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
    addProjectWithId(project, project.getId());
  }
  
  public void addProjectWithId(final Project project, final int id) {
    projects.put(id, project);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final StringBuilder objRep = new StringBuilder();
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
  public boolean equals(final Object obj) {
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