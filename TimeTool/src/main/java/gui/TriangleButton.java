package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JButton;

public class TriangleButton extends JButton {
  public static final int LEFT = -1;
  public static final int RIGHT = 1;

  private static final long serialVersionUID = 751707230340153858L;
  private final int direction;
  private Polygon button;

  public TriangleButton(final int direction) {
    super();

    this.direction = direction;

    // These statements enlarge the button so that it
    // becomes a circle rather than an oval.
    final Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, size.height);
    setPreferredSize(size);
    buildTriangle(size);

    // This call causes the JButton not to paint the background.
    // This allows us to paint a round background.
    setContentAreaFilled(false);
  }

  protected void buildTriangle(final Dimension size) {
    final int[] x3Points = { 0, size.width/2, size.width/2 };
    final int[] y3Points = { size.height/2, 0, size.height };

    if (direction == RIGHT) {
      x3Points[0] = size.width;
    }

    button = new Polygon(x3Points, y3Points, 3);
  }

  // Paint the round background and label.
  @Override
  protected void paintComponent(final Graphics g) {
    if (getModel().isArmed()) {
      // You might want to make the highlight color
      // a property of the RoundButton class.
      g.setColor(Color.lightGray);
    } else {
      g.setColor(getBackground());
    }

    g.fillPolygon(button);
    super.paintComponent(g);
  }

  // Paint the border of the button using a simple stroke.
  @Override
  protected void paintBorder(final Graphics g) {
    g.setColor(getForeground());
    g.drawPolygon(button);
  }

  @Override
  public boolean contains(final int x, final int y) {
    return button.contains(x, y);
  }
}