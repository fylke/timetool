package persistency;

public class PersistencyException extends Exception {
  private static final long serialVersionUID = 1L;

  public PersistencyException(final String message) {
    super(message);
  }

  public PersistencyException(final Exception cause) {
    super(cause);
  }

  public PersistencyException(final String message, final Exception cause) {
    super(message, cause);
  }
}
