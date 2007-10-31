package persistency.projects;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import persistency.ItemAlreadyDefinedException;

public class ProjectSet {
  private Map<Integer, Company> companies;
  
  public ProjectSet() {
    companies = new TreeMap<Integer, Company>();
  }

  /**
   * Gets the company specified by the supplied ID.
   * @param id
   * @return the requested company, null if not found
   */
  public Company getCompany(final int id) {
    return companies.get(id);
  }
  
  public Company getCompanyByName(final String name) {
    for (Company comp : companies.values()) {
      if (comp.getName() == name) {
        return comp;
      }
    }
    return null;
  }
  
  /**
   * Gets all companies.
   * @return all stored companies, null if none stored
   */
  public Collection<Company> getCompanies() {
    return companies.values();
  }

  /**
   * Adds a company to the project set.
   * @param company the company to add
   * @throws ItemAlreadyDefinedException if company already exists in the 
   * project set
   */
  public void addCompany(final Company company) 
      throws ItemAlreadyDefinedException {
    // FIXME HashCode depends on company name which isn't necessarily set!
    if (companies.containsKey(company.hashCode())) {
      throw new ItemAlreadyDefinedException("Company " + company.getName() + 
                                            " is already defined!");
    }
    companies.put(company.hashCode(), company);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder objRep = new StringBuilder();
    objRep.append("id: " + hashCode() + "\n");
        
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
    return true;
  }
}