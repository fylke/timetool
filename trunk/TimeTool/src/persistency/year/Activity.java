package persistency.year;

public class Activity {
  private final int id;
  private String name;
  private String shortName;
  
  public Activity(final int id) {
    super();
    this.id = id;
  }

  /**
   * @return the activity's unique ID
   */
  public int getId() {
    return id;
  }
  
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the shortName
   */
  public String getShortName() {
    return shortName;
  }
  
  /**
   * @param the name of the activity to set
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * @param shortName the shortName to set
   */
  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }
}