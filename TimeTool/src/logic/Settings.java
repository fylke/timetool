package logic;

import java.util.ArrayList;
import java.util.List;

import persistency.projects.Company;

public class Settings {
  private static Settings settings;
  
  private String userName;
  private List<Company> companies;
  
  private Settings() {
    companies = new ArrayList<Company>();
  }
  
  public static Settings getSettings() {
    if (settings == null) {
      settings = new Settings();
    }
    return settings;
  }
  
  public List<Company> getCompanies() {
    return companies;
  }

  public String getUserName() {
    return userName;
  }

  public void setCompanies(final List<Company> companies) {
    this.companies = companies;
  }

  public void setUserName(final String userName) {
    this.userName = userName;
  }
}
