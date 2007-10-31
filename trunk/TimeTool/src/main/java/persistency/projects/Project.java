package persistency.projects;

import gui.MyComboBoxDisplayable;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import persistency.ItemAlreadyDefinedException;


public class Project implements MyComboBoxDisplayable {
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

  public String getLongDispString() {
    return name;
  }
  
  public String getShortDispString() {
    return shortName;
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
    
  /**
   * @param id the id to set
   */
  public void setId(final int id) {
    this.id = id;
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

  public void setCode(final String code) {
    if (code == null || code == "") {
      throw new IllegalArgumentException("Project report code cannot be empty!");
    } else {
      this.code = code;
    }
  }

  //FIXME Maybe throw illegal arg on null.
  public void addActivity(final Activity activity) 
      throws ItemAlreadyDefinedException {
    if (activities.containsKey(activity.hashCode())) {
      throw new ItemAlreadyDefinedException("Activity " + activity.getName() + 
                                            " is already defined!");
    }
    activities.put(activity.getId(), activity);
  }
  
  //FIXME Maybe throw illegal arg on null.
  public void addSubProject(final Project subProject)
      throws ItemAlreadyDefinedException {
    if (subProjects.containsKey(subProject.hashCode())) {
      throw new ItemAlreadyDefinedException("Sub project " + 
                                            subProject.getName() + 
                                            " is already defined!");
    }
    subProjects.put(subProject.getId(), subProject);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder();
    objRep.append("projId: " + id + "\n");
    objRep.append("name: " + name + "\n");
    objRep.append("shortName: " + shortName + "\n");
    objRep.append("code: " + code + "\n");
        
    for (Project subProject : subProjects.values()) {
      objRep.append("Subprojects for " + name + ":\n");
      objRep.append(subProject.toString() + "\n");
    }

    for (Activity activity : activities.values()) {
      objRep.append("Activities for " + name + ":\n");
      objRep.append(activity.toString() + "\n");
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
    result = PRIME * result + ((code == null) ? 0 : code.hashCode());
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
    final Project other = (Project) obj;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}