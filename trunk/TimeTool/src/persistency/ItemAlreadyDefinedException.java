package persistency;

public class ItemAlreadyDefinedException extends PersistencyException {
  private static final long serialVersionUID = 1L;

  public ItemAlreadyDefinedException(final String message) {
    super(message);
  }
  
  public ItemAlreadyDefinedException(final Exception cause) {
    super(cause);
  }
  
  public ItemAlreadyDefinedException(final String message, 
                                     final Exception cause) {
    super(message, cause);
  }
}
