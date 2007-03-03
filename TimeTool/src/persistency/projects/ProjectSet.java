package persistency.projects;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class ProjectSet {
  private int id;
  private Map<Integer, Company> companies;
  
  public ProjectSet() {
    companies = new TreeMap<Integer, Company>();
  }

  public Company getCompany(final int id) {
    return companies.get(id);
  }
  
  public Collection<Company> getCompanies() {
    return companies.values();
  }
  
  /**
   * @return the ID of the project set
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the ID to set
   */
  public void setId(final int id) {
    this.id = id;
  }

  public void addCompany(final Company company) {
    companies.put(company.getId(), company);
  }
  
  /**
   * Finds an unused ID for a company in this set.
   * TODO Improve this, maybe use a hash of Company name?
   * @return
   */
  public int allocateCompanyId() {
    int suggestion = companies.keySet().size();
    while(companies.keySet().contains(suggestion)) {
      suggestion++;
    }
    return suggestion;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder();
    objRep.append("id: " + id + "\n");
        
    for (Company company : companies.values()) {
      objRep.append("Companies:\n");
      objRep.append(company.toString());
    }
    
    return objRep.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((companies == null) ? 0 : companies.hashCode());
    result = PRIME * result + id;
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
    final ProjectSet other = (ProjectSet) obj;
    if (companies == null) {
      if (other.companies != null)
        return false;
    } else if (!companies.equals(other.companies))
      return false;
    if (id != other.id)
      return false;
    return true;
  }
}