package persistency.projects;

import java.util.ArrayList;
import java.util.List;

public class Projects {
  List<Company> companies;

  public List<Company> getCompanies() {
    if (companies == null) {
        companies = new ArrayList<Company>();
    }
    return companies;
  }
  
  public void addCompany(final Company company) {
    if (companies == null) {
      companies = new ArrayList<Company>();
    }
    companies.add(company);
  }
}
