package persistency;

import org.joda.time.LocalTime;
import org.joda.time.ReadableDateTime;

public class XmlUtils {
  private static final int indentSize = 2;
  private static final String indentationToken = "  ";

  public ReadableDateTime stringToTime(final String hhmm,
                                       final ReadableDateTime date) {
    final String[] timeTokens = hhmm.split(":");
    return new LocalTime(Integer.parseInt(timeTokens[0]),
                         Integer.parseInt(timeTokens[1])).toDateTime(date);
  }

  public String xmlify(final String stringToXmlify) {
    return stringToXmlify.replace("&", "&amp;").replace("<", "&lt;").replace("'", "&apos;")
                         .replace(">", "&gt;").replace("\"", "&quot;");
  }

  public String indent(final int level) {
    final StringBuilder indentation = new StringBuilder();
    for (int i = level; i > 0; i--) {
      indentation.append(indentationToken);
    }

    return indentation.toString();
  }

  public String incIndent(final String indent) {
    return indent + indentationToken;
  }

  public String decIndent(final String indent) {
    return indent.substring(indentSize, indent.length());
  }

  public StringBuilder getHeader(final String type) {
    final StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
    sb.append("<!DOCTYPE " + type + " SYSTEM \"" + type + ".dtd\">\n");
    sb.append("<" + type + ">\n");

    return sb;
  }

  public StringBuilder getHeader(final String type, final String... attrs) {
    final StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
    sb.append("<!DOCTYPE " + type + " SYSTEM \"" + type + ".dtd\">\n");

    sb.append("<" + type);
    for (final String element : attrs) {
      sb.append(" " + element);
    }
    sb.append(">\n");

    return sb;
  }
}