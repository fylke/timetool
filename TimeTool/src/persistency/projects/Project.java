package persistency.projects;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


public class Project {
  private int id;
  private String name;
  private String shortName;
  private String code;
  private Map<Integer, Activity> activities;
  private Map<Integer, Project> subProjects;
  
  public Project() {
    activities = new TreeMap<Integer, Activity>();
    subProjects = new TreeMap<Integer, Project>();
  }
  
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
   * @return the activities in this project
   */
  public Collection<Activity> getActivities() {
    return activities.values();
  }
  
  /**
   * 
   * @return the subprojects to this project
   */
  public Collection<Project> getSubProjects() {
    return subProjects.values();
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
    activities.put(activity.getId(), activity);
  }
  
  public void addSubProject(final Project subProject) {
    subProjects.put(subProject.getId(), subProject);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder();
    objRep.append("id: " + id + "\n");
    objRep.append("name: " + name + "\n");
    objRep.append("shortName: " + shortName + "\n");
    objRep.append("code: " + code + "\n");
        
    for (Project subProject : subProjects.values()) {
      objRep.append("Subprojects:\n");
      objRep.append(subProject.toString());
    }

    for (Activity activity : activities.values()) {
      objRep.append("Activities:\n");
      objRep.append(activity.toString());
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
    result = PRIME * result + ((activities == null) ? 0 : activities.hashCode());
    result = PRIME * result + ((code == null) ? 0 : code.hashCode());
    result = PRIME * result + id;
    result = PRIME * result + ((name == null) ? 0 : name.hashCode());
    result = PRIME * result + ((shortName == null) ? 0 : shortName.hashCode());
    result = PRIME * result + ((subProjects == null) ? 0 : subProjects.hashCode());
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
    final Project other = (Project) obj;
    if (activities == null) {
      if (other.activities != null)
        return false;
    } else if (!activities.equals(other.activities))
      return false;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (shortName == null) {
      if (other.shortName != null)
        return false;
    } else if (!shortName.equals(other.shortName))
      return false;
    if (subProjects == null) {
      if (other.subProjects != null)
        return false;
    } else if (!subProjects.equals(other.subProjects))
      return false;
    return true;
  }
}